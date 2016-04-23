/**
 * Paquet de définition
 **/
package org.github.biconou.lab;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

public class GetStarted {

  static final MetricRegistry metrics = new MetricRegistry();

  static final Queue<MyEvent> eventQueue = new ConcurrentLinkedQueue<MyEvent>();

  public static final String EVENTS_METER_METRIC_NAME = "myEvents";
  public static final String PENDING_EVENTS_METRIC_NAME = "pendingEvents";
  public static final String DELAY_STAT_METRIC_NAME = "delayStat";
  public static final String TREATMENT_TIME_METRIC_NAME = "treatmentTime";


  public static void main(String args[]) {

    metrics.register(MetricRegistry.name(GetStarted.class, PENDING_EVENTS_METRIC_NAME), new Gauge<Integer>() {
      @Override
      public Integer getValue() {
        return eventQueue.size();
      }
    });

    startReport();

    startEventsGeneration();

    startEventsTreatment();
  }

  /**
   *
   */
  private static void startEventsTreatment() {

    Thread threadEventsTreatment = new Thread(new Runnable() {
      @Override
      public void run() {

        Timer timer = metrics.timer(MetricRegistry.name(GetStarted.class, TREATMENT_TIME_METRIC_NAME));

        while (true) {
          Timer.Context tContext = null;
          try {
            // get an event from queue
            MyEvent evt = eventQueue.peek();
            if (evt != null) {
              tContext = timer.time();
              Thread.sleep(evt.delay * 1000);
              metrics.histogram(MetricRegistry.name(GetStarted.class, DELAY_STAT_METRIC_NAME)).update(evt.delay);
              eventQueue.remove();
            }
          }
          catch (InterruptedException e) {
          }
          finally {
            if (tContext != null) {
              tContext.stop();
            }
          }
        }
      }
    });

    threadEventsTreatment.start();
  }

  /**
   *
   */
  private static void startEventsGeneration() {

    Thread threadEventsGeneration = new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          try {
            // wait 1 to 5 seconds then mark an event
            int randomTimeToWait = (int) (Math.random() * (5 - 1 + 1)) + 1;
            Thread.sleep(randomTimeToWait * 1000);

            // Add an event to queue.
            eventQueue.add(MyEvent.randomize());
            Meter meter = metrics.meter(MetricRegistry.name(GetStarted.class, EVENTS_METER_METRIC_NAME));
            meter.mark();
          }
          catch (InterruptedException e) {
          }
        }
      }
    });

    threadEventsGeneration.start();
  }

  /**
   *
   */
  static void startReport() {

    // Reporter console
    ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
      .convertRatesTo(TimeUnit.SECONDS)
      .convertDurationsTo(TimeUnit.MILLISECONDS)
      .build();
    reporter.start(10, TimeUnit.SECONDS);

    // Jmx reporter
    final JmxReporter jmxReporter = JmxReporter.forRegistry(metrics).build();
    jmxReporter.start();
  }

}
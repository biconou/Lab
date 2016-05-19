/**
 * Paquet de définition
 **/
package org.github.biconou.lab;

import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Timer;

public class TestTimer {

  static final MetricRegistry metrics = new MetricRegistry();


  public static void main(String args[]) {
    startReport();
    Timer timer = metrics.timer("TimerToTest");
    while (true) {
      try {
        // wait 1 to 1000 milli seconds then mark an event
        int randomTimeToWait = (int) (Math.random() * (1000 - 1 + 1)) + 1;
        Timer.Context tContext = timer.time();
        Thread.sleep(randomTimeToWait);
        tContext.stop();
      }
      catch (InterruptedException e) {
      }
    }
  }



  /**
   *
   */
  static void startReport() {

    // Reporter console
    /* ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
      .convertRatesTo(TimeUnit.SECONDS)
      .convertDurationsTo(TimeUnit.MILLISECONDS)
      .build();
    reporter.start(10, TimeUnit.SECONDS);
    */


    ScheduledReporter maxChecker = new ScheduledReporter(metrics, "maxChecker", new MetricFilter() {
      @Override
      public boolean matches(String name, Metric metric) {
        return true;
      }
    }, TimeUnit.SECONDS, TimeUnit.MILLISECONDS) {
      private long max = 0;
      @Override
      public void report(SortedMap<String, Gauge> gauges, SortedMap<String, Counter> counters, SortedMap<String, Histogram> histograms, SortedMap<String, Meter> meters, SortedMap<String, Timer> timers) {

        long currentMax = timers.get("TimerToTest").getSnapshot().getMax();
        if (currentMax < max) {
          Meter meter = metrics.meter("maxCheckerFail");
          meter.mark();
        } else {
          Meter meter = metrics.meter("maxCheckerOk");
          meter.mark();
        }
        max = currentMax;
      }
    };
    maxChecker.start(10, TimeUnit.SECONDS);

    // Jmx reporter
    final JmxReporter jmxReporter = JmxReporter.forRegistry(metrics).build();
    jmxReporter.start();
  }

}
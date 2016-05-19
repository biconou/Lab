/**
 * Paquet de définition
 **/
package org.github.biconou.lab;

import java.util.concurrent.TimeUnit;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
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
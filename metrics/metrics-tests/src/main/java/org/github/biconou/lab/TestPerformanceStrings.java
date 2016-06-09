/**
 * Paquet de définition
 **/
package org.github.biconou.lab;

import java.util.concurrent.TimeUnit;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

public class TestPerformanceStrings {

  static final MetricRegistry metrics = new MetricRegistry();

  static final String truc1 = "togotogotogot "+
    "jsdfjfmksqdjfmqskjf "+
    "fsdksjdfkjsdflksjd ";

  static final String truc2 = " jsldkfjsldfjsd f l"+
    "  sdfkjsdfkj   dfslkdjfsldkfj   ";

  static final String truc3 = truc1 + "${toto}" + truc2;

  /**
   *
   */
  private static String test1(String toto) {
    String tata = "togotogotogot "+
      "jsdfjfmksqdjfmqskjf "+
      "fsdksjdfkjsdflksjd "+toto+" jsldkfjsldfjsd f l"+
      "  sdfkjsdfkj   dfslkdjfsldkfj   ";

    return tata;
  }

  /**
   *
   */
  private static String test2(String toto) {
    String tata = truc1 + toto + truc2;
    return tata;
  }

  /**
   *
   */
  private static String test3(String toto) {
    String tata = truc3.replace("${toto}",toto);
    return tata;
  }

  private static String test4(String toto) {
    StringBuilder tata = new StringBuilder("togotogotogot "+
      "jsdfjfmksqdjfmqskjf ")
      .append("fsdksjdfkjsdflksjd ").append(toto).append(" jsldkfjsldfjsd f l")
      .append("  sdfkjsdfkj   dfslkdjfsldkfj   ");

    return tata.toString();
  }

  /**
   *
   * @param args
   */
  public static void main(String args[]) {

    int max = 10000000;
    int i = 0;
    //
    Timer timer1 = metrics.timer("test1");
    i = 0;
    Timer.Context context1 = timer1.time();
    for (i=0; i <max; i++) {
      test1("toto");
    }
    context1.stop();
    //


    //
    Timer timer2 = metrics.timer("test2");
    i = 0;
    Timer.Context context2 = timer2.time();
    for (i=0; i <max; i++) {
      test2("toto");
    }
    context2.stop();
    //

    //
    Timer timer3 = metrics.timer("test3");
    i = 0;
    Timer.Context context3 = timer3.time();
    for (i=0; i <max; i++) {
      test3("toto");
    }
    context3.stop();
    //

    //
    Timer timer4 = metrics.timer("test4");
    i = 0;
    Timer.Context context4 = timer4.time();
    for (i=0; i <max; i++) {
      test4("toto");
    }
    context4.stop();
    //


    ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
      .convertRatesTo(TimeUnit.SECONDS)
      .convertDurationsTo(TimeUnit.MILLISECONDS)
      .build();

    reporter.report();

  }
}
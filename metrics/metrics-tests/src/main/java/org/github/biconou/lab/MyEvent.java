/**
 * Paquet de définition
 **/
package org.github.biconou.lab;

import java.util.Random;

public class MyEvent {

  public int delay;


  public static MyEvent randomize() {
    MyEvent evt = new MyEvent();

    // generate a delay between 1 and 5 seconds
    Random r = new Random();
    evt.delay = r.nextInt(5) + 1;

    return evt;
  }
}
 

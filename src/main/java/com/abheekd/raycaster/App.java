package com.abheekd.raycaster;

import com.abheekd.raycaster.gfx.RenderWindow;
import com.abheekd.raycaster.gfx.Window;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {
    // System.out.println( "Hello World!" );
    Window window = new Window();
    RenderWindow renderWindow =
        new RenderWindow(window.getPanel().getRaySource());
  }
}

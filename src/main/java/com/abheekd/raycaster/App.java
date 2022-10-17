package com.abheekd.raycaster;

import com.abheekd.raycaster.gfx.RenderWindow;
import com.abheekd.raycaster.gfx.TDVisualizerWindow;

public class App {
  public static void main(String[] args) {
    // System.out.println( "Hello World!" );
    TDVisualizerWindow window = new TDVisualizerWindow();
    RenderWindow renderWindow =
        new RenderWindow(window);
    // RenderWindow rWin = new RenderWindow();
  }
}

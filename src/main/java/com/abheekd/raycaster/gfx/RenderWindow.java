package com.abheekd.raycaster.gfx;

import com.abheekd.raycaster.objects.RaySource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderWindow extends JFrame {
  private class Panel extends com.abheekd.raycaster.gfx.Panel implements KeyListener {
    public Panel(RaySource r) {
      super(r);
      this.setFocusable(true);
      if (!RenderWindow.this.getFromTwoD()) {
        this.addKeyListener(this);
      }
    }

    // paint the render window
    @Override
    public void paintComponent(Graphics g) {
      // move the resource based on current keys pressed
      raySource.move(keys, (double)super.getWidth() / 10000,
              (double)super.getHeight() / 10000);
      super.paintComponent(g);
      // if it's without a two d
      if (!RenderWindow.this.getFromTwoD()) {
        raySource.calculateDistances();
      }
      // draw the raySource visualisation
      raySource.renderDraw(g, 500, 500, 5000);

      // repaint to paint every frame
      repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // key listener
    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_W)
        keys[0] = true;
      if (e.getKeyCode() == KeyEvent.VK_S)
        keys[1] = true;
      if (e.getKeyCode() == KeyEvent.VK_A)
        keys[2] = true;
      if (e.getKeyCode() == KeyEvent.VK_D)
        keys[3] = true;
    }

    // released key
    @Override
    public void keyReleased(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_W)
        keys[0] = false;
      if (e.getKeyCode() == KeyEvent.VK_S)
        keys[1] = false;
      if (e.getKeyCode() == KeyEvent.VK_A)
        keys[2] = false;
      if (e.getKeyCode() == KeyEvent.VK_D)
        keys[3] = false;
    }

  }

  private final Panel panel;
  // determines whether it gets data from the two d window or independently
  private boolean fromTwoD;

  public RenderWindow() {
    this(new RaySource());
  }

  // main constructor
  public RenderWindow(RaySource r) {
    this.panel = new Panel(r);

    // jframe stuff
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // this.setUndecorated(true);

    this.add(panel);
    this.pack();

    this.setSize(500, 500);
    // this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setResizable(false);
  }

  // constructor if it's coupled with a two d window
  public RenderWindow(TDVisualizerWindow w) {
    this(w.getPanel().getRaySource());
    this.fromTwoD = true;
  }

  // whether or not it's based on a two d window
  public boolean getFromTwoD() {
    return this.fromTwoD;
  }
}

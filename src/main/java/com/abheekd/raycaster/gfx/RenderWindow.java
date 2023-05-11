package com.abheekd.raycaster.gfx;

import com.abheekd.raycaster.objects.RaySource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderWindow extends JFrame {
  private class Panel extends JPanel implements KeyListener {
    private final RaySource raySource;
    private boolean[] keys; // todo: convert to set

    public Panel(RaySource r) {
      this.raySource = r;
      this.keys = new boolean[4];
      this.setFocusable(true);
      if (!RenderWindow.this.getFromTwoD()) {
        this.addKeyListener(this);
      }
    }

    @Override
    public void paintComponent(Graphics g) {
      raySource.move(keys, (double)super.getWidth() / 10000,
              (double)super.getHeight() / 10000);
      super.paintComponent(g);
      if (!RenderWindow.this.getFromTwoD()) {
        raySource.calculateDistances();
      }
      raySource.renderDraw(g, 500, 500, 5000);

      repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

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
  private boolean fromTwoD;

  public RenderWindow() {
    this(new RaySource());
  }

  public RenderWindow(RaySource r) {
    this.panel = new Panel(r);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // this.setUndecorated(true);

    this.add(panel);
    this.pack();

    this.setSize(500, 500);
    // this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setResizable(false);
  }

  public RenderWindow(TDVisualizerWindow w) {
    this(w.getPanel().getRaySource());
    this.fromTwoD = true;
  }

  public boolean getFromTwoD() {
    return this.fromTwoD;
  }
}

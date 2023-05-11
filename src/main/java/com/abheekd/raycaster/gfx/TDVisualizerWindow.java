package com.abheekd.raycaster.gfx;

import com.abheekd.raycaster.objects.Map;
import com.abheekd.raycaster.objects.RaySource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TDVisualizerWindow extends JFrame {
  public static class Panel extends com.abheekd.raycaster.gfx.Panel implements KeyListener {
    private final Map map;

    public Panel(RaySource r) {
      super(r);
      this.map = new Map();
      this.addKeyListener(this);
      this.setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
      raySource.move(keys, (double)super.getWidth() / 10000,
                     (double)super.getHeight() / 10000);
      super.paintComponent(g);
      map.draw(g, super.getWidth(), super.getHeight());
      raySource.calculateDistances();
      raySource.draw(g);

      repaint();

      // System.out.println("Painting!");

      /*try {
          refresh();
      } catch (Exception e) {
          System.out.println("Failed to refresh: " + e);
      }*/
    }

    /*public void refresh() throws InterruptedException {
        Thread.sleep(1000 / 30); // limit to 30 fps
        repaint();
    }*/

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

    public RaySource getRaySource() { return this.raySource; }
  }

  private final Panel panel;

  public TDVisualizerWindow() {
    this.panel = new Panel(new RaySource());

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setUndecorated(true);

    this.add(panel);
    this.pack();

    this.setSize(500, 500);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setResizable(false);
  }

  public Panel getPanel() { return this.panel; }
}

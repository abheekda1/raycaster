package com.abheekd.raycaster.gfx;

import com.abheekd.raycaster.objects.Map;
import com.abheekd.raycaster.objects.RaySource;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

public class RenderWindow extends JFrame {
    private static class Panel extends JPanel {
        private final RaySource raySource;

        public Panel(RaySource r) {
            this.raySource = r;
            this.setFocusable(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            raySource.renderDraw(g, 500, 500, 5000);

            repaint();
        }
    }

    private final Panel panel;

    public RenderWindow(RaySource r) {
        this.panel = new Panel(r);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);

        this.add(panel);
        this.pack();

        this.setSize(500, 500);
        //this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }
}

package com.abheekd.raycaster.objects;

import java.awt.*;

public class Map {
  // just a wrapper around a two d array
  private final int[][] grid = {
      {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 1, 0, 1},
      {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1},
  };
  private int height;
  private int width;

  public Map() {}

  public int[][] getGrid() { return grid; }

  public int getHeight() { return height; }

  public int getWidth() { return width; }

  public void setHeight(int height) { this.height = height; }

  public void setWidth(int width) {
    System.out.println("Set width to: " + width);
    this.width = width;
  }

  public int getXStep() { return width / 5; }

  public int getYStep() { return height / 5; }

  // simply draw squares for every grid square
  public void draw(Graphics g, int w, int h) {
    g.setColor(Color.DARK_GRAY);
    for (int i = 0; i < 5 /* map size */; i++) {
      for (int j = 0; j < 5; j++) {
        if (grid[j][i] == 1) {
          g.fillRect(i * w / 5, j * h / 5, w / 5 - 1, h / 5 - 1);
        }
      }
    }
  }
}

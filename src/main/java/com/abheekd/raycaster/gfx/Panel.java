package com.abheekd.raycaster.gfx;

import com.abheekd.raycaster.objects.RaySource;

import javax.swing.*;

// superclass for panels
public class Panel extends JPanel {
    RaySource raySource;
    boolean[] keys; // todo: convert to set

    // constructor
    public Panel(RaySource r) {
        raySource = r;
        keys = new boolean[4];
    }
}

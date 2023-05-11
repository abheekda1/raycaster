package com.abheekd.raycaster.gfx;

import com.abheekd.raycaster.objects.RaySource;

import javax.swing.*;

public class Panel extends JPanel {
    RaySource raySource;
    boolean[] keys; // todo: convert to set

    public Panel(RaySource r) {
        raySource = r;
        keys = new boolean[4];
    }
}

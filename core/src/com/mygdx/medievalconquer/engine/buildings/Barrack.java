package com.mygdx.medievalconquer.engine.buildings;

import com.mygdx.medievalconquer.engine.buildings.init_class.Building;

import java.util.HashMap;
import java.util.Map;
public class Barrack extends Building {
    public Barrack (int[] pos) {
        super("Barrack", new int[]{2, 2}, pos, 0, 1, 100, "defense", new HashMap<String, Integer>(), "0");
    }
}

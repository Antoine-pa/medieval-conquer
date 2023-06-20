package com.mygdx.medievalconquer.engine.buildings.builds;

import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.utils.Tools;
import com.mygdx.medievalconquer.engine.utils.Coords;

import java.util.HashMap;
public class Barrack extends Building {
    public Barrack (Tools tools, Coords pos) {
        super(tools, "Barrack", new int[]{2, 2}, pos, 0, 1, 100, "defense", new HashMap<>(), "0");
    }
}

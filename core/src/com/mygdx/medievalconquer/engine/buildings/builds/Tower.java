package com.mygdx.medievalconquer.engine.buildings.builds;

import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.tools.Tools;
import com.mygdx.medievalconquer.engine.tools.Coords;

import java.util.HashMap;
public class Tower extends Building {
    public Tower (Tools tools, Coords pos) {
        super(tools, "Tower", new int[]{3, 3}, pos, 0, 1, 100, "defense", new HashMap<String, Integer>(), "0");
    }
}

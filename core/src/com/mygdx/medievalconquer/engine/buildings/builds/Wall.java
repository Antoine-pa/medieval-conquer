package com.mygdx.medievalconquer.engine.buildings.builds;

import com.mygdx.medievalconquer.engine.buildings.init_class.JunctionBuilding;
import com.mygdx.medievalconquer.engine.utils.Tools;
import com.mygdx.medievalconquer.engine.utils.Coords;

import java.util.HashMap;
public class Wall extends JunctionBuilding {
    public Wall (Tools tools, Coords pos) {
        super(tools, "Wall", new int[]{1, 1}, pos, 0, 1, 100, "defense", new HashMap<>(), "0");
    }
}

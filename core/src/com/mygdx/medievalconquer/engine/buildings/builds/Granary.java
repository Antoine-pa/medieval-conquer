package com.mygdx.medievalconquer.engine.buildings.builds;

import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.utils.Tools;
import com.mygdx.medievalconquer.engine.utils.Coords;

import java.util.HashMap;
public class Granary extends Building {
    public Granary (Tools tools, Coords pos) {
        super(tools, "Granary", new int[]{2, 2}, pos, 0, 1, 100, "stockage", new HashMap<>(), "0");
    }
}
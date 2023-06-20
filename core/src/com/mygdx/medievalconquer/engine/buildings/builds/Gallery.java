package com.mygdx.medievalconquer.engine.buildings.builds;

import com.mygdx.medievalconquer.engine.buildings.init_class.ResourceTransportation;
import com.mygdx.medievalconquer.engine.utils.Tools;
import com.mygdx.medievalconquer.engine.utils.Coords;

import java.util.HashMap;
public class Gallery extends ResourceTransportation {
    public Gallery (Tools tools, Coords pos) {
        super(tools, "Gallery", new int[]{1, 1}, pos, 0, 1, 100, "transport", new HashMap<>(), "-1", 5, 10);
    }
}

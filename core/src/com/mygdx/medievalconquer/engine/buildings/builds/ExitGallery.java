package com.mygdx.medievalconquer.engine.buildings.builds;

import com.mygdx.medievalconquer.engine.buildings.init_class.ResourceTransportation;
import com.mygdx.medievalconquer.engine.utils.Tools;
import com.mygdx.medievalconquer.engine.utils.Coords;

import java.util.HashMap;
public class ExitGallery extends ResourceTransportation {
    public ExitGallery (Tools tools, Coords pos) {
        super(tools, "ExitGallery", new int[]{1, 1}, pos, 0, 1, 100, "transport", new HashMap<>(), "-1", 5, 10);
    }
}


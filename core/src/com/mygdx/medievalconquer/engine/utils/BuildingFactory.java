package com.mygdx.medievalconquer.engine.utils;

import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.buildings.builds.*;

import java.util.HashMap;
import java.util.Map;

public class BuildingFactory {
    private static final Map<String, BuildingCreator> BUILDING_CREATORS = new HashMap<>();

    static {
        BUILDING_CREATORS.put("Barrack", Barrack::new);
        BUILDING_CREATORS.put("Wall", Wall::new);
        BUILDING_CREATORS.put("Tower", Tower::new);
        BUILDING_CREATORS.put("Cannon", Cannon::new);
        BUILDING_CREATORS.put("Granary", Granary::new);
        BUILDING_CREATORS.put("Gallery", Gallery::new);
        BUILDING_CREATORS.put("EntranceGallery", EntranceGallery::new);
        BUILDING_CREATORS.put("ExitGallery", ExitGallery::new);
    }

    public static Building createBuilding(String name, Tools tools, Coords pos) {
        BuildingCreator creator = BUILDING_CREATORS.get(name);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown building name: " + name);
        }
        return creator.createBuilding(tools, pos);
    }

    @FunctionalInterface
    private interface BuildingCreator {
        Building createBuilding(Tools tools, Coords pos);
    }
}
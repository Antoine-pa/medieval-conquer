package com.mygdx.medievalconquer.engine.tools;

import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.buildings.builds.Barrack;
import com.mygdx.medievalconquer.engine.buildings.builds.Wall;
import com.mygdx.medievalconquer.engine.buildings.builds.Tower;

import java.util.HashMap;
import java.util.Map;

public class BuildingFactory {
    private static final Map<String, BuildingCreator> BUILDING_CREATORS = new HashMap<>();

    static {
        BUILDING_CREATORS.put("Barrack", Barrack::new);
        BUILDING_CREATORS.put("Wall", Wall::new);
        BUILDING_CREATORS.put("Tower", Tower::new);
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
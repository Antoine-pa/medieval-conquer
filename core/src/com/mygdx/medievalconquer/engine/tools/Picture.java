package com.mygdx.medievalconquer.engine.tools;

import com.mygdx.medievalconquer.engine.buildings.init_class.Building;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.medievalconquer.engine.buildings.init_class.JunctionBuilding;

import java.util.HashMap;
import java.util.Map;

public class Picture {
    private static final Map<String, Map<String, Texture>> pictures = new HashMap<>();

    static {
        pictures.put("Barrack", new HashMap<>());
        pictures.get("Barrack").put("Barrack", new Texture("buildings/Barrack/Barrack.png"));

        pictures.put("Tower", new HashMap<>());
        pictures.get("Tower").put("Tower", new Texture("buildings/Tower/Tower.png"));

        pictures.put("Wall", new HashMap<>());
        pictures.get("Wall").put("Wall0", new Texture("buildings/Wall/Wall0.png"));
        pictures.get("Wall").put("Wall1", new Texture("buildings/Wall/Wall1.png"));
        pictures.get("Wall").put("Wall2_0", new Texture("buildings/Wall/Wall2_0.png"));
        pictures.get("Wall").put("Wall2_1", new Texture("buildings/Wall/Wall2_1.png"));
        pictures.get("Wall").put("Wall3", new Texture("buildings/Wall/Wall3.png"));
        pictures.get("Wall").put("Wall4", new Texture("buildings/Wall/Wall4.png"));
    }
    public static Map<String, Map<String, Texture>> get() {
        return pictures;
    }
    public static Texture get(Building build) {
        if (build instanceof JunctionBuilding) {

            return pictures.get(build.name).get(build.name+((JunctionBuilding) build).get_suffix());
        }
        return pictures.get(build.name).get(build.name);
    }
}

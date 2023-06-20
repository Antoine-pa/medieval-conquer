package com.mygdx.medievalconquer.engine.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.medievalconquer.engine.buildings.init_class.JunctionBuilding;

import java.util.HashMap;
import java.util.Map;

public class Picture {
    private static final Map<String, Map<String, Texture>> pictures = new HashMap<>();
    public static BitmapFont font = new BitmapFont();

    static {
        pictures.put("Barrack", new HashMap<>());
        pictures.get("Barrack").put("Barrack", new Texture("buildings/Barrack/Barrack.png"));

        pictures.put("Cannon", new HashMap<>());
        pictures.get("Cannon").put("Cannon", new Texture("buildings/Cannon/Cannon.png"));



        pictures.put("Field", new HashMap<>());
        pictures.get("Field").put("Field", new Texture("buildings/Field/Field.png"));

        pictures.put("Foundry", new HashMap<>());
        pictures.get("Foundry").put("Foundry", new Texture("buildings/Foundry/Foundry.png"));

        pictures.put("Granary", new HashMap<>());
        pictures.get("Granary").put("Granary", new Texture("buildings/Granary/Granary.png"));

        pictures.put("Grange", new HashMap<>());
        pictures.get("Grange").put("Grange", new Texture("buildings/Grange/Grange.png"));

        pictures.put("Tower", new HashMap<>());
        pictures.get("Tower").put("Tower", new Texture("buildings/Tower/Tower.png"));

        pictures.put("Wall", new HashMap<>());
        pictures.get("Wall").put("Wall0", new Texture("buildings/Wall/Wall0.png"));
        pictures.get("Wall").put("Wall1", new Texture("buildings/Wall/Wall1.png"));
        pictures.get("Wall").put("Wall2_0", new Texture("buildings/Wall/Wall2_0.png"));
        pictures.get("Wall").put("Wall2_1", new Texture("buildings/Wall/Wall2_1.png"));
        pictures.get("Wall").put("Wall3", new Texture("buildings/Wall/Wall3.png"));
        pictures.get("Wall").put("Wall4", new Texture("buildings/Wall/Wall4.png"));

        pictures.put("Gallery", new HashMap<>());
        pictures.get("Gallery").put("Gallery0", new Texture("buildings/Gallery/Gallery0.png"));
        pictures.get("Gallery").put("Gallery1", new Texture("buildings/Gallery/Gallery1.png"));
        pictures.get("Gallery").put("Gallery2_0", new Texture("buildings/Gallery/Gallery2_0.png"));
        pictures.get("Gallery").put("Gallery2_1", new Texture("buildings/Gallery/Gallery2_1.png"));
        pictures.get("Gallery").put("Gallery3", new Texture("buildings/Gallery/Gallery3.png"));
        pictures.get("Gallery").put("Gallery4", new Texture("buildings/Gallery/Gallery4.png"));

        pictures.put("EntranceGallery", new HashMap<>());
        pictures.get("EntranceGallery").put("EntranceGallery0", new Texture("buildings/EntranceGallery/EntranceGallery0.png"));
        pictures.get("EntranceGallery").put("EntranceGallery1", new Texture("buildings/EntranceGallery/EntranceGallery1.png"));
        pictures.get("EntranceGallery").put("EntranceGallery2_0", new Texture("buildings/EntranceGallery/EntranceGallery2_0.png"));
        pictures.get("EntranceGallery").put("EntranceGallery2_1", new Texture("buildings/EntranceGallery/EntranceGallery2_1.png"));
        pictures.get("EntranceGallery").put("EntranceGallery3", new Texture("buildings/EntranceGallery/EntranceGallery3.png"));
        pictures.get("EntranceGallery").put("EntranceGallery4", new Texture("buildings/EntranceGallery/EntranceGallery4.png"));

        pictures.put("ExitGallery", new HashMap<>());
        pictures.get("ExitGallery").put("ExitGallery0", new Texture("buildings/ExitGallery/ExitGallery0.png"));
        pictures.get("ExitGallery").put("ExitGallery1", new Texture("buildings/ExitGallery/ExitGallery1.png"));
        pictures.get("ExitGallery").put("ExitGallery2_0", new Texture("buildings/ExitGallery/ExitGallery2_0.png"));
        pictures.get("ExitGallery").put("ExitGallery2_1", new Texture("buildings/ExitGallery/ExitGallery2_1.png"));
        pictures.get("ExitGallery").put("ExitGallery3", new Texture("buildings/ExitGallery/ExitGallery3.png"));
        pictures.get("ExitGallery").put("ExitGallery4", new Texture("buildings/ExitGallery/ExitGallery4.png"));
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
    public static Texture get(String name, String texture) {
        return pictures.get(name).get(texture);
    }
}

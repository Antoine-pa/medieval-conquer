package com.mygdx.medievalconquer.engine.tools;

import java.util.HashMap;
import java.util.Map;

public class Const {
    public static final Map<String, String[]> DICT_JUNCTIONS = new HashMap<>();
    public static final Map<String, String[]> LIST_BUILD_MENU_EDIT = new HashMap<>();
    public static final String[] LIST_JUNCTION_BUILDING = {"Wall", "Gallery", "ExitGallery", "EntranceGallery"};

    static {
        DICT_JUNCTIONS.put("Gallery", new String[]{"Gallery", "ExitGallery", "EntranceGallery"});
        DICT_JUNCTIONS.put("ExitGallery", new String[]{"Gallery", "EntranceGallery"});
        DICT_JUNCTIONS.put("EntranceGallery", new String[]{"ExitGallery", "Gallery"});
        DICT_JUNCTIONS.put("Wall", new String[]{"Wall", "Tower"});

        LIST_BUILD_MENU_EDIT.put("0", new String[]{"Barrack", "Field", "Granary", "Tower", "Wall", "Grange", "Foundry", "Cannon"});
        LIST_BUILD_MENU_EDIT.put("-1", new String[]{"Gallery", "ExitGallery", "EntranceGallery"});
    }
}

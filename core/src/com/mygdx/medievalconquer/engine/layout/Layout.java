package com.mygdx.medievalconquer.engine.layout;

import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.tools.Tools;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Layout {
    public int[] pos = {0, 0};
    public Tools t;
    public String layer = "-1";
    public boolean alpha = false;

    public HashMap<String, HashSet<Building>> list_build = new HashMap<>();
    public HashMap<String, HashMap<String, HashSet<Building>>> dict_name_build = new HashMap<>();
    public HashMap<String, HashMap<String, HashSet<Building>>> dict_kind_build = new HashMap<>();
    public HashMap<String, HashMap<int[], Building>> dict_pos_build = new HashMap<>();

    public Layout (Tools t) {
        this.t = t;
    }
    public void add_build(Building build) {
        this.list_build.get(build.layer).add(build);
        /*
        * ajout dans les hashmap
        * */
    }

    public int check_pos (Building build, Map<String, Map<int[], Building>> edit_add_tamp) {
        if (edit_add_tamp.get(this.layer).containsKey(build.pos)) {
            return 2;
        }
        else if (this.dict_pos_build.containsKey(build.pos)) {
            return 1;
        }
        return 0;
    }

    public void display () {
        System.out.println(this.list_build);
    }

    public int[] get_case (int[] pos) {
        int[] c = new int[2];
        c[0] = 0;
        c[1] = 0;
        return c;
    }

    public void load_layout () {

    }
    public void reload_images () {

    }
    public void save_layout () {

    }

    public void sup_build (Building build) {

    }
    public void update_galleries_links () {
        for(Building b: this.dict_name_build.get("-1").get("EntranceGallery")){
            //HashSet<Building> l = b.update_links(this, new HashSet<Building>());
        }
    }

    public boolean zoom (int z) {
        //if(z < 0 and )
        return true;
    }
}
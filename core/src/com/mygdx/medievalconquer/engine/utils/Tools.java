package com.mygdx.medievalconquer.engine.utils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Tools extends Json {
    public Map<String, Float> float_cst = new HashMap<>();
    public Map<String, Integer> int_cst = new HashMap<>();
    public Map<String, int[]> array_cst = new HashMap<>();
    public Tools(int size_x, int size_y) {
        super();
        this.set_all_cst(size_x, size_y);
    }
    public Map<String, Integer> check_stock(Map<String, Integer> resources, Map<String, Integer> resources2) {
        Map<String, Integer> r = new HashMap();
        for (Map.Entry res : resources.entrySet()) {

        }
        return r;
    }

    public static int index(boolean[] l, boolean val) {
        int i = 0;
        for(boolean b: l) {
            if(b==val) {
                return i;
            }
            i++;
        }
        return -1;
    }
    public void set_all_cst(int size_x, int size_y) {
        this.int_cst.put("size_x", size_x);
        this.int_cst.put("size_y", size_y);
        this.float_cst.put("SENSIBILITY", 0.2f);
        this.float_cst.put("ZOOM", 1f);
        this.array_cst.put("MENU_EDIT_POS", new int[]{size_x-3*size_y/16, 15*size_y/16, 3*size_y/16, size_y/16});
        this.int_cst.put("LONG_BLOCK_MENU_EDIT", 3*this.array_cst.get("MENU_EDIT_POS")[3]/4);
        this.array_cst.put("POS_BUTTONS_MENU_EDIT", new int[]{((this.array_cst.get("MENU_EDIT_POS")[3])-this.int_cst.get("LONG_BLOCK_MENU_EDIT"))/2, (this.array_cst.get("MENU_EDIT_POS")[1])+(size_y-(this.array_cst.get("MENU_EDIT_POS"))[1])/2-this.int_cst.get("LONG_BLOCK_MENU_EDIT")/2});
        this.int_cst.put("GAP_BLOCK_COL_MENU_EDIT", this.array_cst.get("MENU_EDIT_POS")[3]/8);
        this.int_cst.put("SIZE_CASE", 50);
        this.int_cst.put("INIT_SIZE_CASE", 50);
        this.int_cst.put("SIZE_TEXT", 30);
        float scale = ((float) this.int_cst.get("SIZE_TEXT"))/15;
        Picture.font.getData().setScale(scale, -scale);
    }
    public void set_cst(String name, float val) {
        this.float_cst.replace(name, val);
    }
    public void set_cst(String name, int val) {
        this.int_cst.replace(name, val);
    }
    public void set_cst(String name, int[] val) {
        this.array_cst.replace(name, val);
    }
    public long time() {
        return new Timestamp(System.currentTimeMillis()).getTime();
    }
}
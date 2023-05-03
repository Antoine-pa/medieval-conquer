package com.mygdx.medievalconquer.engine.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.medievalconquer.engine.buildings.init_class.Building;

import java.math.BigDecimal;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class Tools {
    public String path_json = "json/";
    private JSONObject cost;
    public JSONObject layout;
    private JSONObject prod;
    private JSONObject res;
    public Map<String, Float> float_cst = new HashMap<>();
    public Map<String, Integer> int_cst = new HashMap<>();
    public Map<String, int[]> array_cst = new HashMap<>();
    public Tools(int size_x, int size_y) {
        this.set_all_cst(size_x, size_y);
        try {
            this.cost = this.load_cost();
            this.layout = this.load_layout();
            this.prod = this.load_prod();
            this.res = this.load_res();
        }
        catch (IOException e) {
            System.out.println("invalid file name"+e.getMessage());
        }
    }
    public void add_new_res(String name, int maxi, int value) {
    }
    public static void progress_bar (int[] pos, int[] size, float ratio, Color color) {
    }
    public Map<String, Integer> check_stock(Map<String, Integer> resources, Map<String, Integer> resources2) {
        Map<String, Integer> r = new HashMap();
        for (Map.Entry res : resources.entrySet()) {

        }
        return r;
    }
    public static void fill(Color color) {
        ScreenUtils.clear(color);
    }
    public Map<String, Integer> get_cost(String name, int lvl){
        Map<String, Integer> cost = ((Map) ((JSONObject) ((JSONObject) this.cost.get(name)).get(String.valueOf(lvl))).toMap());
        return cost;
    }
    private JSONObject get_json(String name) throws IOException {
        return new JSONObject(FileUtils.readFileToString(new File(this.path_json+name), "utf-8"));
    }
    public Map<String, Integer> get_prod_max(Building build) {
        Map<String, Integer> max = ((Map) ((JSONObject) ((JSONObject) ((JSONObject) this.prod.get(build.name)).get(String.valueOf(build.lvl))).get("max")).toMap());
        return max;
    }
    public Map<String, Integer> get_prod_res(Building build) {
        Map<String, Integer> res = ((Map) ((JSONObject) ((JSONObject) ((JSONObject) this.prod.get(build.name)).get(String.valueOf(build.lvl))).get("prod")).toMap());
        return res;
    }
    public int get_prod_time(Building build) {
        int time = ((int) ((JSONObject) ((JSONObject) this.prod.get(build.name)).get(String.valueOf(build.lvl))).get("time"));
        return time;
    }
    public Map<String, Map<String, Integer>> get_res() {
        return (Map) this.res;
    }
    public Map<String, Integer> get_res(String name) {
        Map<String, Integer> res = ((Map) ((JSONObject) this.res.get(name)).toMap());
        return res;
    }
    private JSONObject load_cost() throws IOException {
        return this.get_json("cost.json");
    }
    private JSONObject load_cst() throws IOException {
        return this.get_json("cst.json");
    }
    public JSONObject load_layout() throws IOException {
        return this.get_json("init_layout.json");
    }
    private JSONObject load_prod() throws IOException {
        return this.get_json("production.json");
    }
    private JSONObject load_res() throws IOException {
        return this.get_json("resources.json");
    }
    public void set_all_cst(int size_x, int size_y) {
        this.int_cst.put("size_x", size_x);
        this.int_cst.put("size_y", size_y);
        this.float_cst.put("SENSIBILITY", 0.4f);
        this.float_cst.put("ZOOM", 1f);
        this.array_cst.put("MENU_EDIT_POS", new int[]{size_x-3*size_y/16, 15*size_y/16, 3*size_y/16, size_y/16});
        this.int_cst.put("LONG_BLOCK_MENU_EDIT", 3*this.array_cst.get("MENU_EDIT_POS")[3]/4);
        this.array_cst.put("POS_BUTTONS_MENU_EDIT", new int[]{((this.array_cst.get("MENU_EDIT_POS")[3])-this.int_cst.get("LONG_BLOCK_MENU_EDIT"))/2, (this.array_cst.get("MENU_EDIT_POS")[1])+(size_y-(this.array_cst.get("MENU_EDIT_POS"))[1])/2-this.int_cst.get("LONG_BLOCK_MENU_EDIT")/2});
        this.int_cst.put("GAP_BLOCK_COL_MENU_EDIT", this.array_cst.get("MENU_EDIT_POS")[3]/8);
        this.int_cst.put("SIZE_CASE", 50);
        this.int_cst.put("INIT_SIZE_CASE", 50);
        this.int_cst.put("SIZE_TEXT", 30);
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
    public void set_res(String name, int value) {
        this.res.remove(name);
        this.res.put(name, value);
    }
    public void text(String text, Color color, int[] pos, int size) {

    }
    public long time() {
        return new Timestamp(System.currentTimeMillis()).getTime();
    }
}
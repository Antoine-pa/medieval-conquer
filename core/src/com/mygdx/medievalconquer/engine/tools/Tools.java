package com.mygdx.medievalconquer.engine.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.medievalconquer.engine.buildings.init_class.Building;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class Tools {
    public String path_json = "json/";
    public String path_assets = "";
    private JSONObject cost;
    private JSONObject cst;
    public JSONObject layout;
    private JSONObject prod;
    private JSONObject res;
    public Tools(int size_x, int size_y) {
        try {
            this.cost = this.load_cost();
            this.cst = this.load_cst();
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

    public static void barre (int[] pos, int[] size, float ratio, int[] color) {

    }
    public Map<String, Integer> check_stock(Map<String, Integer> resources, Map<String, Integer> resources2) {
        Map<String, Integer> r = new HashMap();
        for (Map.Entry res : resources.entrySet()) {
            /*
            if (res.getValue() + resources2.get(res.getKey()) > 0) {

            }
            System.out.println("clé: "+mapentry.getKey()
                    + " | valeur: " + mapentry.getValue());
            */
        }
        return r;
    }
    public float[] color_to_float(int[] color) {
        return new float[]{color[0]/256, color[1]/256, color[2]/256};
    }
    public void display_img(SpriteBatch batch, Texture img, int[] pos, int[] size) {
        int s = this.get_cst_value("SIZE_CASE");
        batch.draw(img, pos[0]*s, pos[1]*s, size[0]*s, size[1]*s);
    }
    public static void fill(int[] color) {
        Gdx.gl.glClearColor(color[0]/255, color[1]/255, color[2]/255, 1);
    }
    public static void fill(int[] color, int alpha) {
        Gdx.gl.glClearColor(color[0]/255, color[1]/255, color[2]/255, alpha);
    }

    public Map<String, Integer> get_cost(String name, int lvl){
        Map<String, Integer> cost = ((Map) ((JSONObject) ((JSONObject) this.cost.get(name)).get(String.valueOf(lvl))).toMap());
        return cost;
    }

    public int get_cst_value(String name) {
        int value = (int) this.cst.get(name);
        return value;
    }

    public int[] get_cst_array(String name) {
        JSONArray array = ((JSONArray) this.cst.get(name));
        int[] ar = new int[array.length()];
        for(int i = 0; i<array.length(); i++) {
            ar[i] = (int) array.get(i);
        }
        return ar;
    }

    public String[] get_cst_array_string(String name) {
        String[] array = (String[]) this.cst.get(name);
        return array;
    }

    public Map<String, String[]> get_cst_dict(String name) {
        Map<String, String[]> dict = (Map) ((JSONObject) this.cst.get(name)).toMap();
        return dict;
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
    public void load_img(String name, int x, int y) throws IOException {

    }
    public void load_img(String name, int x, int y, int alpha) throws IOException {

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

    }
    public void set_cst(String name, int val) {

    }
    public void set_cst(String name, int[] val) {

    }
    public void set_cst(String name, Map val) {

    }

    public void set_res(String name, int value) {

    }
    public void text(String text, int[] color, int[] pos, int size) {

    }

    public long time() {
        return new Timestamp(System.currentTimeMillis()).getTime();
    }
}
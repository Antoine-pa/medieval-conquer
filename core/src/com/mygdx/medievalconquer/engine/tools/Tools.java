package com.mygdx.medievalconquer.engine.tools;

import com.mygdx.medievalconquer.engine.buildings.init_class.Building;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Tools {
    public String path_json = "";
    public String path_assets = "";
    public Map<String, Function<int[], Building>> buildings = new HashMap<>();
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

    public int cst_value(String name) {
        return 1;
    }

    public int[] cst_array(String name) {
        int[] r = {0, 0, 0, 0};
        return r;
    }

    public Map<String, String[]> cst_dict(String name) {
        Map<String, String[]> r = new HashMap();
        return r;
    }
    public void set_all_const(int size_x, int size_y) {

    }
    public Map<String, Integer> cost(String name, int lvl){
        Map<String, Integer> r = new HashMap();
        return r;
    }

    public void set_res(String name, int value) {

    }

    public Map<String, Integer> get_res(String name) {
        Map<String, Integer> r = new HashMap<>();
        return r;
    }

    public long time() {
        return new Timestamp(System.currentTimeMillis()).getTime();
    }

    public Map<String, Integer> prod_res(Building build) {
        return new HashMap<>();
    }
    public Map<String, Integer> prod_max(Building build) {
        return new HashMap<>();
    }
    public int prod_time(Building build) {
        return 1;
    }
}
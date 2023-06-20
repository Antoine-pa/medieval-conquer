package com.mygdx.medievalconquer.engine.utils;

import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Json {
    public String path_json = "/json/";
    private Map<String, Map<String, Map<String, Integer>>> cost;
    public Map<String, Set<Map<String, Object>>> layout;
    private Map<String, Map<String, Map<String, Object>>> prod;
    private Map<String, Map<String, Integer>> res;

    public Json() {
        try {
            this.cost = (Map) this.load_cost();
            this.layout = (Map) this.load_layout();
            this.prod = (Map) this.load_prod();
            this.res = (Map) this.load_res();
        }
        catch (IOException e) {
            System.out.println("invalid file name"+e.getMessage());
        }
    }
    public void add_new_res(String name, int maxi, int value) {
    }
    private JSONObject get_json(String name) throws IOException {
        return new JSONObject(IOUtils.toString(getClass().getResourceAsStream(this.path_json+name)));
    }
    public Map<String, Integer> get_cost(String name, int lvl){
        Map<String, Integer> cost = this.cost.get(name).get(String.valueOf(lvl));
        return cost;
    }
    public Map<String, Integer> get_prod_max(Building build) {
        Map<String, Integer> max = (Map) this.prod.get(build.name).get(String.valueOf(build.lvl)).get("max");
        return max;
    }
    public Map<String, Integer> get_prod_res(Building build) {
        Map<String, Integer> res = (Map) this.prod.get(build.name).get(String.valueOf(build.lvl)).get("prod");
        return res;
    }
    public int get_prod_time(Building build) {
        int time = (int) this.prod.get(build.name).get(String.valueOf(build.lvl)).get("time");
        return time;
    }
    public Map<String, Map<String, Integer>> get_res() {
        return this.res;
    }
    public Map<String, Integer> get_res(String name) {
        Map<String, Integer> res = this.res.get(name);
        return res;
    }
    private Map<String, Object> load_cost() throws IOException {
        return this.get_json("cost.json").toMap();
    }
    public Map<String, Object> load_layout() throws IOException {
        return this.get_json("init_layout.json").toMap();
    }
    private Map<String, Object> load_prod() throws IOException {
        return this.get_json("production.json").toMap();
    }
    private Map<String, Object> load_res() throws IOException {
        return this.get_json("resources.json").toMap();
    }
    public void set_res(String name, int value) {
        this.res.get(name).replace("stock", value);
    }
    public void change_res(String name, int value) {
        this.set_res(name, this.get_res(name).get("stock")+value);
    }
}

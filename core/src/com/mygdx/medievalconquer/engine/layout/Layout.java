package com.mygdx.medievalconquer.engine.layout;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.medievalconquer.engine.tools.BuildingFactory;
import com.mygdx.medievalconquer.engine.buildings.builds.Barrack;
import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.buildings.init_class.JunctionBuilding;
import com.mygdx.medievalconquer.engine.buildings.init_class.ProductionBuilding;
import com.mygdx.medievalconquer.engine.buildings.init_class.ResourceTransportation;
import com.mygdx.medievalconquer.engine.tools.Tools;
import com.mygdx.medievalconquer.engine.tools.Coords;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Layout {
    public float[] pos = {500, 500};
    public Tools tools;
    public String layer = "0";
    public boolean alpha = false;

    public HashMap<String, HashSet<Building>> list_build;
    public HashMap<String, HashMap<String, HashSet<Building>>> dict_name_build;
    public HashMap<String, HashMap<String, HashSet<Building>>> dict_kind_build;
    public HashMap<String, HashMap<Coords, Building>> dict_pos_build;

    public Layout (Tools tools) {
        this.tools = tools;
        this.list_build = new HashMap<>();
        this.list_build.put("0", new HashSet<>());
        this.list_build.put("-1", new HashSet<>());
        this.dict_name_build = new HashMap<>();
        this.dict_name_build.put("0", new HashMap<>());
        this.dict_name_build.put("-1", new HashMap<>());
        this.dict_kind_build = new HashMap<>();
        this.dict_kind_build.put("0", new HashMap<>());
        this.dict_kind_build.put("-1", new HashMap<>());
        this.dict_pos_build = new HashMap<>();
        this.dict_pos_build.put("0", new HashMap<>());
        this.dict_pos_build.put("-1", new HashMap<>());
        Building b = BuildingFactory.createBuilding("Barrack", tools, new Coords(505, 505));
        this.add_build(b);
        //this.add_build(new Building(this.tools, "Barrack", new int[]{1, 1}, new int[]{0, 1}, 0, 1, 100, "defense", new HashMap<>(), "0"));
    }
    public void add_build(Building build) {
        this.list_build.get(build.layer).add(build);
        for(int x = build.pos.x; x < build.pos.x + build.size[0]; x++) {
            for(int y = build.pos.y; y < build.pos.y + build.size[1]; y++) {
                Coords coords = new Coords(x, y);
                this.dict_pos_build.get(build.layer).put(coords, build);
            }
        }
        if (!this.dict_name_build.get(build.layer).containsKey(build.name)) {
            this.dict_name_build.get(build.layer).put(build.name, new HashSet<>());
        }
        this.dict_name_build.get(build.layer).get(build.name).add(build);

        if (!this.dict_kind_build.get(build.layer).containsKey(build.kind)) {
            this.dict_kind_build.get(build.layer).put(build.kind, new HashSet<>());
        }
        this.dict_kind_build.get(build.layer).get(build.kind).add(build);

        if (build.kind == "production") {
            ((ProductionBuilding) build).start_production = true;
        }
        if (build instanceof JunctionBuilding) {
            ((JunctionBuilding) build).load();
        }
    }

    public int check_pos (Building build, Map<String, Map<Coords, Building>> edit_add_tamp) {
        if (edit_add_tamp.get(this.layer).containsKey(build.pos)) {
            return 2;
        }
        else if (this.dict_pos_build.containsKey(build.pos)) {
            return 1;
        }
        return 0;
    }

    public void display (SpriteBatch batch, ShapeRenderer shape) {
        int[] color1;
        int[] color2;
        if (this.layer == "0") {
            color1 = this.tools.get_cst_array("WHITE");
            color2 = this.tools.get_cst_array("BLACK");
        }
        else {
            color1 = this.tools.get_cst_array("BLACK");
            color2 = this.tools.get_cst_array("WHITE");
        }
        this.tools.fill(color1);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.GRAY);
        for(float x = 0; x<=this.tools.get_cst_value("size_x")+ this.tools.get_cst_value("SIZE_CASE"); x = x+this.tools.get_cst_value("SIZE_CASE")) {
            shape.line(x-this.pos[0]%1* tools.get_cst_value("SIZE_CASE"), 0, x-this.pos[0]%1* tools.get_cst_value("SIZE_CASE"), tools.get_cst_value("size_y"));
            for(float y = 0; y<=this.tools.get_cst_value("size_y")+ this.tools.get_cst_value("SIZE_CASE"); y = y+this.tools.get_cst_value("SIZE_CASE")) {
                shape.line(0, y-this.pos[1]%1* tools.get_cst_value("SIZE_CASE"), tools.get_cst_value("size_x"), y-this.pos[1]%1* tools.get_cst_value("SIZE_CASE"));
            }
        }
        shape.end();
        if (this.alpha & this.layer == "-1") {
            for(int y = (int) this.pos[1]-10; y < this.pos[1] + this.tools.get_cst_value("size_y")/this.tools.get_cst_value("SIZE_CASE"); y++) {
                for(int x = (int) this.pos[0]-10; x < this.pos[0] + this.tools.get_cst_value("size_x")/this.tools.get_cst_value("SIZE_CASE"); x++) {
                    Coords coords = new Coords(x, y);
                    if (this.dict_pos_build.get("0").containsKey(coords)) {
                        this.dict_pos_build.get("0").get(coords).display(batch, this.pos, true);
                    }
                }
            }
        }
        Set<Building> builds = new HashSet<>();
        for(int y = (int) this.pos[1]-10; y < this.pos[1] + this.tools.get_cst_value("size_y")/this.tools.get_cst_value("SIZE_CASE"); y++) {
            for(int x = (int) this.pos[0]-10; x < this.pos[0] + this.tools.get_cst_value("size_x")/this.tools.get_cst_value("SIZE_CASE"); x++) {
                Coords coords = new Coords(x, y);
                if (this.dict_pos_build.get(this.layer).containsKey(coords)) {
                    Building b = this.dict_pos_build.get(this.layer).get(coords);
                    if(! builds.contains(b)) {
                        builds.add(b);
                        b.display(batch, this.pos, false);
                    }
                }
            }
        }
        builds.clear();
        //suite
    }

    public Coords get_case (int[] pos) {
        Coords c = new Coords(0, 0);
        c.x = (pos[0]+((int) this.pos[0]*this.tools.get_cst_value("SIZE_CASE")))/this.tools.get_cst_value("SIZE_CASE");
        c.y = (pos[1]+((int) this.pos[1]*this.tools.get_cst_value("SIZE_CASE")))/this.tools.get_cst_value("SIZE_CASE");
        return c;
    }

    public void load_layout () {
        Map<String, Set<JSONObject>> lyt = (Map<String, Set<JSONObject>>) this.tools.layout;
        for(Map.Entry<String, Set<JSONObject>> l : lyt.entrySet()) {
            for(JSONObject build: l.getValue()) {
                //this.add_build();
            }
        }
    }
    public void reload_images () {
        for(Map.Entry<String, HashSet<Building>> l: this.list_build.entrySet()) {
            for(Building build: l.getValue()) {
                build.load();
            }
        }
    }
    public void save_layout () {

    }

    public void sup_build (Building build) {
        this.list_build.get(build.layer).remove(build);
        this.dict_name_build.get(build.layer).get(build.name).remove(build);
        this.dict_kind_build.get(build.layer).get(build.kind).remove(build);
        for(int x = build.pos.x; x < build.pos.x + build.size[0]; x++) {
            for(int y = build.pos.y; y < build.pos.y + build.size[1]; y++) {
                Coords coords = new Coords(x, y);
                this.dict_pos_build.get(build.layer).remove(coords);
            }
        }
    }
    public void update_galleries_links () {
        if (this.dict_name_build.get("-1").containsKey("EntranceGallery")) {
            for(Building b: this.dict_name_build.get("-1").get("EntranceGallery")){
                HashSet<ResourceTransportation> l = ((ResourceTransportation) b).update_transport_links(this, new HashSet<ResourceTransportation>());
                l.clear();
            }
        }
    }

    public boolean zoom (int z) {
        if((z < 0 && this.tools.get_cst_value("ZOOM") >= 0.2) | (z > 0 && this.tools.get_cst_value("ZOOM") <= 1.9)) {
            int old_zoom = this.tools.get_cst_value("ZOOM");
            this.tools.set_cst("ZOOM", (int) (this.tools.get_cst_value("ZOOM") + z/10));
            this.tools.set_cst("SIZE_CASE", (int) (1/this.tools.get_cst_value("ZOOM")*this.tools.get_cst_value("SIZE_CASE")/(1/old_zoom)));
            return true;
        }
        return false;
    }
}
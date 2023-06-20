package com.mygdx.medievalconquer.engine.layout;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.medievalconquer.engine.utils.*;
import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.buildings.init_class.JunctionBuilding;
import com.mygdx.medievalconquer.engine.buildings.init_class.ProductionBuilding;
import com.mygdx.medievalconquer.engine.buildings.init_class.ResourceTransportation;

import java.util.*;

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
        this.load_layout();
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
        Color color1;
        Color color2;
        if (this.layer == "0") {
            color1 = Colors.white;
            color2 = Colors.black;
        }
        else {
            color1 = Colors.black;
            color2 = Colors.white;
        }
        Graphics.fill(color1);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(color2);
        for(float x = 0; x<=this.tools.int_cst.get("size_x") + this.tools.int_cst.get("SIZE_CASE"); x = x+this.tools.int_cst.get("SIZE_CASE")) {
            shape.line(x-this.pos[0]%1* tools.int_cst.get("SIZE_CASE"), 0, x-this.pos[0]%1* tools.int_cst.get("SIZE_CASE"), tools.int_cst.get("size_y"));
            for(float y = 0; y<=this.tools.int_cst.get("size_y")+ this.tools.int_cst.get("SIZE_CASE"); y = y+this.tools.int_cst.get("SIZE_CASE")) {
                shape.line(0, y-this.pos[1]%1* tools.int_cst.get("SIZE_CASE"), tools.int_cst.get("size_x"), y-this.pos[1]%1* tools.int_cst.get("SIZE_CASE"));
            }
        }
        shape.end();
        if (this.alpha & this.layer == "-1") {
            for(int y = (int) this.pos[1]-10; y < this.pos[1] + this.tools.int_cst.get("size_y")/this.tools.int_cst.get("SIZE_CASE"); y++) {
                for(int x = (int) this.pos[0]-10; x < this.pos[0] + this.tools.int_cst.get("size_x")/this.tools.int_cst.get("SIZE_CASE"); x++) {
                    Coords coords = new Coords(x, y);
                    if (this.dict_pos_build.get("0").containsKey(coords)) {
                        this.dict_pos_build.get("0").get(coords).display(batch, this.pos, true);
                    }
                }
            }
        }
        Set<Building> builds = new HashSet<>();
        for(int y = (int) this.pos[1]-10; y < this.pos[1] + this.tools.int_cst.get("size_y")/this.tools.int_cst.get("SIZE_CASE")+2; y++) {
            for(int x = (int) this.pos[0]-10; x < this.pos[0] + this.tools.int_cst.get("size_x")/this.tools.int_cst.get("SIZE_CASE")+2; x++) {
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
        c.x = (pos[0]+((int) this.pos[0]*this.tools.int_cst.get("SIZE_CASE")))/this.tools.int_cst.get("SIZE_CASE");
        c.y = (pos[1]+((int) this.pos[1]*this.tools.int_cst.get("SIZE_CASE")))/this.tools.int_cst.get("SIZE_CASE");
        return c;
    }

    public void load_layout () {
        Map<String, ArrayList<Map>> lyt = (Map) this.tools.layout;
        for(Map.Entry<String, ArrayList<Map>> l : lyt.entrySet()) {
            for(int i = 0; i < l.getValue().size(); i++) {
                Map build = (Map) l.getValue().get(i);
                String name = (String) build.get("name");
                Coords coords = new Coords((int) ((ArrayList) build.get("pos")).get(0), (int) ((ArrayList) build.get("pos")).get(1));
                Building b = BuildingFactory.createBuilding(name, this.tools, coords);
                if (b instanceof JunctionBuilding) {
                    ((JunctionBuilding) b).set_junctions(((ArrayList) ((Map) build.get("other")).get("t")));
                    b.angle = (int) build.get("angle");
                    b.lvl = (int) build.get("lvl");
                    b.life = (int) build.get("life");
                }
                this.add_build(b);
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

    public void zoom (float z) {
        float new_zoom = this.tools.float_cst.get("ZOOM") + z/10;
        new_zoom = Float.max(Float.min(new_zoom, 1.7f), 0.5f);
        this.tools.set_cst("ZOOM", new_zoom);
        this.tools.set_cst("SIZE_CASE", (int) ((1/new_zoom)*this.tools.int_cst.get("INIT_SIZE_CASE")));
    }
}
package com.mygdx.medievalconquer.engine.buildings.init_class;

import com.mygdx.medievalconquer.engine.utils.Tools;
import com.mygdx.medievalconquer.engine.utils.Coords;
import com.mygdx.medievalconquer.engine.layout.Layout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResourceTransportation extends JunctionBuilding {
    public int speed_transport;

    public int capacity_transport;
    public long last_transport = this.tools.time();
    public int[] dir_transport = {0, 0, 0, 0}; //0 = no junction, 1 = input, 2 = output
    public ResourceTransportation(Tools tools, String name, int[] size, Coords pos, int angle, int lvl, int life, String kind, Map<String, Integer> stock, String layer, int speed_transport, int capacity_transport) {
        super(tools, name, size, pos, angle, lvl, life, kind, stock, layer);
        this.capacity_transport = capacity_transport;
        this.speed_transport = speed_transport;
    }

    public ResourceTransportation(Tools tools, String name, int[] size, Coords pos, int angle, int lvl, int life, String kind, Map<String, Integer> stock, String layer, int speed_transport, int capacity_transport, boolean[] t) {
        super(tools, name, size, pos, angle, lvl, life, kind, stock, layer, t);
        this.capacity_transport = capacity_transport;
        this.speed_transport = speed_transport;
    }

    public void transport(Layout layout) {
        long time = this.tools.time();
        if (time - this.last_transport < this.speed_transport) {
            return;
        }
        this.last_transport = time;
        /*
        * HashSet<int[]> pos = new HashSet<>();
        * for(int x = 0; x < 2; x++) {
        *     pos.add(new int[]{this.pos[0] -1+x*2, this.pos[1]});
        *     pos.add(new int[]{this.pos[0], this.pos[1] -1+x*2});
        * }
        * Set<Building> list_build =new HashSet<>();
        * for(int[] p: pos) {
        *     if (layout.dict_pos_build.get(this.layer).containsKey(p)) {
        *         Building b = layout.dict_pos_build.get(this.layer).get(p);
        *         if (b instanceof ResourceTransportation) {
        *             list_build.add(b);
        *         }
        *     }
        * }
        * if (list_build.isEmpty()) {
        *     return;
        * }
        */
        Map<String, Integer> res = new HashMap<>();
        for(int i = 0; i < this.capacity_transport; i++) {
            if (! this.stock.isEmpty()){
                //
            }
        }

        Set<Building> list_build_output = new HashSet<>();
        Set<Building> list_build_input = new HashSet<>();
        for(int i = 0; i < 4; i++) {
            Coords coords;
            if (this.dir_transport[i] != 0) { //output or output
                if (i == 0) {coords = new Coords(this.pos.x+1, this.pos.y);}
                else if (i == 1) {coords = new Coords(this.pos.x, this.pos.y-1);}
                else if (i == 2) {coords = new Coords(this.pos.x-1, this.pos.y);}
                else {coords = new Coords(this.pos.x, this.pos.y+1);}
                if (this.dir_transport[i] == 1) {
                    list_build_input.add(layout.dict_pos_build.get(this.layer).get(coords));
                }
                else {
                    list_build_output.add(layout.dict_pos_build.get(this.layer).get(coords));
                }
            }
        }
        if (! list_build_output.isEmpty()) {
            //
        }
    }

    public HashSet<ResourceTransportation> update_transport_links(Layout layout, HashSet<ResourceTransportation> b_update) {
        Set<Map<Coords, Building>> list_dict_pos_build = new HashSet<>();
        list_dict_pos_build.add(layout.dict_pos_build.get(this.layer));
        for(Building b: this.get_build_adj(list_dict_pos_build)) {
            if (!b_update.contains(b) && b instanceof ResourceTransportation && b.name != "EntranceGallery") {
                if (b.pos.x <= this.pos.x && this.pos.x < b.pos.x + b.size[0]){
                    if (b.pos.y < this.pos.y){//positionnement en bas d'une autre jonction
                        this.dir_transport[1] = 2;
                        ((ResourceTransportation) b).dir_transport[3] = 1;
                    }
                else if (b.pos.y > this.pos.y){//positionnement en haut d'une autre jonction
                        this.dir_transport[3] = 2;
                        ((ResourceTransportation) b).dir_transport[1] = 1;
                    }
                }
                else if (b.pos.y <= this.pos.y && this.pos.y < b.pos.y + b.size[1]) {
                    if (b.pos.x > this.pos.x){//positionnement à gauche d'une autre jonction
                        this.dir_transport[0] = 2;
                        ((ResourceTransportation) b).dir_transport[2] = 1;
                    }
                else if (b.pos.x < this.pos.x){//positionnement à droite d'une autre jonction
                        this.dir_transport[2] = 2;
                        ((ResourceTransportation) b).dir_transport[0] = 1;
                    }
                }
                b_update.add(this);
                if (b.name != "ExitGallery") {
                    ((ResourceTransportation) b).update_transport_links(layout, b_update);
                }
            }
        }
        return b_update;
    }
}
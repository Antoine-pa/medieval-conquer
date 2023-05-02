package com.mygdx.medievalconquer.engine.buildings.init_class;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.tools.Tools;
import com.mygdx.medievalconquer.engine.tools.Coords;

import java.util.*;

public class JunctionBuilding extends Building {
    boolean[] t;
    public JunctionBuilding(Tools tools, String name, int[] size, Coords pos, int angle, int lvl, int life, String kind, Map<String, Integer> stock, String layer) {
        super(tools, name, size, pos, angle, lvl, life, kind, stock, layer);
        this.t = new boolean[]{false, false, false, false};
    }

    public JunctionBuilding(Tools tools, String name, int[] size, Coords pos, int angle, int lvl, int life, String kind, Map<String, Integer> stock, String layer, boolean[] t) {
        super(tools, name, size, pos, angle, lvl, life, kind, stock, layer);
        this.t = t;
    }


    public void add_junction(Set<Map<Coords, Building>> list_dict_pos_build) {
        Set<Building> list_build = this.get_build_adj(list_dict_pos_build);
        for(Building b : list_build) {
            if (b.pos.x <= this.pos.x && this.pos.x < b.pos.x + b.size[0]) {
                if (b.pos.y < this.pos.y) {  //positionnement en bas d'une autre jonction
                    this.t[1] = true;
                    if (b instanceof JunctionBuilding) {
                        ((JunctionBuilding) b).t[3] = true;
                    }
                }
                else if (b.pos.y > this.pos.y) { //positionnement en haut d'une autre jonction
                    this.t[3] = true;
                    if (b instanceof JunctionBuilding) {
                        ((JunctionBuilding) b).t[1] = true;
                    }
                }
            }
            else if (b.pos.y <= this.pos.y && this.pos.y < b.pos.y + b.size[1]) {
                if (b.pos.x > this.pos.x) {  //positionnement à gauche d'une autre jonction
                    this.t[0] = true;
                    if (b instanceof JunctionBuilding) {
                        ((JunctionBuilding) b).t[2] = true;
                    }
                }
                else if (b.pos.x < this.pos.x) { //positionnement à droite d'une autre jonction
                    this.t[2] = true;
                    if (b instanceof JunctionBuilding) {
                        ((JunctionBuilding) b).t[0] = true;
                    }
                }
            }
        }
        list_build.add(this);
        for(Building b : list_build) {
            if (b instanceof JunctionBuilding) {
                ((JunctionBuilding) b).rotate_junction();
            }
        }
    }
    public void del_junction(Set<Map<Coords, Building>> list_dict_pos_build) {
        Set<Building> list_build = this.get_build_adj(list_dict_pos_build);
        for(Building b : list_build) {
            if (b instanceof JunctionBuilding) {
                if (b.pos.x == this.pos.x && b.pos.y  < this.pos.y){ //positionnement en bas d'une autre jonction
                    ((JunctionBuilding) b).t[3] = true;
                }
                else if (b.pos.x == this.pos.x && b.pos.y  > this.pos.y){ //positionnement en haut d'une autre jonction
                    ((JunctionBuilding) b).t[1] = true;
                }
                else if (b.pos.x > this.pos.x && b.pos.y == this.pos.y){ //positionnement à gauche d'une autre jonction
                    ((JunctionBuilding) b).t[2] = true;
                }
                else if (b.pos.x < this.pos.x && b.pos.y  == this.pos.y){ //positionnement à droite d'une autre jonction
                    ((JunctionBuilding) b).t[0] = true;
                }
            }
        }
        for(Building b: list_build) {
            if (b instanceof JunctionBuilding) {
                ((JunctionBuilding) b).rotate_junction();
            }
        }
    }

    public Set<Building> get_build_adj(Set<Map<Coords, Building>> list_dict_pos_build) {
        HashSet<Coords> pos = new HashSet<>();
        for(int x = 0; x < 2; x++) {
            pos.add(new Coords(this.pos.x -1+x*2, this.pos.y));
            pos.add(new Coords(this.pos.x, this.pos.y -1+x*2));
        }
        HashSet<Building> list_build = new HashSet<>();
        String[] junctions = this.tools.get_cst_dict("DICT_JUNCTIONS").get(this.name);
        for(Coords p: pos) {
            for(Map<Coords, Building> dict_pos_build: list_dict_pos_build) {
                if(dict_pos_build.containsKey(p)) {
                    Building b = dict_pos_build.get(p);
                    for(int i = 0; i < junctions.length; i++) {
                        if (junctions[i] == b.name) {
                            list_build.add(b);
                        }
                    }
                }
            }
        }
        return list_build;
    }

    public String get_suffix() {
        int s = 0;
        String suffix;
        for(boolean junc : this.t) {
            if (junc) {
                s++;
            }
        }
        if (s != 2) {
            suffix = String.valueOf(s);
        }
        else {
            if (this.t[0] == this.t[2] && this.t[1] == this.t[3]) {
                suffix = "2_0";
            }
            else {
                suffix = "2_1";
            }
        }
        return suffix;
    }
    public void rotate_junction() {
        int s = 0;
        String suffix;
        for(boolean junc : this.t) {
            if (junc) {
                s++;
            }
        }
        System.out.println(s);
        if (s == 1){
            this.angle = Arrays.asList(this.t).indexOf(true)*90;
        }
        else if (s == 3) {
            this.angle = Arrays.asList(this.t).indexOf(false)*90;
        }
        else if (s == 2) {
            if (this.t[0] == this.t[2] && this.t[1] == this.t[3]) {
                this.angle = Arrays.asList(this.t).indexOf(true)*90;
            }
            else {
                if (this.t[0] == this.t[3] == true) {
                    this.angle = 270;
                }
                else {
                    this.angle = Arrays.asList(this.t).indexOf(true)*90;
                }
            }
        }
    }
    public void set_junctions(ArrayList<Boolean> t) {
        int i = 0;
        for(boolean j: t) {
            this.t[i] = j;
            i++;
        }
    }
}

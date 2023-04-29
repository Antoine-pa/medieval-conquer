package com.mygdx.medievalconquer.engine.buildings.init_class;

import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.tools.Tools;

import java.net.JarURLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

public class JunctionBuilding extends Building {
    boolean[] t = {false, false, false, false};
    public JunctionBuilding(Tools tools, String name, int[] size, int[] pos, int angle, int lvl, int life, String kind, Map<String, Integer> stock, String layer) {
        super(tools, name, size, pos, angle, lvl, life, kind, stock, layer);
    }

    public JunctionBuilding(Tools tools, String name, int[] size, int[] pos, int angle, int lvl, int life, String kind, Map<String, Integer> stock, String layer, boolean[] t) {
        super(tools, name, size, pos, angle, lvl, life, kind, stock, layer);
        this.t = t;
    }


    public void add_junction(Set<Map<int[], Building>> list_dict_pos_build) {
        Set<Building> list_build = this.get_build_adj(list_dict_pos_build);
        for(Building b : list_build) {
            if (b.pos[0] <= this.pos[0] && this.pos[0] < b.pos[0] + b.size[0]) {
                if (b.pos[1] < this.pos[1]) {  //positionnement en bas d'une autre jonction
                    this.t[1] = true;
                    if (b instanceof JunctionBuilding) {
                        ((JunctionBuilding) b).t[3] = true;
                    }
                }
                else if (b.pos[1] > this.pos[1]) { //positionnement en haut d'une autre jonction
                    this.t[3] = true;
                    if (b instanceof JunctionBuilding) {
                        ((JunctionBuilding) b).t[1] = true;
                    }
                }
            }
            else if (b.pos[1] <= this.pos[1] && this.pos[1] < b.pos[1] + b.size[1]) {
                if (b.pos[0] > this.pos[0]) {  //positionnement à gauche d'une autre jonction
                    this.t[0] = true;
                    if (b instanceof JunctionBuilding) {
                        ((JunctionBuilding) b).t[2] = true;
                    }
                }
                else if (b.pos[0] < this.pos[0]) { //positionnement à droite d'une autre jonction
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
    public void del_junction(Set<Map<int[], Building>> list_dict_pos_build) {
        Set<Building> list_build = this.get_build_adj(list_dict_pos_build);
        for(Building b : list_build) {
            if (b instanceof JunctionBuilding) {
                if (b.pos[0] == this.pos[0] && b.pos[1]  < this.pos[1]){ //positionnement en bas d'une autre jonction
                    ((JunctionBuilding) b).t[3] = true;
                }
                else if (b.pos[0] == this.pos[0] && b.pos[1]  > this.pos[1]){ //positionnement en haut d'une autre jonction
                    ((JunctionBuilding) b).t[1] = true;
                }
                else if (b.pos[0] > this.pos[0] && b.pos[1] == this.pos[1]){ //positionnement à gauche d'une autre jonction
                    ((JunctionBuilding) b).t[2] = true;
                }
                else if (b.pos[0] < this.pos[0] && b.pos[1]  == this.pos[1]){ //positionnement à droite d'une autre jonction
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

    public Set<Building> get_build_adj(Set<Map<int[], Building>> list_dict_pos_build) {
        HashSet<int[]> pos = new HashSet<>();
        for(int x = 0; x < 2; x++) {
            pos.add(new int[]{this.pos[0] -1+x*2, this.pos[1]});
            pos.add(new int[]{this.pos[0], this.pos[1] -1+x*2});
        }
        HashSet<Building> list_build = new HashSet<>();
        String[] junctions = this.tools.cst_dict("DICT_JUNCTIONS").get(this.name);
        for(int[] p: pos) {
            for(Map<int[], Building> dict_pos_build: list_dict_pos_build) {
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

    public void load() {
        String name = this.name + this.get_suffix();
        /*
        self.img = t.load_img(f"buildings/{self.name}/{name}.png", int(cst("SIZE_CASE")) * self.size[0], int(cst("SIZE_CASE")) * self.size[1], 255)
        self.img = pygame.transform.rotate(self.img, self.angle)
        self.img_alpha = t.load_img(f"buildings/{self.name}/{name}.png", int(cst("SIZE_CASE"))*self.size[0] , int(cst("SIZE_CASE"))*self.size[1], 32)
        self.img_alpha = pygame.transform.rotate(self.img_alpha, self.angle)
        */
    }

    public void rotate_junction() {
        int s = 0;
        String suffix;
        for(boolean junc : this.t) {
            if (junc) {
                s++;
            }
        }
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
        this.load();
    }
}
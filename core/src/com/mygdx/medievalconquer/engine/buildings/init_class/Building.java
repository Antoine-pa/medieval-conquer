package com.mygdx.medievalconquer.engine.buildings.init_class;

import com.mygdx.medievalconquer.engine.tools.Tools;

import java.util.Map;
public class Building {
    public String name;
    public int[] size;
    public int[] pos;
    public int angle;
    public int lvl;
    public int life;
    public String kind;
    public Map<String, Integer> stock;
    public String layer = "0";
    public Tools tools;
    public Building(Tools tools, String name, int[] size, int[] pos, int angle, int lvl, int life, String kind, Map<String, Integer> stock, String layer) {
        this.tools = tools;
        this.name = name;
        this.size = size;
        this.pos = pos;
        this.angle = angle;
        this.lvl = lvl;
        this.life = life;
        this.kind = kind;
        this.stock = stock;
        this.layer = layer;

    }

    public void rotate (int angle) {

    }
}

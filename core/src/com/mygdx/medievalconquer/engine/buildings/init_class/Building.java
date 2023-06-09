package com.mygdx.medievalconquer.engine.buildings.init_class;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.medievalconquer.engine.utils.Tools;
import com.mygdx.medievalconquer.engine.utils.Coords;
import com.mygdx.medievalconquer.engine.utils.Picture;

import com.badlogic.gdx.graphics.Texture;

import java.util.Map;
public class Building {
    public String name;
    public int[] size;
    public Coords pos;
    public int angle;
    public int lvl;
    public int life;
    public String kind;
    public Map<String, Integer> stock;
    public String layer = "0";
    public Tools tools;
    public Texture img;
    public Building(Tools tools, String name, int[] size, Coords pos, int angle, int lvl, int life, String kind, Map<String, Integer> stock, String layer) {
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
    public void display(SpriteBatch batch, float[] pos, boolean alpha) {
        if (alpha) {
            //
        }
        else {
            int size = this.tools.int_cst.get("SIZE_CASE");
            Texture texture = Picture.get(this);
            batch.begin();
            batch.draw(texture, this.pos.x*size-pos[0]*size, this.pos.y*size-pos[1]*size, this.size[0]*size/2, this.size[1]*size/2, this.size[0]*size, this.size[1]*size, 1, 1, this.angle, 0, 0, texture.getWidth(), texture.getWidth(), false, false);
            batch.end();
        }
        //
    }
    public boolean in_window(float[] pos) {
        return (pos[0] <= this.pos.x && this.pos.x <= this.tools.int_cst.get("size_x")/this.tools.int_cst.get("SIZE_CASE")+pos[0] && pos[1] <= this.pos.y && this.pos.y <= this.tools.int_cst.get("size_y")/this.tools.int_cst.get("SIZE_CASE")+pos[1]);
    }
    public void rotate (int angle) {
        this.angle = this.angle + angle;
        this.angle = this.angle%360;
    }

}

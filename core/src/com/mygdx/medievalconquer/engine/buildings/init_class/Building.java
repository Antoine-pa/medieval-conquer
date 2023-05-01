package com.mygdx.medievalconquer.engine.buildings.init_class;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.medievalconquer.engine.tools.Tools;
import com.mygdx.medievalconquer.engine.tools.Coords;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

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
        this.load();
    }
    public boolean display(SpriteBatch batch, float[] pos, boolean alpha) {
        if (this.in_window(pos)) {
            if (alpha) {
                //
            }
            else {
                int size = this.tools.get_cst_value("SIZE_CASE");
                batch.draw(this.img, this.pos.x*size-pos[0]*size, this.pos.y*size-pos[1]*size, this.size[0]*size, this.size[1]*size);
            }
            //
            return true;
        }
        return false;
    }
    public boolean in_window(float[] pos) {
        return (pos[0] <= this.pos.x && this.pos.x <= this.tools.get_cst_value("size_x")/this.tools.get_cst_value("SIZE_CASE")+pos[0] && pos[1] <= this.pos.y && this.pos.y <= this.tools.get_cst_value("size_y")/this.tools.get_cst_value("SIZE_CASE")+pos[1]);
    }
    public void load() {
        this.img = new Texture(Gdx.files.internal("buildings/" + this.name + "/" + this.name + ".png"));
    }
    public void rotate (int angle) {
        this.angle = this.angle + angle;
        this.angle = this.angle%360;
        this.load();
    }

}

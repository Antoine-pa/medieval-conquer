package com.mygdx.medievalconquer.engine;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.buildings.init_class.ProductionBuilding;
import com.mygdx.medievalconquer.engine.layout.Layout;
import com.mygdx.medievalconquer.engine.menu.Menu;
import com.mygdx.medievalconquer.engine.tools.Tools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Game {
    public Tools t;
    public Layout layout;
    public Menu menu;

    public Game(Tools t) {
        this.t = t;
        this.layout = new Layout(t);
        this.menu = new Menu(t);
        this.menu.update_buttons(this.layout);
        //this.reload_images();
    }

    public void display (SpriteBatch batch, ShapeRenderer shape) { //screen (type ?)
        if (this.menu.action.startsWith("layout") | this.menu.action.startsWith("edit")) {
            this.layout.display(batch, shape);
        }
        //this.menu.display(this.layout);
    }
    public void shifting (int x, int y) {
        this.layout.pos[0] += x;
        this.layout.pos[1] += y;
    }

    public void update_production () {
        for(Map.Entry<String, HashMap<String, HashSet<Building>>> l : this.layout.dict_kind_build.entrySet()) {
            for(Building build: l.getValue().get("production")) {
                ((ProductionBuilding) build).update();
            }
        }
    }
}
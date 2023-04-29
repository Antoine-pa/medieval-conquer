package com.mygdx.medievalconquer.engine;

import com.mygdx.medievalconquer.engine.layout.Layout;
import com.mygdx.medievalconquer.engine.menu.Menu;
import com.mygdx.medievalconquer.engine.tools.Tools;
public class Game {
    public Tools t;
    public Layout layout;
    public Menu menu;

    public Game(Tools t) {
        this.t = t;
        this.layout = new Layout(t);
        this.menu = new Menu(t);
    }

    public void display () { //screen (type ?)
        this.layout.display();
    }

    public void reload_images () {

    }

    public void shifting (int x, int y) {
        this.layout.pos[0] += x;
        this.layout.pos[1] += y;
    }

    public void update_production () {
    }
}
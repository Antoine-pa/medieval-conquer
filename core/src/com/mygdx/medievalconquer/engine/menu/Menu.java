package com.mygdx.medievalconquer.engine.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.medievalconquer.engine.layout.Layout;
import com.mygdx.medievalconquer.engine.utils.*;

import java.util.*;

public class Menu {
    public String action = "layout";
    private EditMenu edit_menu;
    private ResourcesMenu resources_menu;
    private SettingsMenu settings_menu;
    public ButtonsMenu buttons_menu;
    public Tools tools;
    public Menu (Tools tools) {
        this.tools = tools;
        this.edit_menu = new EditMenu(this.tools);
        this.buttons_menu = new ButtonsMenu(this.tools);
        this.resources_menu = new ResourcesMenu(this.tools);
        this.settings_menu = new SettingsMenu(this.tools);
    }

    public void click (int[] pos, Layout layout) {
        if (this.action == "edit-add") { //si il ajoute des bâtiments
            this.edit_menu.click_add(pos, layout);
        } else if (this.action == "edit-sup") {
            this.edit_menu.click_sup(pos, layout);
        }
        String name_but = null;
        for(Map.Entry<String, Button> button : this.buttons_menu.buttons.entrySet()) {
            if (button.getValue().collidepoint(pos)) {
                name_but = button.getKey();
            }
        }
        if (name_but != null) {
            this.edit_menu.click_button(layout, this, name_but);
        }
    }

    public void display(SpriteBatch batch, ShapeRenderer shape, Layout layout) {
        if (this.action.startsWith("edit")) {
            this.edit_menu.display(batch, shape, layout, this.action);
        } else if (this.action == "settings") {
            this.settings_menu.display();
        } else if (this.action == "resources") {
            this.resources_menu.display(batch, shape);
        }
        this.buttons_menu.display(shape, batch);
    }
    public void set_action(String action, Layout layout){
        if (this.action != action) {
            this.action = action;
        } else {
            this.action = "layout";
        }
        this.edit_menu.update_mem_tamp(this.action);
        this.buttons_menu.update_buttons(layout, this.action);
    }

}

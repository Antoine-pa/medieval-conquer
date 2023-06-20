package com.mygdx.medievalconquer.engine.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.medievalconquer.engine.layout.Layout;
import com.mygdx.medievalconquer.engine.utils.Button;
import com.mygdx.medievalconquer.engine.utils.Tools;

import java.util.HashMap;
import java.util.Map;

public class ButtonsMenu {
    private Tools tools;
    public Map<String, Button> buttons = new HashMap<>();
    public ButtonsMenu(Tools tools) {
        this.tools = tools;
    }

    public void display(ShapeRenderer shape, SpriteBatch batch) {
        for (Map.Entry<String, Button> button: this.buttons.entrySet()) {
            button.getValue().display(shape, batch, this.tools);
        }
    }
    public void update_buttons(Layout layout, String action) {
        this.buttons.clear();
        if (action == "layout" | action.startsWith("edit")) {
            this.buttons.put("change_layer", new Button(new float[]{50f, 50f, 150f, 100f}, "layer", 1));
        }
        if(layout.layer == "-1") {
            this.buttons.put("change_transparency", new Button(new float[]{50f, 150f, 150f, 200f}, "trans", 1));
        }
        if (action.startsWith("edit")) {
            float start_menu_x = (float) this.tools.array_cst.get("MENU_EDIT_POS")[0]+this.tools.array_cst.get("POS_BUTTONS_MENU_EDIT")[0];
            float start_menu_y = (float) this.tools.array_cst.get("POS_BUTTONS_MENU_EDIT")[1];
            float end_menu_x = (float) this.tools.array_cst.get("MENU_EDIT_POS")[0]+this.tools.array_cst.get("POS_BUTTONS_MENU_EDIT")[0]+this.tools.int_cst.get("LONG_BLOCK_MENU_EDIT");
            float end_menu_y = (float) this.tools.array_cst.get("POS_BUTTONS_MENU_EDIT")[1]+this.tools.int_cst.get("LONG_BLOCK_MENU_EDIT");
            if (action == "edit-add") {
                buttons.put("edit_angle", new Button(new float[]{start_menu_x, start_menu_y, end_menu_x, end_menu_y}, "a", 1));
                buttons.put("edit_annulation", new Button(new float[]{start_menu_x+this.tools.array_cst.get("MENU_EDIT_POS")[3], start_menu_y, end_menu_x+this.tools.array_cst.get("MENU_EDIT_POS")[3], end_menu_y}, "n", 1));
                buttons.put("edit_validation", new Button(new float[]{start_menu_x+2*this.tools.array_cst.get("MENU_EDIT_POS")[3], start_menu_y, end_menu_x+2*this.tools.array_cst.get("MENU_EDIT_POS")[3], end_menu_y}, "y", 1));
            } else if (action == "edit") {
                buttons.put("edit_construction", new Button(new float[]{start_menu_x, start_menu_y, end_menu_x, end_menu_y}, "c", 1));
                buttons.put("edit_destruction", new Button(new float[]{start_menu_x+this.tools.array_cst.get("MENU_EDIT_POS")[3], start_menu_y, end_menu_x+this.tools.array_cst.get("MENU_EDIT_POS")[3], end_menu_y}, "d", 1));
                buttons.put("edit_validation", new Button(new float[]{start_menu_x+2*this.tools.array_cst.get("MENU_EDIT_POS")[3], start_menu_y, end_menu_x+2*this.tools.array_cst.get("MENU_EDIT_POS")[3], end_menu_y}, "y", 1));
            } else if (action == "edit-sup") {
                buttons.put("edit_annulation", new Button(new float[]{start_menu_x+this.tools.array_cst.get("MENU_EDIT_POS")[3], start_menu_y, end_menu_x+this.tools.array_cst.get("MENU_EDIT_POS")[3], end_menu_y}, "n", 1));
                buttons.put("edit_validation", new Button(new float[]{start_menu_x+2*this.tools.array_cst.get("MENU_EDIT_POS")[3], start_menu_y, end_menu_x+2*this.tools.array_cst.get("MENU_EDIT_POS")[3], end_menu_y}, "y", 1));
            }
        }
    }
}

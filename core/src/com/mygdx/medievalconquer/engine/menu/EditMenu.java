package com.mygdx.medievalconquer.engine.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.buildings.init_class.JunctionBuilding;
import com.mygdx.medievalconquer.engine.layout.Layout;
import com.mygdx.medievalconquer.engine.utils.*;

import java.util.*;

public class EditMenu {
    Tools tools;
    private Map<String, Integer> res_tamp;
    private String build_tamp = "";
    private int build_angle_tamp;
    private Map<String, Map<Coords, Building>> edit_add_tamp;
    private Map<String, Set<Building>> edit_sup_tamp;

    public EditMenu(Tools tools) {
        this.tools = tools;
    }
    public void click_add(int[] pos, Layout layout) {
        int[] pos_menu = {Math.max(0, this.tools.array_cst.get("MENU_EDIT_POS")[0] - Const.LIST_BUILD_MENU_EDIT.get(layout.layer).length * this.tools.array_cst.get("MENU_EDIT_POS")[3]), this.tools.array_cst.get("MENU_EDIT_POS")[1]};
        if (pos_menu[0] < pos[0] & pos_menu[1] < pos[1]) { //si le clic est dans le menu d 'edition
            if (pos[0] < this.tools.array_cst.get("MENU_EDIT_POS")[0]) { //si il click dans les bâtiments et non dans les bouttons
                //sélection bâtiment
                pos[0] = pos[0] - pos_menu[0];
                pos[1] = pos[1] - pos_menu[1];
                int coord_case = pos[0] / this.tools.array_cst.get("MENU_EDIT_POS")[3]; //on calcul les coordonnées de la case
                int index = Const.LIST_BUILD_MENU_EDIT.get(layout.layer).length - coord_case - 1 ; //on calcul l 'index de la case dans la liste des bâtiments
                if (this.build_tamp != Const.LIST_BUILD_MENU_EDIT.get(layout.layer)[index]) {
                    this.build_tamp = Const.LIST_BUILD_MENU_EDIT.get(layout.layer)[index]; //on ajoute le bâtiment à la mémoire tampon dans la section réservé au bâtiment séléctinné pour de la construction
                } else {
                    this.build_tamp = "";
                }
            }
        } else { //si le clic est sur la map
            Coords place = layout.get_case(pos); //récupération des cases
            if (this.build_tamp != "") { //si un bâtiment à construire est séléctionné
                //construction bâtiment
                Building build = BuildingFactory.createBuilding(this.build_tamp, this.tools, place); //on instancie le bâtiment
                build.rotate(this.build_angle_tamp);
                int p = layout.check_pos(build, this.edit_add_tamp);
                if (p == 0 & this.tools.check_stock(this.tools.get_cost(build.name, 1), this.res_tamp).size() == 0) {
                    //on vérifie que la place est libre et que les ressources sont suffisantes
                    for(int x=build.pos.x; x < build.pos.x + build.size[0]; x++) {
                        for(int y=build.pos.y; y < build.pos.y + build.size[1]; y++){
                            Coords t = new Coords(x, y);
                            this.edit_add_tamp.get(layout.layer).put(t, build); //on ajoute le bâtiment dans la mémoire tampon
                        }
                    }
                    for(Map.Entry<String, Integer> res: this.tools.get_cost(build.name, 1).entrySet()) {
                        String key = res.getKey();
                        int value = res.getValue();
                        if (!this.res_tamp.containsKey(key)){
                            this.res_tamp.put(key, 0);
                        }
                        this.res_tamp.put(key, this.res_tamp.get(key) + value);
                    }
                    if (build instanceof JunctionBuilding) {
                        Set<Map<Coords, Building>> l = new HashSet<>();
                        l.add(layout.dict_pos_build.get(layout.layer));
                        l.add(this.edit_add_tamp.get(layout.layer));
                        ((JunctionBuilding) build).add_junction(l);
                    }
                } else if (p == 2) {  //annuler la construction d 'un batiment
                    HashSet<Building> builds = new HashSet<>();
                    for(Map.Entry<Coords, Building> bu : this.edit_add_tamp.get(layout.layer).entrySet()){
                        Building b = bu.getValue();
                        if (!builds.contains(b)) {
                            builds.add(b);
                            for(int x = b.pos.x; x < b.pos.x + b.size[0]; x++) {
                                for(int y = b.pos.y; y < b.pos.y + b.size[1]; y++) {
                                    Coords coords = new Coords(x, y);
                                    if (coords == place) {
                                        build = null;
                                        build = b;
                                    }
                                }
                            }
                        }
                    }
                    if (build instanceof JunctionBuilding){
                        Set<Map<Coords, Building>> l = new HashSet<>();
                        l.add(layout.dict_pos_build.get(layout.layer));
                        l.add(this.edit_add_tamp.get(layout.layer));
                        ((JunctionBuilding) build).del_junction(l);
                    }
                    for(Map.Entry<String, Integer> res: this.tools.get_cost(build.name, 1).entrySet()){
                        if (this.res_tamp.containsKey(res.getKey())) {
                            this.res_tamp.put(res.getKey(), this.res_tamp.get(res.getKey()) - res.getValue());
                            if (this.res_tamp.get(res.getKey()) == 0) {
                                this.res_tamp.remove(res.getKey());
                            }
                        }
                    }
                    for(int x = build.pos.x; x<build.pos.x + build.size[0]; x++){
                        for(int y = build.pos.y; y<build.pos.y + build.size[1]; y++){
                            Coords c = new Coords(x, y);
                            this.edit_add_tamp.get(layout.layer).remove(c);
                        }
                    }
                    build = null;
                } else {
                    build = null;
                }
            }
        }
    }
    public void click_button(Layout layout, Menu menu, String name_but) {
        if (name_but == "edit_validation") {
            HashSet<Building> builds = new HashSet<>();
            for(Map.Entry<Coords, Building> b: this.edit_add_tamp.get(layout.layer).entrySet()) {
                if (!builds.contains(b.getValue())) {
                    layout.add_build(b.getValue());
                    builds.add(b.getValue());
                }
            }
            for(Building build : this.edit_sup_tamp.get(layout.layer)) {
                if (build instanceof JunctionBuilding) {
                    Set<Map<Coords, Building>> l = new HashSet<>();
                    l.add(layout.dict_pos_build.get(layout.layer));
                    l.add(this.edit_add_tamp.get(layout.layer));
                    ((JunctionBuilding) build).del_junction(l);
                }
                layout.sup_build(build);
            }
            for(Map.Entry<String, Integer> res: this.res_tamp.entrySet()) {
                this.tools.change_res(res.getKey(), -res.getValue());
            }
            layout.update_galleries_links();
            layout.save_layout();
            menu.set_action("edit", layout);
        }
        if (name_but == "edit_annulation" | name_but == "change_layer") {
            //del mem_tamp
            menu.set_action("edit", layout);
        }
        if (name_but == "edit_construction") {
            menu.set_action("edit-add", layout);
        }
        if (name_but == "edit_destruction") {
            menu.set_action("edit-sup", layout);
        }
        if (name_but == "change_layer") {
            if (layout.layer == "0") {
                layout.layer = "-1";
            }
            else {
                layout.layer = "0";
            }
            menu.buttons_menu.update_buttons(layout, menu.action);
        }
        if (name_but == "change_transparency") {
            layout.alpha = ! layout.alpha;
        }
    }
    public void click_sup(int[] pos, Layout layout) {
        //destruction d'un bâtiment
        Coords place = layout.get_case(pos); //récupération des cases
        if (layout.dict_pos_build.get(layout.layer).containsKey(place)) {
            Building build = layout.dict_pos_build.get(layout.layer).get(place);
            if (this.edit_sup_tamp.get(layout.layer).contains(build)) {
                this.edit_sup_tamp.get(layout.layer).remove(build);
            }
            else {
                this.edit_sup_tamp.get(layout.layer).add(build);
            }
        }
    }

    public void display(SpriteBatch batch, ShapeRenderer shape, Layout layout, String action) {
        int size_case = this.tools.int_cst.get("SIZE_CASE");
        int size_x = this.tools.int_cst.get("size_x");
        int size_y = this.tools.int_cst.get("size_y");

        Set<Building> builds = new HashSet<>();
        for(Map.Entry<Coords, Building> b: this.edit_add_tamp.get(layout.layer).entrySet()) {
            Building build = b.getValue();
            if (!builds.contains(build)) {
                builds.add(build);
                build.display(batch, layout.pos, false);
                if (build.in_window(layout.pos)) {
                    Graphics.rect(shape, Colors.grey_yellow, (build.pos.x - layout.pos[0])*size_case, (build.pos.y - layout.pos[1])*size_case, size_case*build.size[0], size_case*build.size[1], false, 3);
                }
            }
        }
        builds.clear();
        for(Building b: this.edit_sup_tamp.get(layout.layer)) {
            if (b.in_window(layout.pos)) {
                Graphics.rect(shape, Colors.red_orange, (int) ((b.pos.x - layout.pos[0])*size_case), (int) ((b.pos.y - layout.pos[1])*size_case), size_case*b.size[0], size_case*b.size[1], false, 3);
            }
        }
        int[] menu_edit_pos = this.tools.array_cst.get("MENU_EDIT_POS");
        Graphics.rect(shape, Colors.grey_white, menu_edit_pos[0], menu_edit_pos[1], menu_edit_pos[2], menu_edit_pos[3]);
        if (action == "edit-add") {
            int gap_block_col_menu_edit = this.tools.int_cst.get("GAP_BLOCK_COL_MENU_EDIT");
            int long_block_menu_edit = this.tools.int_cst.get("LONG_BLOCK_MENU_EDIT");
            Graphics.rect(shape, Colors.grey_white, Math.max(0, menu_edit_pos[0] - Const.LIST_BUILD_MENU_EDIT.get(layout.layer).length*menu_edit_pos[3]), menu_edit_pos[1], this.tools.int_cst.get("size_x")-menu_edit_pos[2], menu_edit_pos[3]);
            Graphics.line(shape, Colors.black, menu_edit_pos[0], menu_edit_pos[1], menu_edit_pos[0], this.tools.int_cst.get("size_y"), 1);
            int[] pos_menu = {this.tools.int_cst.get("size_x") - 4*this.tools.array_cst.get("MENU_EDIT_POS")[3], this.tools.int_cst.get("size_y") - (this.res_tamp.size()+1)*this.tools.array_cst.get("MENU_EDIT_POS")[3], 4*this.tools.array_cst.get("MENU_EDIT_POS")[3], this.res_tamp.size()*this.tools.array_cst.get("MENU_EDIT_POS")[3]};
            Map<String, Integer> check = new HashMap<>();
            for(int i = 0; i < Const.LIST_BUILD_MENU_EDIT.get(layout.layer).length; i++) {
                String build = Const.LIST_BUILD_MENU_EDIT.get(layout.layer)[i];
                if (this.build_tamp == build) {
                    Graphics.rect(shape, Colors.grey_yellow, menu_edit_pos[0] - (i+1)*menu_edit_pos[3], menu_edit_pos[1], menu_edit_pos[3]-1, menu_edit_pos[3]);
                    Map<String, Integer> cost = this.tools.get_cost(build, 1);
                    Graphics.rect(shape, Colors.grey_white, pos_menu[0] - pos_menu[2], menu_edit_pos[1]-cost.size()*menu_edit_pos[3], pos_menu[2], cost.size()*menu_edit_pos[3]);
                    Graphics.line(shape, Colors.black, pos_menu[0]-1, menu_edit_pos[1] - cost.size()*menu_edit_pos[3], pos_menu[0]-1, menu_edit_pos[1]);
                    check = this.tools.check_stock(cost, this.res_tamp);
                    int j = 0;
                    for(Map.Entry<String, Integer> res: cost.entrySet()) {
                        Color color;
                        if (check.containsKey(res.getKey())) {
                            color = Colors.red;
                        }
                        else {
                            color = Colors.black;
                        }
                        int[] pos = {pos_menu[0] - pos_menu[2] + menu_edit_pos[3] / 4, menu_edit_pos[1] - cost.size() * (menu_edit_pos[3]) + j * menu_edit_pos[3] + menu_edit_pos[3] / 4};
                        Graphics.text(batch, res.getKey()+" : "+res.getValue(), color, pos[0], pos[1]);
                        j++;
                    }
                }
                String b = build;
                if (Arrays.stream(Const.LIST_JUNCTION_BUILDING).anyMatch(build::equals)) {
                    build = build+"2_0";
                }
                Graphics.blit(batch, Picture.get(b, build), menu_edit_pos[0] - (i+1)*menu_edit_pos[3] + gap_block_col_menu_edit, menu_edit_pos[1] + gap_block_col_menu_edit, long_block_menu_edit, long_block_menu_edit);
                Graphics.line(shape, Colors.black, menu_edit_pos[0] - (i+1)*menu_edit_pos[3], menu_edit_pos[1], menu_edit_pos[0] - (i+1)*menu_edit_pos[3], size_y);
            }
            Graphics.line(shape, Colors.black, menu_edit_pos[0] - Const.LIST_BUILD_MENU_EDIT.get(layout.layer).length * menu_edit_pos[3], size_y - menu_edit_pos[3], size_x, size_y - menu_edit_pos[3]);
            if (pos_menu[3] != 0) {
                Graphics.rect(shape, Colors.grey_white, pos_menu[0], pos_menu[1], pos_menu[2], pos_menu[3]);
                int i = 0;
                for(Map.Entry<String, Integer> res: this.res_tamp.entrySet()) {
                    Color color;
                    if (!check.isEmpty() && check.containsKey(res.getKey())) {
                        color = Colors.red;
                    }
                    else {
                        color = Colors.black;
                    }
                    String text = res.getKey() + " : " + res.getValue() + "/" + this.tools.get_res(res.getKey()).get("stock");
                    Graphics.text(batch, text, color, pos_menu[0]+menu_edit_pos[3]/4, pos_menu[1]+i*menu_edit_pos[3]+menu_edit_pos[3]/4);
                    i++;
                }
            }
        }
    }
    public void update_mem_tamp(String action) {
        if (action.startsWith("edit")) {
            if (this.res_tamp == null) {
                this.res_tamp = new HashMap<>();
            }
            else {
                this.res_tamp.clear();
            }
            if (this.edit_sup_tamp == null) {
                this.edit_sup_tamp = new HashMap<>();
            }
            else {
                this.edit_sup_tamp.clear();
            }
            if (this.edit_add_tamp == null) {
                this.edit_add_tamp = new HashMap<>();
            }
            else {
                this.edit_add_tamp.clear();
            }
            this.build_tamp = "";
            this.build_angle_tamp = 0;
            this.edit_sup_tamp.put("-1", new HashSet<>());
            this.edit_sup_tamp.put("0", new HashSet<>());
            this.edit_add_tamp.put("-1", new HashMap<>());
            this.edit_add_tamp.put("0", new HashMap<>());
        }
        else {
            this.res_tamp = null;
            this.build_tamp = "";
            this.build_angle_tamp = 0;
            this.edit_sup_tamp = null;
            this.edit_add_tamp = null;
        }
    }
}

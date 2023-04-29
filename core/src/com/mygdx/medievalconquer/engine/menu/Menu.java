package com.mygdx.medievalconquer.engine.menu;

import com.mygdx.medievalconquer.engine.layout.Layout;
import com.mygdx.medievalconquer.engine.tools.Button;
import com.mygdx.medievalconquer.engine.tools.Tools;
import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.buildings.init_class.JunctionBuilding;

import java.util.*;

public class Menu {
    public String action = "Layout";
    public Map<String, Button> buttons = new HashMap<>();
    public Tools t;

    private Map<String, Integer> res_tamp;
    private Map<String, String> build_tamp;
    private int build_angle_tamp;
    private Map<String, Map<int[], Building>> edit_add_tamp;
    private Map<String, Set<Building>> edit_sup_tamp;
    public Menu (Tools t) {
        this.t = t;
    }

    public void click (int[] pos, Layout layout) {
        if (this.action == "edit-add") { //si il ajoute des bâtiments
            int[] pos_menu = {Math.max(0, this.t.cst_array("MENU_EDIT_POS")[0] - this.t.cst_dict("LIST_BUILD_MENU_EDIT").get(layout.layer).length * this.t.cst_array("MENU_EDIT_POS")[3]), this.t.cst_array("MENU_EDIT_POS")[1]};
            if (pos_menu[0] < pos[0] && pos_menu[ 1] <pos[1]) { //si le clic est dans le menu d 'edition
                if (pos[0] < this.t.cst_array("MENU_EDIT_POS")[0]) { //si il click dans les bâtiments et non dans les bouttons
                    //sélection bâtiment
                    pos[0] = pos[0] - pos_menu[0];
                    pos[1] = pos[1] - pos_menu[1];
                    int coord_case = pos[0] / this.t.cst_array("MENU_EDIT_POS")[3]; //on calcul les coordonnées de la case
                    int index = this.t.cst_dict("LIST_BUILD_MENU_EDIT").get(layout.layer).length - coord_case - 1 ; //on calcul l 'index de la case dans la liste des bâtiments
                    if (this.build_tamp.get("build") != this.t.cst_dict("LIST_BUILD_MENU_EDIT").get(layout.layer)[index]) {
                        this.build_tamp.put("build", this.t.cst_dict("LIST_BUILD_MENU_EDIT").get(layout.layer)[index]); //on ajoute le bâtiment à la mémoire tampon dans la section réservé au bâtiment séléctinné pour de la construction
                    }
                    else {
                        this.build_tamp.remove("build");
                    }
                }
            }
            else { //si le clic est sur la map
                int[] place = layout.get_case(pos); //récupération des cases
                if (this.build_tamp.containsKey("build")) { //si un bâtiment à construire est séléctionné
                    //construction bâtiment
                    Building build = this.t.buildings.get(this.build_tamp.get("build")).apply(place); //on instanciele bâtiment
                    build.rotate(this.build_angle_tamp);
                    int p = layout.check_pos(build, this.edit_add_tamp);
                    if (p == 0 && this.t.check_stock(t.cost(build.name, 1), this.res_tamp).size() == 0) {
                        //on vérifie que la place est libre et que les ressources sont suffisantes
                        for(int x=build.pos[0]; x < build.pos[0] + build.size[0]; x++) {
                            for(int y=build.pos[1]; y < build.pos[1] + build.size[1]; y++){
                                int[] t = {x, y};
                                this.edit_add_tamp.get(layout.layer).put(t, build); //on ajoute le bâtiment dans la mémoire tampon
                            }
                        }
                        for(Map.Entry<String, Integer> res: this.t.cost(build.name, 1).entrySet()) {
                            String key = res.getKey();
                            int value = res.getValue();
                            if (!this.res_tamp.containsKey(key)){
                                this.res_tamp.put(key, 0);
                            }
                            this.res_tamp.put(key, this.res_tamp.get(key) + value);
                        }
                        if (build instanceof JunctionBuilding) {
                            //build.add_junction([_map.dict_pos_build[_map.layer], self.mem_tamp["list_build"][_map.layer]["add"]])
                        }
                    }
                    else if (p == 2) {  //annuler la construction d 'un batiment
                        HashSet<Building> builds = new HashSet<>();
                        for(Map.Entry<int[], Building> bu : this.edit_add_tamp.get(layout.layer).entrySet()){
                            Building b = bu.getValue();
                            if (!builds.contains(b)) {
                                builds.add(b);
                                for(int x = b.pos[0]; x < b.pos[0] + b.size[0]; x++) {
                                    for(int y = b.pos[1]; y < b.pos[1] + b.size[1]; y++) {
                                        int[] coords = {x, y};
                                        if (coords == place) {
                                            build = null;
                                            build = b;
                                        }
                                    }
                                }
                            }
                        }
                        if (build instanceof JunctionBuilding){
                            //build.del_junction([_map.dict_pos_build[_map.layer], self.mem_tamp["list_build"][_map.layer]["add"]])
                        }
                        for(Map.Entry<String, Integer> res: this.t.cost(build.name, 1).entrySet()){
                            if (this.res_tamp.containsKey(res.getKey())) {
                                this.res_tamp.put(res.getKey(), this.res_tamp.get(res.getKey()) - res.getValue());
                                if (this.res_tamp.get(res.getKey()) == 0) {
                                    this.res_tamp.remove(res.getKey());
                                }
                            }
                        }
                        for(int x = build.pos[0]; x<build.pos[0] + build.size[0]; x++){
                            for(int y = build.pos[1]; y<build.pos[1] + build.size[1]; y++){
                                int[] c = {x, y};
                                this.edit_add_tamp.get(layout.layer).remove(c);
                            }
                        }
                        build = null;
                    }
                    else {
                        build = null;
                    }
                }
            }
        }
        else if (this.action == "edit-sup") {
            //destruction d'un bâtiment
            int[] place = layout.get_case(pos); //récupération des cases
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
        for(Map.Entry<String, Button> button : this.buttons.entrySet()) {
            if (button.getValue().collidepoint(pos)) {
                if (button.getKey() == "edit-validation") {
                    if (this.action.startsWith("edit")) {
                        HashSet<Building> builds = new HashSet<>();
                        for(Map.Entry<int[], Building> b: this.edit_add_tamp.get(layout.layer).entrySet()) {
                            if (!builds.contains(b.getValue())) {
                                layout.add_build(b.getValue());
                                builds.add(b.getValue());
                            }
                        }
                        for(Building build : this.edit_sup_tamp.get(layout.layer)) {
                            if (build instanceof JunctionBuilding) {
                                Set<Map<int[], Building>> l = new HashSet<>();
                                l.add(layout.dict_pos_build.get(layout.layer));
                                l.add(this.edit_add_tamp.get(layout.layer));
                                ((JunctionBuilding) build).del_junction(l);
                            }
                            layout.sup_build(build);
                        }
                        for(Map.Entry<String, Integer> res: this.res_tamp.entrySet()) {
                            this.t.set_res(res.getKey(), this.t.get_res(res.getKey()).get("stock") - res.getValue());
                        }
                        layout.update_galleries_links();
                        layout.save_layout();
                    }
                    this.set_action("edit", layout);
                }
                if (button.getKey() == "edit-annulation" | button.getKey() == "change-layer") {
                    //del mem_tamp
                    this.set_action("edit", layout);
                }
                if (button.getKey() == "edit-construction") {
                    this.set_action("edit-add", layout);
                }
                if (button.getKey() == "edit-destruction") {
                    this.set_action("edit-sup", layout);
                }
                if (button.getKey() == "change-layer") {
                    if (layout.layer == "0") {
                        layout.layer = "-1";
                    }
                    else {
                        layout.layer = "0";
                    }
                    this.update_buttons(layout);
                }
                if (button.getKey() == "change_transparency") {
                    layout.alpha = ! layout.alpha;
                }
            }
        }
    }

    public void display(Layout layout) {

    }

    public void display_edit(Layout layout) {

    }
    public void display_resources(Layout layout) {

    }

    public void display_settings(Layout layout) {

    }
    public void set_action(String action, Layout layout){

    }

    public void update_buttons (Layout layout) {

    }
    public void update_mem_tamp () {

    }
}

package com.mygdx.medievalconquer.engine.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.medievalconquer.engine.layout.Layout;
import com.mygdx.medievalconquer.engine.tools.Button;
import com.mygdx.medievalconquer.engine.tools.Tools;
import com.mygdx.medievalconquer.engine.tools.Coords;
import com.mygdx.medievalconquer.engine.buildings.init_class.Building;
import com.mygdx.medievalconquer.engine.buildings.init_class.JunctionBuilding;
import com.mygdx.medievalconquer.engine.tools.BuildingFactory;

import java.util.*;

public class Menu {
    public String action = "layout";
    public Map<String, Button> buttons = new HashMap<>();
    public Tools tools;
    private Map<String, Integer> res_tamp;
    private String build_tamp = "";
    private int build_angle_tamp;
    private Map<String, Map<Coords, Building>> edit_add_tamp;
    private Map<String, Set<Building>> edit_sup_tamp;
    public Menu (Tools tools) {
        this.tools = tools;
    }

    public void click (int[] pos, Layout layout) {
        if (this.action == "edit-add") { //si il ajoute des bâtiments
            int[] pos_menu = {Math.max(0, this.tools.get_cst_array("MENU_EDIT_POS")[0] - this.tools.get_cst_dict("LIST_BUILD_MENU_EDIT").get(layout.layer).length * this.tools.get_cst_array("MENU_EDIT_POS")[3]), this.tools.get_cst_array("MENU_EDIT_POS")[1]};
            if (pos_menu[0] < pos[0] && pos_menu[ 1] <pos[1]) { //si le clic est dans le menu d 'edition
                if (pos[0] < this.tools.get_cst_array("MENU_EDIT_POS")[0]) { //si il click dans les bâtiments et non dans les bouttons
                    //sélection bâtiment
                    pos[0] = pos[0] - pos_menu[0];
                    pos[1] = pos[1] - pos_menu[1];
                    int coord_case = pos[0] / this.tools.get_cst_array("MENU_EDIT_POS")[3]; //on calcul les coordonnées de la case
                    int index = this.tools.get_cst_dict("LIST_BUILD_MENU_EDIT").get(layout.layer).length - coord_case - 1 ; //on calcul l 'index de la case dans la liste des bâtiments
                    if (this.build_tamp != this.tools.get_cst_dict("LIST_BUILD_MENU_EDIT").get(layout.layer)[index]) {
                        this.build_tamp = this.tools.get_cst_dict("LIST_BUILD_MENU_EDIT").get(layout.layer)[index]; //on ajoute le bâtiment à la mémoire tampon dans la section réservé au bâtiment séléctinné pour de la construction
                    }
                    else {
                        this.build_tamp = "";
                    }
                }
            }
            else { //si le clic est sur la map
                Coords place = layout.get_case(pos); //récupération des cases
                if (this.build_tamp != "") { //si un bâtiment à construire est séléctionné
                    //construction bâtiment
                    Building build = BuildingFactory.createBuilding(this.build_tamp, this.tools, place); //on instancie le bâtiment
                    build.rotate(this.build_angle_tamp);
                    int p = layout.check_pos(build, this.edit_add_tamp);
                    if (p == 0 && this.tools.check_stock(this.tools.get_cost(build.name, 1), this.res_tamp).size() == 0) {
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
                            //build.add_junction([_map.dict_pos_build[_map.layer], self.mem_tamp["list_build"][_map.layer]["add"]])
                        }
                    }
                    else if (p == 2) {  //annuler la construction d 'un batiment
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
                            //build.del_junction([_map.dict_pos_build[_map.layer], self.mem_tamp["list_build"][_map.layer]["add"]])
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
                    }
                    else {
                        build = null;
                    }
                }
            }
        }
        else if (this.action == "edit-sup") {
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
        for(Map.Entry<String, Button> button : this.buttons.entrySet()) {
            if (button.getValue().collidepoint(pos)) {
                if (button.getKey() == "edit-validation") {
                    if (this.action.startsWith("edit")) {
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
                            this.tools.set_res(res.getKey(), this.tools.get_res(res.getKey()).get("stock") - res.getValue());
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

    public void display(SpriteBatch batch, Layout layout) {
        if (this.action == "layout") {
            return;
        }
        else if (this.action.startsWith("edit")) {
            this.display_edit(batch, layout);
        }
        else if (this.action == "settings") {
            this.display_settings(layout);
        }
        else if (this.action == "ressources") {
            this.display_resources(layout);
        }
        for (Map.Entry<String, Button> button: this.buttons.entrySet()) {
            button.getValue().display();
        };
    }

    public void display_edit(SpriteBatch batch, Layout layout) {
        Set<Building> builds = new HashSet<>();
        for(Map.Entry<Coords, Building> b: this.edit_add_tamp.get(layout.layer).entrySet()) {
            if (!builds.contains(b.getValue())) {
                builds.add(b.getValue());
                boolean d = b.getValue().display(batch, layout.pos, false);
                if (d) {
                    //
                }
            }
        }
        builds.clear();
        for(Building b: this.edit_sup_tamp.get(layout.layer)) {
            if (b.in_window(layout.pos)) {
                //
            }
        }
        //
        if (this.action == "edit-add") {
            //
            int[] pos_menu = {this.tools.get_cst_value("size_x") - 4*this.tools.get_cst_array("MENU_EDIT_POS")[3], this.tools.get_cst_value("size_y") - (this.res_tamp.size()+1)*this.tools.get_cst_array("MENU_EDIT_POS")[3], 4*this.tools.get_cst_array("MENU_EDIT_POS")[3], this.res_tamp.size()*this.tools.get_cst_array("MENU_EDIT_POS")[3]};
            Map<String, Integer> check = new HashMap<>();
            for(int i = 0; i<this.tools.get_cst_dict("LIST_BUILD_MENU_EDIT").get(layout.layer).length; i++) {
                String build = this.tools.get_cst_dict("LIST_BUILD_MENU_EDIT").get(layout.layer)[i];
                if (this.build_tamp == build) {
                    //
                    Map<String, Integer> cost = this.tools.get_cost("build", 1);
                    //
                    check = this.tools.check_stock(cost, this.res_tamp);
                    int j = 0;
                    for(Map.Entry<String, Integer> res: cost.entrySet()) {
                        int[] color;
                        if (check.containsKey(res.getKey())) {
                            color = this.tools.get_cst_array("RED");
                        }
                        else {
                            color = this.tools.get_cst_array("BLACK");
                        }
                        int[] pos = {pos_menu[0] - pos_menu[2] + this.tools.get_cst_array("MENU_EDIT_POS")[3] / 4, this.tools.get_cst_array("MENU_EDIT_POS")[1] - cost.size() * (this.tools.get_cst_array("MENU_EDIT_POS")[3]) + j * this.tools.get_cst_array("MENU_EDIT_POS")[3] + this.tools.get_cst_array("MENU_EDIT_POS")[3] / 4};
                        this.tools.text(res.getKey()+" : "+String.valueOf(res.getValue()), color, pos, 20);
                        j++;
                    }
                }
                String b = build;
                if (Arrays.stream(this.tools.get_cst_array_string("LIST_JUNCTION_BUILDING")).anyMatch(build::equals)) {
                    build = build+"2_0";
                }
                //
            }
            //
            if (pos_menu[3] != 0) {
                //
                int i = 0;
                for(Map.Entry<String, Integer> res: this.res_tamp.entrySet()) {
                    int[] color;
                    if (!check.isEmpty() && check.containsKey(res.getKey())) {
                        color = this.tools.get_cst_array("RED");
                    }
                    else {
                        color = this.tools.get_cst_array("BLACK");
                    }
                    int[] pos = {pos_menu[0]+this.tools.get_cst_array("MENU_EDIT_POS")[3]/4, pos_menu[1]+i*this.tools.get_cst_array("MENU_EDIT_POS")[3]+this.tools.get_cst_array("MENU_EDIT_POS")[3]/4};
                    String text = res.getKey() + " : " + String.valueOf(res.getValue()) + "/" + String.valueOf(this.tools.get_res(res.getKey()).get("stock"));
                    this.tools.text(text, color, pos, 20);
                    i++;
                }
            }
        }
    }
    public void display_resources(Layout layout) {
        Map<String, Map<String, Integer>> resources = this.tools.get_res();

        int i = 0;
        for(Map.Entry<String, Map<String, Integer>> res: resources.entrySet()) {
            float ratio = res.getValue().get("stock") / res.getValue().get("max");
            int[] pos = new int[]{this.tools.get_cst_value("size_x")/2-this.tools.get_cst_value("size_x")/16, this.tools.get_cst_value("size_y")*(3*resources.size()+2+6*i)/(12*resources.size())};
            int[] size = new int[]{this.tools.get_cst_value("size_x")/8, this.tools.get_cst_value("size_y")/(6*resources.size())};
            this.tools.barre(pos, size, ratio, this.tools.get_cst_array("RED"));
            pos = new int[]{this.tools.get_cst_value("size_x")/4+this.tools.get_cst_value("size_x")/16, this.tools.get_cst_value("size_y")/4+this.tools.get_cst_value("size_y")/(6*resources.size())+i*this.tools.get_cst_value("size_y")/(2*resources.size())};
            this.tools.text(res.getKey(), this.tools.get_cst_array("BLACK"), pos, this.tools.get_cst_value("size_y")/(6*resources.size()));
            pos = new int[]{this.tools.get_cst_value("size_x")/2+this.tools.get_cst_value("size_x")/8, this.tools.get_cst_value("size_y")/4+this.tools.get_cst_value("size_y")/(6*resources.size())+i*this.tools.get_cst_value("size_y")/(2*resources.size())};
            String text = String.valueOf(res.getValue().get("stock")) + "/" + String.valueOf(res.getValue().get("max")) + " ("+ String.valueOf(ratio*100)+"%)";
            this.tools.text(text, this.tools.get_cst_array("BLACK"), pos, this.tools.get_cst_value("size_y")/(6*resources.size()));
            i++;
        }
    }

    public void display_settings(Layout layout) {

    }
    public void set_action(String action, Layout layout){
        if (this.action != action) {
            this.action = action;
        }
        else {
            this.action = "layout";
        }
        this.update_mem_tamp();
        this.update_buttons(layout);
    }

    public void update_buttons (Layout layout) {
        this.buttons.clear();
        this.buttons.put("change_layer", new Button(new int[]{50, 50, 150, 100}, "layer", 1));
        if(layout.layer == "-1") {
            this.buttons.put("change_transparency", new Button(new int[]{50, 150, 150, 200}, "trans", 1));
        }
        if (this.action == "edit-add") {
            //
        }
        else if (this.action == "edit") {
            //
        }
        else if (this.action == "edit-sup") {

        }
    }
    public void update_mem_tamp () {
        if (this.action.startsWith("edit")) {
            this.res_tamp.clear();
            this.edit_sup_tamp.clear();
            this.edit_add_tamp.clear();
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

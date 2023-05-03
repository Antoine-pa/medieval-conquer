package com.mygdx.medievalconquer.engine.buildings.init_class;

import com.mygdx.medievalconquer.engine.tools.Tools;
import com.mygdx.medievalconquer.engine.tools.Coords;

import java.util.Map;

public class ProductionBuilding extends  Building {
    public boolean start_production = false;
    public long time = this.tools.time();
    public ProductionBuilding(Tools tools, String name, int[] size, Coords pos, int angle, int lvl, int life, String kind, Map<String, Integer> stock, String layer) {
        super(tools, name, size, pos, angle, lvl, life, kind, stock, layer);
    }

    public boolean check_product() {
        Map<String, Integer> prod_max = this.tools.get_prod_max(this);
        Map<String, Integer> prod_res = this.tools.get_prod_res(this);
        for(Map.Entry<String, Integer> r: prod_max.entrySet()) {
            if (this.stock.containsKey(r.getKey()) | r.getValue() >= this.stock.get(r.getKey())+prod_res.get(r.getKey())) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        int prod_time = this.tools.get_prod_time(this);
        Map<String, Integer> prod_res = this.tools.get_prod_res(this);
        if (this.start_production && tools.time() - this.time >= prod_time && this.check_product()){
            for(Map.Entry<String, Integer> p: prod_res.entrySet()) {
                if(! this.stock.containsKey(p.getKey())) {
                    this.stock.put(p.getKey(), 0);
                }
                //this.stock.replace(p.getKey(), this.stock.get(p.getKey())+p.getValue());
                //System.out.println(this.stock.get(p.getKey()));
                this.time = tools.time();
            }
        }
    }
}
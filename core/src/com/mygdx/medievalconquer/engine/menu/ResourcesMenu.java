package com.mygdx.medievalconquer.engine.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.medievalconquer.engine.layout.Layout;
import com.mygdx.medievalconquer.engine.utils.Colors;
import com.mygdx.medievalconquer.engine.utils.Graphics;
import com.mygdx.medievalconquer.engine.utils.Tools;

import java.util.Map;

public class ResourcesMenu {
    private Tools tools;
    public ResourcesMenu(Tools tools) {
        this.tools = tools;
    }
    public void display(SpriteBatch batch, ShapeRenderer shape) {
        Map<String, Map<String, Integer>> resources = this.tools.get_res();
        int size_x = this.tools.int_cst.get("size_x");
        int size_y = this.tools.int_cst.get("size_y");
        Graphics.fill(Colors.black);
        Graphics.rect(shape, Colors.grey_white, size_x/4, size_y/4, size_x/2, size_y/2);
        int i = 0;
        for(Map.Entry<String, Map<String, Integer>> res: resources.entrySet()) {
            int x = size_x/2-size_x/16;
            int y = size_y*(3*resources.size()+2+6*i)/(12*resources.size());
            int width = size_x/8;
            int height = size_y/(6*resources.size());
            float ratio = (float) res.getValue().get("stock") / (float) res.getValue().get("max");
            Graphics.progress_bar(shape, Colors.grey_white, Colors.red, x, y, width, height, ratio);
            Graphics.text(batch, res.getKey(), Colors.black, size_x/4+size_x/16, size_y/4+size_y/(6*resources.size())+i*size_y/(2*resources.size()), size_y/(6*resources.size()));
            String text = res.getValue().get("stock")+"/"+res.getValue().get("max")+" ("+String.format("%.2f", ratio*100)+"%)";
            Graphics.text(batch, text, Colors.black, size_x/2+size_x/8, size_y/4+size_y/(6*resources.size())+i*size_y/(2*resources.size()), size_y/(6*resources.size()), false);
            i++;
        }
    }
}

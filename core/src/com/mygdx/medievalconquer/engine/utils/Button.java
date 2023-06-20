package com.mygdx.medievalconquer.engine.utils;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

public class Button {
    public float[] coords;
    public String text;
    public int thickness;
    public Color background_color;
    public Color font_color;

    public Button(float[] coords, String text, int thickness) {
        this.coords = coords;
        this.text = text;
        this.thickness = thickness;
        this.background_color = Colors.white;
        this.font_color = Colors.black;
    }

    public Button(float[] coords, String text, int thickness, Color background_color, Color font_color) {
        this.coords = coords;
        this.text = text;
        this.thickness = thickness;
        this.background_color = background_color;
        this.font_color = font_color;
    }

    public boolean collidepoint (int[] pos) {
        return this.coords[0] <= pos[0] & pos[0] <= this.coords[2]  & this.coords[1] <= pos[1] & pos[1] <= this.coords[3];
    }

    public void display(ShapeRenderer shape, SpriteBatch batch, Tools tools) {
        Graphics.rect(shape, Colors.black, (int) this.coords[0], (int) this.coords[1], this.coords[2]-this.coords[0], this.coords[3]-this.coords[1]);
        Graphics.rect(shape, this.background_color, (int) (this.coords[0]+this.thickness), (int) (this.coords[1]+this.thickness), this.coords[2]-this.coords[0]-2*this.thickness, this.coords[3]-this.coords[1]-2*this.thickness);
        Graphics.text(batch, this.text, this.font_color, (int) this.coords[0], (int) (this.coords[1]+this.coords[3]-this.coords[1]-2*this.thickness-5*tools.int_cst.get("SIZE_TEXT")/4), (int) (this.coords[2]-this.coords[0]-2*this.thickness));
    }
}
package com.mygdx.medievalconquer.engine.tools;

public class Button {
    public int[] coords;
    public String text;
    public int thickness;
    public int[] background_color;
    public int[] font_color;

    public Button(int[] coords, String text, int thickness) {
        this.coords = coords;
        this.text = text;
        this.thickness = thickness;
    }

    public Button(int[] coords, String text, int thickness, int[] background_color, int[] font_color) {
        this.coords = coords;
        this.text = text;
        this.thickness = thickness;
        this.background_color = background_color;
        this.font_color = font_color;
    }

    public boolean collidepoint (int[] pos) {
        return this.coords[0] <= pos[0] && pos[0] <= this.coords[2]  && this.coords[1] <= pos[1] && pos[1] <= this.coords[3];
    }

    public void display() {

    }

    private int[] pos_text () {
        //int[] r = {this.coords[0]+this.thickness+3, this.coords[3]-this.thickness-1.25*cst("SIZE_TEXT")};
        int[] r = {0, 0};
        return r;
    }
}
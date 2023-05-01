package com.mygdx.medievalconquer.engine.tools;

public class Coords {
    public int x;
    public int y;
    public Coords(int[] pos) {
        this.x = pos[0];
        this.y = pos[1];
    }
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return (String.valueOf(this.x) + String.valueOf(this.y)).hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Coords && ((Coords) obj).hashCode() == this.hashCode());
    }
}

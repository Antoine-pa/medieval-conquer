package com.mygdx.medievalconquer.engine.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;

public class Graphics {
    public static void blit(SpriteBatch batch, Texture texture, int x, int y, int width, int height) {
        batch.begin();
        batch.draw(texture, x, y, width, height);
        batch.end();
    }
    public static void fill(Color color) {
        ScreenUtils.clear(color);
    }
    public static void line(ShapeRenderer shape, Color color, int x, int y, int x2, int y2) {
        Graphics.line(shape, color, x, y, x2, y2, 1);
    }
    public static void line(ShapeRenderer shape, Color color, int x, int y, int x2, int y2, int thickness) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(color);
        Gdx.gl.glLineWidth(thickness);
        shape.line(x, y, x2, y2);
        shape.end();
    }
    public static void progress_bar(ShapeRenderer shape, Color color1, Color color2, int x, int y, int width, int height, float ratio) {
        Graphics.rect(shape, color1, x, y, width, height);
        Graphics.rect(shape, color2, x, y, width*ratio, height);
        Graphics.rect(shape, Colors.black, x, y, width, height, false);
    }
    public static void rect(ShapeRenderer shape, Color color, float x, float y, float width, float height) {
        Graphics.rect(shape, color, (int) x, (int) y, width, height, true, 1);
    }
    public static void rect(ShapeRenderer shape, Color color, int x, int y, float width, float height) {
        Graphics.rect(shape, color, x, y, width, height, true, 1);
    }
    public static void rect(ShapeRenderer shape, Color color, float x, float y, float width, float height, int thickness) {
        Graphics.rect(shape, color, (int) x, (int) y, width, height, true, thickness);
    }
    public static void rect(ShapeRenderer shape, Color color, int x, int y, float width, float height, int thickness) {
        Graphics.rect(shape, color, x, y, width, height, true, thickness);
    }
    public static void rect(ShapeRenderer shape, Color color, float x, float y, float width, float height, boolean filled) {
        Graphics.rect(shape, color, (int) x, (int) y, width, height, filled, 1);
    }
    public static void rect(ShapeRenderer shape, Color color, int x, int y, float width, float height, boolean filled) {
        Graphics.rect(shape, color, x, y, width, height, filled, 1);
    }
    public static void rect(ShapeRenderer shape, Color color, float x, float y, float width, float height, boolean filled, int thickness) {
        Graphics.rect(shape, color, (int) x, (int) y, width, height, filled, thickness);
    }
    public static void rect(ShapeRenderer shape, Color color, int x, int y, float width, float height, boolean filled, int thickness) {
        if (filled) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
        }
        else {
            shape.begin(ShapeRenderer.ShapeType.Line);
        }
        shape.setColor(color);
        Gdx.gl.glLineWidth(thickness);
        shape.rect(x, y, width, height);
        shape.end();
    }
    public static void text(SpriteBatch batch, String txt, Color font_color, int x, int y) {
        batch.begin();
        Picture.font.setColor(font_color);
        Picture.font.draw(batch, txt, x, y);
        batch.end();
    }
    public static void text(SpriteBatch batch, String txt, Color font_color, int x, int y, int width) {
        Graphics.text(batch, txt, font_color, x, y, width, true);
    }
    public static void text(SpriteBatch batch, String txt, Color font_color, int x, int y, int width, boolean centered) {
        batch.begin();
        Picture.font.setColor(font_color);
        if(!centered) {
            Picture.font.draw(batch, txt, x, y, width, Align.left, false);
        }else{
            Picture.font.draw(batch, txt, x, y, width, Align.center, false);
        }
        batch.end();
    }
}

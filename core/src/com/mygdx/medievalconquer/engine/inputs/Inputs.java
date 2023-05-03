package com.mygdx.medievalconquer.engine.inputs;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.medievalconquer.Main;

public class Inputs implements InputProcessor {
    public Main main;

    public Inputs(Main main) {
        this.main = main;
    }
    @Override
    public boolean keyDown(int keycode) {
        // Méthode appelée lorsque la touche est enfoncée
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // Méthode appelée lorsque la touche est relâchée
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // Méthode appelée lorsque l'utilisateur tape une touche qui peut être représentée par un caractère
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Méthode appelée lorsque l'utilisateur clique sur la souris
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Méthode appelée lorsque l'utilisateur relâche le clic de souris
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Méthode appelée lorsque l'utilisateur déplace la souris tout en maintenant le clic
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // Méthode appelée lorsque l'utilisateur déplace la souris sans cliquer
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // Méthode appelée lorsque l'utilisateur utilise la molette de la souris
        //System.out.println(this.main.t.get_cst_value("ZOOM"));
        this.main.game.layout.zoom(amountY);
        return false;
    }
}

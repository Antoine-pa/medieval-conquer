package com.mygdx.medievalconquer.engine.inputs;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.medievalconquer.Main;

public class Inputs implements InputProcessor {
    public Main main;
    public boolean arrow_left = false;
    public boolean arrow_right = false;
    public boolean arrow_up = false;
    public boolean arrow_down = false;

    public Inputs(Main main) {
        this.main = main;
    }
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == 22) {
            this.arrow_right = true;
            this.main.game.layout.pos[0] += this.main.t.float_cst.get("SENSIBILITY");
        }
        else if (keycode == 19) {
            this.arrow_up = true;
            this.main.game.layout.pos[1] -= this.main.t.float_cst.get("SENSIBILITY");
        }
        else if (keycode == 21) {
            this.arrow_left = true;
            this.main.game.layout.pos[0] -= this.main.t.float_cst.get("SENSIBILITY");
        }
        else if (keycode == 20) {
            this.arrow_down = true;
            this.main.game.layout.pos[1] += this.main.t.float_cst.get("SENSIBILITY");
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        //change angle of tamp build
        if (keycode == 22) {
            this.arrow_right = false;
        }
        else if (keycode == 19) {
            this.arrow_up = false;
        }
        else if (keycode == 21) {
            this.arrow_left = false;
        }
        else if (keycode == 20) {
            this.arrow_down = false;
        }
        else if (keycode == 131) {
            this.main.game.menu.set_action("settings", this.main.game.layout);
        }
        else if (keycode == 46) {
            this.main.game.menu.set_action("resources", this.main.game.layout);
        }
        else if (keycode == 33) {
            this.main.game.menu.set_action("edit", this.main.game.layout);
        }
        //echap = 111
        else {
            System.out.println(keycode);
        }
        return true;
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
        if(pointer == 0 & button == 0) {
            this.main.game.menu.click(new int[]{screenX, screenY}, this.main.game.layout);
        }
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
        this.main.game.layout.zoom(amountY);
        return false;
    }

    public void action() {
        float sensibility = this.main.t.float_cst.get("SENSIBILITY");
        if (this.arrow_down & this.arrow_right) {
            this.main.game.layout.pos[0] += sensibility/(Math.sqrt(2));
            this.main.game.layout.pos[1] += sensibility/(Math.sqrt(2));
        } else if (this.arrow_down & this.arrow_left) {
            this.main.game.layout.pos[0] -= sensibility/(Math.sqrt(2));
            this.main.game.layout.pos[1] += sensibility/(Math.sqrt(2));
        } else if (this.arrow_up & this.arrow_left) {
            this.main.game.layout.pos[0] -= sensibility/(Math.sqrt(2));
            this.main.game.layout.pos[1] -= sensibility/(Math.sqrt(2));
        } else if (this.arrow_up & this.arrow_right) {
            this.main.game.layout.pos[0] += sensibility/(Math.sqrt(2));
            this.main.game.layout.pos[1] -= sensibility/(Math.sqrt(2));
        } else if (this.arrow_right) {
            this.main.game.layout.pos[0] += this.main.t.float_cst.get("SENSIBILITY");
        }
        else if (this.arrow_up) {
            this.main.game.layout.pos[1] -= this.main.t.float_cst.get("SENSIBILITY");
        }
        else if (this.arrow_left) {
            this.main.game.layout.pos[0] -= this.main.t.float_cst.get("SENSIBILITY");
        }
        else if (this.arrow_down) {
            this.main.game.layout.pos[1] += this.main.t.float_cst.get("SENSIBILITY");
        }
    }
}

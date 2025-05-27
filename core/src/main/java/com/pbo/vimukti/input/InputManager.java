package com.pbo.vimukti.input;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.pbo.vimukti.entities.Player;

public class InputManager {
    private Player player;
    private final float speed = 200f;

    public InputManager(Player player) {
        this.player = player;
    }

    public void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.move(-speed * delta, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.move(speed * delta, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.move(0, speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.move(0, -speed * delta);
        }
    }
}

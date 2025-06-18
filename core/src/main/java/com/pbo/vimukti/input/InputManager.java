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
        boolean isMoving=false;
        boolean isJump=false;
        boolean isHit = false;

        float speed = player.getSpeed();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.move(-speed * delta, 0);
            isMoving=true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.move(speed * delta, 0);
            isMoving=true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.jump();
            isJump=true;

        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            player.hit();

        }


        player.setMoving(isMoving,isJump);
    }
}

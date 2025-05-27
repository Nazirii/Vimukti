package com.pbo.vimukti.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private Texture texture;
    public float x, y;

    public Player() {
        texture = new Texture("player.png"); // ganti dgn assetmu
        x = 100;
        y = 100;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public void dispose() {
        texture.dispose();
    }
}


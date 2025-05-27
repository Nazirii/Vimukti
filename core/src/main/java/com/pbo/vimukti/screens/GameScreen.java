package com.pbo.vimukti.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.pbo.vimukti.MainGame;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {
    private MainGame game;
    private Texture tes;
    public GameScreen(MainGame game) {
        this.game = game;
    }
    @Override
    public void show(){
        tes = new Texture("tes.png");
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(tes, 100, 100, 64, 64);
        game.batch.end();
    }

    // Sisanya override kosong dulu
    public void resize(int w, int h) {}
    public void hide() {}
    public void pause() {}
    public void resume() {}
    public void dispose() {
        tes.dispose();
    }
}

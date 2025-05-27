package com.pbo.vimukti.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.pbo.vimukti.MainGame;

public class MenuScreen implements Screen {
    private MainGame game;
    private BitmapFont font;
    public MenuScreen(MainGame game) {
        this.game = game;
    }

    public void show() {
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // contoh pindah screen kalau tekan ENTER
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game)); // pindah ke game screen
        }

        game.batch.begin();
        font.draw(game.batch, "Tekan ENTER untuk main", 100, 150);
        game.batch.end();
    }
    // Sisanya override kosong dulu
    public void resize(int w, int h) {}
    public void hide() {}
    public void pause() {}
    public void resume() {}
    public void dispose() {
        font.dispose();
    }
}

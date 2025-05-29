package com.pbo.vimukti.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.pbo.vimukti.MainGame;
import com.badlogic.gdx.graphics.Texture;
import com.pbo.vimukti.entities.Player;
import com.pbo.vimukti.input.InputManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    private MainGame game;
    private Texture tes;
    private Player player;
    private InputManager input ;
    private SpriteBatch batch;

    public GameScreen(MainGame game) {
        this.game = game;
        this.player=new Player();
        this.input = new InputManager(player);
        this.batch=game.batch;
    }
    @Override
    public void show(){
        tes = new Texture("tes.png");
    }
    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }
    public void update(float delta){
        input.handleInput(delta);
        player.update(delta);
    }
    public void draw (){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        player.render(batch); // gambar player
        batch.end();
    }

    // Sisanya override kosong dulu
    public void resize(int w, int h) {}
    public void hide() {}
    public void pause() {}
    public void resume() {}
    public void dispose() {
        tes.dispose();
        player.dispose();
    }
}

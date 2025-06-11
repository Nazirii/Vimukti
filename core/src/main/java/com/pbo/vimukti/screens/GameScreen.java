package com.pbo.vimukti.screens;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.pbo.vimukti.MainGame;
import com.badlogic.gdx.graphics.Texture;
import com.pbo.vimukti.entities.*;

import com.pbo.vimukti.input.InputManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pbo.vimukti.ui.HealthBar;

public class GameScreen implements Screen {
    Array<BaseEnemies> enemies = new Array<>();
    private MainGame game;
    private Texture tes;
    private Player player;
    private InputManager input ;
    private SpriteBatch batch;
    private HealthBar healthBar;


    public GameScreen(MainGame game) {
        this.game = game;
        this.player=new Player();
        this.input = new InputManager(player);
        this.batch=game.batch;
        enemies.add(new Worm());
        enemies.add(new Golem());
        enemies.add(new Mushroom());
        
        
        this.healthBar = new HealthBar(20, 430, 200, 25, 200); 
    }
    @Override
    public void show(){
        tes = new Texture("tes.png");
        
        
        
        
        Gdx.input.setInputProcessor(null);
    }
    @Override
    public void render(float delta) {
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game, this));
            return;
        }
        
        
        if (player.getPlayerHP() <= 0) {
            game.setScreen(new GameOverScreen(game));
            return;
        }
        
        update(delta);
        draw();
    }
    public void update(float delta){
        input.handleInput(delta);
        player.update(delta);
        for(BaseEnemies enemy : enemies) {
            enemy.update(delta, player.x);
        }
        battlehandler(player,enemies);

    }
    public void draw (){
        Gdx.gl.glClearColor(0.827f, 0.827f, 0.827f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        player.render(batch); 
        for(BaseEnemies enemy:enemies) {
            enemy.render(batch);
        }
        
        
        healthBar.render(batch, player.getPlayerHP());
        
        
        for(BaseEnemies enemy:enemies) {
            enemy.renderHealthBar(batch);
        }
        
        batch.end();
        



    }
    public void battlehandler(Player player ,Array<BaseEnemies> enemies){
        for (BaseEnemies enemy : enemies){
            if (player.getBounds().overlaps(enemy.getBounds())) {
            
                if (player.isHitting()) {
                enemy.gethit(player.x);
            }
                if ( enemy.isAttacking() && enemy.isHashit()){
                    player.getHitFromEnemy(enemy);
                    enemy.setishit(true);

                }


            }
    }
}
    
    public void resize(int w, int h) {}
    public void hide() {}
    public void pause() {}
    public void resume() {}
    public void dispose() {
        tes.dispose();
        player.dispose();
        for (BaseEnemies enemy :enemies){
            enemy.dispose();
        }
        healthBar.dispose();
    }
}

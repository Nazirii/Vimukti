package com.pbo.vimukti.screens;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.pbo.vimukti.MainGame;
import com.badlogic.gdx.graphics.Texture;
import com.pbo.vimukti.entities.*;
import com.pbo.vimukti.utils.GameConstants;

import com.pbo.vimukti.input.InputManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.pbo.vimukti.ui.HealthBar;

public class GameScreen implements Screen {
    Array<BaseEnemies> enemies = new Array<>();
    private MainGame game;
    private Texture tes;
    private Texture backgroundTexture;
    private Texture bossBackgroundTexture;
    private Player player;
    private InputManager input ;
    private SpriteBatch batch;
    private HealthBar healthBar;
    private BitmapFont font;


    private int currentEnemyIndex = 0;
    private String[] enemySequence = {"Mushroom", "Worm", "Golem"};
    private boolean needToSpawnNextEnemy = false;
    private boolean gameCompleted = false;
    private String currentEnemyType = "";
    private boolean isBossMode = false;
    private boolean transitionToBoss = false;
    private float transitionTimer = 0f;
    private final float transitionDuration = 2.0f;


    private boolean showVictoryText = false;
    private String victoryText = "";
    private float victoryTextTimer = 0f;
    private final float victoryTextDuration = 2.0f;


    public GameScreen(MainGame game) {
        this.game = game;
        this.player=new Player();
        this.input = new InputManager(player);
        this.batch=game.batch;
        this.font = new BitmapFont();
        font.getData().setScale(3.0f);


        spawnNextEnemy();

        this.healthBar = new HealthBar(20, 650, 200, 25, 200);
    }
    @Override
    public void show(){
        tes = new Texture("tes.png");
        backgroundTexture = new Texture("backgroundgame.png");
        bossBackgroundTexture = new Texture("bossbackground.png");

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


        if (gameCompleted) {
            game.setScreen(new VictoryScreen(game));
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

        
        if (transitionToBoss) {
            transitionTimer += delta;
            if (transitionTimer >= transitionDuration) {
                transitionToBoss = false;
                isBossMode = true;
                transitionTimer = 0f;
            }
        }

        if (showVictoryText) {
            victoryTextTimer += delta;
            if (victoryTextTimer >= victoryTextDuration) {
                showVictoryText = false;
                victoryTextTimer = 0f;
            }
        }


        checkEnemySpawning();
    }
    public void draw (){
        Gdx.gl.glClearColor(0.827f, 0.827f, 0.827f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.viewport.apply();
        batch.setProjectionMatrix(game.camera.combined);

        batch.begin();

        
        if (transitionToBoss) {
            
            float alpha = transitionTimer / transitionDuration;
            batch.setColor(1, 1, 1, 1 - alpha);
            batch.draw(backgroundTexture, 0, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
            batch.setColor(1, 1, 1, alpha);
            batch.draw(bossBackgroundTexture, 0, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
            batch.setColor(1, 1, 1, 1); 
        } else if (isBossMode) {
            batch.draw(bossBackgroundTexture, 0, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
        } else {
            batch.draw(backgroundTexture, 0, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
        }

        player.render(batch);
        for(BaseEnemies enemy:enemies) {
            enemy.render(batch);
        }


        healthBar.render(batch, player.getPlayerHP());


        for(BaseEnemies enemy:enemies) {
            enemy.renderHealthBar(batch);
        }


        if (showVictoryText) {
            font.setColor(1.0f, 1.0f, 0.0f, 1.0f);
            float textWidth = font.getSpaceXadvance() * victoryText.length() * 0.6f;
            float centerX = (GameConstants.GAME_WIDTH - textWidth) / 2f - 50f;
            float centerY = GameConstants.GAME_HEIGHT / 2f + 100f;
            font.draw(batch, victoryText, centerX, centerY);
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

    public void resize(int w, int h) {
        game.viewport.update(w, h);
    }
    public void hide() {}
    public void pause() {}
    public void resume() {}
    public void dispose() {
        tes.dispose();
        backgroundTexture.dispose();
        bossBackgroundTexture.dispose();
        player.dispose();
        for (BaseEnemies enemy :enemies){
            enemy.dispose();
        }
        healthBar.dispose();
        font.dispose();
    }


    private void spawnNextEnemy() {
        String enemyType = enemySequence[currentEnemyIndex];
        currentEnemyType = enemyType;

        switch (enemyType) {
            case "Mushroom":
                enemies.add(new Mushroom());
                break;
            case "Worm":
                enemies.add(new Worm());
                break;
            case "Golem":
                enemies.add(new Golem());
                break;
        }


        currentEnemyIndex = (currentEnemyIndex + 1) % enemySequence.length;
        needToSpawnNextEnemy = false;
    }


    private void checkEnemySpawning() {
        boolean allEnemiesDefeated = true;
        for (BaseEnemies enemy : enemies) {
            if (!enemy.isDeadFinished()) {
                allEnemiesDefeated = false;
                break;
            }
        }

        if (allEnemiesDefeated && !needToSpawnNextEnemy && !gameCompleted && !showVictoryText) {

            if (currentEnemyType.equals("Mushroom")) {
                showVictoryText = true;
                victoryText = "LEVEL 2";
                victoryTextTimer = 0f;
            } else if (currentEnemyType.equals("Worm")) {
                showVictoryText = true;
                victoryText = "FINAL BOSS";
                victoryTextTimer = 0f;
                
                transitionToBoss = true;
                transitionTimer = 0f;
            }


            if (currentEnemyType.equals("Golem")) {

                gameCompleted = true;
                enemies.clear();
                return;
            }


            enemies.clear();
            needToSpawnNextEnemy = true;


            if (showVictoryText) {

                return;
            } else {
                spawnNextEnemy();
            }
        }


        if (needToSpawnNextEnemy && !showVictoryText) {
            spawnNextEnemy();
        }
    }
}

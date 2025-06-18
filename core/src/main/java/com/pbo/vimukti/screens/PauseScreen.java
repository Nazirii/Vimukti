package com.pbo.vimukti.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pbo.vimukti.MainGame;
import com.pbo.vimukti.utils.GameConstants;

public class PauseScreen implements Screen {
    private MainGame game;
    private GameScreen gameScreen;    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private Table table;
    private Texture backgroundTexture;
    private Texture pausedTexture;

    public PauseScreen(MainGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
    }

    @Override
    public void show() {        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2.0f);
        
        
        backgroundTexture = new Texture("backgroundpause.png");
        
        
        pausedTexture = new Texture("PAUSED.png");
          stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);
        
        
        skin = new Skin();
        skin.add("default", font);
          
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.valueOf("#A0BF40");
        buttonStyle.downFontColor = Color.GRAY;
        buttonStyle.overFontColor = Color.YELLOW;
        skin.add("default", buttonStyle);
        
        
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);
        
        
        table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);
          
        Image pauseImage = new Image(pausedTexture);
        
        
        TextButton resumeButton = new TextButton("RESUME", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                Gdx.input.setInputProcessor(null);
                game.setScreen(gameScreen);
            }
        });
        
        TextButton mainMenuButton = new TextButton("MAIN MENU", skin);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.dispose(); 
                
                Gdx.input.setInputProcessor(null);
                game.setScreen(new MenuScreen(game));
            }
        });
        
        
        table.add(pauseImage).padBottom(50).row();
        table.add(resumeButton).padBottom(20).row();
        table.add(mainMenuButton).padBottom(20);
    }    @Override
    public void render(float delta) {
        
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Apply viewport
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.camera.combined);
        
        game.batch.begin();
        
        game.batch.setColor(0.7f, 0.7f, 0.7f, 1f);
        game.batch.draw(backgroundTexture, 0, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
        game.batch.setColor(1f, 1f, 1f, 1f); 
        game.batch.end();
        
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(gameScreen);
        }
        
        stage.act(delta);
        stage.draw();
    }    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }@Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
        backgroundTexture.dispose();
        pausedTexture.dispose();
    }
}

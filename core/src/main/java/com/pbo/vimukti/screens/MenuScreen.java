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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pbo.vimukti.MainGame;
import com.pbo.vimukti.utils.GameConstants;

public class MenuScreen implements Screen {
    private MainGame game;
    private BitmapFont font;
    private Texture backgroundTexture;
    private Texture logoTexture;
    private Stage stage;
    private Skin skin;
    private Table table;
    
    public MenuScreen(MainGame game) {
        this.game = game;
    }

    public void show() {
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2.5f); 
        
        
        backgroundTexture = new Texture("menu.png");
        
        logoTexture = new Texture("logo.png");
        
        
        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);
        
        
        skin = new Skin();
        skin.add("default", font);
        
        
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.valueOf("#A0BF40");
        buttonStyle.downFontColor = Color.WHITE;
        buttonStyle.overFontColor = Color.YELLOW;
        skin.add("default", buttonStyle);
        
        
        table = new Table();
        table.setFillParent(true);
        table.center(); 
        table.padTop(130); 
        stage.addActor(table);
        
        
        TextButton startButton = new TextButton("START GAME", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                Gdx.input.setInputProcessor(null);
                game.setScreen(new GameScreen(game));
            }
        });
        
        TextButton optionButton = new TextButton("OPTION", skin);
        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
            }
        });
        
        TextButton exitButton = new TextButton("EXIT GAME", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        
        
        table.add(startButton).padBottom(30).row();
        table.add(optionButton).padBottom(30).row();
        table.add(exitButton).padBottom(30);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            
            Gdx.input.setInputProcessor(null);
            game.setScreen(new GameScreen(game)); 
        }

        // Apply viewport
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        
        
        game.batch.draw(backgroundTexture, 0, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
        
        
        float logoScale = 0.5f; 
        float logoWidth = logoTexture.getWidth() * logoScale;
        float logoHeight = logoTexture.getHeight() * logoScale;
        float logoX = (GameConstants.GAME_WIDTH - logoWidth) / 2;
        float logoY = GameConstants.GAME_HEIGHT - logoHeight - 50;
        game.batch.draw(logoTexture, logoX, logoY, logoWidth, logoHeight);
        
        game.batch.end();
        
        
        stage.act(delta);
        stage.draw();
    }
    
    
    public void resize(int w, int h) {
        game.viewport.update(w, h);
        stage.getViewport().update(w, h, true);
    }
    public void hide() {}
    public void pause() {}
    public void resume() {}
    public void dispose() {
        font.dispose();
        backgroundTexture.dispose(); 
        logoTexture.dispose(); 
        stage.dispose(); 
        skin.dispose(); 
    }
}

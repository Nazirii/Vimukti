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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pbo.vimukti.MainGame;

public class GameOverScreen implements Screen {
    private MainGame game;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;    private Table table;
    private Texture backgroundTexture;
    private Texture gameOverLogoTexture;

    public GameOverScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2.0f);
          
        backgroundTexture = new Texture("gameover.png");
        
        
        gameOverLogoTexture = new Texture("gameoverlogo.png");
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        
        skin = new Skin();
        skin.add("default", font);
          
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.valueOf("#E78827"); 
        buttonStyle.downFontColor = Color.WHITE;
        buttonStyle.overFontColor = Color.YELLOW;
        skin.add("default", buttonStyle);
          
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.valueOf("#E78827");
        skin.add("default", labelStyle);
        
        
        table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);
          
        Image gameOverImage = new Image(gameOverLogoTexture);
          Label instructionLabel = new Label("You have been defeated!", skin);
        instructionLabel.setFontScale(1.0f);
        
        
        TextButton restartButton = new TextButton("RESTART", skin);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                Gdx.input.setInputProcessor(null);
                game.setScreen(new GameScreen(game));
            }
        });
        
        TextButton mainMenuButton = new TextButton("MAIN MENU", skin);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                Gdx.input.setInputProcessor(null);
                game.setScreen(new MenuScreen(game));
            }
        });
        
        TextButton exitButton = new TextButton("EXIT GAME", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
          
        table.add(gameOverImage).padBottom(30).row();
        table.add(instructionLabel).padBottom(50).row();
        table.add(restartButton).padBottom(20).row();
        table.add(mainMenuButton).padBottom(20).row();
        table.add(exitButton).padBottom(20);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            
            Gdx.input.setInputProcessor(null);
            game.setScreen(new GameScreen(game));
        }
        
        game.batch.begin();
        
        game.batch.setColor(0.3f, 0.3f, 0.3f, 1f);
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.setColor(1f, 1f, 1f, 1f); 
        game.batch.end();
        
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
        backgroundTexture.dispose();
        gameOverLogoTexture.dispose();
    }
}

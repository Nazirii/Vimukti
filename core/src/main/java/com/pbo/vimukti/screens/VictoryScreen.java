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

public class VictoryScreen implements Screen {
    private MainGame game;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private Table table;
    private Texture backgroundTexture;
    private Texture victoryLogoTexture;

    public VictoryScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2.0f);
        
        backgroundTexture = new Texture("victorybackground.png");
        victoryLogoTexture = new Texture("victorylogo.png");
        
        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);
        
        skin = new Skin();
        skin.add("default", font);
        
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.valueOf("#E7E127"); 
        buttonStyle.downFontColor = Color.WHITE;
        buttonStyle.overFontColor = Color.YELLOW;
        skin.add("default", buttonStyle);
        
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.valueOf("#E7E127");
        skin.add("default", labelStyle);
        
        table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);
        
        Image victoryImage = new Image(victoryLogoTexture);
        Label congratsLabel = new Label("Congratulations! You defeated all enemies!", skin);
        congratsLabel.setFontScale(1.0f);
        
        TextButton playAgainButton = new TextButton("PLAY AGAIN", skin);
        playAgainButton.addListener(new ClickListener() {
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
        
        table.add(victoryImage).padBottom(30).row();
        table.add(congratsLabel).padBottom(50).row();
        table.add(playAgainButton).padBottom(20).row();
        table.add(mainMenuButton).padBottom(20).row();
        table.add(exitButton).padBottom(20);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Gdx.input.setInputProcessor(null);
            game.setScreen(new GameScreen(game));
        }
        
        // Apply viewport
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.camera.combined);
        
        game.batch.begin();
        game.batch.setColor(0.7f, 1.0f, 0.7f, 1f);
        game.batch.draw(backgroundTexture, 0, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
        game.batch.setColor(1f, 1f, 1f, 1f); 
        game.batch.end();
        
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
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
        victoryLogoTexture.dispose();
    }
}

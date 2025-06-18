package com.pbo.vimukti;
import com.pbo.vimukti.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pbo.vimukti.utils.GameConstants;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public Viewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        
        // Create camera and viewport for responsive scaling
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT, camera);
        viewport.apply();
        
        camera.position.set(GameConstants.GAME_WIDTH / 2, GameConstants.GAME_HEIGHT / 2, 0);
        camera.update();
        
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (getScreen() != null) {
            getScreen().dispose();
        }
    }
}

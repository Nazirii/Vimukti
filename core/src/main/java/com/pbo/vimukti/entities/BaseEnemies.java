package com.pbo.vimukti.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract  class BaseEnemies {
    protected float speed;
    protected float x, y;
    protected float hp;
    protected boolean isAlive = true;
    protected float damage;

    public abstract void update(float delta, float playerX);
    public abstract void render(SpriteBatch batch);
    public abstract void gethit(float playerX);
    public abstract Rectangle getBounds();
    public boolean isAlive() { return isAlive; }
    public abstract void dispose();
}

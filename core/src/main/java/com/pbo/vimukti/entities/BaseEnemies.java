package com.pbo.vimukti.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pbo.vimukti.ui.EnemyHealthBar;

public abstract  class BaseEnemies {
    protected float speed;
    protected float x, y;
    protected float hp;
    protected boolean isAlive = true;
    protected float damage;
    protected EnemyHealthBar healthBar;

    public abstract void update(float delta, float playerX);
    public abstract void render(SpriteBatch batch);
    public abstract void gethit(float playerX);
    public abstract Rectangle getBounds();
    public boolean isAlive() { return isAlive; }
    public abstract void dispose();
    public abstract boolean isAttacking();
    public abstract boolean isHashit();
    public abstract void setishit(boolean value);
    public abstract float getStateTime();
    public abstract void debugdraw(ShapeRenderer shapeRenderer);


    public float getHP() { return hp; }
    public abstract float getMaxHP();
    public abstract float getSpriteWidth();
    public abstract float getSpriteHeight();

    public boolean isDeadFinished() {
        return false; // default untuk enemy yang belum override
    }


    public abstract float getSpriteLeftX();

    public void renderHealthBar(SpriteBatch batch) {
        if (healthBar != null) {
            healthBar.render(batch, hp, getSpriteLeftX(), y, getSpriteWidth(), getSpriteHeight());
        }
    }
}

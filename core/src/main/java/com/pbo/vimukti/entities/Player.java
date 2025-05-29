package com.pbo.vimukti.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    private Texture texture;
    private float speed = 200f;
    public float x, y;
    float stateTime;
    private boolean isMoving=false;
    Texture sheet;
    TextureRegion[] walkFrames;
    Animation<TextureRegion> walkAnim;

    public Player() {

        x = 100;
        y = 100;
        sheet = new Texture("run.png");
        TextureRegion[][] tmp = TextureRegion.split(sheet, 64, 64);
        walkFrames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            walkFrames[i] = tmp[0][i];
        }
        walkAnim = new Animation<TextureRegion>(0.1f, walkFrames);
        stateTime = 0f;
    }
    public float getSpeed() {
        return speed;
    }
    public void render(SpriteBatch batch) {
        TextureRegion frame = walkAnim.getKeyFrame(stateTime, true);
        batch.draw(frame, x, y);

    }
    public void update(float delta){
        stateTime+=delta;
        if (isMoving) {
            stateTime += delta;
        } else {
            stateTime = 0; // kembali ke frame pertama saat diam
        }
    }
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
        isMoving = dx != 0 || dy != 0;
    }
    public void setMoving(boolean isMoving){
        this.isMoving=isMoving;
    }
    public void dispose() {
        sheet.dispose();
    }
}


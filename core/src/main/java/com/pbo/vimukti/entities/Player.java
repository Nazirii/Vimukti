package com.pbo.vimukti.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    //atribut dasar player
    private float speed = 200f;
    public float x, y;
    private float jumpSpeed = 500f;
    //kedaan/status game/frame
    float stateTime;
    private float gravity = 1000f;
    private final float groundY = 100f; // posisi tanah (bisa disesuaikan)
    private float velocityY = 0;
    //keadaan/status player
    private boolean onGround = true;
    private boolean isMoving=false;
    private boolean isJump=false;
    private boolean hadapkanan=true;
    private boolean isHit = false;
    //animasi
        //jalan
    Texture walk;
    TextureRegion[] walkFrames;
    Animation<TextureRegion> walkAnim;
        //lompat
    Texture jump;
    TextureRegion[] jumpFrames;
    Animation<TextureRegion> jumpAnim;
        //serang
    Texture hit;
    TextureRegion[] hitFrames;
    Animation<TextureRegion> hitAnim;

    public Player() {

        x = 100;
        y = 100;
        //set animasi jalan
        walk = new Texture("run.png");
        TextureRegion[][] tmpw = TextureRegion.split(walk, 64, 64);
        walkFrames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            walkFrames[i] = tmpw[0][i];
        }
        walkAnim = new Animation<TextureRegion>(0.1f, walkFrames);
        //set animasi lompat
        jump = new Texture("jump.png");
        TextureRegion[][] tmpj = TextureRegion.split(jump, 64, 64);
        jumpFrames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            jumpFrames[i] = tmpj[0][i];
        }
        jumpAnim = new Animation<TextureRegion>(0.1f, jumpFrames);
        //set animasi hit
        hit = new Texture("hit.png");
        TextureRegion[][] tmph = TextureRegion.split(hit,64,64);
        hitFrames=new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            hitFrames[i] = tmph[0][i];
        }
        hitAnim = new Animation<TextureRegion>(0.03f,hitFrames);
        stateTime = 0f;


    }
    public float getSpeed() {
        return speed;
    }
    public void render(SpriteBatch batch) {
        TextureRegion frame = walkAnim.getKeyFrame(stateTime, true);
        TextureRegion jumpFrame = jumpAnim.getKeyFrame(stateTime, false);
        TextureRegion hitFrame = hitAnim.getKeyFrame(stateTime,true);

        if(isHit){
            frame=hitAnim.getKeyFrame(stateTime,true);;
            if (isHit && hitAnim.isAnimationFinished(stateTime)) {
                isHit = false;
                stateTime = 0f;
            }
            }
        else if (isJump) {
            frame = jumpFrame;
            if (jumpAnim.isAnimationFinished(stateTime) && onGround) {
                isJump = false; // Reset status lompat
                stateTime = 0f;  // Reset animasi agar jalan dimulai dari awal
            }
        } else {
            frame = walkAnim.getKeyFrame(stateTime, true);
        }
        TextureRegion frame_new = new TextureRegion(frame);
        if (!hadapkanan) {
            frame_new.flip(true, false);
        }

        batch.draw(frame_new, x, y);

    }
    public void update(float delta){

    //cek lagi gerak atau ga//logic jalan
        if (isMoving || isJump || isHit) {
            stateTime += delta;
        } else {
            stateTime = 0; // kembali ke frame pertama saat diam
        }

    //logic lompat
        // Gravitasi kalau tidak di tanah
        if (!onGround) {
            velocityY -= gravity * delta;
            y += velocityY * delta;

            // Cek kalau kena tanah
            if (y <= groundY) {
                y = groundY;
                onGround = true;
                velocityY = 0;
            }
        }

    }
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
        if (dx > 0) hadapkanan = true;
        else if (dx < 0) hadapkanan = false;
    }
    public void setMoving(boolean isMoving,boolean isJump){
        this.isMoving=isMoving;
        this.isJump=isJump;

    }
    public void jump(){
        if (onGround) {
            velocityY = jumpSpeed;
            onGround = false;
            isJump = true;
            stateTime = 0f;
        }
    }
    public void hit(){
        isHit=true;
        stateTime=0f;
    }
    public void dispose() {

        walk.dispose();
        jump.dispose();
        hit.dispose();
    }
}


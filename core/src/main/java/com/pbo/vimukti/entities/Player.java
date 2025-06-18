package com.pbo.vimukti.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    
    private float speed = 200f;
    public float x, y;
    private float jumpSpeed = 500f;
    float playerHP ;
    float invincibilityTime ;
    private  boolean isinvincible;
    
    float stateTime;
    private float gravity = 1000f;
    private final float groundY = 170f; 
    private float velocityY = 0;
    private float scale = 2.0f; 
    
    private boolean onGround = true;
    private boolean isMoving=false;
    private boolean isJump=false;
    private boolean hadapkanan=true;
    private boolean isHit = false;
    public  boolean isKnockedback=false;
    private boolean isGettingHit = false;
    
        
    Texture walk;
    TextureRegion[] walkFrames;
    Animation<TextureRegion> walkAnim;
        
    Texture jump;
    TextureRegion[] jumpFrames;
    Animation<TextureRegion> jumpAnim;
        
    Texture hit;
    TextureRegion[] hitFrames;
    Animation<TextureRegion> hitAnim;
        
    Texture gethit;
    TextureRegion[] getHitFrames;
    Animation<TextureRegion> getHitAnim;
        
    float velocityX = 0;
    float knockbackPower = 500f;


    public Player() {
        playerHP=200;
        invincibilityTime=0f;
        isinvincible=false;
        scale = 2.0f;
        x = 100;
        y = 170;
        
        walk = new Texture("run.png");
        TextureRegion[][] tmpw = TextureRegion.split(walk, 64, 64);
        walkFrames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            walkFrames[i] = tmpw[0][i];
        }
        walkAnim = new Animation<TextureRegion>(0.1f, walkFrames);
        
        jump = new Texture("jump.png");
        TextureRegion[][] tmpj = TextureRegion.split(jump, 64, 64);
        jumpFrames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            jumpFrames[i] = tmpj[0][i];
        }
        jumpAnim = new Animation<TextureRegion>(0.1f, jumpFrames);
        
        hit = new Texture("hit.png");
        TextureRegion[][] tmph = TextureRegion.split(hit,64,64);
        hitFrames=new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            hitFrames[i] = tmph[0][i];
        }
        hitAnim = new Animation<TextureRegion>(0.03f,hitFrames);
        
        gethit = new Texture("Gethit.png");
        TextureRegion[][] tmpgh = TextureRegion.split(gethit,64,64);
        getHitFrames=new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            getHitFrames[i] = tmpgh[0][i];
        }
        getHitAnim = new Animation<TextureRegion>(0.1f,getHitFrames);
        stateTime = 0f;


    }
    public float getSpeed() {
        return speed;
    }
    public void render(SpriteBatch batch) {
        TextureRegion frame = walkAnim.getKeyFrame(stateTime, true);
        TextureRegion jumpFrame = jumpAnim.getKeyFrame(stateTime, false);
        TextureRegion hitFrame = hitAnim.getKeyFrame(stateTime,true);
        TextureRegion gethitFrame= getHitAnim.getKeyFrame(stateTime,false);

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
                isJump = false; 
                stateTime = 0f;  
            }
        }
        else if (isGettingHit) {
            frame=gethitFrame;
            float alpha = 0.5f + 0.5f * (float)Math.sin(10 * stateTime); 
            batch.setColor(1, 1, 1, alpha);
            if (getHitAnim.isAnimationFinished(stateTime)){
                isGettingHit=false;
            };


        }
        else {
            frame = walkAnim.getKeyFrame(stateTime, true);
        }

        TextureRegion frame_new = new TextureRegion(frame);
        if (!hadapkanan) {
            frame_new.flip(true, false);
        }

        batch.draw(frame_new, x, y, frame_new.getRegionWidth() * scale, frame_new.getRegionHeight() * scale);
        batch.setColor(1, 1, 1, 1f);



    }
    public void update(float delta){
        
        if (invincibilityTime > 0f)
            invincibilityTime -= delta;
    
        if (isMoving || isJump || isHit || isGettingHit) {
            stateTime += delta;
        } else {
            stateTime = 0; 
        }

    
        
        if (!onGround) {
            velocityY -= gravity * delta;
            y += velocityY * delta;

            
            if (y <= groundY) {
                y = groundY;
                onGround = true;
                velocityY = 0;
            }
        }
        
        if (isKnockedback) {
            x += velocityX * delta;
            velocityX *= 0.9f;
            
            if (Math.abs(velocityX) < 5f) {
                velocityX = 0;
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
    public Rectangle getBounds() {
        return new Rectangle(x, y, 64, 64);
    }
    public boolean isHitting(){
        return isHit;
    }

    public void getHitFromEnemy(BaseEnemies enemy) {
        if (!isInvincible()) {

            playerHP -= enemy.damage;
            invincibilityTime = 1.0f; 
            isGettingHit = true;
            System.out.println("Player kena hit! Sisa HP: " + playerHP);
            if (x > enemy.x) {velocityX = knockbackPower; }  
            else {velocityX = -knockbackPower;}
            isKnockedback=true;
            stateTime = 0f;


        }

    }
    public boolean isInvincible(){
        return invincibilityTime>0f;
    }

    public float getPlayerHP() {
        return playerHP;
    }

    public void dispose() {
        walk.dispose();
        jump.dispose();
        hit.dispose();
        gethit.dispose();
    }
}


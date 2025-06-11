package com.pbo.vimukti.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.pbo.vimukti.ui.EnemyHealthBar;

public class Golem extends BaseEnemies {
    
    public float scale;
    
    boolean ishit= false;
    boolean isalive=true;
    private boolean isDeadFinished = false;
    private float attackCooldown = 0f;
    private final float attackDelay = 1.5f; 
    private boolean isAttacking = false;
    private boolean hashit=false;



    

    float stateTime;
    private float gravity = 1000f;
    private final float groundY = 100f; 
    private float velocityY = 0;
    
    private boolean onGround = true;
    private boolean isMoving=false;
    private boolean hadapkanan=true;
    private boolean getHit = false;
    private float hitCooldown = 0f;
    private final float invincibilityDuration = 1.0f;
    
    
    Texture walk;
    TextureRegion[] walkFrames;
    Animation<TextureRegion> walkAnim;
    
    float velocityX = 0;
    float knockbackPower = 500f;
    boolean isKnockedback = false;
    
    Texture dead;
    TextureRegion[] deadFrames;
    Animation<TextureRegion> deadAnim;

    
    Texture gethit;
    TextureRegion[] gethitFrames;
    Animation<TextureRegion> gethitAnim;
    
    Texture attack;
    TextureRegion[] attackFrames;
    Animation<TextureRegion> attackAnim;

    public Golem() {
        scale=3.0f;
        hp=300;
        damage=30;
        x = 150;
        y = 100;
        speed=30f;
        
        
        healthBar = new EnemyHealthBar(60, 8, hp); 
        
        
        walk = new Texture("Golem/Golem_1_walk.png");
        TextureRegion[][] tmpw = TextureRegion.split(walk, 90, 64);
        walkFrames = new TextureRegion[10];
        for (int i = 0; i < 10; i++) {
            walkFrames[i] = tmpw[0][i];
        }
        walkAnim = new Animation<TextureRegion>(0.1f, walkFrames);

        
        gethit = new Texture("Golem/Golem_1_hurt.png");
        TextureRegion[][] tmpg = TextureRegion.split(gethit,90,64);
        gethitFrames=new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            gethitFrames[i] = tmpg[0][i];
        }
        gethitAnim = new Animation<TextureRegion>(0.15f,gethitFrames);

        
        dead = new Texture("Golem/Golem_1_die.png");
        TextureRegion[][] tmpd = TextureRegion.split(dead,90,64);
        deadFrames=new TextureRegion[12];
        for (int i = 0; i < 12; i++) {
            deadFrames[i] = tmpd[0][i];
        }
        deadAnim = new Animation<TextureRegion>(0.1f,deadFrames);
        
        attack = new Texture("Golem/Golem_1_attack.png");
        TextureRegion[][] tmpat = TextureRegion.split(attack,90,64);
        attackFrames=new TextureRegion[11];
        for (int i = 0; i < 11; i++) {
            attackFrames[i] = tmpat[0][i];
        }
        attackAnim= new Animation<TextureRegion>(0.15f,attackFrames);

    }
    public float getSpeed() {
        return speed;
    }
    public void render(SpriteBatch batch) {
        if(isDeadFinished) return;
        TextureRegion frame = walkAnim.getKeyFrame(stateTime, true);
        TextureRegion gethitFrame = gethitAnim.getKeyFrame(stateTime,false);
        TextureRegion deadframe=deadAnim.getKeyFrame(stateTime,false);

        if(!isalive) {
            frame=deadframe;
        }

        else if(getHit){
            frame=gethitFrame;
        }
        else if (isAttacking) {
            frame = attackAnim.getKeyFrame(stateTime, false);
        }
        else {

            frame = walkAnim.getKeyFrame(stateTime, true);
        }
        TextureRegion frame_new = new TextureRegion(frame);
        if (!hadapkanan) {
            frame_new.flip(true, false);
        }

        float drawX = x - (frame_new.getRegionWidth() * scale) / 2f;
        float drawY = y;
        batch.draw(frame_new, drawX, drawY,frame_new.getRegionWidth()*scale,frame_new.getRegionHeight()*scale);

    }
    public void update(float delta,float player_x){
        if(!isalive) {
            stateTime += delta;
            if (deadAnim.isAnimationFinished(stateTime)) {
                isDeadFinished = true; 
            }
            return;
        };

        if (hitCooldown > 0) {
            hitCooldown -= delta;
        }
        if (isKnockedback) {
            x += velocityX * delta;
            velocityX *= 0.9f;
            
            if (Math.abs(velocityX) < 5f) {
                velocityX = 0;
                isKnockedback = false;}
            if (getHit && gethitAnim.isAnimationFinished(stateTime)) {
                getHit = false;
            }
        }

        if (player_x<x){
            x -=delta*speed;
            isMoving=true;
            hadapkanan=false;
        }
        else {
            x+=delta*speed;
            isMoving=true;
            hadapkanan=true;
        }
        
        if (isMoving) {
            stateTime += delta;
        } else {
            stateTime = 0; 
        }
        
        
        float distanceToPlayer = Math.abs(player_x - x);
        if (distanceToPlayer < 40f && attackCooldown <= 0f && isalive && !isAttacking) {
            isAttacking = true;
            hashit=false;
            stateTime=0f;
            
        }
        if(isAttacking){
            stateTime+=delta;
            if (getHit && attackAnim.getKeyFrameIndex(stateTime) < 7) {
                
                isAttacking = false;
                hashit = false;
                stateTime = 0;
                attackCooldown = attackDelay;
                System.out.println("anjay nangkis");
                return;
            }
            if (attackAnim.getKeyFrameIndex(stateTime)==7 ){
                hashit=true;
            }
            if(attackAnim.isAnimationFinished(stateTime)){
                isAttacking=false;
                hashit=false;
                attackCooldown = attackDelay;
                stateTime=0f;
            }
        }
        attackCooldown -= delta;

    }
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
        if (dx > 0) hadapkanan = true;
        else if (dx < 0) hadapkanan = false;
    }

    public void gethit(float player_x){
        if (hitCooldown > 0 || !isalive) return;
        getHit=true;
        isAttacking=false;
        hp -=50;
        System.out.println("HP"+hp);
        if (hp<=0){
            isalive=false;
        }
        if (x > player_x) {velocityX = knockbackPower; }  
        else {velocityX = -knockbackPower;}              
        isKnockedback = true;
        stateTime = 0f;
        hitCooldown = invincibilityDuration;
        hashit = false;
        stateTime = 0f;



    }
    public Rectangle getBounds() {
        
        float hitboxOffsetX = (90/2f - 55/2f) * scale; 
        float hitboxOffsetY = 0; 

        return new Rectangle(x - hitboxOffsetX, y + hitboxOffsetY, 35 * scale, 40 * scale);
    }
    public boolean isAttacking() {
        return isAttacking;
    }
    public boolean isHashit(){
        return hashit;
    }
    public void setishit(boolean value){
        ishit=value;
    }
    public float getStateTime(){
        return stateTime;
    }
    public boolean isDeadFinished() {
        return isDeadFinished;
    }

    @Override
    public boolean isAlive() {
        return isalive;
    }
    public void debugdraw(ShapeRenderer shapeRenderer) {
        if (isDeadFinished) return;

        Rectangle bounds = getBounds();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        shapeRenderer.end();
    }
    public void dispose() {

        walk.dispose();
        gethit.dispose();
        dead.dispose();
        attack.dispose();
        if (healthBar != null) {
            healthBar.dispose();
        }
    }
    
    
    @Override
    public float getMaxHP() {
        return 300f; 
    }
    
    @Override
    public float getSpriteWidth() {
        return 35f * scale; 
    }
    
    @Override
    public float getSpriteHeight() {
        return 40f * scale; 
    }
    
    @Override
    public float getSpriteLeftX() {
        float hitboxOffsetX = (90/2f - 55/2f) * scale;
        return x - hitboxOffsetX; 
    }
}


package com.pbo.vimukti.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Worm extends BaseEnemies {

    //atribut
    boolean ishit= false;
    boolean isalive=true;
    private boolean isDeadFinished = false;
    private float attackCooldown = 0f;
    private final float attackDelay = 1.5f; // musuh bisa mukul tiap 1.5 detik
    private boolean isAttacking = false;



    //kedaan/status game/frame

    float stateTime;
    private float gravity = 1000f;
    private final float groundY = 100f; // posisi tanah (bisa disesuaikan)
    private float velocityY = 0;
    //keadaan/status entities
    private boolean onGround = true;
    private boolean isMoving=false;
    private boolean hadapkanan=true;
    private boolean getHit = false;
    private float hitCooldown = 0f;
    private final float invincibilityDuration = 1.0f;
    //animasi
    //jalan
    Texture walk;
    TextureRegion[] walkFrames;
    Animation<TextureRegion> walkAnim;
    //knockback
    float velocityX = 0;
    float knockbackPower = 500f;
    boolean isKnockedback = false;
    //mati
    Texture dead;
    TextureRegion[] deadFrames;
    Animation<TextureRegion> deadAnim;

    //serang
    Texture gethit;
    TextureRegion[] gethitFrames;
    Animation<TextureRegion> gethitAnim;

    public Worm() {
        hp=200;
        damage=100;
        x = 100;
        y = 100;
        speed=100f;
        //set animasi jalan
        walk = new Texture("Worm/walk.png");
        TextureRegion[][] tmpw = TextureRegion.split(walk, 90, 64);
        walkFrames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            walkFrames[i] = tmpw[0][i];
        }
        walkAnim = new Animation<TextureRegion>(0.1f, walkFrames);

        //set animasi hit
        gethit = new Texture("Worm/GetHit.png");
        TextureRegion[][] tmpg = TextureRegion.split(gethit,90,64);
        gethitFrames=new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            gethitFrames[i] = tmpg[0][i];
        }
        gethitAnim = new Animation<TextureRegion>(0.15f,gethitFrames);

        //set animasi dead
        dead = new Texture("Worm/Death.png");
        TextureRegion[][] tmpd = TextureRegion.split(dead,90,64);
        deadFrames=new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            deadFrames[i] = tmpd[0][i];
        }
        deadAnim = new Animation<TextureRegion>(0.1f,deadFrames);

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
        else {

            frame = walkAnim.getKeyFrame(stateTime, true);
        }
        TextureRegion frame_new = new TextureRegion(frame);
        if (!hadapkanan) {
            frame_new.flip(true, false);
        }

        batch.draw(frame_new, x, y);

    }
    public void update(float delta,float player_x){
        if(!isalive) {
            stateTime += delta;
            if (deadAnim.isAnimationFinished(stateTime)) {
                isDeadFinished = true; // Flag jadi true kalau udah selesai
            }
            return;
        };
        if (hitCooldown > 0) {
            hitCooldown -= delta;
        }
        if (isKnockedback) {
            x += velocityX * delta;
            velocityX *= 0.9f;
            // stop knockback kalau sudah cukup lambat
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
        //cek lagi gerak atau ga//logic jalan
        if (isMoving) {
            stateTime += delta;
        } else {
            stateTime = 0; // kembali ke frame pertama saat diam
        }
        //nyerang
        // Jarak serang
        float distanceToPlayer = Math.abs(player_x - x);
        if (distanceToPlayer < 60f && attackCooldown <= 0f && isalive) {
            isAttacking = true;
            attackCooldown = attackDelay;
            // Serang player (nanti manggil method player.getHitFromEnemy() dari luar)
        } else {
            isAttacking = false;
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
        hp -=50;
        System.out.println("HP"+hp);
        if (hp<=0){
            isalive=false;
        }
        if (x > player_x) {velocityX = knockbackPower; }  // dorong ke kanan
        else {velocityX = -knockbackPower;}              // dorong ke kiri
        isKnockedback = true;
        stateTime = 0f;
        hitCooldown = invincibilityDuration;
//        System.out.println("Knockback velocityX: " + velocityX);


    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, 90, 64);
    }
    public boolean isAttacking() {
        return isAttacking;
    }
    public boolean isDeadFinished() {
        return isDeadFinished;
    }

    @Override
    public boolean isAlive() {
        return isalive;
    }

    public void dispose() {

        walk.dispose();
        gethit.dispose();
        dead.dispose();
    }
}


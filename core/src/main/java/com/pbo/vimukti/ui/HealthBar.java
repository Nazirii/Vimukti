package com.pbo.vimukti.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class HealthBar {
    private float x, y, width, height;
    private float maxHP;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    
    public HealthBar(float x, float y, float width, float height, float maxHP) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxHP = maxHP;
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont(); 
        this.font.getData().setScale(0.8f); 
    }    public void render(SpriteBatch batch, float currentHP) {
        
        float healthPercentage = Math.max(0, Math.min(1, currentHP / maxHP));
        
        
        String healthText = String.format("HP: %.0f/%.0f", currentHP, maxHP);
        font.setColor(0f, 0f, 0f, 1f); 
        font.draw(batch, healthText, x, y + height + 15);
        
        
        batch.end();
        
        
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        
        shapeRenderer.setColor(0.6f, 0.1f, 0.1f, 1f); 
        shapeRenderer.rect(x, y, width, height);
        
        
        if (healthPercentage > 0) {
            if (healthPercentage > 0.6f) {
                shapeRenderer.setColor(0.2f, 0.8f, 0.2f, 1f); 
            } else if (healthPercentage > 0.3f) {
                shapeRenderer.setColor(0.8f, 0.8f, 0.2f, 1f); 
            } else {
                shapeRenderer.setColor(0.8f, 0.3f, 0.1f, 1f); 
            }
            shapeRenderer.rect(x, y, width * healthPercentage, height);
        }
        
        shapeRenderer.end();
        
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0f, 0f, 0f, 1f); 
        shapeRenderer.rect(x, y, width, height);
        
        
        shapeRenderer.setColor(1f, 1f, 1f, 0.8f); 
        shapeRenderer.rect(x + 1, y + 1, width - 2, height - 2);
        
        shapeRenderer.end();
        
        
        batch.begin();
    }
      public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (font != null) {
            font.dispose();
        }
    }
}

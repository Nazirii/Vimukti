package com.pbo.vimukti.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EnemyHealthBar {
    private float width, height;
    private float maxHP;
    private ShapeRenderer shapeRenderer;
    private float offsetY; 
    
    public EnemyHealthBar(float width, float height, float maxHP) {
        this.width = width;
        this.height = height;
        this.maxHP = maxHP;
        this.offsetY = 10f; 
        this.shapeRenderer = new ShapeRenderer();
    }
    
    public void render(SpriteBatch batch, float currentHP, float enemyX, float enemyY, float enemyWidth, float enemyHeight) {
        
        if (currentHP <= 0) return;
        
        
        float healthPercentage = Math.max(0, Math.min(1, currentHP / maxHP));
        
        
        float barX = enemyX + (enemyWidth - width) / 2;
        float barY = enemyY + enemyHeight + offsetY;
        
        
        batch.end();
        
        
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        
        shapeRenderer.setColor(0.4f, 0.1f, 0.1f, 0.8f); 
        shapeRenderer.rect(barX, barY, width, height);
        
        
        if (healthPercentage > 0) {
            if (healthPercentage > 0.6f) {
                shapeRenderer.setColor(0.2f, 0.8f, 0.2f, 0.9f); 
            } else if (healthPercentage > 0.3f) {
                shapeRenderer.setColor(0.8f, 0.6f, 0.1f, 0.9f); 
            } else {
                shapeRenderer.setColor(0.8f, 0.2f, 0.1f, 0.9f); 
            }
            shapeRenderer.rect(barX, barY, width * healthPercentage, height);
        }
        
        shapeRenderer.end();
        
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0f, 0f, 0f, 1f); 
        shapeRenderer.rect(barX, barY, width, height);
        shapeRenderer.end();
        
        
        batch.begin();
    }
    
    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}

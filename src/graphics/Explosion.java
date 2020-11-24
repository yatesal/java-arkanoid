package graphics;

import bricks.Modifier;
import entity.GameEntity;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Explosion implements GameEntity{
    
    private List<Particle> pieces;
    private float alpha;
    private double fadeSpeed;
    private boolean isActive;
    
    public Explosion(int x, int y, Color c, int numPieces){
        isActive = true;
        alpha = 1f;
        fadeSpeed = .05;
        pieces = new ArrayList<Particle>();
        for(int i = 0; i < numPieces; i++){
            pieces.add(new Particle(x,y,c));
        }
    }
    
    public void update() {
        alpha -= fadeSpeed;
        if(alpha < 0){
            alpha = 0;
            isActive = false;
        }
        for(Particle p : pieces){
            p.update();
        }
    }
    
    public float getAlpha(){
        return alpha;
    }
    
    public void draw(Graphics2D g2){
        AlphaComposite alcom;
        alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2.setComposite(alcom); //draw at the set transparency
        for(Particle p : pieces){
            p.draw(g2);
        }
        alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g2.setComposite(alcom); //draw at the full opacity
    }
    
    //placeholder
    public Rectangle getRect(){
        return new Rectangle(0,0,0,0);
    }
    
    public void modify(Modifier m){
        
    }

    public boolean getIsActive() {
        return isActive;
    }
}

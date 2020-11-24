package bricks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Brick {
    
    //fields
    protected int x, y, width, height, strength;
    protected boolean isActive;
    protected Color myColor;
    
    //constructor
    public Brick(int x, int y){
        this.x = x;
        this.y = y;
        width = 30;
        height = 20;
        strength = (int)(Math.random() * 3);
        isActive = true;
        myColor = new Color(0, 255 - strength * 100, 255 - strength * 100);
    }
    
    public Color getColor(){
        return myColor;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public boolean getIsActive(){
        return isActive;
    }
    
    public void setIsActive(boolean active){
        isActive = active;
    }
    
    public void gotHit(){
        strength--;
        if(strength < 0){
            isActive = false;
        } else {
            myColor = new Color(0, 255 - strength * 100, 255 - strength * 100);
        }
    }
    
    public Rectangle getRect(){
        return new Rectangle(x, y, width, height);
    }

    
    public void update(){
        
    }
        
    public void draw(Graphics2D g2){
        g2.setColor(myColor);
        g2.fillRoundRect(x, y, width, height, 20, 20);
    }
}

package bricks;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import main.GameWindow;

public class Modifier extends Brick{
    
    protected boolean isFalling;
    protected double dy;
    
    public Modifier(int x, int y){
        super(x, y);
        dy = (Math.random() * 4 + 1);
        isFalling = false;
    }
    
    public void setIsFalling(boolean falling){
        isFalling = falling;
    }
    
    public void update(){
        if(isFalling){
            y += dy;
        }
        if(y > GameWindow.HEIGHT){
            isActive = false;
        }
    }
    
    public void draw(Graphics2D g2){
        super.draw(g2);
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(x, y, width, height, 20, 20);
    }
    
}

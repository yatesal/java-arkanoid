package entity;

import bricks.Modifier;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import main.GameWindow;


public class Ball implements GameEntity {
    
    //fields
    private double x, y, dx, dy; //position and velocity values
    private Color ballColor;
    
    //collision
    private double xdest, ydest, xtemp, ytemp;
    private boolean topLeft, topRight, bottomRight, bottomLeft;
    
    //tail
    private List<Shape> tail;
    private float alpha;
    public final int TAIL_LENGTH = 10;
    
    public static final int SIZE = 10;
    
    public Ball(){
        
        x = GameWindow.WIDTH / 2;
        y = GameWindow.HEIGHT / 2;
        dx = 4;
        dy = 4;
        ballColor = Color.DARK_GRAY;
        
        xdest = x;
        ydest = y;
        
        xtemp = x;
        ytemp = y;
        
        topLeft = topRight = bottomLeft = bottomRight = false;
        
        tail = new ArrayList<Shape>();
        alpha = 0f;
        
    }

    public void update(){

        //update position of ball based on velocity
        x += dx;
        y += dy;
        
        //if ball hits a wall, bounce away
        if(x < 0){
            dx = -dx;
            x = 0;
        }
        
        if(y < 0){
            dy = -dy;
            y = 0;
        }
        
        if(x > GameWindow.WIDTH - SIZE){
            dx = -dx;
            x = GameWindow.WIDTH - SIZE;
        }
        
        if(y > GameWindow.HEIGHT - SIZE - 23){
            dy = -dy;
            y = GameWindow.HEIGHT - SIZE - 23;
        }
        
        updateTail();
    }
    
    public void draw(Graphics2D g2){
        g2.setColor(ballColor);
        g2.fillOval((int)x, (int)y, SIZE, SIZE);
        drawTail(g2);
    }
    
    public void updateTail() {
        if(tail.size() > 0){
            tail.remove(0);
        }
        while(tail.size() < TAIL_LENGTH){
            tail.add(new Ellipse2D.Double(x, y, SIZE, SIZE));
        }
    }
    
    public void drawTail(Graphics2D g2){
        AlphaComposite alcom;
        for(Shape s: tail){
            if(alpha > 1){
                alpha = 1;
            }
            if(alpha < 0){
                alpha = 0;
            }
            alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2.setComposite(alcom);
            
            g2.fill(s);
            alpha +=.1;
        }
        alpha = 0;
        alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g2.setComposite(alcom);
    }
            
    //Determine if a corner of our ball intersects with a different Rectangle
    public void calculateCorners(double xpos, double ypos, Rectangle r){
        
        Point tl = new Point((int)xpos, (int)ypos);
        Point tr = new Point((int)xpos + SIZE, (int)ypos);
        Point bl = new Point((int)xpos, (int)ypos + SIZE);
        Point br = new Point((int)xpos + SIZE, (int)ypos + SIZE);
        
        topLeft = topRight = bottomLeft = bottomRight = false;
        
        topLeft = r.contains(tl);
        topRight = r.contains(tr);
        bottomLeft = r.contains(bl);
        bottomRight = r.contains(br);
    }
    
    public boolean checkCollisions(Rectangle r){
        boolean collisionDetected = false;
        
        xdest = x + dx;
        ydest = y + dy;
        
        xtemp = x;
        ytemp = y;
        
        //check for horizontal collisions
        calculateCorners(xdest, y, r);
        
        if(dx > 0){
            if (topRight || bottomRight){
                dx = -dx;
                collisionDetected = true;
                xtemp = r.getMinX() - SIZE - 1;
            }
        }
        
        if(dx < 0){
            if(topLeft || bottomLeft){
                dx = -dx;
                collisionDetected = true;
                xtemp = r.getMaxX()+1;
            }
        }
        
        //check for vertical collisions
        calculateCorners(x, ydest, r);
        
        if(dy > 0){
            if (bottomRight || bottomLeft){
                dy = -dy;
                collisionDetected = true;
                ytemp = r.getMinY() - SIZE- 1;
            }
        }

        if(dy < 0){
            if (topRight || topLeft){
                dy = -dy;
                collisionDetected = true;
                ytemp = r.getMaxY() + 1;
            }
        }        
        
        return collisionDetected;
    }
    
    public void setPosition(){
        xtemp += dx;
        ytemp += dy;
        x = xtemp;
        y = ytemp;
    }
    
    public Rectangle getRect(){
        return new Rectangle((int)x, (int)y, SIZE, SIZE);
    }

    public void modify(Modifier m) {

    }
    
    public boolean getIsActive(){
        return true;
    }
}

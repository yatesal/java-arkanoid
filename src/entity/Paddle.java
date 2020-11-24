package entity;

import bricks.LargePaddle;
import bricks.Modifier;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import main.GameWindow;

public class Paddle implements GameEntity{
    
    private double drawx; //x coordinate at which paddle is drawn
    private double targetx; //x coordinate at which paddle is trying to get
    public static final int YPOS = 400; //y coorindate of paddle
    private int paddleWidth, paddleHeight;
    private Color paddleColor;
    private boolean largePaddleActive;
    private int targetLargePaddleCount, largePaddleCount;
    
    public Paddle(){
        drawx = GameWindow.WIDTH / 2 - 40;
        targetx = drawx;
        paddleWidth = 80;
        paddleHeight = 20;
        paddleColor = Color.DARK_GRAY;
        largePaddleActive = false;
        targetLargePaddleCount = 500;
        largePaddleCount = 0;
    }
    
    public void update(){
        drawx += (targetx - drawx) * .1; //helps smooth out the motion
        if(largePaddleActive){
            largePaddleCount++;
            if(largePaddleCount > targetLargePaddleCount){
                largePaddleActive = false;
                largePaddleCount = 0;
                paddleWidth = paddleWidth / 2;
            }
        }
        
        if(drawx < 0){
            drawx = 0;
        }
        
        if (drawx > GameWindow.WIDTH - paddleWidth - 6){
            drawx = GameWindow.WIDTH - paddleWidth - 6;
        }
        
    }
    
    public void draw(Graphics2D g2){
        g2.setColor(paddleColor);
        g2.fillRoundRect((int)drawx, YPOS, paddleWidth, paddleHeight, 20, 20);
    }
    
    public Rectangle getRect(){
        return new Rectangle((int)drawx, YPOS, paddleWidth, paddleHeight);
    }

    public void mouseMoved(int mousex){
        //position center of paddle at location of mouse
        targetx = mousex - paddleWidth / 2;
    }

    public void largePaddle(){
        if(!largePaddleActive){
            paddleWidth = paddleWidth * 2;
            largePaddleActive = true;
        }
    }
    
    public void modify(Modifier m) {
        if(m instanceof LargePaddle){
            largePaddle();
        }
    }    
    
    public boolean getIsActive(){
        return true;
    }
    
}

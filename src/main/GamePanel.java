package main;

import bricks.Brick;
import bricks.LargePaddle;
import bricks.Modifier;
import entity.Ball;
import entity.GameEntity;
import entity.Paddle;
import graphics.Explosion;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel{
    
    private BufferedImage img;
    private Graphics2D g2;
    private boolean running;
    private Paddle thePaddle;
    private Ball theBall;
    private Brick[][] bricks;
    private List<GameEntity> entities;
    private int score;
    
    //screen shake fields
    private boolean screenShakeActive;
    private int screenShakeCount, targetScreenShakeCount;
    
    //brick placement fields
    public static final int NUM_COLS = 9;
    public static final int NUM_ROWS = 4;
    public static final int GRIDSIZE = 60;
    public static final int OFFSET = 60;
    
    public GamePanel(){
        
        //setting up game window
        img = new BufferedImage(GameWindow.WIDTH, GameWindow.HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) img.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        running = true;
        
        //set up entities
        thePaddle = new Paddle();
        theBall = new Ball();
        entities = new ArrayList<GameEntity>();
        entities.add(thePaddle);
        entities.add(theBall);
        
        //set up bricks
        bricks = new Brick[NUM_ROWS][NUM_COLS];
        generateBricks();
        
        //setting up screenshake variables and score
        screenShakeActive = false;
        screenShakeCount = 0;
        targetScreenShakeCount = 10;
        score = 0;
        
        //setting up mouse motion listener
        this.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e) {
                
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                thePaddle.mouseMoved(e.getX());
            }
            
        });
    }
    
    //game loop
    public void playGame(){
        
        //game loop
        while(running){
            update();
            
            draw();
            
            repaint(); //part of Swing; calls paintComponent
                        
            try {
                Thread.sleep(20);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    
    //update based on time
    public void update(){
        for(GameEntity ge : entities){
            ge.update();
        }
        removeEntities();
        checkCollisions();
        theBall.setPosition();
        updateBricks();
        updateScreenShake();

    }
    
    public void updateBricks(){
        for(Brick[] row : bricks){
            for(Brick b: row){
                b.update();
            }
        }
    }
    
    public void updateScreenShake(){
        if(screenShakeActive){
            screenShakeCount++;
        } else {
            screenShakeCount = 0;
        }
        if(screenShakeCount > targetScreenShakeCount){
            screenShakeActive = false;
        }
    }
    
    //create image in memory
    public void draw(){
        
        //background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);
        g2.setColor(Color.BLACK);
        g2.drawString("Score: " + score, 20, 20);
        
        //entities
        for(GameEntity ge : entities){
            ge.draw(g2);
        }
        drawBricks();
        
    }
    
    //paint the image on the GameWindow
    public void paintComponent(Graphics g){
        if(screenShakeActive){
            int x = (int)(Math.random() * 10 - 5);
            int y = (int)(Math.random() * 10 - 5);
            g.drawImage(img, x, y, null);
        } else{
            g.drawImage(img, 0, 0, null);
        }
    }
    
    public void drawBricks(){
        for(Brick[] row: bricks){
            for(Brick b : row){
                if(b.getIsActive()){
                    b.draw(g2);
                }
            }
        }
    }
    
    public void generateBricks(){
        for(int row = 0; row < bricks.length; row++){
            for(int col = 0; col < bricks[row].length; col++){
                int rand = (int)(Math.random()*5);
                if(rand == 0){
                    bricks[row][col] = new LargePaddle(col * GRIDSIZE + OFFSET, row * GRIDSIZE + OFFSET);
                } else {
                    bricks[row][col] = new Brick(col * GRIDSIZE + OFFSET, row * GRIDSIZE + OFFSET);
                }
            }
        }
    }
    
    public void removeEntities(){
        int i = 0;
        while(i < entities.size()){
            if(!entities.get(i).getIsActive()){
                entities.remove(i);
            } else {
                i++;
            }
        }
    }
    
    public void checkCollisions(){
        theBall.checkCollisions(thePaddle.getRect());
        
        for(Brick[] row : bricks){
            for(Brick b : row){
                if(b.getIsActive()) {
                    if(theBall.checkCollisions(b.getRect())){
                        score += 10;
                        if(b instanceof Modifier){
                            score += 50;
                            ((Modifier)b).setIsFalling(true);
                        } else {
                            b.gotHit();
                            if(!b.getIsActive()){
                                score += 100;
                                entities.add(new Explosion((int)(b.getRect().getMinX() + b.getWidth() / 2), (int)(b.getRect().getMinY() + b.getHeight() / 2), b.getColor(), 20));
                            }
                            screenShakeActive = true;
                        }
                    }
                }
                if(b.getRect().intersects(thePaddle.getRect())){
                    b.setIsActive(false);
                    thePaddle.modify((Modifier)b);
                }
            }
        }
    }
}
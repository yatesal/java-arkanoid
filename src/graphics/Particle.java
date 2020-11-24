package graphics;

import java.awt.Color;
import java.awt.Graphics2D;

public class Particle {
    
    private double x, y, dx, dy, size;
    private Color c;
    
    public Particle(double x, double y){
        this.x = x;
        this.y = y;
        dx = Math.random() * 6 - 3;
        dy = Math.random() * 6 - 3;
        size = Math.random() * 10 + 4;
        c = Color.CYAN;
    }
    
    public Particle(double x, double y, Color c){
        this.x = x;
        this.y = y;
        dx = Math.random() * 6 - 3;
        dy = Math.random() * 6 - 3;
        size = Math.random() * 10 + 4;
        this.c = c;
    }
    
    public void update(){
        x += dx;
        y += dy;
    }
    
    public void draw(Graphics2D g2){
        g2.setColor(c);
        g2.fillRoundRect((int)x, (int)y, (int)size, (int)size, (int)(size/2),(int)(size/2));
    }
}

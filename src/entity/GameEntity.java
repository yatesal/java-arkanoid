package entity;

import bricks.Modifier;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface GameEntity {
    
    void update();
    void draw(Graphics2D g2);
    Rectangle getRect();
    void modify(Modifier m);
    boolean getIsActive();
    
}

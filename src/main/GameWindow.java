package main;

import javax.swing.JFrame;

public class GameWindow {
    
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    
    public static void main(String[] args){
        
        JFrame theWindow = new JFrame("Game Shell");
        GamePanel thePanel = new GamePanel();
        theWindow.setSize(WIDTH, HEIGHT);
        theWindow.setResizable(false);
        theWindow.setLocationRelativeTo(null);
        theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        theWindow.add(thePanel);
        
        theWindow.setVisible(true);
        
        thePanel.playGame();
                
    }
}

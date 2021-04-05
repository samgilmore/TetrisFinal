package Tetris;

import javafx.scene.shape.Rectangle;

/**
 *Simple Square class extends JavaFX Rectangle object
 * 
 */
public class Square extends Rectangle{

    /**
     *Constructor that presets width and height to PIECE_SIZE final int in App.java
     */
    public Square() {
        this.setWidth(App.PIECE_SIZE);
        this.setHeight(App.PIECE_SIZE);
    }
    
}

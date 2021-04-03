package Tetris;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Piece {
    
    //Each piece in Tetris only has 4 squares.
    ArrayList<Square> squares = new ArrayList<>();
    Color color;
    private String id;
    
    //In Tetris, each piece can be rotated into 4 different orientations.
    //This int identifies which orientation the piece is in.
    public int orientation;

    //Default constructor
    public Piece() {
        this.id = "null";
        this.color = Color.TRANSPARENT;
    }
    
    //Constructor
    public Piece(ArrayList<Square> squares, String id) {
        this.squares = squares;
        
        this.id = id;
        this.orientation = 1;
        
        if (id.equals("I")) {
            this.color = Color.CYAN;
        } else if (id.equals("J")) {
            this.color = Color.DARKBLUE;
        } else if (id.equals("L")) {
            this.color = Color.ORANGE;
        } else if (id.equals("O")) {
            this.color = Color.GOLD;
        } else if (id.equals("S")) {
            this.color = Color.LIGHTGREEN;
        } else if (id.equals("T")) {
            this.color = Color.MAGENTA;
        } else if (id.equals("Z")){
            this.color = Color.RED;
        } else {
            this.color = Color.TRANSPARENT;
        }
        
        for (int i = 0; i < 4; i++) {
            squares.get(i).setFill(color);
        }
        
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getId() {
        return id;
    }

    public int getOrientation() {
        return orientation;
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }
    
    public void changeOrientation() {
        //when called, the piece rotates a single time. If at final orientation (4), then it goes back to 1.
        if (orientation < 4) {
            orientation += 1;
        } else {
            orientation = 1;
        }
    }
    
    
}

package Tetris;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author Sam Gilmore, Harris Khan
 */
public class Piece {
    
    //Each piece in Tetris only has 4 squares.
    ArrayList<Square> squares = new ArrayList<>();
    Color color;
    private String id;
    
    //In Tetris, each piece can be rotated into 4 different orientations.
    //This int identifies which orientation the piece is in.
    private int orientation;

    //Default constructor

    /**
     * Default constructor
     */
    public Piece() {
        this.id = "null";
        this.color = Color.TRANSPARENT;
    }
    
    //Constructor

    /**
     * A constructor
     * @param squares An ArrayList of Square objects
     * @param id A String that identifies the Piece object
     */
    public Piece(ArrayList<Square> squares, String id) {
        this.squares = squares;
        
        this.id = id;
        this.orientation = 1;
        
        //Sets the color of the object depending on the id
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

    /**
     *Sets the String identifier for the Piece object
     * @param id String id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *Sets the orientation value for the Piece object
     * @param orientation Integer orientation
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    /**
     *Gets the String id of the Piece object
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     *Gets the Integer orientation of the Piece object
     * @return int orientation
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     *Gets the ArrayList of 4 Square objects of the Piece object
     * @return ArrayList<Square> squares
     */
    public ArrayList<Square> getSquares() {
        return squares;
    }
    
    /**
     *Increments the orientation Integer value by 1 if 1-3 or back to 1 if already 4
     */
    public void changeOrientation() {
        //when called, the piece rotates a single time. If at final orientation (4), then it goes back to 1.
        if (orientation < 4) {
            orientation += 1;
        } else {
            orientation = 1;
        }
    }
}

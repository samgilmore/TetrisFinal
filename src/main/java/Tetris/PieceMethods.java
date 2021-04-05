package Tetris;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Sam Gilmore, Harris Khan
 */
public class PieceMethods {
    
    public static final int PIECE_SIZE = App.PIECE_SIZE; //PIECE_SIZE int from App.java
    public static final int ROWS = App.ROWS; //ROW int from App.java
    public static final int COLUMNS = App.COLUMNS; //COLUMNS int from App.java
    public static int[][] grid = App.grid; //grid array from App.java
    private static final int BOARD_WIDTH = COLUMNS * PIECE_SIZE; //BOARD_WIDTH int calculated using COLUMNS and PIECE_SIZE (pixel width)
    private static final int BOARD_HEIGHT = ROWS * PIECE_SIZE; //BOARD_HEIGHT int calculated using ROWS and PIECE_SIZE (pixel height)
    
    /**
     *Creates a random piece out of 7 possible options, each with a specific spawn location and orientation
     * @return Piece object
     */
    public static Piece createRandomPiece() {
       //A random number 0-6 is generated
       Random rand = new Random();
       int randomPiece = rand.nextInt(7);
       
       //An id and ArrayList of Square's will be returned in the Piece object
       String id;
       ArrayList<Square> squares = new ArrayList<>();
       
       //A tetris piece only has four squares. These squares spawn in specific places
       //at the top of the board.
       squares.add(new Square());
       squares.add(new Square());
       squares.add(new Square());
       squares.add(new Square());
       
       //Piece spawn positions from: https://harddrop.com/wiki/Spawn_Location#:~:text=Tetris%20at%20tetris.com%20spawns,existing%20blocks%20on%20the%20field.
       //Each square must be set in a specific spot when spawned in
       if (randomPiece == 0) {
           id = "I";
           squares.get(0).setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           squares.get(1).setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           squares.get(2).setX(BOARD_WIDTH / 2);
           squares.get(3).setX(BOARD_WIDTH / 2 + PIECE_SIZE);
           squares.get(0).setY(PIECE_SIZE);
           squares.get(1).setY(PIECE_SIZE);
           squares.get(2).setY(PIECE_SIZE);
           squares.get(3).setY(PIECE_SIZE);
       } else if (randomPiece == 1) {
           id = "J";
           squares.get(0).setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           squares.get(1).setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           squares.get(2).setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           squares.get(3).setX(BOARD_WIDTH / 2);
           squares.get(1).setY(PIECE_SIZE);
           squares.get(2).setY(PIECE_SIZE);
           squares.get(3).setY(PIECE_SIZE);
       } else if (randomPiece == 2) {
           id = "L";
           squares.get(0).setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           squares.get(1).setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           squares.get(2).setX(BOARD_WIDTH / 2);
           squares.get(3).setX(BOARD_WIDTH / 2);
           squares.get(0).setY(PIECE_SIZE);
           squares.get(1).setY(PIECE_SIZE);
           squares.get(2).setY(PIECE_SIZE);
       } else if (randomPiece == 3) {
           id = "O";
           squares.get(0).setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           squares.get(1).setX(BOARD_WIDTH / 2);
           squares.get(2).setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           squares.get(3).setX(BOARD_WIDTH / 2);
           squares.get(2).setY(PIECE_SIZE);
           squares.get(3).setY(PIECE_SIZE);
       } else if (randomPiece == 4) {
           id = "S";
           squares.get(0).setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           squares.get(1).setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           squares.get(2).setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           squares.get(3).setX(BOARD_WIDTH / 2);
           squares.get(0).setY(PIECE_SIZE);
           squares.get(1).setY(PIECE_SIZE);
       } else if (randomPiece == 5) {
           id = "T";
           squares.get(0).setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           squares.get(1).setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           squares.get(2).setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           squares.get(3).setX(BOARD_WIDTH / 2);
           squares.get(0).setY(PIECE_SIZE);
           squares.get(1).setY(PIECE_SIZE);
           squares.get(3).setY(PIECE_SIZE);
       } else {
           id = "Z";
           squares.get(0).setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           squares.get(1).setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           squares.get(2).setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           squares.get(3).setX(BOARD_WIDTH / 2);
           squares.get(2).setY(PIECE_SIZE);
           squares.get(3).setY(PIECE_SIZE);
       }
       return new Piece(squares,id);
    }

    //The tetris "grid" is how data will be stored if a piece has landed in a
    //certain position on the board. If a piece occupies an element in grid, it will
    //hold a value of '1'. If there is no piece in that element, it will hold a value
    //of '0'. Thus, as a piece is falling, we can only move it left or right if
    //all of its 4 squares are able to move into an empty, or '0', spot in the grid.
    //It also cannot move outside of the playing area.

    /**
     *This moves a Piece object leftwards visually if nothing is in the way 
     * @param piece Piece object with position moved left a space
     */
    public static void moveLeft(Piece piece) {
        //Continue if each square won't move off the board
        if (piece.getSquares().get(0).getX() - PIECE_SIZE >= 0 &&
                piece.getSquares().get(1).getX() - PIECE_SIZE >= 0 &&
                piece.getSquares().get(2).getX() - PIECE_SIZE >= 0 &&
                piece.getSquares().get(3).getX() - PIECE_SIZE >= 0) {
            //Next, find the value of the potential location in the grid array for each square in the piece.
            //If the location is empty for every square, aka == 0, then move piece visually.
            //We don't need to update grid array yet because the piece hasn't landed.
            int potentials[] = new int[4];
            for (int i=0; i < 4; i++) {
                potentials[i] = grid[(int) piece.getSquares().get(i).getX() / PIECE_SIZE - 1][(int) piece.getSquares().get(i).getY() / PIECE_SIZE];
            }
            if (potentials[0] == 0 && potentials[1] == 0 && potentials[2] == 0 && potentials[3] == 0) {
                for (int i=0; i < piece.getSquares().size(); i++) {
                    piece.getSquares().get(i).setX(piece.getSquares().get(i).getX() - PIECE_SIZE);
                }
            }
        }
    }
    
    /**
     *This moves a Piece object rightwards visually if nothing is in the way
     * @param piece Piece object with position moved right a space
     */
    public static void moveRight(Piece piece) {
        //Continue if each square won't move off the board
        if (piece.getSquares().get(0).getX() + PIECE_SIZE <= BOARD_WIDTH - PIECE_SIZE &&
                piece.getSquares().get(1).getX() + PIECE_SIZE <= BOARD_WIDTH - PIECE_SIZE &&
                piece.getSquares().get(2).getX() + PIECE_SIZE <= BOARD_WIDTH - PIECE_SIZE &&
                piece.getSquares().get(3).getX() + PIECE_SIZE <= BOARD_WIDTH - PIECE_SIZE) {
            //Next, find the value of the potential location in the grid array for each square in the piece.
            //If the location is empty for every square, aka == 0, then move piece visually.
            //We don't need to update grid array yet because the piece hasn't landed.
            int potentials[] = new int[4];
            for (int i=0; i < 4; i++) {
                potentials[i] = grid[(int) piece.getSquares().get(i).getX() / PIECE_SIZE + 1][(int) piece.getSquares().get(i).getY() / PIECE_SIZE];
            }
            if (potentials[0] == 0 && potentials[1] == 0 && potentials[2] == 0 && potentials[3] == 0) {
                for (int i=0; i < piece.getSquares().size(); i++) {
                    piece.getSquares().get(i).setX(piece.getSquares().get(i).getX() + PIECE_SIZE);
                }
            }
        }
    }
    
    /**
     *This moves a Piece object down if there is space. If something blocks that object, then the piece is permanently placed.
     * The grid 2D array in App.java is also updated to hold values of 1 where this piece now lies. A new falling object will now be
     * controlled by the player and a new next piece will be generated.
     * @param piece Piece object moved downwards a space
     */
    public static void moveDown(Piece piece) {
        //Accounts for the bottom of the board
        if (piece.getSquares().get(0).getY() + PIECE_SIZE <= BOARD_HEIGHT - PIECE_SIZE &&
                piece.getSquares().get(1).getY() + PIECE_SIZE <= BOARD_HEIGHT - PIECE_SIZE &&
                piece.getSquares().get(2).getY() + PIECE_SIZE <= BOARD_HEIGHT - PIECE_SIZE &&
                piece.getSquares().get(3).getY() + PIECE_SIZE <= BOARD_HEIGHT - PIECE_SIZE) {
           
            //Next, find the value of the potential location in the grid array for each square in the piece.
            //If the location is empty for every square, == 0, then move piece visually.
            //We don't need to update grid array yet because the piece hasn't landed.
            
            int potentialOne = grid[(int) piece.getSquares().get(0).getX() / PIECE_SIZE ][(int) piece.getSquares().get(0).getY() / PIECE_SIZE + 1];
            int potentialTwo = grid[(int) piece.getSquares().get(1).getX() / PIECE_SIZE ][(int) piece.getSquares().get(1).getY() / PIECE_SIZE + 1];
            int potentialThree = grid[(int) piece.getSquares().get(2).getX() / PIECE_SIZE ][(int) piece.getSquares().get(2).getY() / PIECE_SIZE + 1];
            int potentialFour = grid[(int) piece.getSquares().get(3).getX() / PIECE_SIZE ][(int) piece.getSquares().get(3).getY() / PIECE_SIZE + 1];
            
            if (potentialOne == 0 && potentialTwo == 0 && potentialThree == 0 && potentialFour == 0) {
                piece.getSquares().get(0).setY(piece.getSquares().get(0).getY() + PIECE_SIZE);
                piece.getSquares().get(1).setY(piece.getSquares().get(1).getY() + PIECE_SIZE);
                piece.getSquares().get(2).setY(piece.getSquares().get(2).getY() + PIECE_SIZE);
                piece.getSquares().get(3).setY(piece.getSquares().get(3).getY() + PIECE_SIZE);
            }  
        }
        // Now, generate a new piece once the current piece has landed
        
        if (piece.getSquares().get(0).getY() == BOARD_HEIGHT - PIECE_SIZE || piece.getSquares().get(1).getY() == BOARD_HEIGHT - PIECE_SIZE ||
                piece.getSquares().get(2).getY() == BOARD_HEIGHT - PIECE_SIZE || piece.getSquares().get(3).getY() == BOARD_HEIGHT - PIECE_SIZE || isPieceBelow(piece)) {
            for (int i = 0; i < 4; i++) {
                grid[(int) piece.getSquares().get(i).getX() / PIECE_SIZE][(int) piece.getSquares().get(i).getY() / PIECE_SIZE] = 1;
            }
            //Since piece has landed, we need to check if a row has been completed
            App.deleteRow(App.pane);
            
            //Next, we need to generate a new piece now that the old one has landed as well as take control of the new falling piece.
            App.fallingPiece = App.nextPiece;
            App.nextPiece = createRandomPiece();
            App.pane.getChildren().addAll(App.fallingPiece.getSquares().get(0),App.fallingPiece.getSquares().get(1),App.fallingPiece.getSquares().get(2),App.fallingPiece.getSquares().get(3)); 
            App.gridLines.toFront();
            App.topCover.toFront();
            App.handleKeyPress(App.fallingPiece);
            
            //Now that nextPiece has been updated, we need to update the next piece image
            switch (App.nextPiece.getId()) {
                case "I":
                    App.nextView.setImage(App.iImage);
                    break;
                case "J":
                    App.nextView.setImage(App.jImage);
                    break;
                case "L":
                    App.nextView.setImage(App.lImage);
                    break;
                case "O":
                    App.nextView.setImage(App.oImage);
                    break;
                case "S":
                    App.nextView.setImage(App.sImage);
                    break;
                case "T":
                    App.nextView.setImage(App.tImage);
                    break;
                case "Z":
                    App.nextView.setImage(App.zImage);
                    break;
                default:
                    break;
            }
            App.nextBox.toFront();          
        }
    }
    
    /**
     *This returns a Boolean that determines whether a piece lies below the current position of the falling piece
     * @return Boolean isPieceBelow
     */
    private static boolean isPieceBelow(Piece piece) {
        boolean isPieceBelow = false;
        for (int i = 0; i < piece.getSquares().size(); i++) {
            if (App.grid[(int) piece.getSquares().get(i).getX() / PIECE_SIZE][(int) piece.getSquares().get(i).getY() / PIECE_SIZE + 1] == 1) {
                isPieceBelow = true;
            }
        }
        return isPieceBelow;
    }
    
    /**
     *This returns a Boolean that determines whether a piece or part of the board will be in the way of the movement of a single Square
     * @param square Square square
     * @param xMove int xMove
     * @param yMove int yMove
     * @return Boolean
     */
    public static boolean isPieceEmpty(Square square, int xMove, int yMove) {
        boolean isXContained = false; 
        boolean isYContained = false;
        //logic to ensure each piece is within the window if shifted 
        if(xMove >= 0) {
            isXContained = square.getX() + xMove * PIECE_SIZE <= BOARD_WIDTH - PIECE_SIZE; 
        }
        else if(xMove < 0) {
            isXContained = square.getX() + xMove * PIECE_SIZE >= 0; 
        }
        
        if (yMove >= 0) {
            isYContained = square.getY() - yMove * PIECE_SIZE > 0; 
        }
        else if (yMove < 0) {
            isYContained = square.getY() + yMove * PIECE_SIZE < BOARD_HEIGHT; 
        }
        return isXContained && isYContained && App.grid[(int) square.getX() / PIECE_SIZE + xMove][(int) square.getY() / PIECE_SIZE - yMove] == 0; 
    }
    
    /**
     *This moves a Square object rightwards a certain number of times if in the playable area
     * 
     */
    private static void moveSquareRight(Square square, int repetitions) {
        for(int i = 0; i < repetitions; i++) {
            if (square.getX() <= BOARD_WIDTH - 2 * PIECE_SIZE) {
            square.setX(square.getX() + PIECE_SIZE);
            }
        }
    }

    /**
     *This moves a Square object leftwards a certain number of times if in the playable area
     * 
     */
    private static void  moveSquareLeft(Square square, int repetitions) {
        for (int i = 0; i < repetitions; i++) {
            if (square.getX() >= PIECE_SIZE) {
                square.setX(square.getX() - PIECE_SIZE);
            }
        }
    }

    /**
     *This moves a Square object upwards a certain number of times if in the playable area
     * 
     */
    private static void moveSquareUp(Square square, int repetitions) {
        for (int i = 0; i < repetitions; i++) {
            if (square.getY() > PIECE_SIZE) {
                square.setY(square.getY() - PIECE_SIZE);
            }
        }
    }

    /**
     *This moves a Square object downwards a certain number of times if in the playable area
     * 
     */
    private static void moveSquareDown(Square square, int repetitions) {
        for (int i = 0; i < repetitions; i++) {
            if (square.getY() < BOARD_HEIGHT - PIECE_SIZE) {
                square.setY(square.getY() + PIECE_SIZE);
            }
        }
    }
    
    /**
     *This takes a Piece and moves each individual square depending on the current orientation of the piece.
     * TETRIS pieces have a very particular way of rotating.
     * @param piece Piece object
     */
    public static void rotatePiece(Piece piece) {
        int orientationOfPiece = piece.getOrientation(); //pulls the current orientation of the piece
        
        //Depending on the piece id and the current orientation, the squares will move in a variety of directions
        switch(piece.getId()) {
            case "I": {
                if(orientationOfPiece == 1 && isPieceEmpty(piece.getSquares().get(0), 2, 1) && isPieceEmpty(piece.getSquares().get(1), 1, 0) &&  isPieceEmpty(piece.getSquares().get(2), 0, -2) && isPieceEmpty(piece.getSquares().get(3), -1, -2)) {
                    moveSquareUp(piece.getSquares().get(0), 1);
                    moveSquareRight(piece.getSquares().get(0), 2);
                    moveSquareRight(piece.getSquares().get(1), 1);
                    moveSquareDown(piece.getSquares().get(2), 1);
                    moveSquareDown(piece.getSquares().get(3), 2);        
                    moveSquareLeft(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                if(orientationOfPiece == 2 && isPieceEmpty(piece.getSquares().get(0), 1, -2) && isPieceEmpty(piece.getSquares().get(1), 0, -1) &&  isPieceEmpty(piece.getSquares().get(2), 0, -1) && isPieceEmpty(piece.getSquares().get(3), -2, 1)) {
                    moveSquareDown(piece.getSquares().get(0), 2);
                    moveSquareRight(piece.getSquares().get(0), 1);
                    moveSquareDown(piece.getSquares().get(1), 1);
                    moveSquareLeft(piece.getSquares().get(2), 1);
                    moveSquareUp(piece.getSquares().get(3), 1);        
                    moveSquareLeft(piece.getSquares().get(3), 2);
                    piece.changeOrientation();
                    break;
                }
                if(orientationOfPiece == 3 && isPieceEmpty(piece.getSquares().get(0), -2, -1) && isPieceEmpty(piece.getSquares().get(1), -1, 0) &&  isPieceEmpty(piece.getSquares().get(2), 0, 1) && isPieceEmpty(piece.getSquares().get(3), 1, 2)) {
                    moveSquareDown(piece.getSquares().get(0), 1);
                    moveSquareLeft(piece.getSquares().get(0), 2);
                    moveSquareLeft(piece.getSquares().get(1), 1);
                    moveSquareUp(piece.getSquares().get(2), 1);
                    moveSquareUp(piece.getSquares().get(3), 2);        
                    moveSquareRight(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                if(orientationOfPiece == 4 && isPieceEmpty(piece.getSquares().get(0), -1, 2) && isPieceEmpty(piece.getSquares().get(1), 0, 1) &&  isPieceEmpty(piece.getSquares().get(2), 1, 0) && isPieceEmpty(piece.getSquares().get(3), 2, -1)) {
                    moveSquareUp(piece.getSquares().get(0), 2);
                    moveSquareLeft(piece.getSquares().get(0), 1);
                    moveSquareUp(piece.getSquares().get(1), 1);
                    moveSquareRight(piece.getSquares().get(2), 1);
                    moveSquareDown(piece.getSquares().get(3), 1);        
                    moveSquareRight(piece.getSquares().get(3), 2);
                    piece.changeOrientation();
                    break;
                }
                break; 
            }
            case "Z": {
                 if(orientationOfPiece == 1 && isPieceEmpty(piece.getSquares().get(0), 2, 0) && isPieceEmpty(piece.getSquares().get(1), 1, -1) && isPieceEmpty(piece.getSquares().get(3), -1, -1)) {
                    moveSquareRight(piece.getSquares().get(0), 2);
                    moveSquareDown(piece.getSquares().get(1), 1);
                    moveSquareRight(piece.getSquares().get(1), 1);
                    moveSquareLeft(piece.getSquares().get(3), 1);        
                    moveSquareDown(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                 if(orientationOfPiece == 2 && isPieceEmpty(piece.getSquares().get(0), 0, -2) && isPieceEmpty(piece.getSquares().get(1), -1, -1) && isPieceEmpty(piece.getSquares().get(3), -1, 1)) {
                    moveSquareDown(piece.getSquares().get(0), 2);
                    moveSquareDown(piece.getSquares().get(1), 1);
                    moveSquareLeft(piece.getSquares().get(1), 1);
                    moveSquareLeft(piece.getSquares().get(3), 1);        
                    moveSquareUp(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                 if(orientationOfPiece == 3 && isPieceEmpty(piece.getSquares().get(0), -2, 0) && isPieceEmpty(piece.getSquares().get(1), -1, 1) && isPieceEmpty(piece.getSquares().get(3), 1, 1)) {
                    moveSquareLeft(piece.getSquares().get(0), 2);
                    moveSquareLeft(piece.getSquares().get(1), 1);
                    moveSquareUp(piece.getSquares().get(1), 1);
                    moveSquareUp(piece.getSquares().get(3), 1);        
                    moveSquareRight(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                 if(orientationOfPiece == 4 && isPieceEmpty(piece.getSquares().get(0), 0, 2) && isPieceEmpty(piece.getSquares().get(1), 1, 1) && isPieceEmpty(piece.getSquares().get(3), 1, -1)) {
                    moveSquareUp(piece.getSquares().get(0), 2);
                    moveSquareUp(piece.getSquares().get(1), 1);
                    moveSquareRight(piece.getSquares().get(1), 1);
                    moveSquareRight(piece.getSquares().get(3), 1);        
                    moveSquareDown(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                break; 
            }
            case "T": {
                if(orientationOfPiece == 1 && isPieceEmpty(piece.getSquares().get(0), 1, 1) && isPieceEmpty(piece.getSquares().get(2), 1, -1) && isPieceEmpty(piece.getSquares().get(3), -1, -1)) {
                    moveSquareRight(piece.getSquares().get(0), 1);
                    moveSquareUp(piece.getSquares().get(0), 1);
                    moveSquareRight(piece.getSquares().get(2), 1);
                    moveSquareDown(piece.getSquares().get(2), 1);
                    moveSquareLeft(piece.getSquares().get(3), 1);        
                    moveSquareDown(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                if(orientationOfPiece == 2 && isPieceEmpty(piece.getSquares().get(0), 1, -1) && isPieceEmpty(piece.getSquares().get(2), -1, -1) && isPieceEmpty(piece.getSquares().get(3), -1, 1)) {
                    moveSquareRight(piece.getSquares().get(0), 1);
                    moveSquareDown(piece.getSquares().get(0), 1);
                    moveSquareLeft(piece.getSquares().get(2), 1);
                    moveSquareDown(piece.getSquares().get(2), 1);
                    moveSquareLeft(piece.getSquares().get(3), 1);        
                    moveSquareUp(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                if(orientationOfPiece == 3 && isPieceEmpty(piece.getSquares().get(0), -1, -1) && isPieceEmpty(piece.getSquares().get(2), -1, 1) && isPieceEmpty(piece.getSquares().get(3), 1, 1)) {
                    moveSquareDown(piece.getSquares().get(0), 1);
                    moveSquareLeft(piece.getSquares().get(0), 1);
                    moveSquareLeft(piece.getSquares().get(2), 1);
                    moveSquareUp(piece.getSquares().get(2), 1);
                    moveSquareRight(piece.getSquares().get(3), 1);        
                    moveSquareUp(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                if(orientationOfPiece == 4 && isPieceEmpty(piece.getSquares().get(0), -1, 1) && isPieceEmpty(piece.getSquares().get(2), 1, 1) && isPieceEmpty(piece.getSquares().get(3), 1, -1)) {
                    moveSquareLeft(piece.getSquares().get(0), 1);
                    moveSquareUp(piece.getSquares().get(0), 1);
                    moveSquareRight(piece.getSquares().get(2), 1);
                    moveSquareUp(piece.getSquares().get(2), 1);
                    moveSquareRight(piece.getSquares().get(3), 1);        
                    moveSquareDown(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                break; 
            }
            case "S": {
                if(orientationOfPiece == 1 && isPieceEmpty(piece.getSquares().get(0), 1, 1) && isPieceEmpty(piece.getSquares().get(2), 1, -1) && isPieceEmpty(piece.getSquares().get(3), 0, -2)) {
                    moveSquareRight(piece.getSquares().get(0), 1);
                    moveSquareUp(piece.getSquares().get(0), 1);
                    moveSquareRight(piece.getSquares().get(2), 1);
                    moveSquareDown(piece.getSquares().get(2), 1);        
                    moveSquareDown(piece.getSquares().get(3), 2);
                    piece.changeOrientation();
                    break;
                }
                if(orientationOfPiece == 2 && isPieceEmpty(piece.getSquares().get(0), 1, -1) && isPieceEmpty(piece.getSquares().get(2), -1, -1) && isPieceEmpty(piece.getSquares().get(3), -2, 0)) {
                    moveSquareRight(piece.getSquares().get(0), 1);
                    moveSquareDown(piece.getSquares().get(0), 1);
                    moveSquareLeft(piece.getSquares().get(2), 1);
                    moveSquareDown(piece.getSquares().get(2), 1);
                    moveSquareLeft(piece.getSquares().get(3), 2);        
                    piece.changeOrientation();
                    break;
                }
                if(orientationOfPiece == 3 && isPieceEmpty(piece.getSquares().get(0), -1, -1) && isPieceEmpty(piece.getSquares().get(2), -1, 1) && isPieceEmpty(piece.getSquares().get(3), 0, 2)) {
                    moveSquareLeft(piece.getSquares().get(0), 1);
                    moveSquareDown(piece.getSquares().get(0), 1);
                    moveSquareLeft(piece.getSquares().get(2), 1);
                    moveSquareUp(piece.getSquares().get(2), 1);
                    moveSquareUp(piece.getSquares().get(3), 2);        
                    piece.changeOrientation();
                    break;
                }
                if(orientationOfPiece == 4 && isPieceEmpty(piece.getSquares().get(0), -1, 1) && isPieceEmpty(piece.getSquares().get(2), 1, 1) && isPieceEmpty(piece.getSquares().get(3), 2, 0)) {
                    moveSquareLeft(piece.getSquares().get(0), 1);
                    moveSquareUp(piece.getSquares().get(0), 1);
                    moveSquareRight(piece.getSquares().get(2), 1);
                    moveSquareUp(piece.getSquares().get(2), 1);
                    moveSquareRight(piece.getSquares().get(3), 2);
                    piece.changeOrientation();
                    break;
                }
                break; 
            }
            case "O": {
                break; 
            }
            case "L": {
                if(orientationOfPiece == 1 && isPieceEmpty(piece.getSquares().get(0), 1, 1) && isPieceEmpty(piece.getSquares().get(2), -1, -1) && isPieceEmpty(piece.getSquares().get(3), 0, -2)) {
                    moveSquareRight(piece.getSquares().get(0), 1);
                    moveSquareUp(piece.getSquares().get(0), 1);
                    moveSquareLeft(piece.getSquares().get(2), 1);
                    moveSquareDown(piece.getSquares().get(2), 1);
                    moveSquareDown(piece.getSquares().get(3), 2);
                    piece.changeOrientation();
                    break;
                }
                if(orientationOfPiece == 2 && isPieceEmpty(piece.getSquares().get(0), 1, -1) && isPieceEmpty(piece.getSquares().get(2), -1, 1) && isPieceEmpty(piece.getSquares().get(3), -2, 0)) {
                    moveSquareRight(piece.getSquares().get(0), 1);
                    moveSquareDown(piece.getSquares().get(0), 1);
                    moveSquareLeft(piece.getSquares().get(2), 1);
                    moveSquareUp(piece.getSquares().get(2), 1);
                    moveSquareLeft(piece.getSquares().get(3), 2);
                    piece.changeOrientation();
                    break;
                }
                if(orientationOfPiece == 3 && isPieceEmpty(piece.getSquares().get(0), -1, -1) && isPieceEmpty(piece.getSquares().get(2), 1, 1) && isPieceEmpty(piece.getSquares().get(3), 0, 2)) {
                    moveSquareLeft(piece.getSquares().get(0), 1);
                    moveSquareDown(piece.getSquares().get(0), 1);
                    moveSquareRight(piece.getSquares().get(2), 1);
                    moveSquareUp(piece.getSquares().get(2), 1);
                    moveSquareUp(piece.getSquares().get(3), 2);
                    piece.changeOrientation();
                    break;
                }
                if(orientationOfPiece == 4 && isPieceEmpty(piece.getSquares().get(0), -1, 1) && isPieceEmpty(piece.getSquares().get(2), 1, -1) && isPieceEmpty(piece.getSquares().get(3), 2, 0)) {
                    moveSquareLeft(piece.getSquares().get(0), 1);
                    moveSquareUp(piece.getSquares().get(0), 1);
                    moveSquareRight(piece.getSquares().get(2), 1);
                    moveSquareDown(piece.getSquares().get(2), 1);
                    moveSquareRight(piece.getSquares().get(3), 2);
                    piece.changeOrientation();
                    break;
                }
                break; 
            }
            case "J": {
                if (orientationOfPiece == 1 && isPieceEmpty(piece.getSquares().get(0), 2, 0) && isPieceEmpty(piece.getSquares().get(1), 1, 1) && isPieceEmpty(piece.getSquares().get(3), -1, -1)) {
                    moveSquareRight(piece.getSquares().get(0), 2);
                    moveSquareUp(piece.getSquares().get(1), 1);
                    moveSquareRight(piece.getSquares().get(1), 1);
                    moveSquareDown(piece.getSquares().get(3), 1);
                    moveSquareLeft(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                if (orientationOfPiece == 2 && isPieceEmpty(piece.getSquares().get(0), 0, -2) && isPieceEmpty(piece.getSquares().get(1), 1, -1) && isPieceEmpty(piece.getSquares().get(3), -1, 1)) {
                    moveSquareDown(piece.getSquares().get(0), 2);
                    moveSquareDown(piece.getSquares().get(1), 1);
                    moveSquareRight(piece.getSquares().get(1), 1);
                    moveSquareUp(piece.getSquares().get(3), 1);
                    moveSquareLeft(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                if (orientationOfPiece == 3 && isPieceEmpty(piece.getSquares().get(0), -2, 0) && isPieceEmpty(piece.getSquares().get(1), -1, -1) && isPieceEmpty(piece.getSquares().get(3), 1, 1)) {
                    moveSquareLeft(piece.getSquares().get(0), 2);
                    moveSquareDown(piece.getSquares().get(1), 1);
                    moveSquareLeft(piece.getSquares().get(1), 1);
                    moveSquareUp(piece.getSquares().get(3), 1);
                    moveSquareRight(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                if (orientationOfPiece == 4 && isPieceEmpty(piece.getSquares().get(0), 0, 2) && isPieceEmpty(piece.getSquares().get(1), -1, 1) && isPieceEmpty(piece.getSquares().get(3), 1, -1)) {
                    moveSquareUp(piece.getSquares().get(0), 2);
                    moveSquareUp(piece.getSquares().get(1), 1);
                    moveSquareLeft(piece.getSquares().get(1), 1);
                    moveSquareDown(piece.getSquares().get(3), 1);
                    moveSquareRight(piece.getSquares().get(3), 1);
                    piece.changeOrientation();
                    break;
                }
                break;
            }
        }
    }
    
}

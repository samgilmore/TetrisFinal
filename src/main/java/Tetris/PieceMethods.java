package Tetris;

import java.util.ArrayList;
import java.util.Random;

public class PieceMethods {
    
    public static final int PIECE_SIZE = App.PIECE_SIZE;
    public static final int ROWS = App.ROWS;
    public static final int COLUMNS = App.COLUMNS;
    public static int[][] grid = App.grid;
    private static final int BOARD_WIDTH = COLUMNS * PIECE_SIZE;
    private static final int BOARD_HEIGHT = ROWS * PIECE_SIZE;
    
    public static Piece createRandomPiece() {
       Random rand = new Random();
       int randomPiece = rand.nextInt(7);
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
    
    public static void moveDown(Piece piece) {
        if (piece.getSquares().get(0).getY() + PIECE_SIZE <= BOARD_HEIGHT - PIECE_SIZE &&
                piece.getSquares().get(1).getY() + PIECE_SIZE <= BOARD_HEIGHT - PIECE_SIZE &&
                piece.getSquares().get(2).getY() + PIECE_SIZE <= BOARD_HEIGHT - PIECE_SIZE &&
                piece.getSquares().get(3).getY() + PIECE_SIZE <= BOARD_HEIGHT - PIECE_SIZE) {
           
            //Next, find the value of the potential location in the grid array for each square in the piece.
            //If the location is empty for every square, aka == 0, then move piece visually.
            //We don't need to update grid array yet because the piece hasn't landed.
            
            int potentialOne = grid[(int) piece.getSquares().get(0).getX() / PIECE_SIZE ][(int) piece.getSquares().get(0).getY() / PIECE_SIZE];
            int potentialTwo = grid[(int) piece.getSquares().get(1).getX() / PIECE_SIZE ][(int) piece.getSquares().get(1).getY() / PIECE_SIZE];
            int potentialThree = grid[(int) piece.getSquares().get(2).getX() / PIECE_SIZE ][(int) piece.getSquares().get(2).getY() / PIECE_SIZE];
            int potentialFour = grid[(int) piece.getSquares().get(3).getX() / PIECE_SIZE ][(int) piece.getSquares().get(3).getY() / PIECE_SIZE];
            
            if (potentialOne == 0 && potentialTwo == 0 && potentialThree == 0 && potentialFour == 0) {
                piece.getSquares().get(0).setY(piece.getSquares().get(0).getY() + PIECE_SIZE);
                piece.getSquares().get(1).setY(piece.getSquares().get(1).getY() + PIECE_SIZE);
                piece.getSquares().get(2).setY(piece.getSquares().get(2).getY() + PIECE_SIZE);
                piece.getSquares().get(3).setY(piece.getSquares().get(3).getY() + PIECE_SIZE);
            }  
        }
        // generate a new piece once the current piece has landed
        
        if (piece.getSquares().get(0).getY() == BOARD_HEIGHT - PIECE_SIZE || piece.getSquares().get(1).getY() == BOARD_HEIGHT - PIECE_SIZE ||
                piece.getSquares().get(2).getY() == BOARD_HEIGHT - PIECE_SIZE || piece.getSquares().get(3).getY() == BOARD_HEIGHT - PIECE_SIZE || isPieceBelow(piece)) {
            for (int i = 0; i < 4; i++) {
                grid[(int) piece.getSquares().get(i).getX() / PIECE_SIZE][(int) piece.getSquares().get(i).getY() / PIECE_SIZE] = 1;
            }
            //Since piece has landed, we need to check if a row has been completed
            
            //Next, we need to generate a new piece now that the old one has landed as well as take control of the new falling piece.
            App.fallingPiece = App.nextPiece;
            App.nextPiece = createRandomPiece();
            App.pane.getChildren().addAll(App.fallingPiece.getSquares().get(0),App.fallingPiece.getSquares().get(1),App.fallingPiece.getSquares().get(2),App.fallingPiece.getSquares().get(3)); 
            App.gridLines.toFront();
            App.handleKeyPress(App.fallingPiece);
        }
        
    }
    
    private static boolean isPieceBelow(Piece piece) {
        boolean isPieceBelow = false;
        for (int i = 0; i < piece.getSquares().size(); i++) {
            if (App.grid[(int) piece.getSquares().get(i).getX() / PIECE_SIZE][(int) piece.getSquares().get(i).getY() / PIECE_SIZE + 1] == 1) {
                isPieceBelow = true;
            }
        }
        return isPieceBelow;
    }
}

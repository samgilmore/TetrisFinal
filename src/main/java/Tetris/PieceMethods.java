package Tetris;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class PieceMethods {
    
    public static final int PIECE_SIZE = App.PIECE_SIZE;
    public static final int ROWS = App.ROWS;
    public static final int COLUMNS = App.COLUMNS;
    public static int[][] grid = App.grid;
    private static final int BOARD_WIDTH = COLUMNS * PIECE_SIZE;
    
    public static Piece createRandomPiece() {
       Random rand = new Random();
       int randomPiece = rand.nextInt(7);
       String id;
       ArrayList<Square> squares = new ArrayList<>();
       
       //A tetris piece only has four squares. These squares spawn in specific places
       //at the top of the board.
       Square one = new Square();
       Square two = new Square();
       Square three = new Square();
       Square four = new Square();
       
       //Piece spawn positions from: https://harddrop.com/wiki/Spawn_Location#:~:text=Tetris%20at%20tetris.com%20spawns,existing%20blocks%20on%20the%20field.

       if (randomPiece == 0) {
           id = "I";
           one.setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           two.setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           three.setX(BOARD_WIDTH / 2);
           four.setX(BOARD_WIDTH / 2 + PIECE_SIZE);
           one.setY(PIECE_SIZE);
           two.setY(PIECE_SIZE);
           three.setY(PIECE_SIZE);
           four.setY(PIECE_SIZE);
       } else if (randomPiece == 1) {
           id = "J";
           one.setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           two.setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           three.setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           four.setX(BOARD_WIDTH / 2);
           two.setY(PIECE_SIZE);
           three.setY(PIECE_SIZE);
           four.setY(PIECE_SIZE);
       } else if (randomPiece == 2) {
           id = "L";
           one.setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           two.setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           three.setX(BOARD_WIDTH / 2);
           four.setX(BOARD_WIDTH / 2);
           one.setY(PIECE_SIZE);
           two.setY(PIECE_SIZE);
           three.setY(PIECE_SIZE);
       } else if (randomPiece == 3) {
           id = "O";
           one.setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           two.setX(BOARD_WIDTH / 2);
           three.setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           four.setX(BOARD_WIDTH / 2);
           three.setY(PIECE_SIZE);
           four.setY(PIECE_SIZE);
       } else if (randomPiece == 4) {
           id = "S";
           one.setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           two.setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           three.setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           four.setX(BOARD_WIDTH / 2);
           one.setY(PIECE_SIZE);
           two.setY(PIECE_SIZE);
       } else if (randomPiece == 5) {
           id = "T";
           one.setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           two.setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           three.setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           four.setX(BOARD_WIDTH / 2);
           one.setY(PIECE_SIZE);
           two.setY(PIECE_SIZE);
           four.setY(PIECE_SIZE);
       } else {
           id = "Z";
           one.setX(BOARD_WIDTH / 2 - (PIECE_SIZE * 2));
           two.setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           three.setX(BOARD_WIDTH / 2 - PIECE_SIZE);
           four.setX(BOARD_WIDTH / 2);
           three.setY(PIECE_SIZE);
           four.setY(PIECE_SIZE);
       }
       
       squares.add(one);
       squares.add(two);
       squares.add(three);
       squares.add(four);
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
            int potentialOne = grid[(int) piece.getSquares().get(0).getX() / PIECE_SIZE - 1][(int) piece.getSquares().get(0).getY() / PIECE_SIZE];
            int potentialTwo = grid[(int) piece.getSquares().get(1).getX() / PIECE_SIZE - 1][(int) piece.getSquares().get(1).getY() / PIECE_SIZE];
            int potentialThree = grid[(int) piece.getSquares().get(2).getX() / PIECE_SIZE - 1][(int) piece.getSquares().get(2).getY() / PIECE_SIZE];
            int potentialFour = grid[(int) piece.getSquares().get(3).getX() / PIECE_SIZE - 1][(int) piece.getSquares().get(3).getY() / PIECE_SIZE];
            if (potentialOne == 0 && potentialTwo == 0 && potentialThree == 0 && potentialFour == 0) {
                piece.getSquares().get(0).setX(piece.getSquares().get(0).getX() - PIECE_SIZE);
                piece.getSquares().get(1).setX(piece.getSquares().get(1).getX() - PIECE_SIZE);
                piece.getSquares().get(2).setX(piece.getSquares().get(2).getX() - PIECE_SIZE);
                piece.getSquares().get(3).setX(piece.getSquares().get(3).getX() - PIECE_SIZE);
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
            int potentialOne = grid[(int) piece.getSquares().get(0).getX() / PIECE_SIZE + 1][(int) piece.getSquares().get(0).getY() / PIECE_SIZE];
            int potentialTwo = grid[(int) piece.getSquares().get(1).getX() / PIECE_SIZE + 1][(int) piece.getSquares().get(1).getY() / PIECE_SIZE];
            int potentialThree = grid[(int) piece.getSquares().get(2).getX() / PIECE_SIZE + 1][(int) piece.getSquares().get(2).getY() / PIECE_SIZE];
            int potentialFour = grid[(int) piece.getSquares().get(3).getX() / PIECE_SIZE + 1][(int) piece.getSquares().get(3).getY() / PIECE_SIZE];
            if (potentialOne == 0 && potentialTwo == 0 && potentialThree == 0 && potentialFour == 0) {
                piece.getSquares().get(0).setX(piece.getSquares().get(0).getX() + PIECE_SIZE);
                piece.getSquares().get(1).setX(piece.getSquares().get(1).getX() + PIECE_SIZE);
                piece.getSquares().get(2).setX(piece.getSquares().get(2).getX() + PIECE_SIZE);
                piece.getSquares().get(3).setX(piece.getSquares().get(3).getX() + PIECE_SIZE);
            }
        }
    }
    
    
}

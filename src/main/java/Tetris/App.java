package Tetris;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.Line;

public class App extends Application {

    public static final int PIECE_SIZE = 30;
    public static final int ROWS = 22;
    public static final int COLUMNS = 10;
    public static int[][] grid = new int [COLUMNS][ROWS];
    public static int score = 0;
    
    private static Pane pane = new Pane();
    
    @Override
    public void start(Stage stage) {
        
        Piece nextPiece = PieceMethods.createRandomPiece();
        pane.getChildren().addAll(nextPiece.getSquares().get(0),nextPiece.getSquares().get(1),nextPiece.getSquares().get(2),nextPiece.getSquares().get(3));
        
        
        
        
        
        //Creating the Grid Lines
        Group grid = new Group();
        for (int i = 0; i < COLUMNS; i++) {
            Line tempLine = new Line(PIECE_SIZE + (i * PIECE_SIZE), 0, PIECE_SIZE + (i * PIECE_SIZE), ROWS * PIECE_SIZE);
            tempLine.setStrokeWidth(1);
            grid.getChildren().add(tempLine); 
        }
        for (int i = 0; i < ROWS; i++) {
            Line tempLine = new Line(0, PIECE_SIZE + (i * PIECE_SIZE), COLUMNS * PIECE_SIZE, PIECE_SIZE + (i * PIECE_SIZE));
            tempLine.setStrokeWidth(1);
            grid.getChildren().add(tempLine);
        }
        
        pane.getChildren().addAll(grid);
        Scene scene = new Scene(pane, COLUMNS * PIECE_SIZE + 250, ROWS * PIECE_SIZE);
        stage.setScene(scene);
        stage.setTitle("TETRIS");
        stage.show();
        
    }

    public static void main(String[] args) {
        launch();
    }

}
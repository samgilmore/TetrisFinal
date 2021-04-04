package Tetris;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class App extends Application {

    public static final int PIECE_SIZE = 30;
    public static final int ROWS = 22;
    public static final int COLUMNS = 10;
    public static int[][] grid = new int [COLUMNS][ROWS];
    public static int score = 0;
    
    private static Pane pane = new Pane();
    
    @Override
    public void start(Stage stage) {
        
        //The tetris "grid" is how data will be stored if a piece has landed in a
        //certain position on the board. If a piece occupies an element in grid, it will
        //hold a value of '1'. If there is no piece in that element, it will hold a value of '0'.
        
        //Fill grid with initial 0's
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++){
                grid[j][i] = 0;
            }
        }
        
        //Background color for grid
        Rectangle back = new Rectangle();
        back.setWidth(COLUMNS * PIECE_SIZE);
        back.setHeight(ROWS * PIECE_SIZE);
        back.setFill(Color.BLACK);
        
        VBox rightPanel = new VBox();
        rightPanel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        rightPanel.setMinWidth(250);
        //rightPanel.set;
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setTranslateX(COLUMNS * PIECE_SIZE);
        
        Text scoreLabel = new Text();
        scoreLabel.setText("SCORE");
        scoreLabel.setTextAlignment(TextAlignment.CENTER);
        scoreLabel.setFont(Font.font("Arial Black", 35));
        scoreLabel.setFill(Color.GREEN);
        HBox scoreBox = new HBox();
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.getChildren().add(scoreLabel);
       // scoreLabel.setY(ROWS * PIECE_SIZE / 3);
        
        rightPanel.getChildren().addAll(scoreBox);
        pane.getChildren().addAll(back,rightPanel);
        
        Piece nextPiece = PieceMethods.createRandomPiece();
        pane.getChildren().addAll(nextPiece.getSquares().get(0),nextPiece.getSquares().get(1),nextPiece.getSquares().get(2),nextPiece.getSquares().get(3));
        
        
        
        //Creating the Grid Lines
        Group grid = new Group();
        for (int i = 0; i < COLUMNS; i++) {
            Line tempLine = new Line(PIECE_SIZE + (i * PIECE_SIZE), 0, PIECE_SIZE + (i * PIECE_SIZE), ROWS * PIECE_SIZE);
            tempLine.setStrokeWidth(1);
            tempLine.setStroke(Color.LIGHTGREY);
            grid.getChildren().add(tempLine); 
        }
        for (int i = 0; i < ROWS; i++) {
            Line tempLine = new Line(0, PIECE_SIZE + (i * PIECE_SIZE), COLUMNS * PIECE_SIZE, PIECE_SIZE + (i * PIECE_SIZE));
            tempLine.setStrokeWidth(1);
            tempLine.setStroke(Color.LIGHTGREY);
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
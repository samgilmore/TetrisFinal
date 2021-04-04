package Tetris;

import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    public static Group gridLines = new Group();
    public static int score = 0;
    private static boolean running = true;
    public static Piece fallingPiece;
    public static Piece nextPiece = PieceMethods.createRandomPiece();
    
    public static Pane pane = new Pane();
    private static Scene scene = new Scene(pane, COLUMNS * PIECE_SIZE + 250, ROWS * PIECE_SIZE);
    
    @Override
    public void start(Stage stage) {
        
        //Background color for grid
        Rectangle back = new Rectangle();
        back.setWidth(COLUMNS * PIECE_SIZE);
        back.setHeight(ROWS * PIECE_SIZE);
        back.setFill(Color.BLACK);
        
        VBox rightPanel = new VBox();
        rightPanel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        rightPanel.setMinWidth(250);
        rightPanel.setSpacing(25);
        rightPanel.setMinHeight(ROWS * PIECE_SIZE);
        rightPanel.setTranslateX(COLUMNS * PIECE_SIZE);
        
        Text scoreLabel = new Text();
        scoreLabel.setText("SCORE");
        scoreLabel.setTextAlignment(TextAlignment.CENTER);
        scoreLabel.setFont(Font.font("Arial Black", 35));
        scoreLabel.setStroke(Color.BLACK);
        scoreLabel.setStrokeWidth(1);
        scoreLabel.setFill(Color.GREEN);
        HBox scoreLabelBox = new HBox();
        scoreLabelBox.setPadding(new Insets(150,0,0,0));
        scoreLabelBox.setAlignment(Pos.CENTER);
        scoreLabelBox.getChildren().add(scoreLabel);
        
        Text scoreText = new Text();
        scoreText.setText("0");
        scoreText.setTextAlignment(TextAlignment.CENTER);
        scoreText.setFont(Font.font("Arial Black", 30));
        scoreText.setFill(Color.BLACK);
        HBox scoreBox = new HBox();
        scoreBox.setPadding(new Insets(0,0,90,0));
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.getChildren().add(scoreText);
        
        Button startButton = new Button("START");
        startButton.minWidth(40);
        startButton.setStyle("-fx-font-size: 2.5em; -fx-border-color: #111111; -fx-border-width: 2px;");
        HBox startBox = new HBox();
        startBox.setAlignment(Pos.CENTER);
        startBox.getChildren().add(startButton);
        
        Button restartButton = new Button("RESTART");
        restartButton.minWidth(40);
        restartButton.setStyle("-fx-font-size: 2.5em; -fx-border-color: #111111; -fx-border-width: 2px;");
        HBox restartBox = new HBox();
        restartBox.setAlignment(Pos.CENTER);
        restartBox.getChildren().add(restartButton);
        
        rightPanel.getChildren().addAll(scoreLabelBox, scoreBox, startBox, restartBox);
        pane.getChildren().addAll(back,rightPanel);
        
        fallingPiece = nextPiece;
        nextPiece = PieceMethods.createRandomPiece();
        pane.getChildren().addAll(fallingPiece.getSquares().get(0),fallingPiece.getSquares().get(1),fallingPiece.getSquares().get(2),fallingPiece.getSquares().get(3)); 
        handleKeyPress(fallingPiece);
        
        
//        pane.getChildren().addAll(nextPiece.getSquares().get(0),nextPiece.getSquares().get(1),nextPiece.getSquares().get(2),nextPiece.getSquares().get(3)); 
//        handleKeyPress(nextPiece);
        
        //Creating the Grid Lines
        for (int i = 0; i < COLUMNS; i++) {
            Line tempLine = new Line(PIECE_SIZE + (i * PIECE_SIZE), 0, PIECE_SIZE + (i * PIECE_SIZE), ROWS * PIECE_SIZE);
            tempLine.setStrokeWidth(1);
            tempLine.setStroke(Color.LIGHTGREY);
            gridLines.getChildren().add(tempLine); 
        }
        for (int i = 0; i < ROWS; i++) {
            Line tempLine = new Line(0, PIECE_SIZE + (i * PIECE_SIZE), COLUMNS * PIECE_SIZE, PIECE_SIZE + (i * PIECE_SIZE));
            tempLine.setStrokeWidth(1);
            tempLine.setStroke(Color.LIGHTGREY);
            gridLines.getChildren().add(tempLine);
        }
        
        pane.getChildren().addAll(gridLines);
        
        stage.setScene(scene);
        stage.setTitle("TETRIS");
        stage.show();
        
        //The tetris "grid" is how data will be stored if a piece has landed in a
        //certain position on the board. If a piece occupies an element in grid, it will
        //hold a value of '1'. If there is no piece in that element, it will hold a value of '0'.
        
        //Fill grid with initial 0's
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++){
                grid[j][i] = 0;
            }
        }
        
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (running == true) {
                        score++;
                        scoreText.setText(Integer.toString(score));
                        PieceMethods.moveDown(fallingPiece);
                    }
                });
            }
        };
        
        
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
        
    }

    public static void main(String[] args) {
        launch();
    }

    public static void handleKeyPress (Piece piece) {
                scene.setOnKeyPressed((KeyEvent t) -> {
                    if (t.getCode() == KeyCode.A) {
                        PieceMethods.moveLeft(piece);
                    } else if (t.getCode() == KeyCode.D) {
                        PieceMethods.moveRight(piece);
                    } else if (t.getCode() == KeyCode.S) {
                        PieceMethods.moveDown(piece);
                    }
                });
    }
    
}
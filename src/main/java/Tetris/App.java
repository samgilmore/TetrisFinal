package Tetris;

import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public static int score = 0;
    private static boolean running = false;
    public static Piece fallingPiece;
    public static Piece nextPiece = PieceMethods.createRandomPiece();
    private static int losingTicks = 0;
    
    public static Pane pane = new Pane();
    private static final Scene scene = new Scene(pane, COLUMNS * PIECE_SIZE + 250, ROWS * PIECE_SIZE);
    public static Group gridLines = new Group();
    private static final VBox rightPanel = new VBox();
    private static final Rectangle back = new Rectangle();
    public static final Rectangle topCover = new Rectangle();
    private static final Text scoreLabel = new Text();
    private static final HBox scoreLabelBox = new HBox();
    private static final Text scoreText = new Text();
    private static final HBox scoreBox = new HBox();
    private static final Button startButton = new Button("START");
    private static final HBox startBox = new HBox();
    private static final Button resetButton = new Button("RESET");
    private static final HBox resetBox = new HBox();
    private static final Image logoImage = new Image("file:tetrislogo.png");
    private static final ImageView logoView = new ImageView();
    private static final HBox logoBox = new HBox();
    public static final Image iImage = new Image("file:i.jpg");
    public static final Image jImage = new Image("file:j.jpg");
    public static final Image lImage = new Image("file:l.jpg");
    public static final Image oImage = new Image("file:o.jpg");
    public static final Image sImage = new Image("file:s.jpg");
    public static final Image tImage = new Image("file:t.jpg");
    public static final Image zImage = new Image("file:z.jpg");
    public static final ImageView nextView = new ImageView();
    public static final HBox nextBox = new HBox();
    private static final Text nextText = new Text("NEXT:");
    private static final Image gameOverImage = new Image("file:gameover.png");
    private static final ImageView gameOverView = new ImageView();
    
    @Override
    public void start(Stage stage) {
        //Background color for grid
        back.setWidth(COLUMNS * PIECE_SIZE);
        back.setHeight(ROWS * PIECE_SIZE);
        back.setFill(Color.BLACK);
        
        topCover.setWidth(COLUMNS * PIECE_SIZE + 1);
        topCover.setHeight(PIECE_SIZE * 2);
        topCover.setFill(Color.BLACK);
        
        nextView.setPreserveRatio(true);
        nextView.setFitWidth(40);
        nextText.setFont(Font.font("Arial Black", 20));
        nextText.setFill(Color.WHITE);
        nextBox.setMinWidth(COLUMNS * PIECE_SIZE);
        nextBox.setSpacing(10);
        nextBox.setAlignment(Pos.CENTER);
        nextBox.setMinHeight(PIECE_SIZE * 2);
        
        rightPanel.setBackground(new Background(new BackgroundFill(Color.web("000930"), CornerRadii.EMPTY, Insets.EMPTY)));
        rightPanel.setMinWidth(250);
        rightPanel.setSpacing(25);
        rightPanel.setMinHeight(ROWS * PIECE_SIZE);
        rightPanel.setTranslateX(COLUMNS * PIECE_SIZE);
        
        //image from https://static.wikia.nocookie.net/nintendo/images/4/46/Tetris_logo.png/revision/latest?cb=20191220154645&path-prefix=en
        logoView.setImage(logoImage);
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);
        logoBox.setPadding(new Insets(20,0,0,0));
        logoBox.setAlignment(Pos.CENTER);
        logoBox.getChildren().add(logoView);
        
        scoreLabel.setText("SCORE");
        scoreLabel.setTextAlignment(TextAlignment.CENTER);
        scoreLabel.setFont(Font.font("Arial Black", 35));
        scoreLabel.setStroke(Color.WHITE);
        scoreLabel.setStrokeWidth(2);
        scoreLabel.setFill(Color.LIGHTGREEN);
        scoreLabelBox.setPadding(new Insets(40,0,0,0));
        scoreLabelBox.setAlignment(Pos.CENTER);
        scoreLabelBox.getChildren().add(scoreLabel);
        
        scoreText.setText("0");
        scoreText.setTextAlignment(TextAlignment.CENTER);
        scoreText.setFont(Font.font("Arial Black", 30));
        scoreText.setFill(Color.WHITE);
        scoreBox.setPadding(new Insets(0,0,40,0));
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.getChildren().add(scoreText);
        
        startButton.minWidth(40);
        startButton.setStyle("-fx-font-size: 2.5em; -fx-border-color: #111111; -fx-border-width: 2px;");
        startBox.setAlignment(Pos.CENTER);
        startBox.getChildren().add(startButton);
        startButton.setOnAction(startButtonEvent);
        
        resetButton.minWidth(40);
        resetButton.setStyle("-fx-font-size: 2.5em; -fx-border-color: #111111; -fx-border-width: 2px;");
        resetBox.setAlignment(Pos.CENTER);
        resetBox.getChildren().add(resetButton);
        resetButton.setOnAction(resetButtonEvent);
        
        gameOverView.setImage(gameOverImage);
        gameOverView.setFitWidth(COLUMNS * PIECE_SIZE - 40);
        gameOverView.setPreserveRatio(true);
        gameOverView.setX(20);
        gameOverView.setY(ROWS * PIECE_SIZE / 2 - 75);
        
        rightPanel.getChildren().addAll(logoBox,scoreLabelBox, scoreBox, startBox, resetBox);
        pane.getChildren().addAll(back,rightPanel);
        
        fallingPiece = nextPiece;
        nextPiece = PieceMethods.createRandomPiece();
        pane.getChildren().addAll(fallingPiece.getSquares().get(0),fallingPiece.getSquares().get(1),fallingPiece.getSquares().get(2),fallingPiece.getSquares().get(3)); 
        
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
        
        //Render grid lines then top cover over the top 2 rows
        pane.getChildren().addAll(gridLines, topCover);
        
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
                        if (fallingPiece.getSquares().get(0).getY() == 0 || fallingPiece.getSquares().get(1).getY() == 0 || 
                                fallingPiece.getSquares().get(2).getY() == 0 || fallingPiece.getSquares().get(3).getY() == 0) {
                            losingTicks++;
                        } else {
                            losingTicks = 0;
                        }

                        if (losingTicks == 2) {
                            System.out.println("Game Over");
                            running = false;
                            nextBox.getChildren().clear();
                            //pane.getChildren().add(gameOverView);
                        }
                    
                        handleKeyPress(fallingPiece);
                        score++;
                        scoreText.setText(Integer.toString(score));
                        PieceMethods.moveDown(fallingPiece);
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 750);
        
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
                    } else if (t.getCode() == KeyCode.W) {
                        PieceMethods.rotatePiece(piece);
                    }
                });
    }
    
    private static final EventHandler<ActionEvent> startButtonEvent = (ActionEvent e) -> {
            running = true;
            
            //Resets the image for the next piece
            switch (nextPiece.getId()) {
                case "I":
                    nextView.setImage(iImage);
                    break;
                case "J":
                    nextView.setImage(jImage);
                    break;
                case "L":
                    nextView.setImage(lImage);
                    break;
                case "O":
                    nextView.setImage(oImage);
                    break;
                case "S":
                    nextView.setImage(sImage);
                    break;
                case "T":
                    nextView.setImage(tImage);
                    break;
                case "Z":
                    nextView.setImage(zImage);
                    break;
                default:
                    break;
            }
            
            nextBox.getChildren().addAll(nextText, nextView);
            pane.getChildren().addAll(nextBox);
            System.out.println("Starting Game");
    };
    
    private static final EventHandler<ActionEvent> resetButtonEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            running = false;
            pane.getChildren().clear();
            pane.getChildren().addAll(back,rightPanel);

            fallingPiece = nextPiece;
            nextPiece = PieceMethods.createRandomPiece();
            pane.getChildren().addAll(fallingPiece.getSquares().get(0),fallingPiece.getSquares().get(1),fallingPiece.getSquares().get(2),fallingPiece.getSquares().get(3)); 
            pane.getChildren().addAll(gridLines, topCover);
            
            //RESET grid with initial 0's
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++){
                    grid[j][i] = 0;
                }
            }
            
            score = 0;
            scoreText.setText(Integer.toString(score));
            
            nextBox.getChildren().clear();
            
            System.out.println("Resetting Game");
        }
    };
    
}
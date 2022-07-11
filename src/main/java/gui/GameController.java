package gui;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.tinylog.Logger;
import puzzle.Direction;
import puzzle.Position;
import puzzle.PuzzleState;

import java.util.Optional;
import java.util.stream.Stream;

public class GameController {

    @FXML
    private GridPane grid;
    @FXML
    private GridPane grid2;

    @FXML
    private TextField numberOfMovesField;


    private Image[] pieceImages;

    private PuzzleState state;

    private IntegerProperty numberOfMoves = new SimpleIntegerProperty(0);

    @FXML
    private void initialize() {
        createControlBindings();
        loadImages();
        resetGame();
        populateGrid();
        registerKeyEventHandler();
    }

    private void createControlBindings() {
        numberOfMovesField.textProperty().bind(numberOfMoves.asString());
    }

    private void resetGame() {
        state = new PuzzleState(PuzzleState.START_RED_POS,PuzzleState.START_BLUE_POS);
        numberOfMoves.set(0);
        populateGrid();
        //.solvedProperty().addListener(this::handleGameOver);
    }

    private void handleGameOver() {
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Game Over");
            alert.setContentText("Congratulations, you have solved the puzzle!");
            alert.showAndWait();
            resetGame();

    }

    private void populateGrid() {
        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                var square = createSquare(row, col);
                grid.add(square, col, row);
            }
        }
    }

    private StackPane createSquare(int row, int col) {
        var square = new StackPane();
        //square.getStyleClass().add("square");
        //square.getStyleClass().add((row + col) % 2 == 0 ? "light": "dark");
        for (var i = 0; i < 4; i++) {
            var pieceView = new ImageView(pieceImages[i]);
            pieceView.visibleProperty().bind(createBindingForPieceAtPosition(i, row, col));
            square.getChildren().add(pieceView);
        }
        //square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private BooleanBinding createBindingForPieceAtPosition(int n, int row, int col) {
        return new BooleanBinding() {
            {
                super.bind(state.positionProperty(n));
            }
            @Override
            protected boolean computeValue() {
                var pos = state.positionProperty(n).get();
                return pos.getRow() == row && pos.getCol() == col;
            }
        };
    }

    private void performMove(Direction direction) {
        if (state.canMove(direction)) {
            Logger.info("Move: {}", direction);
            state.move(direction);
            Logger.trace("New state: {}", state);

            //if(state.equals(new PuzzleState(new Position(0,4),new Position(3,4)))){
            if(state.isSolved()){
                handleGameOver();
            }
            numberOfMoves.set(numberOfMoves.get() + 1);
        } else {
            Logger.warn("Invalid move: {}", direction);
        }
    }

    private void loadImages() {
        pieceImages = Stream.of( "redHole.png", "blueHole.png","redBall.png", "blueBall.png")
                .map(s -> "/images/" + s)
                .peek(s -> Logger.debug("Loading image resource {}", s))
                .map(Image::new)
                .toArray(Image[]::new);
    }

    private void registerKeyEventHandler() {
        KeyCombination restartKeyCombination = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        KeyCombination quitKeyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
        Platform.runLater(() -> grid.getScene().setOnKeyPressed(
                keyEvent -> {
                    if (restartKeyCombination.match(keyEvent)) {
                        Logger.debug("Restarting game...");
                        resetGame();
                    } else if (quitKeyCombination.match(keyEvent)) {
                        Logger.debug("Exiting...");
                        Platform.exit();
                    } else if (keyEvent.getCode() == KeyCode.UP) {
                        Logger.debug("Up arrow pressed");
                        performMove(Direction.UP);
                    } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                        Logger.debug("Right arrow pressed");
                        performMove(Direction.RIGHT);
                    } else if (keyEvent.getCode() == KeyCode.DOWN) {
                        Logger.debug("Down arrow pressed");
                        performMove(Direction.DOWN);
                    } else if (keyEvent.getCode() == KeyCode.LEFT) {
                        Logger.debug("Left arrow pressed");
                        performMove(Direction.LEFT);
                    }
                }
        ));
    }



}

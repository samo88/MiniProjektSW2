package view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PlayerPane extends VBox {

    private Label playerName;
    private Label playerWinStatus;

    public PlayerPane(){

        this.playerName = new Label("playerName");
        this.playerWinStatus = new Label("---");

        this.getChildren().addAll(playerName, playerWinStatus);
        this.getStyleClass().add("player");

    }
    public Label getPlayerName() {
        return playerName;
    }
    public Label getPlayerWinStatus() {
        return playerWinStatus;
    }
}

package view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class InfoPane extends BorderPane {

    private Label info;

    public InfoPane(){

        this.info = new Label("Informationen...");
        this.setCenter(info);

        this.getStyleClass().add("info");
    }

    public Label getInfo() {
        return info;
    }
}

package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;


public class StartView extends GridPane {

    private Label playerName,playerName2, rowLabel, columnLabel;
    private TextField nameField,nameField2, rowField, columnField;

    private Button confirmBtn;


    private Stage stage;
    public StartView(Stage primaryStage){

        this.stage = primaryStage;
        this.playerName = new Label("Nickname: ");
        this.playerName2 = new Label("Nickname2:");
        this.nameField = new TextField("Samo");
        this.nameField2 = new TextField("Seda");

        this.rowLabel = new Label("Zeilen?");
        this.rowField = new TextField("6");
        this.columnLabel = new Label("Spalten?");
        this.columnField = new TextField("7");

        this.confirmBtn = new Button("OK");

        this.add(playerName,4,1);
        this.add(nameField,5,1);
        this.add(playerName2,4,2);
        this.add(nameField2,5,2);
        this.add(rowLabel,4,3);
        this.add(rowField,5, 3);
        this.add(columnLabel, 4,4);
        this.add(columnField, 5,4);
        this.add(confirmBtn, 5,5);

        this.getStyleClass().add("start");
    }
    public TextField getNameField() {
        return nameField;
    }
    public TextField getNameField2() {
        return nameField2;
    }
    public TextField getRowField() {
        return rowField;
    }
    public TextField getColumnField() {
        return columnField;
    }
    public Button getConfirmBtn() {
        return confirmBtn;
    }

    public void startSign(){
        Scene scene = new Scene(this, 800, 800);
        stage.setScene(scene);
        stage.setTitle("CONNECT4 - THE GAME");
        stage.show();
        scene.getStylesheets().add(getClass().getResource("applications.css").toExternalForm());

    }

    public Label getRowLabel() {
        return rowLabel;
    }

    public Label getColumnLabel() {
        return columnLabel;
    }
}

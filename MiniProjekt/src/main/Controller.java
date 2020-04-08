package main;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import view.*;

import java.util.ArrayList;

public class Controller {

    private BoardPane board;
    private StartView start;
    private GameView game;
    private ArrayList<Field> fieldArray;
    private ArrayList<Field> fieldArrayTwo;

    private InfoPane info;
    private PlayerPane playerOne;
    private PlayerPane playerTwo;
    private Stage primaryStage;

    public static int counter;           //Counter für die genaue Reihenfolge der Spieler
    public static int rowSize;          //Variable für die Anzahl der Reihen
    public static int columnSize;      //Variablen für die Anzahl der Spalten
    public static int fieldCounter;   //

    public static  EventHandler event;

    public Controller(StartView start, GameView game, BoardPane board, Stage primaryStage, InfoPane info, PlayerPane playerOne, PlayerPane playerTwo) {

        this.start = start;
        this.board = board;
        this.game = game;
        this.info = info;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        this.playerOne.getStyleClass().add("one");
        this.playerTwo.getStyleClass().add("two");

        this.counter = 0;
        this.fieldCounter = 0;

        this.fieldArray = new ArrayList<Field>();
        this.fieldArrayTwo = new ArrayList<Field>();

        this.primaryStage = primaryStage;

        this.start.getConfirmBtn().setOnAction(e -> {
                this.game.setLeft(playerOne);
                this.game.setRight(playerTwo);
                this.playerTwo.getPlayerName().setText(start.getNameField2().getText());
                this.playerOne.getPlayerName().setText(start.getNameField().getText());
                int rowSize = Integer.parseInt(start.getRowField().getText());
                int columnSize = Integer.parseInt(start.getColumnField().getText());

                if (rowSize < 4 || columnSize < 4 || rowSize >= columnSize || rowSize > 9 || columnSize > 9) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ungültige Spielfeldgrösse");
                    alert.setHeaderText("Bitte Angaben prüfen..");
                    alert.setContentText("Wähle  ein 6x7 oder 8x9 grosses Spielfeld aus");
                    alert.show();
                    System.out.println("Bitte mindestens 4x4 Feld auswählen...");
                } else {
                    setBoardSize(rowSize, columnSize);
                    game.setBottom(info);
                    game.setCenter(board);
                    game.startGame();

                    if (counter % 2 == 0) {
                        playerTwo.setStyle("-fx-border-color: #947a23");

                    }
                }
            });
    }
    public void setBoardSize(int row, int column) {
        this.rowSize = row;
        this.columnSize = column;

        for (int i = 0; i < row; i++) {
            for (int z = 0; z < column; z++) {
                Field field = new Field(fieldCounter);
                fieldCounter++;
                board.add(field, z, i);
                fieldArray.add(field);
                fieldController(field);
            }
        }
        fieldCounter=0;
        info.getInfo().setText("Rot beginnt...");
    }
    public void fieldController(Field field) {
        event = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int currentMaxOrdinal = field.getFieldID();
                int currentMinOrdinal = field.getFieldID();

                while (currentMaxOrdinal < (rowSize * columnSize) - columnSize) {
                    currentMaxOrdinal += columnSize;
                }
                while (currentMinOrdinal > 0 && (currentMinOrdinal - columnSize) > 0) {
                    currentMinOrdinal -= columnSize;
                }

                while (currentMaxOrdinal >= currentMinOrdinal) {
                    Paint fieldColor = fieldArray.get(currentMaxOrdinal).getField().getFill();
                    if (fieldColor == Color.GOLD) {
                        if (counter % 2 > 0) {
                            fieldArray.get(currentMaxOrdinal).getField().setFill(Color.GREEN);
                            fieldCounter++;
                            info.getInfo().setText(playerTwo.getPlayerName().getText() + " ist dran...");
                            playerTwo.setStyle("-fx-border-color: black");
                            playerOne.setStyle("-fx-border-color: #947a23");
                            counter++;
                        } else {
                            if (counter % 2 == 0) {
                                fieldArray.get(currentMaxOrdinal).getField().setFill(Color.RED);
                                fieldCounter++;
                                playerOne.setStyle("-fx-border-color: black");
                                playerTwo.setStyle("-fx-border-color: #947a23");
                                info.getInfo().setText(playerOne.getPlayerName().getText() + " ist dran...");
                                counter++;
                            }
                        }
                        break;
                    }
                    currentMaxOrdinal -= columnSize;
                }
                if(proofFields()){
                    for (Field f: fieldArray){
                        f.setOnMouseClicked(null);
                    }
                }
            }
        };
        field.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
       
    }

    public boolean proofFields() {                                 // Klasse, die die einzelnen Methoden für diagonale PRüfung enthält
        int greenCounter = 0;       //Zähler für die je Spalte/Reihe gezählten identischen Farben*
        int greenWin = 0;           //Zähler für belegte 4 Felder in einer Reihe **
        int redCounter = 0;         //*
        int redWin = 0;             //**

        boolean proof = false;

        if(proofDiagOne(greenCounter,greenWin,redCounter,redWin)){
            proof = true;
       }
        if(proofDiagTwo(greenCounter,greenWin,redCounter,redWin)){
            proof = true;
        }
        if(proofDiagThree(greenCounter,greenWin,redCounter,redWin)){
            proof = true;
        }
        if(proofDiagFour(greenCounter,greenWin,redCounter,redWin)){
            proof = true;
        }
        if(proofDiagFive(greenCounter,greenWin,redCounter,redWin)){
            proof = true;
        }
        if(proofDiagSix(greenCounter,greenWin,redCounter,redWin)){
            proof = true;
        }
        if(proofColumns(greenCounter,greenWin,redCounter,redWin)){
            proof = true;
        }
        if(proofRows(greenCounter,greenWin,redCounter,redWin)){
            proof = true;
        }
        return proof;
    }
    public boolean proofRows(int greenCounter,int greenWin, int redCounter, int redWin) {

        boolean proof = false;

        ArrayList<Field> fieldStrokes = new ArrayList<Field>();
        for (int i = columnSize - 1; i <= (columnSize * rowSize); i += columnSize) {
            for (int u = (i - (columnSize - 1)); u < i; u++) {
                Field f1 = fieldArray.get(u);
                Field f2 = fieldArray.get(u + 1);
                if (f1.getColor() == f2.getColor() && f1.getFieldID() == f2.getFieldID() - 1 && f1.getColor() != Color.GOLD) {
                    if (f1.getColor() == Color.RED) {
                        redCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        if (redCounter == 3) {
                            redWin++;
                            showFourLine(fieldStrokes);
                            info.getInfo().setText(playerTwo.getPlayerName().getText() + " gewinnt......");
                            playerTwo.getPlayerWinStatus().setText("GEWONNEN");
                            proof = true;
                            break;
                        }}
                    if (f1.getColor() == Color.GREEN) {
                        greenCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        if (greenCounter == 3) {
                            greenWin++;
                            showFourLine(fieldStrokes);
                            info.getInfo().setText(playerOne.getPlayerName().getText() + " gewinnt......");
                            playerOne.getPlayerWinStatus().setText("GEWONNEN");
                            proof = true;
                            break;
                        }
                    }
                }else{
                    redCounter=0;
                    greenCounter=0;
                    fieldStrokes.clear();
                }
            }
            redCounter = 0;
            greenCounter = 0;
            checkRemainFields();
            fieldStrokes.clear();
        }
        return proof;
    }
    private boolean proofColumns(int greenCounter,int greenWin, int redCounter, int redWin) {

        ArrayList<Field> fieldStrokes = new ArrayList<Field>();
        boolean proof = false;
        for (int i = 0; i < columnSize; i++) {
            for (int u = i; u < ((rowSize - 1) * columnSize); u += columnSize) {
                Field f1 = fieldArray.get(u);
                Field f2 = fieldArray.get(u + columnSize);
                if (f1.getColor() == f2.getColor() && f1.getFieldID() == f2.getFieldID() - columnSize && f1.getColor() != Color.GOLD) {
                    if (f1.getColor() == Color.RED) {
                        redCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        if (redCounter == 3) {
                            redWin++;
                            showFourLine(fieldStrokes);
                            info.getInfo().setText(playerTwo.getPlayerName().getText() + " gewinnt......");
                            playerTwo.getPlayerWinStatus().setText("GEWONNEN");
                            redCounter = 0;
                            proof = true;
                            break;
                        }
                    }
                    if (f1.getColor() == Color.GREEN) {
                        greenCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        if (greenCounter == 3) {
                            greenWin++;
                            showFourLine(fieldStrokes);
                            greenCounter = 0;
                            info.getInfo().setText(playerOne.getPlayerName().getText() + " gewinnt......");
                            playerOne.getPlayerWinStatus().setText("GEWONNEN");
                            proof = true;
                            break;
                        }
                    }
                }else{
                        redCounter=0;
                        greenCounter=0;
                        fieldStrokes.clear();
                        checkRemainFields();
                    }
                }
            redCounter = 0;
            greenCounter = 0;
            fieldStrokes.clear();
            }
        return proof;
        }
    private boolean proofDiagSix(int greenCounter, int greenWin, int redCounter, int redWin) {
        ArrayList<Field> fieldStrokes = new ArrayList<Field>();
        boolean proof = false;
        for (int i = 4; i < (columnSize-1); i++){
            for (int o = i; o < i*(columnSize-1); o+=columnSize-1){
                Field f1 = fieldArray.get(o);
                Field f2 = fieldArray.get(o + (columnSize-1));
                if (f1.getColor() == f2.getColor() && f1.getColor() != Color.GOLD&&f1.getFieldID() == f2.getFieldID() - (columnSize -1)) {
                    if (f1.getColor()==Color.RED) {
                        redCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        if (redCounter == 3) {
                            redWin++;
                            playerTwo.getPlayerWinStatus().setText("GEWONNEN");
                            info.getInfo().setText(playerTwo.getPlayerName().getText() + " gewinnt......");
                            proof = true;

                        }}
                    if (f1.getColor() == Color.GREEN) {
                        greenCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        if (greenCounter == 3) {
                            greenWin++;
                            showFourLine(fieldStrokes);
                            playerOne.getPlayerWinStatus().setText("GEWONNEN");
                            info.getInfo().setText(playerOne.getPlayerName().getText() + " gewinnt......");
                            proof = true;
                            break;
                        }
                    }
                }else{
                    redCounter=0;
                    greenCounter=0;
                    fieldStrokes.clear();
                    checkRemainFields();
                }
            }
            redCounter = 0;
            greenCounter=0;
            fieldStrokes.clear();
        }
        return proof;
    }
    private boolean proofDiagFive(int greenCounter, int greenWin, int redCounter, int redWin) {
        ArrayList<Field> fieldStrokes = new ArrayList<Field>();

        boolean proof = false;
        for (int i = columnSize-1; i < (rowSize-3)*columnSize; i+= columnSize){
            for(int e = i ; e< (rowSize*columnSize)-(columnSize-1); e+=(columnSize-1)){
                Field f1 = fieldArray.get(e);
                Field f2 = fieldArray.get(e + (columnSize-1));
                if (f1.getColor() == f2.getColor() && f1.getColor() != Color.GOLD&&f1.getFieldID() == f2.getFieldID() - (columnSize -1)) {
                    if (f1.getColor()==Color.RED) {
                        redCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        if (redCounter == 3) {
                            redWin++;
                            showFourLine(fieldStrokes);
                            info.getInfo().setText(playerTwo.getPlayerName().getText() + " gewinnt......");
                            playerTwo.getPlayerWinStatus().setText("GEWONNEN");
                            proof = true;
                            break;
                        }
                    }
                    if (f1.getColor() == Color.GREEN) {
                        greenCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        if (greenCounter == 3) {
                            greenWin++;
                            showFourLine(fieldStrokes);
                            info.getInfo().setText(playerOne.getPlayerName().getText() + " gewinnt......");
                            playerOne.getPlayerWinStatus().setText("GEWONNEN");
                            proof = true;
                            break;
                        }
                    }
                }else{
                    redCounter=0;
                    greenCounter=0;
                    fieldStrokes.clear();
                    checkRemainFields();
                }
            }
            redCounter = 0;
            greenCounter=0;
            fieldStrokes.clear();
        }
        return proof;
    }
    private boolean proofDiagFour(int greenCounter, int greenWin, int redCounter, int redWin) {
        ArrayList<Field> fieldStrokes = new ArrayList<Field>();
        boolean proof = false;
        for (int i = 3; i < (2*columnSize)+3; i+=(columnSize-1)){
            Field f1 = fieldArray.get(i);
            Field f2 = fieldArray.get(i + (columnSize -1));
            if (f1.getColor() == f2.getColor() && f1.getColor() != Color.GOLD&&f1.getFieldID() == f2.getFieldID() - (columnSize + 1)) {
                if (f1.getColor()==Color.RED) {
                    redCounter++;
                    fieldStrokes.add(f1);
                    fieldStrokes.add(f2);
                    if (redCounter == 3) {
                        redWin++;
                        showFourLine(fieldStrokes);
                        info.getInfo().setText(playerTwo.getPlayerName().getText() + " gewinnt......");
                        playerTwo.getPlayerWinStatus().setText("GEWONNEN");
                        proof = true;
                        break;
                    }
                }
                if (f1.getColor() == Color.GREEN) {
                    greenCounter++;
                    fieldStrokes.add(f1);
                    fieldStrokes.add(f2);
                    if (greenCounter == 3) {
                        greenWin++;
                        showFourLine(fieldStrokes);
                        info.getInfo().setText(playerOne.getPlayerName().getText() + " gewinnt......");
                        playerOne.getPlayerWinStatus().setText("GEWONNEN");
                        proof = true;
                        break;
                    }
                }

            }else{
                redCounter=0;
                greenCounter=0;
                fieldStrokes.clear();
                checkRemainFields();
            }
        }
        return proof;
    }
    private boolean proofDiagThree(int greenCounter, int greenWin, int redCounter, int redWin) {
        ArrayList<Field> fieldStrokes = new ArrayList<Field>();
        boolean proof = false;
        for (int q = columnSize - 4; q <= (3 * (columnSize + 1) + columnSize - 4)-(columnSize+1); q += columnSize + 1) {
            Field f1 = fieldArray.get(q);
            Field f2 = fieldArray.get(q + columnSize + 1);
            if (f1.getColor() == f2.getColor() && f1.getColor() != Color.GOLD) {
                if (f1.getFieldID() == f2.getFieldID() - (columnSize + 1)&&f1.getFieldID() == f2.getFieldID() - (columnSize + 1)) {
                    System.out.println(f1.getFieldID()+"--"+f2.getFieldID());
                    if (f1.getColor() == Color.RED) {
                        redCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        System.out.println(fieldStrokes);
                        if (redCounter == 3) {
                            redWin++;
                            showFourLine(fieldStrokes);
                            info.getInfo().setText(playerTwo.getPlayerName().getText() + " gewinnt......");
                            playerTwo.getPlayerWinStatus().setText("GEWONNEN");
                            proof = true;
                            break;
                        }
                    }
                    if (f1.getColor() == Color.GREEN) {
                         greenCounter++;
                         fieldStrokes.add(f1);
                         fieldStrokes.add(f2);
                        System.out.println(fieldStrokes);
                    if (greenCounter == 3) {
                        greenWin++;
                        showFourLine(fieldStrokes);
                        info.getInfo().setText(playerOne.getPlayerName().getText() + " gewinnt......");
                        playerOne.getPlayerWinStatus().setText("GEWONNEN");
                        proof = true;
                        break;
                    }
                }
                }else{
                    redCounter=0;
                    greenCounter=0;
                    fieldStrokes.clear();
                    checkRemainFields();
                }
            }
        }
        return proof;
    }
    private boolean proofDiagTwo(int greenCounter, int greenWin, int redCounter, int redWin) {
        ArrayList<Field> fieldStrokes = new ArrayList<Field>();
        boolean proof = false;
        for (int r = 1; r < columnSize - 4; r++) {
            for (int e = r; e < (rowSize * columnSize) - (columnSize + 1); e += (columnSize + 1)) {
                Field f1 = fieldArray.get(e);
                Field f2 = fieldArray.get(e + columnSize + 1);
                if (f1.getColor() == f2.getColor() && f1.getColor() != Color.GOLD&&f1.getFieldID() == f2.getFieldID() - (columnSize + 1)) {
                    if (f1.getColor() == Color.RED) {
                        redCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        if (redCounter == 3) {
                            redWin++;
                            showFourLine(fieldStrokes);
                            info.getInfo().setText(playerTwo.getPlayerName().getText() + " gewinnt......");
                            playerTwo.getPlayerWinStatus().setText("GEWONNEN");
                            proof = true;
                            break;
                        }
                    }
                    if (f1.getColor() == Color.GREEN) {
                        greenCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        if (greenCounter == 3) {
                            greenWin++;
                            showFourLine(fieldStrokes);
                            info.getInfo().setText(playerOne.getPlayerName().getText() + " gewinnt......");
                            playerOne.getPlayerWinStatus().setText("GEWONNEN");
                            proof = true;
                            break;
                        }
                    }
                }else{
                    redCounter=0;
                    greenCounter=0;
                    fieldStrokes.clear();
                    checkRemainFields();
                }
            }
            redCounter = 0;
            greenCounter=0;
            fieldStrokes.clear();
        }
        return proof;
    }
    private boolean proofDiagOne(int greenCounter, int greenWin, int redCounter, int redWin) {
        ArrayList<Field> fieldStrokes = new ArrayList<Field>();
        boolean proof = false;
        for (int i = 0; i < (rowSize - 3) * columnSize; i += columnSize) {
            for (int u = i; u < (rowSize * columnSize) - columnSize + 1; u += (columnSize + 1)) {
                Field f1 = fieldArray.get(u);
                Field f2 = fieldArray.get(u + columnSize + 1);
                if (f1.getColor()== f2.getColor()&& f1.getColor()!=Color.GOLD && f1.getFieldID()== f2.getFieldID()-(columnSize+1)){
                    if(f1.getColor()==Color.RED){
                        redCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        if(redCounter==3){
                            redWin++;
                            showFourLine(fieldStrokes);
                            info.getInfo().setText(playerTwo.getPlayerName().getText() + " gewinnt......");
                            playerTwo.getPlayerWinStatus().setText("GEWONNEN");
                            proof = true;
                            break;
                        }}
                    if (f1.getColor() == Color.GREEN) {
                        greenCounter++;
                        fieldStrokes.add(f1);
                        fieldStrokes.add(f2);
                        if (greenCounter == 3) {
                            greenWin++;
                            showFourLine(fieldStrokes);
                            info.getInfo().setText(playerOne.getPlayerName().getText() + " gewinnt......");
                            playerOne.getPlayerWinStatus().setText("GEWONNEN");
                            proof = true;
                            break;
                        }}
                }else{
                    redCounter=0;
                    greenCounter=0;
                    fieldStrokes.clear();
                    checkRemainFields();
                }
            }
            redCounter = 0;
            greenCounter=0;
           fieldStrokes.clear();
        }
        return proof;
    }
    private void checkRemainFields(){
        if(fieldCounter == fieldArray.size()){
            info.getInfo().setText("Spiel vorbei....");
        }
    }
    private void showFourLine(ArrayList<Field> list){
        for (Field f: list) {
            f.setShinyStroke();

        }
    }

    }



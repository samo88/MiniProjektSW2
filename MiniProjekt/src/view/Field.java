package view;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.StrokeType;
import main.Controller;

import java.util.Collection;

public class Field extends Label  {

    private Ellipse field;
    private int fieldID;

    public Field(int id){

        this.fieldID = id;

        this.field= new Ellipse();
        this.field.setRadiusX(20);
        this.field.setRadiusY(20);
        this.field.setStroke(Color.CADETBLUE);
        this.field.setStrokeWidth(3);
        this.field.setFill(Color.GOLD);

        field.getStyleClass().add("coin");
        this.setGraphic(field);
        this.setAlignment(Pos.CENTER);
    }
    public int getFieldID() {
        return fieldID;
    }
    public Ellipse getField() {
        return field;
    }
    public Paint getColor(){
        return this.field.getFill();
    }
    public String fieldToString(){
        return "ID: " + this.fieldID +" Farbe:"+ getColor() ;
    }
    public void setShinyStroke(){
        this.field.setStroke(Color.GHOSTWHITE);

    }
}

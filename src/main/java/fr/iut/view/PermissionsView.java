package fr.iut.view;

import fr.iut.App;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;

/**
 * Created by theo on 08/02/17.
 */
public class PermissionsView extends Scene {

    public static final double PERMISSION_WIDTH = App.SCREEN_W / 2;
    public static final double PERMISSION_HEIGHT = App.SCREEN_H / 2;

    private App app;

    public PermissionsView(App app){
        super(new VBox(), PERMISSION_WIDTH, PERMISSION_HEIGHT);
        this.app = app;

        VBox wrapper = (VBox) getRoot();

        GridPane gridPane = new GridPane();
        gridPane.setMinHeight(PERMISSION_HEIGHT);
        gridPane.setStyle("-fx-background-color: rgb(12, 27, 51);");
        gridPane.setAlignment(Pos.TOP_CENTER);


        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(20);
            gridPane.getColumnConstraints().add(column);
        }

        for(int i = 0; i < 8; i++){
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(10);
            gridPane.getRowConstraints().add(row);
        }

        HeaderView header = new HeaderView("Permissions");
        header.setMinWidth(PERMISSION_WIDTH);
        wrapper.getChildren().add(header);

        String[] titles = {"Lecture", "Modification"};
        for(int i=0; i<titles.length; i++){
            Text text = new Text(titles[i]);
            text.setStyle("-fx-font-weight: bold;" +
                    "-fx-font-size: 17px" );
            text.setFill(Color.WHITESMOKE);
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, i+1, 0);
        }

        String[] labels = {"Clients", "Incidents", "Salariés", "Fournisseurs", "Stocks", "Carte"};
        for(int i=0; i<labels.length; i++) {
            Text text = new Text(labels[i]);
            text.setStyle("-fx-font-weight: bold;" +
                    "-fx-font-size: 17px");
            text.setFill(Color.WHITESMOKE);
            gridPane.add(text, 0, i+1);

            RadioButton read_button = new RadioButton();
            GridPane.setHalignment(read_button, HPos.CENTER);
            gridPane.add(read_button, 1,i+1);

            RadioButton edit_button = new RadioButton();
            GridPane.setHalignment(edit_button, HPos.CENTER);
            gridPane.add(edit_button, 2, i+1);
        }

        Button confirm = new Button("Valider");
        confirm.getStylesheets().add(new File("res/style.css").toURI().toString());
        confirm.getStyleClass().add("record-sales");
        confirm.setMinSize(PERMISSION_WIDTH / 6, PERMISSION_HEIGHT / 10);
        GridPane.setHalignment(confirm, HPos.RIGHT);
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            }
        });
        gridPane.add(confirm,0, labels.length+1);

        Button cancel = new Button("Annuler");
        cancel.getStylesheets().add(new File("res/style.css").toURI().toString());
        cancel.getStyleClass().add("record-sales");
        cancel.setMinSize(PERMISSION_WIDTH / 6, PERMISSION_HEIGHT / 10);
        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            }
        });
        gridPane.add(cancel, 2, labels.length+1);

        wrapper.getChildren().add(gridPane);
    }
}

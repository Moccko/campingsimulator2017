package fr.iut.view;


import fr.iut.controller.StatisticsController;

import javafx.beans.NamedArg;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.chart.Chart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;

enum StatisticsState {
    CATEGORIES,
    STAT_LIST,
    GRAPHIC
}

/**
 * the statistics screen
 */
class StatisticsView extends SubScene {

    private static final int NUMBER_OF_CATEGORIES = 6;
    private static final double CATEGORIE_BUTTON_SIZE = HomeView.TAB_CONTENT_W / 6;

    /**
     * instance of the StatisticsController
     */
    private StatisticsController controller;
    /**
     * state of the statistics screen
     */
    private StatisticsState statisticsState = StatisticsState.CATEGORIES;
    private SelectedCategory selectedCategory = SelectedCategory.NONE;
    private int selectedStat = -1;

    HBox chartTypeSelectBox = new HBox();

    /**
     * @param controller
     */
    public StatisticsView(@NamedArg("controller") StatisticsController controller) {
        super(new VBox(), HomeView.TAB_CONTENT_W, HomeView.TAB_CONTENT_H);

        this.controller = controller;

        VBox wrapper = (VBox) getRoot();
        HeaderView header = new HeaderView("Statistiques");
        ScrollPane scrollPane = new ScrollPane();

        wrapper.setPrefSize(HomeView.TAB_CONTENT_W, HomeView.TAB_CONTENT_H);
        wrapper.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(5))));

        GridPane body = new GridPane();
        body.setMinWidth(HomeView.TAB_CONTENT_W);
        body.setMinHeight(17 * HomeView.TAB_CONTENT_H / 19);
        body.setAlignment(Pos.CENTER);
        body.setStyle("-fx-background-color: rgb(12, 27, 51);");
        body.setPadding(new Insets(50, 0, 50, 0));
        body.setHgap(100);
        body.setVgap(50);

        scrollPane = new ScrollPane();
        scrollPane.setContent(body);
        scrollPane.setMinHeight(17 * HomeView.TAB_CONTENT_H / 19);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Button back_button = new Button("←");
        back_button.getStylesheets().add(new File("res/style.css").toURI().toString());
        back_button.getStyleClass().add("record-sales");
        back_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch (statisticsState) {
                    case STAT_LIST:
                        statisticsState = StatisticsState.CATEGORIES;
                        buildInterface(body);
                        break;

                    case GRAPHIC:
                        statisticsState = StatisticsState.STAT_LIST;
                        buildInterface(body);
                        break;

                    default:
                        break;
                }
            }
        });

        Button pieTypeButton = new Button("Pie");
        pieTypeButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        pieTypeButton.getStyleClass().add("record-sales");
        Button lineTypeButton = new Button("Line");
        lineTypeButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        lineTypeButton.getStyleClass().add("record-sales");
        Button barTypeButton = new Button("Bar");
        barTypeButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        barTypeButton.getStyleClass().add("record-sales");
        chartTypeSelectBox.getChildren().addAll(pieTypeButton, lineTypeButton, barTypeButton);
        chartTypeSelectBox.setSpacing(10);
        chartTypeSelectBox.setVisible(false);

        buildInterface(body);

        header.setLeft(back_button);
        header.setRight(chartTypeSelectBox);
        wrapper.getChildren().addAll(header, scrollPane);
    }

    public void buildInterface(GridPane body) {
        body.getChildren().clear();

        String[] categories_labels = {"Réservations", "Clients", "Produits / Achats", "Salariés", "Incidents", "Autres"};

        switch (this.statisticsState) {
            case CATEGORIES:
                chartTypeSelectBox.setVisible(false);
                for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
                    int finalI = i;

                    Button button = new Button(categories_labels[i]);
                    button.setPrefSize(CATEGORIE_BUTTON_SIZE, CATEGORIE_BUTTON_SIZE);
                    button.setAlignment(Pos.CENTER);
                    button.getStylesheets().add(new File("res/style.css").toURI().toString());
                    button.getStyleClass().add("record-sales");
                    button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            selectedCategory = SelectedCategory.values()[finalI];
                            statisticsState = StatisticsState.STAT_LIST;
                            buildInterface(body);
                        }
                    });

                    body.add(button, i % 3, (int) Math.floor(i / 3));
                }
                break;

            case STAT_LIST:
                chartTypeSelectBox.setVisible(false);
                String[] options = new String[]{};
                switch (this.selectedCategory) {
                    case RESERVATIONS:
                        options = new String[]{
                                "Emplacements les \nplus réservés",
                                "Emplacements les \nmoins réservés",
                                "Réservations impayées",
                        };
                        break;

                    case CLIENTS:
                        options = new String[]{
                                "Clients les \nplus fidèles",
                                "Clients les \nplus plaintifs",
                                "Clients les \nmoins plaintifs",
                                "Clients achetant \nle plus"
                        };
                        break;

                    case PURCHASES:
                        options = new String[]{
                                "Produits les \nplus vendus",
                                "Produits les \nmoins vendus",
                                "Produits vendus les \nplus rapidement"
                        };
                        break;

                    case EMPLOYEES:
                        options = new String[]{
                                "Employés travaillant \nle plus",
                        };
                        break;

                    case PROBLEMS:
                        options = new String[]{
                                "Problèmes en fonction \ndu temps",
                                "Problèmes en fonction \ndes clients",
                                "Problèmes en fonction \ndu lieu"
                        };
                        break;

                    case OTHERS:
                        options = new String[]{
                                "Connection les \nplus fréquentes"
                        };
                        break;

                    case NONE:
                        break;
                }

                for (int i = 0; i < options.length; i++) {
                    int finalI = i;

                    Button button = new Button(options[i]);
                    button.setPrefSize(CATEGORIE_BUTTON_SIZE, CATEGORIE_BUTTON_SIZE);
                    button.setAlignment(Pos.CENTER);
                    button.getStylesheets().add(new File("res/style.css").toURI().toString());
                    button.getStyleClass().add("record-sales");
                    button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            statisticsState = StatisticsState.GRAPHIC;
                            selectedStat = finalI;
                            buildInterface(body);
                        }
                    });
                    body.add(button, i % 3, (int) i / 3);
                }
                break;

            case GRAPHIC:
                chartTypeSelectBox.setVisible(true);
                ChartView chart = controller.makeChart(selectedCategory, selectedStat, ChartType.PIE);
                body.add(chart.getTable(), 0, 0);
                body.add(chart.getChart(), 1, 0);
                break;
        }
    }
}

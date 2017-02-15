package fr.iut.view;


import fr.iut.App;
import fr.iut.controller.HomeController;
import fr.iut.model.Product;
import javafx.beans.NamedArg;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class ProductManagerView extends SubScene {

    public static double PRODUCT_MANAGER_W = App.SCREEN_W * 5 / 6;
    public static double PRODUCT_MANAGER_H = App.SCREEN_H * 7 / 9;

    private HomeController controller;

    private ArrayList<Product> products_list;
    private StackPane lastClicked;
    private Product lastClickedValue;

    private VBox products_box;
    private VBox details = new VBox();
    private GridPane grid = new GridPane();

    public ProductManagerView(@NamedArg("controller") HomeController controller) {
        super(new AnchorPane(), PRODUCT_MANAGER_W, PRODUCT_MANAGER_H);
        this.controller = controller;

        AnchorPane components = (AnchorPane) getRoot();

        VBox wrapper = new VBox();
        wrapper.setPrefSize(PRODUCT_MANAGER_W, PRODUCT_MANAGER_H);
        wrapper.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
        wrapper.setPadding(new Insets(0));
        wrapper.setLayoutY(0);

        components.setStyle("-fx-background-color: rgb(12, 27, 51);");

        HeaderView header = new HeaderView("Gestion des stocks");

        HBox sort_options = new HBox();
        ObservableList<String> options =
                FXCollections.observableArrayList("Nom (alphabétique)", "Nom (alphabétique inverse)", "Quantité (croissante)",
                        "Quantité (décroissante)", "Prix (croissant)", "Prix (décroissant)");
        ComboBox<String> sort_by = new ComboBox<>(options);
        Label sort_by_label = new Label("Trier selon: ");
        sort_by_label.setLabelFor(sort_by);
        TextField search_field = new TextField();
        Label search_label = new Label("Rechercher: ");
        search_label.setLabelFor(search_field);

        HBox body = new HBox();
        body.setMinWidth(PRODUCT_MANAGER_W * 19 / 20);

        products_box = new VBox();
        products_box.setSpacing(8);
        products_box.setPrefSize(PRODUCT_MANAGER_W / 4, PRODUCT_MANAGER_H * 9 / 10);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(products_box);
        scrollPane.setMinSize(products_box.getPrefWidth(), products_box.getPrefHeight());

        details.getStylesheets().add(new File("res/style.css").toURI().toString());
        details.getStyleClass().add("product-detail");
        details.setPrefWidth(3 * PRODUCT_MANAGER_W / 4);
        details.setMaxHeight(17 * PRODUCT_MANAGER_H / 19);

        HeaderView details_header = new HeaderView("Détails du produit");
        grid.setGridLinesVisible(true);
        grid.getStylesheets().add(new File("res/style.css").toURI().toString());
        grid.getStyleClass().add("stock-grid");

        HBox buttons = new HBox();
        buttons.setSpacing(PRODUCT_MANAGER_W / 8);
        for (int i = 0; i < 2; i++) {
            Button button = new Button();
            if (i == 0) {
                button.setText("Gérer fournisseurs");
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                    }
                });
            } else {
                button.setText("Réaprovisionner");
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                    }
                });
            }
            button.setPrefWidth(PRODUCT_MANAGER_W / 6);
            button.getStylesheets().add(new File("res/style.css").toURI().toString());
            button.getStyleClass().add("record-sales");
            buttons.getChildren().add(button);
        }

        details.getChildren().addAll(details_header, grid, buttons);
        VBox.setMargin(buttons, new Insets(PRODUCT_MANAGER_H / 15, 0, 0, PRODUCT_MANAGER_W / 9));

        buildProductsList(0, "", true);

        lastClicked = (StackPane) products_box.getChildren().get(0);
        lastClicked.setStyle("-fx-background-color: #ff6600;");
        lastClickedValue = products_list.get(0);

        sort_options.setSpacing(10);
        sort_by_label.setStyle("-fx-text-fill: whitesmoke; -fx-font-size: 18px");
        sort_by.getSelectionModel().select(0);
        sort_by.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                buildProductsList(sort_by.getSelectionModel().getSelectedIndex(), search_field.getText(), false);
            }
        });
        search_label.setStyle("-fx-text-fill: whitesmoke; -fx-font-size: 18px");
        search_field.setPrefWidth(PRODUCT_MANAGER_W / 7);
        search_field.setPromptText("Nom du produit");
        search_field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    buildProductsList(sort_by.getSelectionModel().getSelectedIndex(), search_field.getText(), false);
                }
            }
        });
        sort_options.getChildren().addAll(sort_by_label, sort_by, search_label, search_field);
        HBox.setMargin(sort_by, new Insets(0, PRODUCT_MANAGER_W / 2, 0, 0));
        header.getChildren().add(sort_options);
        StackPane.setMargin(sort_options, new Insets(5, 0, 0, 0));

        body.getChildren().addAll(scrollPane, details);
        wrapper.getChildren().addAll(header, body);
        components.getChildren().add(wrapper);

        buildDetails();
    }

    private void refresh_product_list() {
        products_list = controller.getProductsList();
    }

    private void buildProductsList(@NamedArg("sort_option") int sort_option,
                                   @NamedArg("search_value") String search,
                                   @NamedArg("refresh") boolean refresh) {

        if (refresh)
            products_list = controller.getProductsList();

        products_list.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                double result = 0;
                switch (sort_option) {
                    case 1:
                        result = o2.getLabel().compareTo(o1.getLabel());
                        break;
                    case 2:
                        result = o1.getStock() - o2.getStock();
                        break;
                    case 3:
                        result = o2.getStock() - o1.getStock();
                        break;
                    case 4:
                        result = o1.getSellPrice() - o2.getSellPrice();
                        break;
                    case 5:
                        result = o2.getSellPrice() - o1.getSellPrice();
                        break;
                    default:
                        result = o1.getLabel().compareTo(o2.getLabel());
                        break;
                }
                return (int) result;
            }
        });
        products_box.getChildren().clear();

        for (Product p : products_list) {
            if (p.getLabel().contains(search)) {
                System.out.println(p.getLabel().contains(""));
                StackPane pane = new StackPane();
                VBox product_wrapper = new VBox();
                HBox informations = new HBox();

                Text name = new Text(p.getLabel());
                Text stock = new Text("Quantité: " + String.valueOf(p.getStock()));
                Text price = new Text("Prix: " + String.valueOf(p.getSellPrice()));

                pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(5))));

                name.setFill(Color.WHITESMOKE);
                stock.setFill(Color.WHITESMOKE);
                price.setFill(Color.WHITESMOKE);

                informations.setSpacing(10);
                informations.setAlignment(Pos.CENTER);
                informations.getChildren().addAll(stock, price);

                VBox.setMargin(name, new Insets(15, 0, 5, 0));
                VBox.setMargin(informations, new Insets(0, 0, 15, 0));
                product_wrapper.getChildren().addAll(name, informations);
                product_wrapper.setPadding(new Insets(0, 10, 10, 10));

                pane.getChildren().add(product_wrapper);

                if (products_box.getChildren().size() % 2 == 0)
                    pane.setStyle("-fx-background-color: #336699;");
                else
                    pane.setStyle("-fx-background-color: #0F355C;");

                pane.setOnMouseClicked(event -> {
                    if (lastClicked != null) {
                        if (products_box.getChildren().indexOf(lastClicked) % 2 == 0)
                            lastClicked.setStyle("-fx-background-color: #336699;");
                        else
                            lastClicked.setStyle("-fx-background-color: #0F355C;");
                    }
                    pane.setStyle("-fx-background-color: #ff6600;");
                    lastClicked = pane;
                    lastClickedValue = products_list.get(products_box.getChildren().indexOf(pane));
                    actualiseDetails();
                });

                products_box.getChildren().add(pane);
            }
        }
    }

    private void buildDetails() {
        String[] labels = {"Nom", "En stock", "Quantité critique", "Livré le"};
        for (int i = 0; i < 2; i++) {
            ColumnConstraints constraint = new ColumnConstraints();
            constraint.setPrefWidth(details.getPrefWidth() / 2);
            grid.getColumnConstraints().add(constraint);
        }
        for (int i = 0; i < labels.length; i++) {
            RowConstraints constraint = new RowConstraints();
            constraint.setPrefHeight(details.getMaxHeight() / 6);
            grid.getRowConstraints().add(constraint);
        }
        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(labels[i] + ":");
            label.getStylesheets().add(new File("res/style.css").toURI().toString());
            label.getStyleClass().add("grid-stock-label");
            grid.addRow(i, label);
            GridPane.setMargin(label, new Insets(0, 0, 0, 30));
        }
        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(".");
            label.getStylesheets().add(new File("res/style.css").toURI().toString());
            label.getStyleClass().add("grid-stock-field");
            grid.add(label, 1, i);
            GridPane.setMargin(label, new Insets(0, 0, 0, 30));
        }

        actualiseDetails();
    }

    private void actualiseDetails() {
        for (Node node : grid.getChildren()) {
            if (node instanceof Label && GridPane.getColumnIndex(node) == 1) {
                switch (GridPane.getRowIndex(node)) {
                    case 0:
                        ((Label) node).setText(lastClickedValue.getLabel());
                        break;
                    case 1:
                        ((Label) node).setText(String.valueOf(lastClickedValue.getStock()));
                        break;
                    case 2:
                        ((Label) node).setText("TODO GET WITH DAO");
                        break;
                    case 3:
                        ((Label) node).setText("TODO GET WITH DAO");
                        break;
                }
            }
        }
    }
}

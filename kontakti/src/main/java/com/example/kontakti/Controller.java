package com.example.kontakti;

import com.example.kontakti.podaci.Kontakti;
import com.example.kontakti.podaci.PodaciSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class Controller {
    private List<Kontakti> stavkeKontakata;
    @FXML
    private TableView<Kontakti> stavkeTableView;
    @FXML
    private TableColumn firstNameCol;
    @FXML
    private TableColumn lastNameCol;
    @FXML
    private TableColumn phoneNumberCol;
    @FXML
    private TableColumn eMailCol;
    @FXML
    private BorderPane mainPane;
    @FXML
    private ContextMenu contextMenu;

    public void initialize() {

        contextMenu = new ContextMenu();
        MenuItem obrisiStavku = new MenuItem("Delete");

        obrisiStavku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Kontakti kontakti = stavkeTableView.getSelectionModel().getSelectedItem();
                obrisiStavku(kontakti);
            }
        });
        contextMenu.getItems().addAll(obrisiStavku);

        ObservableList<Kontakti> stavkeOLista = FXCollections.observableArrayList();

        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Kontakti, String>("firstName")
        );
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Kontakti, String>("lastName")
        );
        phoneNumberCol.setCellValueFactory(
                new PropertyValueFactory<Kontakti, String>("phoneNumber")
        );
        eMailCol.setCellValueFactory(
                new PropertyValueFactory<Kontakti, String>("eMail")
        );

        stavkeTableView.setRowFactory(new Callback<TableView<Kontakti>, TableRow<Kontakti>>() {
            @Override
            public TableRow call(TableView<Kontakti> stavkeTableView) {
                TableRow row = new TableRow() {
                    @Override
                    protected void updateItem(Object o, boolean b) {
                        super.updateItem(o, b);
                    }
                };
                row.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                row.setContextMenu(null);
                            } else {
                                row.setContextMenu(contextMenu);
                            }
                        }
                );
                return row;
            }
        });
        lastNameCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn tableColumn) {
                TableCell celija = new TableCell<Kontakti, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText("");
                        } else {
                            setText(item);

                            if (item.length() <= 5) {
                                setTextFill(Color.RED);
                            }
                            if (item.length() > 5) {
                                setTextFill(Color.YELLOWGREEN);
                            }
                            if (item.length() > 10) {
                                setTextFill(Color.ORANGE);
                            }
                        }
                    }
                };
                return celija;
            }
        });
        stavkeTableView.setItems(PodaciSingleton.getInstance().getStavkeOLista());
        stavkeTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        stavkeTableView.getSelectionModel().selectFirst();
    }
    @FXML
    public void otvoriDijalog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dodavanje-kontakata-dialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Ne moze da otvori dijalog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> rezultat = dialog.showAndWait();
        if (rezultat.isPresent() && rezultat.get() == ButtonType.OK) {
            DodavanjeKontroler kontroler = fxmlLoader.getController();
            Kontakti kontakti = kontroler.ubaciStavku();
            if (kontakti != null) {
                stavkeTableView.getSelectionModel().select(kontakti);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Greska!");
                alert.setContentText("Morate uneti sve stavke");
                Optional<ButtonType> rezultat1 = alert.showAndWait();
            }
        }
    }
    public void obrisiStavku(Kontakti kontakti) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Brisanje stavki kontakata");
        alert.setHeaderText("Brisanje stavke " + kontakti.getFirstName());
        alert.setContentText("Dali ste sigurni?");
        Optional<ButtonType> rezultat = alert.showAndWait();
        if (rezultat.isPresent() && rezultat.get() == ButtonType.OK) {
            PodaciSingleton.getInstance().obrisiStavku(kontakti);
        }
    }
}



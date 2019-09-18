package com.kevin.controller;

import com.kevin.Main;
import com.kevin.entity.Category;
import com.kevin.entity.Item;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<Category> boxCat;
    @FXML
    private DatePicker boxDate;
    @FXML
    private TableView<Item> tblData;
    @FXML
    private TableColumn<Item, String> col1;
    @FXML
    private TableColumn<Item, String> col2;
    @FXML
    private TableColumn<Item, String> col3;
    @FXML
    private TableColumn<Item, String> col4;
    private ObservableList<Category> categories;
    private ObservableList<Item> items = FXCollections.observableArrayList();
    @FXML
    private Button btnSave;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnUpdate;

    @FXML
    private void btnSave(ActionEvent actionEvent) {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || boxCat.getSelectionModel().isEmpty() || boxDate.getEditor().getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please fill ID / Name / Category/ Date");

            alert.show();
        }
        else {
            int j = 0;
            for (Item c : items) {
                if (txtName.getText().equals(c.getName())) {
                    j++;
                }
            }

            if (j >= 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Duplicate Name");

                alert.show();

            }
            else {
                Item i = new Item();
                i.setId(Integer.parseInt(txtId.getText().trim()));
                i.setName(txtName.getText().trim());
                i.setCategory(boxCat.getSelectionModel().getSelectedItem());
                i.setDate(boxDate.getValue());
                items.add(i);

                txtId.clear();
                txtName.clear();
                boxDate.getEditor().clear();
                boxCat.getSelectionModel().select(-1);
            }
        }
    }

    @FXML
    private void btnReset(ActionEvent actionEvent) {
        txtId.clear();
        txtName.clear();
        boxCat.getSelectionModel().select(-1);
        boxDate.getEditor().clear();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
    }

    @FXML
    private void btnUpdate(ActionEvent actionEvent) {
        if (txtName.getText().isEmpty() || txtId.getText().isEmpty() || boxCat.getSelectionModel().isEmpty() || boxDate.getEditor().getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please fill ID / Name / Category/ Date");

            alert.show();
        }
        else {
            Item i = tblData.getSelectionModel().getSelectedItem();
            i.setName(txtName.getText().trim());
            i.setId(Integer.parseInt(txtId.getText().trim()));
            i.setCategory(boxCat.getSelectionModel().getSelectedItem());
            i.setDate(boxDate.getValue());
            items.setAll(i);

            txtName.clear();
            txtId.clear();
            boxDate.getEditor().clear();
            boxCat.getSelectionModel().select(-1);

            btnUpdate.setDisable(true);
            btnSave.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boxCat.setItems(getCategories());
        tblData.setItems(items);
        col1.setCellValueFactory(data ->{
            Item i = data.getValue();
            return new SimpleStringProperty(String.valueOf(i.getId()));
        });
        col2.setCellValueFactory(data ->{
            Item i = data.getValue();
            return new SimpleStringProperty(String.valueOf(i.getName()));
        });
        col3.setCellValueFactory(data ->{
            Item i = data.getValue();
            return new SimpleStringProperty(String.valueOf(i.getCategory()));
        });
        col4.setCellValueFactory(data ->{
            Item i = data.getValue();
            return new SimpleStringProperty(String.valueOf(i.getDate()));
        });
    }

    @FXML
    private void categoryShow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Category.fxml"));
            VBox root = loader.load();
            CategoryController controller = loader.getController();
            controller.setMainController(this);


            Stage mainStage = new Stage();
            mainStage.setTitle("Category Management");
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Category> getCategories() {
        if(categories == null){
            categories = FXCollections.observableArrayList();
        }
        return categories;
    }

    @FXML
    private void tblClicked(MouseEvent mouseEvent) {
        Item c = tblData.getSelectionModel().getSelectedItem();
        txtName.setText(c.getName());
        txtId.setText(String.valueOf(c.getId()));
        boxCat.getSelectionModel().select(c.getCategory());
        boxDate.getEditor().setText(c.getDate().toString());

        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
    }

    @FXML
    private void close(ActionEvent actionEvent) {
        Platform.exit();
    }
}


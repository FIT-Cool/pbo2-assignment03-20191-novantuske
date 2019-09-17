package com.kevin.controller;

import com.kevin.Main;
import com.kevin.entity.Category;
import com.kevin.entity.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    private TableView tblData;
    @FXML
    private TableColumn<Item, String> col1;
    @FXML
    private TableColumn<Item, String> col2;
    @FXML
    private TableColumn<Item, String> col3;
    @FXML
    private TableColumn<Item, String> col4;
    private ObservableList<Category> categories;
    private ObservableList<Item> items;

    @FXML
    private void btnSave(ActionEvent actionEvent) {
        Item i = new Item();
        i.setId(Integer.parseInt(txtId.getText().trim()));
        i.setName(txtName.getText().trim());
        i.setCategory(boxCat.getSelectionModel().getSelectedItem());
        i.setDate(String.valueOf(boxDate.getValue()));
        items.add(i);
    }

    @FXML
    private void btnReset(ActionEvent actionEvent) {
        txtId.setText("");
        txtName.setText("");
    }

    @FXML
    private void btnUpdate(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
}


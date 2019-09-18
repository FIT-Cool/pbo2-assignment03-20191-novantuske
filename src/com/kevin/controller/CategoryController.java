package com.kevin.controller;

import com.kevin.entity.Category;
import com.kevin.entity.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {
    @FXML
    private TextField txtCatID;
    @FXML
    private TextField txtCatName;
    @FXML
    private TableView<Category> tblCatData;
    @FXML
    private TableColumn<Category, String> col1;
    @FXML
    private TableColumn<Category, String> col2;
    private ItemController mainController;

    @FXML
    private void btnSave(ActionEvent actionEvent) {
        if (txtCatID.getText().isEmpty() || txtCatName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please fill ID / Name ");

            alert.show();
        }
        else {
            int j = 0;
            for (Category c : mainController.getCategories()) {
                if (txtCatName.getText().equals(c.getName())) {
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
                Category c = new Category();
                c.setName(txtCatName.getText().trim());
                c.setID(Integer.parseInt(txtCatID.getText().trim()));
                mainController.getCategories().add(c);
            }
        }
    }


    @FXML
    private void tblClicked(MouseEvent mouseEvent) {
        Category c = tblCatData.getSelectionModel().getSelectedItem();
        txtCatName.setText(c.getName());
        txtCatID.setText(String.valueOf(c.getId()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col1.setCellValueFactory(data ->{
            Category c = data.getValue();
            return new SimpleStringProperty(String.valueOf(c.getId()));
        });
        col2.setCellValueFactory(data ->{
            Category c = data.getValue();
            return new SimpleStringProperty(c.getName());
        });
    }

    public void setMainController(ItemController itemController) {
        this.mainController = itemController;
        tblCatData.setItems(mainController.getCategories());
    }
}

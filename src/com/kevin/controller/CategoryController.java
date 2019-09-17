package com.kevin.controller;

import com.kevin.entity.Category;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private ObservableList<Category> categories;
    private ItemController mainController;

    @FXML
    private void btnSave(ActionEvent actionEvent) {
        Category c = new Category();
        c.setName(txtCatName.getText().trim());
        c.setID(Integer.parseInt(txtCatID.getText().trim()));
        mainController.getCategories().add(c);
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

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public void setMainController(ItemController itemController) {
        this.mainController = itemController;
        tblCatData.setItems(mainController.getCategories());
    }
}

package controller;

import bo.custom.Impl.ItemBoImpl;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBconnection;
import dto.ItemDto;
import dto.tm.ItemTB;
import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dao.Custom.IMPL.ItemDaoImpl;
import dao.Custom.ItemDao;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemFormController {
    public TableView<ItemTB> itemTB;
    public TableColumn colCode;
    public TableColumn colDesc;
    public TableColumn colUnitP;
    public TableColumn colQty;
    public TableColumn colOption;
    public AnchorPane itempane;
    public JFXTextField texCode;
    public JFXTextField texqty;
    public JFXTextField texUnitp;
    public JFXTextField texdesc;
    private ItemBo itemBo =new ItemBoImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitP.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));
         lodeItemTable();
        itemTB.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue,newValue) -> {
            setData(newValue);
        });

    }

    private void setData(ItemTB newValue) {
              texCode.setText(newValue.getCode());
              texCode.setEditable(false);
              texdesc.setText(newValue.getDescription());
              texUnitp.setText(String.valueOf(newValue.getUnitPrice()));
              texqty.setText(String.valueOf(newValue.getQty()));
    }

    private void lodeItemTable() throws SQLException, ClassNotFoundException {
        ObservableList<ItemTB> itemTBS= FXCollections.observableArrayList();
        List<Item> itemList1=new ArrayList<>();
        itemList1=itemBo.getAllItem();
        for (Item t1:itemList1) {
            JFXButton btn=new JFXButton("Delete");
           ItemTB itemTB1=new ItemTB(t1.getCode(),
                   t1.getDescription(),
                   t1.getUnitPrice(),
                   t1.getQtyOnHand(),
                   btn);
           itemTBS.add(itemTB1);
            btn.setOnAction(actionEvent -> {
                itemBo.deleteItem(t1.getCode());
                try {
                    lodeItemTable();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        itemTB.setItems(itemTBS);
    }

    public void relodeButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
          lodeItemTable();
    }

    public void saveButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        texCode.setEditable(true);
       boolean isSave= itemBo.saveItem(new ItemDto(texCode.getText(),
                texdesc.getText(),
                Double.parseDouble(texUnitp.getText()),
                Integer.parseInt(texqty.getText())));
       if (isSave){
           new Alert(Alert.AlertType.INFORMATION, "Inserted successfully").show();
           claerField();
           lodeItemTable();
       }else {
           new Alert(Alert.AlertType.ERROR, "Duplicate...").show();
       }

    }

    private void claerField() {
        texCode.clear();
        texdesc.clear();
        texUnitp.clear();
        texqty.clear();
    }

    public void backbuttonOnAction(ActionEvent actionEvent) {
        Stage stage=(Stage) itempane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/DashboardForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
     boolean isUpdate = itemBo.updateItem(new ItemDto(texCode.getText(),
                texdesc.getText(),
                Double.parseDouble(texUnitp.getText()),
                Integer.parseInt(texqty.getText())));
        if (isUpdate){
            new Alert(Alert.AlertType.INFORMATION, "Update successfully").show();
            claerField();
            lodeItemTable();
        }else {
            new Alert(Alert.AlertType.ERROR, "Duplicate...").show();
        }
    }

    public void reportButtonOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/Reports/Item_Report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBconnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}


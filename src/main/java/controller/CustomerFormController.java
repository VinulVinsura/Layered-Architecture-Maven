package controller;

import bo.custom.CustomerBo;
import bo.custom.Impl.CustomerBoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBconnection;
import dto.CustomerDto;
import dto.tm.CustomerTB;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dao.Custom.CustomerDao;
import dao.Custom.IMPL.CustomerDaoIMPL;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFormController {
    public JFXTextField texid;
    public JFXTextField texName;
    public JFXTextField texAddress;
    public JFXTextField texSalary;
    public AnchorPane customerpane;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOption;
    public TableView<CustomerTB> tabCustomer;
    public Label custIdLable;
    private CustomerBo customerBo =new CustomerBoImpl();
    private void claerField() {
        texid.clear();
        texName.clear();
        texAddress.clear();
        texSalary.clear();

    }

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));
        loadCustomerTable();
        setCustomerId();
        tabCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue,newValue) -> {
            setData(newValue);
        });



    }

    private void setCustomerId() {
        custIdLable.setText(customerBo.getLastCustomerId());
        texid.setText(customerBo.getLastCustomerId());
    }

    private void setData(CustomerTB newValue) {
        if (newValue !=null) {
            texid.setEditable(false);
            texid.setText(newValue.getId());
            texName.setText(newValue.getName());
            texAddress.setText(newValue.getAddress());
            texSalary.setText(String.valueOf(newValue.getSalary()));

        }
    }

    private void loadCustomerTable(){
        ObservableList<CustomerTB> custTB= FXCollections.observableArrayList();
        List<Customer> customerList= null;
        try {
            customerList = customerBo.getAllCustomer();
            for (Customer c:customerList) {
                JFXButton btn=new JFXButton("Delete");
                CustomerTB c1=new CustomerTB(c.getId(),
                        c.getName(),
                        c.getAddress(),
                        c.getSalary(),
                        btn);
                custTB.add(c1);
                btn.setOnAction(actionEvent -> {
                    customerBo.deleteCustomer(c1.getId());
                    setCustomerId();
                    loadCustomerTable();
                    new Alert(Alert.AlertType.INFORMATION, "Customer "+c1.getId()+" Deleted...").show();

                });
            }
            tabCustomer.setItems(custTB);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void relodeBAUttonOnAction(ActionEvent actionEvent){
        loadCustomerTable();
    }

    public void backButtonOnAction(ActionEvent actionEvent) {
       Stage stage=(Stage) customerpane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/DashboardForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {

        try {
            texid.setEditable(false);
           boolean isSave = customerBo.saveCustomer(new CustomerDto(custIdLable.getText(),
                    texName.getText(),
                    texAddress.getText(),
                    Double.parseDouble(texSalary.getText())));
            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved...").show();
                claerField();
                loadCustomerTable();
                setCustomerId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Duplicate...").show();
                claerField();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "ERROR").show();
            claerField();


        }
    }


    public void updateButtonOnAction(ActionEvent actionEvent) {
        boolean isUpdate = customerBo.updateCustomer(new CustomerDto(texid.getText(),
                texName.getText(),
                texAddress.getText(),
                Double.parseDouble(texSalary.getText())));
        if (isUpdate){
            new Alert(Alert.AlertType.INFORMATION, "Update successfully").show();
            claerField();
            loadCustomerTable();
        }else {
            new Alert(Alert.AlertType.ERROR, "Duplicate...").show();
        }
    }

    public void reportButtonOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/Reports/Customer_reports.jrxml");
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

package controller;

import bo.custom.Impl.OrderBoImpl;
import bo.custom.Impl.OrderDetailBoImpl;
import bo.custom.OrderBo;
import bo.custom.OrderDetailBo;
import com.jfoenix.controls.JFXButton;
import dto.OrderDetailDto;
import dto.tm.OrderDetailTB;
import dto.tm.OrderTB;
import entity.Orders;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrdersFormController {
    public TableView<OrderTB> ordersTB;
    public TableColumn colorderID;
    public TableColumn colDate;
    public TableColumn colcustId;
    public TableColumn colOption;
    public AnchorPane orderPane;
    public TableView<OrderDetailTB> orderDetailTB;
    public TableColumn colOrderId;
    public TableColumn colItemCode;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    private OrderBo orderBo=new OrderBoImpl();
    private OrderDetailBo orderDetailBo=new OrderDetailBoImpl();
    private  ObservableList<OrderTB> orderTBList= FXCollections.observableArrayList();
    private  ObservableList<OrderDetailTB> orderDetailTBList= FXCollections.observableArrayList();
    private List<Orders> orderList=new ArrayList<>();
    public void initialize(){
        colorderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colcustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        lodeOrdersTable();
        ordersTB.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue,newValue) -> {
            setData(newValue);


        });
    }

    private void setData(OrderTB newValue) {
        for (OrderDetailDto odDto:orderDetailBo.getOrderDetail(newValue.getOrderId())) {
            orderDetailTBList.add(new OrderDetailTB(odDto.getOrderId(),
                    odDto.getItemCode(),
                    odDto.getQty(),
                    odDto.getUnitPrice()));
        }
        orderDetailTB.setItems(orderDetailTBList);


    }

    private void lodeOrdersTable() {
        orderList=orderBo.getAllOrders();
        for (Orders o1:orderList) {
            JFXButton btn=new JFXButton("Delete");
            OrderTB oTB=new OrderTB(o1.getId(),
                    o1.getDate(),
                    o1.getCustomerId(),
                    btn);
            orderTBList.add(oTB);
        }
        ordersTB.setItems(orderTBList);

    }

    public void backButtonOnAction(ActionEvent actionEvent) {
        Stage stage=(Stage) orderPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/DashboardForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

package controller;

import bo.custom.CustomerBo;
import bo.custom.Impl.CustomerBoImpl;
import bo.custom.Impl.ItemBoImpl;
import bo.custom.Impl.OrderBoImpl;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.OrderDetailDto;
import dto.OrderDto;
import dto.tm.CustomerTB;
import dto.tm.ItemTB;
import dto.tm.OrderTB;
import dto.tm.PlaceOrderTB;
import entity.Customer;
import entity.Item;
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
import dao.Custom.IMPL.ItemDaoImpl;
import dao.Custom.IMPL.OrderDaoImpl;
import dao.Custom.ItemDao;
import dao.Custom.OrderDao;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormController {

    public AnchorPane placeOrdepane;
    public JFXTextField texDescr;
    public JFXTextField texUnitPrice;
    public JFXTextField texQty;
    public JFXTextField texName;
    public JFXComboBox texCode;
    public JFXComboBox texId;
    public TableColumn colCode;
    public TableColumn colDescr;
    public TableColumn colQTY;
    public TableColumn colAmount;
    public TableColumn colOption;
    public TableView<PlaceOrderTB> placeOrderTB;
    public Label custIdLabal;
    private CustomerBo customerBo =new CustomerBoImpl();
    private ItemBo itemBo =new ItemBoImpl();
    private OrderBo orderBo =new OrderBoImpl();
    private List<Customer> customerTBList;
    private List<Item> itemTBList;

    private ObservableList<PlaceOrderTB> orderTb=FXCollections.observableArrayList();
    public Label totalLabal;
    private double tot=0;
    public void initialize() throws SQLException, ClassNotFoundException {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescr.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));
        lodeCustomer();
        lodeItem();
        setOrderId();

        texId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, id) ->{
            for (Customer entity:customerTBList) {
                   if(entity.getId().equals(id)){
                         texName.setText(entity.getName());
                   }
            }
        });
        texCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, code) -> {
            for (Item entity:itemTBList) {
                 if(entity.getCode().equals(code)){
                     texDescr.setText(entity.getDescription());
                     texUnitPrice.setText(String.format("%.2f",entity.getUnitPrice()));
                 }
            }
        });

    }


    private void lodeItem() throws SQLException, ClassNotFoundException {
        itemTBList= itemBo.getAllItem();
        ObservableList list=FXCollections.observableArrayList();
        for (Item entity:itemTBList) {
            list.add(entity.getCode());
        }
        texCode.setItems(list);

    }

    private void lodeCustomer() throws SQLException, ClassNotFoundException {
       customerTBList= customerBo.getAllCustomer();
       ObservableList list=FXCollections.observableArrayList();
        for (Customer entity:customerTBList) {
           list.add(entity.getId());
        }
        texId.setItems(list);

    }

    public void addButtonOnAction(ActionEvent actionEvent) {
          JFXButton button=new JFXButton("Delete");

         if (Integer.parseInt(texQty.getText())<=itemBo.getQty(texCode.getValue().toString())){
             Double amount=Integer.parseInt(texQty.getText())*Double.parseDouble(texUnitPrice.getText());
             PlaceOrderTB placeOrderTB1=new PlaceOrderTB((String) texCode.getValue(),
                     texDescr.getText(),
                     Integer.parseInt(texQty.getText()),
                     amount,
                     button);
             boolean isEqual=false;
             button.setOnAction(actionEvent1 -> {
                 orderTb.remove(placeOrderTB1);
                 tot-=placeOrderTB1.getAmount();
                 placeOrderTB.refresh();
                 totalLabal.setText(String.format("%.2f",tot));

             });

             for (PlaceOrderTB order1:orderTb) {
                 if (order1.getCode().equals(placeOrderTB1.getCode())){

                     order1.setQty(order1.getQty()+placeOrderTB1.getQty());
                     order1.setAmount(order1.getAmount()+placeOrderTB1.getAmount());
                     isEqual=true;
                     tot+=amount;
                     placeOrderTB.refresh();
                 }
             }
             if (!isEqual){
                 orderTb.add(placeOrderTB1);
                 tot+=amount;
             }

             placeOrderTB.setItems(orderTb);
             totalLabal.setText(String.format("%.2f",tot));
             claerTexField();
         }else {
             new Alert(Alert.AlertType.ERROR, "Out Of Stock").show();
         }

    }

    private void claerTexField() {
        texDescr.clear();
        texUnitPrice.clear();
        texQty.clear();
    }

    public  void setOrderId(){
        custIdLabal.setText(orderBo.lastOrder());

    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException {
        List<OrderDetailDto> list=new ArrayList<>();
        for (PlaceOrderTB order:orderTb) {
            list.add(new OrderDetailDto(custIdLabal.getText(),
                    order.getCode(),
                    order.getQty(),
                    order.getAmount()/order.getQty()));
            itemBo.updateQty(order.getCode(),order.getQty());
        }
        boolean isSave = orderBo.placeOrder(new OrderDto(custIdLabal.getText(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                texId.getValue().toString(),
                list));

        if(isSave){
            new Alert(Alert.AlertType.INFORMATION, "Order Placed").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Something Wrong").show();
        }

    }


    public void backButtonOnAction(ActionEvent actionEvent) {
        Stage stage=(Stage) placeOrdepane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/DashboardForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void combCustomerOnAction(ActionEvent actionEvent) {

    }

    public void combItemOnAction(ActionEvent actionEvent) {

    }
}

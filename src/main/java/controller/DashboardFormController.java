package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {
    public AnchorPane pane;
    public Label timelapse;

    public void initialize(){
        calulatTime();
    }

    private void calulatTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, actionEvent -> timelapse.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:MM:SS")))), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void customerButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/CustomerForm.fxml"))));
        stage.setTitle("Customer Form");
        Image image=new Image("\\img\\title-bar.png"); //set Title Icon
        stage.getIcons().add(image);   //set Title Icon
        stage.show();


    }


    public void itemButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage=(Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/ItemForm.fxml"))));
        stage.setTitle("Item Form");
        Image image=new Image("\\img\\title-bar.png"); //set Title Icon
        stage.getIcons().add(image);   //set Title Icon
        stage.show();
    }

    public void placeOrderButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage=(Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/PlaceOrderForm.fxml"))));
        stage.setTitle("Place Order Form");
        Image image=new Image("\\img\\title-bar.png"); //set Title Icon
        stage.getIcons().add(image);   //set Title Icon
        stage.show();
    }

    public void ordersButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage=(Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/OrdersForm.fxml"))));
        stage.setTitle("Orders Form");
        Image image=new Image("\\img\\title-bar.png"); //set Title Icon
        stage.getIcons().add(image);   //set Title Icon
        stage.show();
    }
}

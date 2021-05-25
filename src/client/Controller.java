package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import client.Client;
import database.*;

import java.io.*;

public class Controller{
    private Client client;
    private SpaceMarineCollection collection;
    private String username;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCollection(SpaceMarineCollection collection) {
        this.collection = collection;
    }

    @FXML
    private TextField usernameField;

    @FXML
    private Button signInButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button closeButton;
    @FXML
    private Label infoText;
    private ObservableResourceFactory resourceFactory;

    public void setResourceFactory(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
    }

    @FXML
    private void signInButtonOnAction() throws IOException {
        if (client.checkLogin(usernameField.getText(), passwordField.getText()) && (!usernameField.getText().equals("")) && (!passwordField.getText().equals(""))){
            Stage stageOld = (Stage) signInButton.getScene().getWindow();

            Stage stage = new Stage();
            FXMLLoader content = new FXMLLoader();
            content.setLocation(getClass().getResource("Content.fxml"));
            Parent root = content.load();
            ContentController contentController;
            contentController = content.getController();
            contentController.setCollection(collection);
            contentController.initLangs(resourceFactory);
            contentController.setClient(client);
            stage.setTitle("Content");
            stageOld.close();
            stage.setScene(new Scene(root, 1100, 700));
            contentController.setUsername(usernameField.getText());
            stage.show();
        } else{
            Stage stage = new Stage();
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("IncorrectPassword.fxml"));
            Parent root = load.load();
            stage.setTitle("Error");
            stage.setScene(new Scene(root, 200, 100));
            stage.show();

        }
    }

    @FXML
    private void closeButtonOnAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}

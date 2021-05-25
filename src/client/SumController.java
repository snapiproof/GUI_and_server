package client;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SumController implements Initializable {
    @FXML
    private Label infoText;
    @FXML
    private Button closeButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        infoText.setText(Client.AskCollection("localhost", 5001).info());
    }
    @FXML
    private void closeButtonOnAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
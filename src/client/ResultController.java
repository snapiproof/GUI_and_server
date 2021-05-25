package client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController{
    Client client;

    public void setClient(Client client) {
        this.client = client;
    }
    public ResultController(Client client){
        infoText.setText(client.getTextField());
    }

    @FXML
    private Label infoText;
    @FXML
    private Button closeButton;
    @FXML
    private void closeButtonOnAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

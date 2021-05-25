package client;

import client.Client;
import database.SpaceMarineCollection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoController implements Initializable {
    @FXML
    private Label infoText;
    @FXML
    private Button closeButton;
    SpaceMarineCollection spaceMarineCollection;

    public void setSpaceMarineCollection(SpaceMarineCollection spaceMarineCollection) {
        this.spaceMarineCollection = spaceMarineCollection;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spaceMarineCollection = Client.AskCollection("localhost", 5001);
        infoText.setText(spaceMarineCollection.info());
    }
    @FXML
    private void closeButtonOnAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

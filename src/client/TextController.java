package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TextController {
    Client client;

    public void setClient(Client client) {
        this.client = client;
    }
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @FXML
    private Label textLabel;
    @FXML
    private Label resultLabel;
    @FXML
    private TextField textField;
    @FXML
    private Button closeButton;
    private ContentController contentController;

    public void setContentController(ContentController contentController) {
        this.contentController = contentController;
    }

    public void setTextField(String textField) {
        textLabel.setText(textField);
    }

    @FXML
    private void okButtonOnAction(){
        String s = "";
        Stage stageOld = (Stage) closeButton.getScene().getWindow();
        String executeTitle = "ExecuteScript";
        String remove_any_by_healthTitle = "Remove any element with health";
        String remove_lower_keyTitle = "Remove element with key lower than";
        String remove_greater_keyTitle = "Remove element with key greater than";
        String count_less_than_healthTitle = "Count elements with health less than";
        if (stageOld.getTitle().equals(executeTitle)) {
            s = client.executeScript(textField.getText(), username);
        }else if (stageOld.getTitle().equals(remove_any_by_healthTitle)) {
            s = client.remove_any_by_health(textField.getText(), username);
        }else if (stageOld.getTitle().equals(remove_lower_keyTitle)) {
            s = client.remove_lower_key(textField.getText(), username);
        }else if (stageOld.getTitle().equals(remove_greater_keyTitle)) {
            s = client.remove_greater_key(textField.getText(), username);
        }else if (stageOld.getTitle().equals(count_less_than_healthTitle)) {
            s = client.count_less_than_health(textField.getText());
        }
        contentController.refreshCollection(client.askCollection());
        resultLabel.setText(s);
    }
    @FXML
    private void closeButtonOnAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemoveController {
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

    @FXML
    private void okButtonOnAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        String s = client.remove(textField.getText(), username);
        contentController.refreshCollection(client.askCollection());
        resultLabel.setText(s);
    }
    @FXML
    private void closeButtonOnAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

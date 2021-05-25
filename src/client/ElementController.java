package client;

import database.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

public class ElementController implements Initializable {
    Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private ComboBox<AstartesCategory> categoryBox;
    @FXML
    private ComboBox<Weapon> weaponBox;
    @FXML
    private ComboBox<MeleeWeapon> meleeWeaponBox;
    @FXML
    private Label nameLabel;
    @FXML
    private Label coordinatesXLabel;
    @FXML
    private Label coordinatesYLabel;
    @FXML
    private Label healthLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label meleeWeaponLabel;
    @FXML
    private Label weaponLabel;
    @FXML
    private Label chapterNameLabel;
    @FXML
    private Label chapterLegionLabel;
    @FXML
    private Label chapterSizeLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField coordinatesXField;
    @FXML
    private TextField coordinatesYField;
    @FXML
    private TextField healthField;
    @FXML
    private TextField chapterNameField;
    @FXML
    private TextField chapterLegionField;
    @FXML
    private TextField chapterSizeField;
    @FXML
    private TextField keyField;
    private String username;
    private ContentController contentController;

    public void setContentController(ContentController contentController) {
        this.contentController = contentController;
    }

    public void setNameField(String nameField) {
        this.nameField.setText(nameField);
    }

    public void setWeaponBox(Weapon weaponBox) {
        this.weaponBox.setValue(weaponBox);
    }

    public void setCategoryBox(AstartesCategory category) {
        this.categoryBox.setValue(category);
    }

    public void setChapterLegionField(String legion) {
        this.chapterLegionField.setText(legion);
    }

    public void setMeleeWeaponBox(MeleeWeapon meleeWeaponBox) {
        this.meleeWeaponBox.setValue(meleeWeaponBox);
    }

    public void setChapterSizeField(String size) {
        this.chapterSizeField.setText(size);
    }

    public void setChapterNameField(String size) {
        this.chapterNameField.setText(size);
    }

    public void setCoordinatesXField(String size) {
        this.coordinatesXField.setText(size);
    }

    public void setCoordinatesYField(String size) {
        this.coordinatesYField.setText(size);
    }

    public void setHealthField(String size) {
        this.healthField.setText(size);
    }

    public void setKeyField(String size) {
        this.keyField.setText(size);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @FXML
    private void enterButtonOnAction(){
        try {
            String name = nameField.getText();
            Coordinates coordinates = new Coordinates(Double.parseDouble(coordinatesXField.getText()), Long.parseLong(coordinatesYField.getText()));
            Double health = Double.parseDouble(healthField.getText());
            Chapter chapter = new Chapter(chapterNameField.getText(), chapterLegionField.getText(), Integer.parseInt(chapterSizeField.getText()));
            String creationTime = java.time.ZonedDateTime.now().toString();
            Long key = Long.parseLong(keyField.getText());
            SpaceMarine spaceMarine = new SpaceMarine(name, coordinates, creationTime, health, categoryBox.getValue(), weaponBox.getValue(), meleeWeaponBox.getValue(), chapter);
            Stage stageOld = (Stage) categoryLabel.getScene().getWindow();
            String addTitle = "AddElement";
            String updateTitle = "UpdateElement";
            if (stageOld.getTitle().equals(addTitle)) {
                client.insert(keyField.getText(), spaceMarine, username);
            }else if (stageOld.getTitle().equals(updateTitle)) {
                client.update(id, spaceMarine, username);
            }else client.replace_if_lowe(keyField.getText(), spaceMarine, username);
            contentController.refreshCollection(client.askCollection());
            stageOld.close();

        }catch (Exception e){
            try {
                Stage stage = new Stage();
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("ErrorElement.fxml"));
                Parent root = load.load();
                stage.setTitle("Error");
                stage.setScene(new Scene(root, 200, 100));
                stage.show();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryBox.setItems(FXCollections.observableArrayList(AstartesCategory.values()));
        weaponBox.setItems(FXCollections.observableArrayList(Weapon.values()));
        meleeWeaponBox.setItems(FXCollections.observableArrayList(MeleeWeapon.values()));
    }
}

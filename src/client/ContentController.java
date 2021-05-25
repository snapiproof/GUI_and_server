package client;

import database.Console;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.ServerReading;
import server.User;
import client.Client;
import database.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.util.Duration.millis;

public class ContentController implements Initializable {
    private Client client;
    private SpaceMarineCollection collection;
    private String username;
    private Shape prevClicked;
    private Color prevColor;
    @FXML
    private TableColumn<SpaceMarine, String> creationDateColumn;
    @FXML
    private TableColumn<SpaceMarine, String> nameColumn;
    @FXML
    private TableColumn<SpaceMarine, Double> healthColumn;
    @FXML
    private TableColumn<SpaceMarine, Double> coordinatesXColumn;
    @FXML
    private TableColumn<SpaceMarine, Long> coordinatesYColumn;
    @FXML
    private TableColumn<SpaceMarine, AstartesCategory> categoryColumn;
    @FXML
    private TableColumn<SpaceMarine, Weapon> weaponTypeColumn;
    @FXML
    private TableColumn<SpaceMarine, MeleeWeapon> meleeWeaponColumn;
    @FXML
    private TableColumn<SpaceMarine, String> chapterNameColumn;
    @FXML
    private TableColumn<SpaceMarine, String> chapterLegionName;
    @FXML
    private TableColumn<SpaceMarine, Integer> chapterSizeColumn;
    @FXML
    private TableColumn<SpaceMarine, Long> idColumn;
    @FXML
    private TableColumn<SpaceMarine, String> ownerColumn;
    @FXML
    private TableView<SpaceMarine> spaceMarineTable;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Button closeButton;
    @FXML
    private Label usernameLabel;
    private String text;
    @FXML
    private Label textLabel;
    @FXML
    private TextField textField;
    @FXML
    private AnchorPane canvasPane;
    @FXML
    private Tab tableTab;
    @FXML
    private Tab canvasTab;
    @FXML
    private Button infoButton;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button executeScriptButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Tooltip infoButtonTooltip;
    @FXML
    private Tooltip addButtonTooltip;
    @FXML
    private Tooltip updateButtonTooltip;
    @FXML
    private Tooltip removeButtonTooltip;
    @FXML
    private Tooltip clearButtonTooltip;
    @FXML
    private Tooltip executeScriptButtonTooltip;
    @FXML
    private Tooltip refreshButtonTooltip;
    @FXML
    private TableColumn<SpaceMarine, Long> keyColumn;
    private ObservableResourceFactory resourceFactory;
    HashMap<String, Locale> localeMap = new HashMap<>();
    private Map<Shape, Long> shapeMap;
    private Map<Long, Text> textMap;
    private Map<String, Color> userColorMap;
    private Random randomGenerator;



    public void setClient(Client client) {
        this.client = client;
    }
    public void setCollection(SpaceMarineCollection collection){
        this.collection = collection;
    }
    ObservableList<SpaceMarine> listr = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        randomGenerator = new Random(1821L);
        userColorMap = new HashMap<>();
        textMap = new HashMap<>();
        shapeMap = new HashMap<>();
        keyColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, Long>("key"));
        idColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, Long>("id"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, String>("owner"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, String>("creationDate"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, String>("name"));
        healthColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, Double>("health"));
        coordinatesXColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, Double>("xfs"));
        coordinatesYColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, Long>("y"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, AstartesCategory>("category"));
        meleeWeaponColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, MeleeWeapon>("meleeWeapon"));
        weaponTypeColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, Weapon>("weaponType"));
        chapterLegionName.setCellValueFactory(new PropertyValueFactory<SpaceMarine, String>("chapterLegion"));
        chapterNameColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, String>("chapterName"));
        chapterSizeColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, Integer>("chapterCount"));
        spaceMarineTable.setItems(listr);
        collection = Client.AskCollection("localhost", 5001);
        refreshCollection(collection);
        localeMap.put("Русский", new Locale("ru", "RU"));
        languageComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));
    }

    private void bindGuiLanguage(){
        resourceFactory.setResources(ResourceBundle.getBundle(MainClient.BUNDLE, localeMap.get(languageComboBox.getSelectionModel().getSelectedItem())));
        idColumn.textProperty().bind(resourceFactory.getStringBinding("IdColumn"));
        ownerColumn.textProperty().bind(resourceFactory.getStringBinding("OwnerColumn"));
        creationDateColumn.textProperty().bind(resourceFactory.getStringBinding("CreationDateColumn"));
        nameColumn.textProperty().bind(resourceFactory.getStringBinding("NameColumn"));
        coordinatesXColumn.textProperty().bind(resourceFactory.getStringBinding("CoordinatesXColumn"));
        coordinatesYColumn.textProperty().bind(resourceFactory.getStringBinding("CoordinatesYColumn"));
        healthColumn.textProperty().bind(resourceFactory.getStringBinding("HealthColumn"));
        categoryColumn.textProperty().bind(resourceFactory.getStringBinding("CategoryColumn"));
        weaponTypeColumn.textProperty().bind(resourceFactory.getStringBinding("WeaponColumn"));
        meleeWeaponColumn.textProperty().bind(resourceFactory.getStringBinding("MeleeWeaponColumn"));
        chapterNameColumn.textProperty().bind(resourceFactory.getStringBinding("ChapterNameColumn"));
        chapterSizeColumn.textProperty().bind(resourceFactory.getStringBinding("ChapterSizeColumn"));

        tableTab.textProperty().bind(resourceFactory.getStringBinding("TableTab"));
        canvasTab.textProperty().bind(resourceFactory.getStringBinding("CanvasTab"));

        infoButton.textProperty().bind(resourceFactory.getStringBinding("InfoButton"));
        addButton.textProperty().bind(resourceFactory.getStringBinding("AddButton"));
        updateButton.textProperty().bind(resourceFactory.getStringBinding("UpdateButton"));
        removeButton.textProperty().bind(resourceFactory.getStringBinding("RemoveButton"));
        clearButton.textProperty().bind(resourceFactory.getStringBinding("ClearButton"));
        executeScriptButton.textProperty().bind(resourceFactory.getStringBinding("ExecuteScriptButton"));
        refreshButton.textProperty().bind(resourceFactory.getStringBinding("RefreshButton"));
        infoButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("InfoButtonTooltip"));
        addButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("AddButtonTooltip"));
        updateButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("UpdateButtonTooltip"));
        removeButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("RemoveButtonTooltip"));
        clearButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("ClearButtonTooltip"));
        executeScriptButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("ExecuteScriptButtonTooltip"));
        refreshButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("RefreshButtonTooltip"));
    }
    public void initLangs(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
        for (String localeName :  localeMap.keySet()) {
            if (localeMap.get(localeName).equals(resourceFactory.getResources().getLocale()))
                languageComboBox.getSelectionModel().select(localeName);
        }
        if (languageComboBox.getSelectionModel().getSelectedItem().isEmpty())
            languageComboBox.getSelectionModel().selectFirst();
        languageComboBox.setOnAction((event) ->
                resourceFactory.setResources(ResourceBundle.getBundle(MainClient.BUNDLE, localeMap.get(languageComboBox.getValue()))));
        bindGuiLanguage();
    }

    private void refreshCanvas() {
        shapeMap.keySet().forEach(s -> canvasPane.getChildren().remove(s));
        shapeMap.clear();
        textMap.values().forEach(s -> canvasPane.getChildren().remove(s));
        textMap.clear();
        for (SpaceMarine marine : spaceMarineTable.getItems()) {
            if (!userColorMap.containsKey(marine.getOwner()))
                userColorMap.put(marine.getOwner(),
                        Color.color(randomGenerator.nextDouble(), randomGenerator.nextDouble(), randomGenerator.nextDouble()));

            double size = Math.min(marine.getHealth(), 250);

            Shape circleObject = new Circle(size, userColorMap.get(marine.getOwner()));
            circleObject.setOnMouseClicked(this::shapeOnMouseClicked);
            circleObject.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(marine.getCoordinates().getX()));
            circleObject.translateYProperty().bind(canvasPane.heightProperty().divide(2).subtract(marine.getCoordinates().getY()));

            Text textObject = new Text(marine.getId().toString());
            textObject.setOnMouseClicked(circleObject::fireEvent);
            textObject.setFont(Font.font(size / 3));
            textObject.setFill(userColorMap.get(marine.getOwner()).darker());
            textObject.translateXProperty().bind(circleObject.translateXProperty().subtract(textObject.getLayoutBounds().getWidth() / 2));
            textObject.translateYProperty().bind(circleObject.translateYProperty().add(textObject.getLayoutBounds().getHeight() / 4));

            canvasPane.getChildren().add(circleObject);
            canvasPane.getChildren().add(textObject);
            shapeMap.put(circleObject, marine.getId());
            textMap.put(marine.getId(), textObject);

            ScaleTransition circleAnimation = new ScaleTransition(millis(800), circleObject);
            ScaleTransition textAnimation = new ScaleTransition(millis(800), textObject);
            circleAnimation.setFromX(0);
            circleAnimation.setToX(1);
            circleAnimation.setFromY(0);
            circleAnimation.setToY(1);
            textAnimation.setFromX(0);
            textAnimation.setToX(1);
            textAnimation.setFromY(0);
            textAnimation.setToY(1);
            circleAnimation.play();
            textAnimation.play();
        }
    }
    private void shapeOnMouseClicked(MouseEvent event) {
        Shape shape = (Shape) event.getSource();
        long id = shapeMap.get(shape);
        for (SpaceMarine marine : spaceMarineTable.getItems()) {
            if (marine.getId() == id) {
                spaceMarineTable.getSelectionModel().select(marine);
                break;
            }
        }
        if (prevClicked != null) {
            prevClicked.setFill(prevColor);
        }
        prevClicked = shape;
        prevColor = (Color) shape.getFill();
        shape.setFill(prevColor.brighter());
    }

    public String getLanguage() {
        return languageComboBox.getValue();
    }



    public void refreshCollection(SpaceMarineCollection spaceMarineCollection){
        String all = spaceMarineCollection.show();
        String[] q = all.split("\n");
        int i = q.length;
        int f;
        listr.clear();
        for (f = 1; f<i; f++) {
            String collection = all.split("\n")[f];
            String key = collection.split(" ")[1];
            String element = collection.split(" ")[3];
            SpaceMarine elem = Console.getElementFromLine(element);
            listr.add(elem);
        }
        refreshCanvas();
    }


    @FXML
    private void enterButtonOnAction(){

    }
    @FXML
    private void infoButtonOnAction(){
        try {
            Stage stage = new Stage();
            FXMLLoader content = new FXMLLoader();
            content.setLocation(getClass().getResource("Info.fxml"));
            Parent root = content.load();
            InfoController infoController = content.getController();
            stage.setTitle("Info");
            stage.setScene(new Scene(root, 700, 150));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void updateSelected(){
        try {
            if (spaceMarineTable.getSelectionModel().getSelectedItem().getOwner().equals(getUsername())) {
                Stage stage = new Stage();
                FXMLLoader content = new FXMLLoader();
                content.setLocation(getClass().getResource("Element.fxml"));
                Parent root = content.load();
                ElementController elementController = content.getController();
                elementController.setContentController(this);
                elementController.setClient(client);
                elementController.setId(spaceMarineTable.getSelectionModel().getSelectedItem().getId().toString());
                elementController.setChapterNameField(spaceMarineTable.getSelectionModel().getSelectedItem().getChapterName());
                elementController.setChapterLegionField(spaceMarineTable.getSelectionModel().getSelectedItem().getChapterLegion());
                elementController.setChapterSizeField(String.valueOf(spaceMarineTable.getSelectionModel().getSelectedItem().getChapterCount()));
                elementController.setHealthField(String.valueOf(spaceMarineTable.getSelectionModel().getSelectedItem().getHealth()));
                elementController.setCoordinatesXField(String.valueOf(spaceMarineTable.getSelectionModel().getSelectedItem().getXfs()));
                elementController.setCoordinatesYField(String.valueOf(spaceMarineTable.getSelectionModel().getSelectedItem().getY()));
                elementController.setCategoryBox(spaceMarineTable.getSelectionModel().getSelectedItem().getCategory());
                elementController.setWeaponBox(spaceMarineTable.getSelectionModel().getSelectedItem().getWeaponType());
                elementController.setMeleeWeaponBox(spaceMarineTable.getSelectionModel().getSelectedItem().getMeleeWeapon());
                elementController.setKeyField(String.valueOf(spaceMarineTable.getSelectionModel().getSelectedItem().getKey()));
                elementController.setNameField(String.valueOf(spaceMarineTable.getSelectionModel().getSelectedItem().getName()));

                elementController.setUsername(getUsername());
                stage.setTitle("UpdateElement");
                stage.setScene(new Scene(root, 510, 600));
                stage.show();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }



    @FXML
    private void addButtonOnAction(){
        try {
            Stage stage = new Stage();
            FXMLLoader content = new FXMLLoader();
            content.setLocation(getClass().getResource("Element.fxml"));
            Parent root = content.load();
            ElementController elementController = content.getController();
            elementController.setContentController(this);
            elementController.setClient(client);
            elementController.setUsername(getUsername());
            stage.setTitle("AddElement");
            stage.setScene(new Scene(root, 510, 600));
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
        refreshCollection(client.askCollection());
    }
    @FXML
    private void updateButtonOnAction(){
        try {
            Stage stage = new Stage();
            FXMLLoader content = new FXMLLoader();
            content.setLocation(getClass().getResource("Element.fxml"));
            Parent root = content.load();
            ElementController elementController = content.getController();
            elementController.setClient(client);
            elementController.setContentController(this);
            elementController.setUsername(getUsername());
            stage.setTitle("UpdateElement");
            stage.setScene(new Scene(root, 510, 600));
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
        refreshCollection(client.askCollection());
    }
    @FXML
    private void removeButtonOnAction(){
        try {
            Stage stage = new Stage();
            FXMLLoader text = new FXMLLoader();
            text.setLocation(getClass().getResource("RemoveByKey.fxml"));
            Parent root = text.load();
            RemoveController removeController = text.getController();
            removeController.setClient(client);
            removeController.setContentController(this);
            removeController.setUsername(getUsername());
            stage.setTitle("RemoveByKey");
            stage.setScene(new Scene(root, 500, 361));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void remove_any_by_healthButtonOnAction(){
        try {
            Stage stage = new Stage();
            FXMLLoader text = new FXMLLoader();
            text.setLocation(getClass().getResource("TextField.fxml"));
            Parent root = text.load();
            TextController textController = text.getController();
            textController.setClient(client);
            textController.setContentController(this);
            textController.setUsername(getUsername());
            stage.setTitle("Remove any element with health");
            textController.setTextField("Remove any element with health");
            stage.setScene(new Scene(root, 500, 361));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
        refreshCollection(client.askCollection());
    }
    @FXML
    private void remove_lower_keyButtonOnAction(){
        try {
            Stage stage = new Stage();
            FXMLLoader text = new FXMLLoader();
            text.setLocation(getClass().getResource("TextField.fxml"));
            Parent root = text.load();
            TextController textController = text.getController();
            textController.setClient(client);
            textController.setContentController(this);
            textController.setUsername(getUsername());
            stage.setTitle("Remove element with key lower than");
            textController.setTextField("Remove element with key lower than");
            stage.setScene(new Scene(root, 500, 361));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
        refreshCollection(client.askCollection());
    }
    @FXML
    private void remove_greater_keyButtonOnAction(){
        try {
            Stage stage = new Stage();
            FXMLLoader text = new FXMLLoader();
            text.setLocation(getClass().getResource("TextField.fxml"));
            Parent root = text.load();
            TextController textController = text.getController();
            textController.setClient(client);
            textController.setContentController(this);
            textController.setUsername(getUsername());
            stage.setTitle("Remove element with key greater than");
            textController.setTextField("Remove element with key greater than");
            stage.setScene(new Scene(root, 500, 361));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
        refreshCollection(client.askCollection());
    }
    @FXML
    private void count_less_than_healthButtonOnAction(){
        try {
            Stage stage = new Stage();
            FXMLLoader text = new FXMLLoader();
            text.setLocation(getClass().getResource("TextField.fxml"));
            Parent root = text.load();
            TextController textController = text.getController();
            textController.setClient(client);
            textController.setContentController(this);
            textController.setUsername(getUsername());
            String s = "Count elements with health less than";
            stage.setTitle("Count elements with health less than");
            textController.setTextField(s);
            stage.setScene(new Scene(root, 500, 361));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
        refreshCollection(client.askCollection());
    }
    @FXML
    private void replace_if_loweButtonOnAction(){
        try {
            Stage stage = new Stage();
            FXMLLoader content = new FXMLLoader();
            content.setLocation(getClass().getResource("Element.fxml"));
            Parent root = content.load();
            ElementController elementController = content.getController();
            elementController.setClient(client);
            elementController.setContentController(this);
            elementController.setUsername(getUsername());
            stage.setTitle("ReplaceElement");
            stage.setScene(new Scene(root, 510, 600));
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
        refreshCollection(client.askCollection());
    }

    @FXML
    private void refreshButtonOnAction(){
        refreshCollection(client.askCollection());
        refreshCanvas();

    }
    @FXML
    private void executeScriptButtonOnAction(){
        try {
            Stage stage = new Stage();
            FXMLLoader text = new FXMLLoader();
            text.setLocation(getClass().getResource("TextField.fxml"));
            Parent root = text.load();
            TextController textController = text.getController();
            textController.setClient(client);
            textController.setContentController(this);
            textController.setUsername(getUsername());
            stage.setTitle("ExecuteScript");
            textController.setTextField("Enter name of file with script");
            stage.setScene(new Scene(root, 500, 361));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
        refreshCollection(client.askCollection());
    }
    @FXML
    private void clearButtonOnAction(){
        client.clear(getUsername());
        refreshCollection(client.askCollection());
    }

    @FXML
    private void closeButtonOnAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        setText(textField.getText());
        stage.close();
    }

    public void setUsername(String username) {
        usernameLabel.setText(username);
    }

    public String getUsername() {
        return usernameLabel.getText();
    }
    public void setText(String string) {
        text = string;
    }

    public String getText() {
        return text;
    }
}

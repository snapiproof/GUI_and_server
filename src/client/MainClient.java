package client;

import database.SpaceMarineCollection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class MainClient extends Application {
    public static final String BUNDLE = "client.bundles.GUI";


    @Override
    public void start(Stage primaryStage) throws Exception {
        Client client = new Client("localhost", 5001);
        FXMLLoader loginWindowLoader = new FXMLLoader();
        loginWindowLoader.setLocation(getClass().getResource("sample.fxml"));
        Parent root = loginWindowLoader.load();
        Controller controller = loginWindowLoader.getController();
        SpaceMarineCollection collection = client.askCollection();
        controller.setCollection(collection);
        controller.setClient(client);
        ObservableResourceFactory resourceFactory = new ObservableResourceFactory();
        resourceFactory.setResources(ResourceBundle.getBundle(BUNDLE));
        controller.setResourceFactory(resourceFactory);
        primaryStage.setTitle("Sign in");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
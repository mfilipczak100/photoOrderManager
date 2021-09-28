package photoOrderManager;

import photoOrderManager.model.Order;
import photoOrderManager.scenes.startScene.StartSceneController;
import photoOrderManager.settings.Settings;
import photoOrderManager.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class App extends Application {

    private Stage stage;
    private Scene scene;
    private Settings settings;
    private Order order;
    private MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage=primaryStage;
        mainController=new MainController();
        settings=loadSettings();

        mainController.setStartSceneController(new StartSceneController(this));

        scene=new Scene(mainController.getStartSceneController().getStartSceneView());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Settings getSettings() {
        return settings;
    }

    public Scene getScene() {
        return scene;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public MainController getMainController() {
        return mainController;
    }

    public Stage getStage() {
        return stage;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    private Settings loadSettings(){
        File file=new File("settings.obj");
        if (file.exists()){

            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois=new ObjectInputStream(fis)){
                    Settings settings=(Settings)ois.readObject();
                    return settings;
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        return new Settings();
    }
}

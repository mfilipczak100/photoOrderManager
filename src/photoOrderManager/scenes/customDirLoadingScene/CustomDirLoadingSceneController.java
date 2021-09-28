package photoOrderManager.scenes.customDirLoadingScene;

import javafx.concurrent.Task;
import photoOrderManager.App;
import photoOrderManager.scenes.formatScene.FormatSceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import photoOrderManager.service.PhotoFinder;

import java.io.File;
import java.util.Optional;

public class CustomDirLoadingSceneController {

    private CustomDirLoadingSceneView customDirLoadingSceneView;
    private App app;
    private ObservableList<String> directoriesList = FXCollections.observableArrayList();

    public CustomDirLoadingSceneController(App app) {
        customDirLoadingSceneView = new CustomDirLoadingSceneView();
        this.app = app;
        customDirLoadingSceneView.getDirectoriesList().setItems(directoriesList);
        initializeStopButton();
        initializeAddDirButton();
        initializeNextButton();
        initializeRemoveDirButton();
    }

    private void initializeRemoveDirButton() {
        Button removeDirButton = customDirLoadingSceneView.getRemoveDirButton();
        removeDirButton.setOnAction(e -> {
            if (!customDirLoadingSceneView.getDirectoriesList().getSelectionModel().isEmpty()) {
                String path= customDirLoadingSceneView.getDirectoriesList().getSelectionModel().getSelectedItem();
                directoriesList.remove(path);
                customDirLoadingSceneView.getDirectoriesList().refresh();
            }else{
                showWarningDialog("Wybierz folder.","Nie wybrano folderu do usunięcia.");
            }
        });
    }

    private void initializeStopButton() {
        Button stopButton = customDirLoadingSceneView.getStopButton();
        stopButton.setOnAction(e -> {
            int result = app.getOrder().abortOrder();
            if (result == 1) {
                app.getMainController().setCustomDirLoadingSceneController(null);
            }
        });
    }

    private void initializeAddDirButton() {
        Button addDirButton = customDirLoadingSceneView.getAddDirButton();
        addDirButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDir = directoryChooser.showDialog(app.getStage());
            if (selectedDir != null) {
                directoriesList.add(selectedDir.getAbsolutePath());
                customDirLoadingSceneView.getDirectoriesList().refresh();
            }
        });
    }

    private void initializeNextButton() {
        Button nextButton = customDirLoadingSceneView.getNextButton();
        nextButton.setOnAction(e -> {
            startSearching();
        });
    }

    private void startSearching() {

        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeight(200);
        alert.setTitle("Informacja");
        alert.setHeaderText("Trwa wyszukiwanie zdjęć. Proszę czekać.");
        alert.getButtonTypes().clear();
        ButtonType cancelButton=new ButtonType("Anuluj");
        alert.getButtonTypes().add(cancelButton);
        ButtonType okButton=new ButtonType("OK");


        Task<Void> task=new Task<Void>() {
            int photoCount=0;

            @Override
            protected Void call() {
                PhotoFinder photoFinder=new PhotoFinder(app);
                int count=photoFinder.findPhotosInAllDirectories(directoriesList);
                photoCount+=count;
                return null;
            }

            @Override
            protected void failed() {
                super.failed();
                alert.setHeaderText("Coś poszło nie tak.");
                alert.setHeight(200);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                if (photoCount>0) {
                    alert.setHeaderText("Znaleziono " + photoCount + " zdjęć.");
                    alert.setHeight(200);
                    alert.getButtonTypes().add(okButton);
                }else {
                    ButtonType okButton=new ButtonType("Ok");
                    alert.setTitle("Ostrzeżenie");
                    alert.setHeaderText("Nie znaleziono zdjęć.");
                    alert.setContentText("Nie można przejść dalej, ponieważ program nie wykrył żadnych zdjęć.");
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.getButtonTypes().clear();
                    alert.getButtonTypes().add(okButton);
                }
            }
        };
        new Thread(task).start();
        Optional<ButtonType> optional=alert.showAndWait();
        if (optional.isPresent()&&optional.get().equals(okButton)){
            changeScene();
        }else if (optional.isPresent()&&optional.get().equals(cancelButton)){
            app.getOrder().getDirectoryList().clear();
        }
    }

    private void changeScene(){
        FormatSceneController formatSceneController=new FormatSceneController(app);
        app.getMainController().setFormatSceneController(formatSceneController);
        app.getMainController().setCustomDirLoadingSceneController(null);
        app.getScene().setRoot(formatSceneController.getFormatScene());
    }

    private void showWarningDialog(String header,String message){
        ButtonType okButton=new ButtonType("Ok");
        Alert alert=new Alert(Alert.AlertType.WARNING,message,okButton);
        alert.setTitle("Ostrzeżenie");
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    public CustomDirLoadingSceneView getCustomDirLoadingSceneView() {
        return customDirLoadingSceneView;
    }

}

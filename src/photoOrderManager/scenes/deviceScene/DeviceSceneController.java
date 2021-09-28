package photoOrderManager.scenes.deviceScene;

import photoOrderManager.App;
import photoOrderManager.scenes.customDirLoadingScene.CustomDirLoadingSceneController;
import photoOrderManager.scenes.formatScene.FormatSceneController;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import photoOrderManager.service.PhotoFinder;

import java.io.File;
import java.util.Optional;

public class DeviceSceneController {

    private App app;
    private DeviceSceneView deviceSceneView;
    private Label infoLabel;
    private Button pendriveButton;
    private Button otherButton;

    public DeviceSceneController(App app){
        this.app=app;
        deviceSceneView=new DeviceSceneView();
        infoLabel=deviceSceneView.getInfoLabel();
        pendriveButton=deviceSceneView.getPendriveButton();
        otherButton=deviceSceneView.getOtherButton();

        pendriveButton.setOnAction(e->{
            boolean pathExists=findPendrive(app.getSettings().getPathToPendrive());
            if (pathExists) {
                startSearching();
            }else{
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ostrzeżenie");
                alert.setHeaderText("Nie znaleziono pendrive");
                alert.setContentText("Aby rozpocząć musisz włożyć pendrive do komputera");
                alert.showAndWait();
            }
        });

        otherButton.setOnAction(e->{
            CustomDirLoadingSceneController customDirLoadingSceneController=new CustomDirLoadingSceneController(app);
            app.getMainController().setCustomDirLoadingSceneController(customDirLoadingSceneController);
            app.getMainController().setDeviceSceneController(null);
            app.getScene().setRoot(customDirLoadingSceneController.getCustomDirLoadingSceneView());
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
                int count=photoFinder.findPhotos(app.getSettings().getPathToPendrive(),true);
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

    private boolean findPendrive(String path){
        File f=new File(path);
        return f.exists();
    }

    public DeviceSceneView getDeviceSceneView() {
        return deviceSceneView;
    }

    private void changeScene(){
        FormatSceneController formatSceneController=new FormatSceneController(app);
        app.getMainController().setFormatSceneController(formatSceneController);
        app.getMainController().setDeviceSceneController(null);
        app.getScene().setRoot(formatSceneController.getFormatScene());
    }
}

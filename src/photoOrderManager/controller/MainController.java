package photoOrderManager.controller;

import javafx.scene.image.ImageView;
import photoOrderManager.model.ImageInformation;
import photoOrderManager.scenes.chooseScene.ChooseSceneController;
import photoOrderManager.scenes.cropScene.CropSceneController;
import photoOrderManager.scenes.customDirLoadingScene.CustomDirLoadingSceneController;
import photoOrderManager.scenes.deviceScene.DeviceSceneController;
import photoOrderManager.scenes.endScene.EndSceneController;
import photoOrderManager.scenes.enlargeScene.EnlargeSceneController;
import photoOrderManager.scenes.formatScene.FormatSceneController;
import photoOrderManager.scenes.loadingScene.LoadingSceneController;
import photoOrderManager.scenes.newOrderScene.NewOrderSceneController;
import photoOrderManager.scenes.paperScene.PaperSceneController;
import photoOrderManager.scenes.settingsScene.SettingsSceneController;
import photoOrderManager.scenes.startScene.StartSceneController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class MainController {

    private StartSceneController startSceneController;
    private NewOrderSceneController newOrderSceneController;
    private DeviceSceneController deviceSceneController;
    private LoadingSceneController loadingSceneController;
    private FormatSceneController formatSceneController;
    private PaperSceneController paperSceneController;
    private CropSceneController cropSceneController;
    private ChooseSceneController chooseSceneController;
    private EndSceneController endSceneController;
    private EnlargeSceneController enlargeSceneController;
    private CustomDirLoadingSceneController customDirLoadingSceneController;
    private SettingsSceneController settingsSceneController;


    public StartSceneController getStartSceneController() {
        return startSceneController;
    }

    public void setStartSceneController(StartSceneController startSceneController) {
        this.startSceneController = startSceneController;
    }

    public NewOrderSceneController getNewOrderSceneController() {
        return newOrderSceneController;
    }

    public void setNewOrderSceneController(NewOrderSceneController newOrderSceneController) {
        this.newOrderSceneController = newOrderSceneController;
    }

    public DeviceSceneController getDeviceSceneController() {
        return deviceSceneController;
    }

    public void setDeviceSceneController(DeviceSceneController deviceSceneController) {
        this.deviceSceneController = deviceSceneController;
    }

    public LoadingSceneController getLoadingSceneController() {
        return loadingSceneController;
    }

    public void setLoadingSceneController(LoadingSceneController loadingSceneController) {
        this.loadingSceneController = loadingSceneController;
    }

    public FormatSceneController getFormatSceneController() {
        return formatSceneController;
    }

    public void setFormatSceneController(FormatSceneController formatSceneController) {
        this.formatSceneController = formatSceneController;
    }

    public PaperSceneController getPaperSceneController() {
        return paperSceneController;
    }

    public void setPaperSceneController(PaperSceneController paperSceneController) {
        this.paperSceneController = paperSceneController;
    }

    public CropSceneController getCropSceneController() {
        return cropSceneController;
    }

    public void setCropSceneController(CropSceneController cropSceneController) {
        this.cropSceneController = cropSceneController;
    }

    public ChooseSceneController getChooseSceneController() {
        return chooseSceneController;
    }

    public void setChooseSceneController(ChooseSceneController chooseSceneController) {
        this.chooseSceneController = chooseSceneController;
    }

    public EndSceneController getEndSceneController() {
        return endSceneController;
    }

    public void setEndSceneController(EndSceneController endSceneController) {
        this.endSceneController = endSceneController;
    }

    public EnlargeSceneController getEnlargeSceneController() {
        return enlargeSceneController;
    }

    public void setEnlargeSceneController(EnlargeSceneController enlargeSceneController) {
        this.enlargeSceneController = enlargeSceneController;
    }

    public CustomDirLoadingSceneController getCustomDirLoadingSceneController() {
        return customDirLoadingSceneController;
    }

    public void setCustomDirLoadingSceneController(CustomDirLoadingSceneController customDirLoadingSceneController) {
        this.customDirLoadingSceneController = customDirLoadingSceneController;
    }

    public SettingsSceneController getSettingsSceneController() {
        return settingsSceneController;
    }

    public void setSettingsSceneController(SettingsSceneController settingsSceneController) {
        this.settingsSceneController = settingsSceneController;
    }

    public boolean showConfirmationWindow(String message){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Potwierdzenie");
        alert.setContentText(message);
        ButtonType yesButton=new ButtonType("Tak");
        ButtonType noButton=new ButtonType("Nie");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(yesButton,noButton);
        Optional<ButtonType> result=alert.showAndWait();
        if (result.isPresent()&&result.get()==yesButton){
            return true;
        }
        return false;
    }

    public void rotateImage(ImageView imageView, ImageInformation imageInformation){
        if (imageInformation!=null){
            switch (imageInformation.getOrientation()) {
                case 1: break;
                case 3:
                    imageView.setRotate(180);
                    break;
                case 6:
                    imageView.setRotate(90);
                    break;
                case 8:
                    imageView.setRotate(270);
                    break;
            }

        }
    }

}

package photoOrderManager.scenes.settingsScene;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import photoOrderManager.App;
import photoOrderManager.scenes.startScene.StartSceneController;
import photoOrderManager.settings.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;

import java.io.*;
import java.math.BigDecimal;
import java.util.Map;

public class SettingsSceneController {

    private App app;
    private SettingsSceneView settingsSceneView;
    private Settings settings;
    private ObservableList<String> formatObservableList;

    public SettingsSceneController(App app) {
        this.app=app;
        settingsSceneView=new SettingsSceneView();
        createSettings();
        initialize();
        updatePendrivePathLabel();
        updateOrderPathLabel();
    }

    private void createSettings(){
        Settings activeSettings=app.getSettings();
        settings=new Settings();
        settings.setPathToPendrive(activeSettings.getPathToPendrive());
        settings.setPathToOrdersDirectory(activeSettings.getPathToOrdersDirectory());
        settings.setFormatToOrder(activeSettings.getFormatToOrder());
    }

    private void updatePendrivePathLabel(){
        Label label=settingsSceneView.getPendrivePathLabel();
        label.setText(settings.getPathToPendrive());
    }

    private void updateOrderPathLabel(){
        Label label=settingsSceneView.getOrderPathlabel();
        label.setText(settings.getPathToOrdersDirectory());
    }

    private void initialize(){
        initializeList();
        initializeChoosePendrivePathButton();
        initializeChooseOrderPathButton();
        initializeExitButton();
        initalizeRemoveFormatButton();
        initializeAddFormatButton();
        initalizeSaveButton();
        initializeDefaultsButton();
    }

    private void saveSettingsToFile(Settings settings){
        try (FileOutputStream fos = new FileOutputStream("settings");
             ObjectOutputStream oos = new ObjectOutputStream(fos)){

            oos.writeObject(settings);
        }catch (IOException e){

        }
    }

    private void initializeDefaultsButton() {
        Button button=settingsSceneView.getDefaultsButton();
        button.setOnAction(e->{
            boolean result=app.getMainController().showConfirmationWindow("Czy napewno chcesz wrócić do ustawień domyślnych?");
            if (result){
                settings=new Settings();
                app.setSettings(settings);
                saveSettingsToFile(settings);
            }
        });
    }

    private void initalizeSaveButton() {
        Button button=settingsSceneView.getSaveButton();
        button.setOnAction(e->{
            boolean result=app.getMainController().showConfirmationWindow("Czy napewno chcesz zapisać zmiany?");
            if (result){
                app.setSettings(settings);
                saveSettingsToFile(settings);
                returnToStartScene();
            }
        });
    }

    private void initializeExitButton() {
        Button button=settingsSceneView.getExitButton();
        button.setOnAction(e->{
            boolean result=app.getMainController().showConfirmationWindow("Czy napewno chcesz wyjść?");
            if (result){
                returnToStartScene();
            }
        });
    }

    private void returnToStartScene(){
        StartSceneController startSceneController=new StartSceneController(app);
        app.getMainController().setStartSceneController(startSceneController);
        app.getMainController().setSettingsSceneController(null);
        app.getScene().setRoot(startSceneController.getStartSceneView());
    }

    private void initalizeRemoveFormatButton(){
        Button button=settingsSceneView.getRemoveFormatButton();
        button.setOnAction(e->{
            ListView<String> listView=settingsSceneView.getFormatList();
            if (!listView.getSelectionModel().isEmpty()){
                int index=listView.getSelectionModel().getSelectedIndex();
                String str=formatObservableList.remove(index);
                String arr[]=str.split("-");
                arr[0]=arr[0].trim();
                settings.getFormatToOrder().remove(arr[0]);
                listView.refresh();
            }
        });
    }

    private void initializeAddFormatButton(){
        Button button=settingsSceneView.getAddFormatButton();
        button.setOnAction(e->{
            try{
                int width=Integer.parseInt(settingsSceneView.getFormatTextField1().getText());
                int height=Integer.parseInt(settingsSceneView.getFormatTextField2().getText());
                int priceA=Integer.parseInt(settingsSceneView.getPriceTextField1().getText());
                int priceB=Integer.parseInt(settingsSceneView.getPriceTextField2().getText());
                if (width<=0||height<=0||priceA<0||priceB<0||priceB>99||(priceA==0&&priceB==0)){
                    throw new IllegalArgumentException();
                }
                String format=width+"x"+height;
                double priceD=Double.parseDouble(priceA+"."+priceB);
                BigDecimal price=BigDecimal.valueOf(priceD);
                String line=format+" - "+price+"zł";
                formatObservableList.add(line);
                settings.getFormatToOrder().put(format,price);
                System.out.println(settings.getFormatToOrder());
            }catch (Exception exception){

            }
        });
    }

    private void initializeList(){
        formatObservableList=FXCollections.observableArrayList();
        Map<String, BigDecimal> formatMap=settings.getFormatToOrder();
        for (String format:formatMap.keySet()){
            BigDecimal price=formatMap.get(format);
            String result=format+" - "+price+"zł";
            formatObservableList.add(result);
        }
        settingsSceneView.getFormatList().setItems(formatObservableList);
    }

    private void initializeChoosePendrivePathButton(){
        Button button=settingsSceneView.getChoosePendrivePathButton();
        button.setOnAction(e->{
            DirectoryChooser dc=new DirectoryChooser();
            File file=dc.showDialog(app.getStage());
            if (file!=null){
                settings.setPathToPendrive(file.getAbsolutePath());
                updatePendrivePathLabel();
            }
        });
    }

    private void initializeChooseOrderPathButton(){
        Button button=settingsSceneView.getChooseOrderPathButton();
        button.setOnAction(e->{
            DirectoryChooser dc=new DirectoryChooser();
            File file=dc.showDialog(app.getStage());
            if (file!=null){
                settings.setPathToOrdersDirectory(file.getAbsolutePath());
                updateOrderPathLabel();
            }
        });
    }

    public SettingsSceneView getSettingsSceneView() {
        return settingsSceneView;
    }
}

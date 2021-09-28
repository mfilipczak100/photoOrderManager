package photoOrderManager.scenes.loadingScene;

import photoOrderManager.App;
import photoOrderManager.model.DirectoryInfo;
import photoOrderManager.model.ImageInformation;
import photoOrderManager.scenes.formatScene.FormatSceneController;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;

public class LoadingSceneController {

    private App app;
    private LoadingSceneView loadingSceneView;
    private Label loadinglabel;
    private Button nextButton;
    private Button stopButton;
    private IntegerProperty photoCountProperty;
    boolean searchFinished=false;

    public LoadingSceneController(App app){
        loadingSceneView=new LoadingSceneView();
        this.app=app;
        loadinglabel=loadingSceneView.getLoadinglabel();
        nextButton=loadingSceneView.getNextButton();
        stopButton=loadingSceneView.getStopButton();
        photoCountProperty=new SimpleIntegerProperty(0);

        StringBinding stringBinding=new StringBinding() {

            {
                super.bind(photoCountProperty);
            }
            @Override
            protected String computeValue() {
                return "Znaleziono "+photoCountProperty.getValue()+" zdjęć.";
            }
        };
        loadinglabel.textProperty().bind(stringBinding);

        nextButton.setOnAction(e->{
            if (!searchFinished){
                findPhotos(app.getSettings().getPathToPendrive());
                searchFinished=true;
                nextButton.setText("Dalej");
            }else{
                FormatSceneController formatSceneController=new FormatSceneController(app);
                app.getMainController().setFormatSceneController(formatSceneController);
                app.getMainController().setLoadingSceneController(null);
                app.getScene().setRoot(formatSceneController.getFormatScene());
            }
        });

        stopButton.setOnAction(e->{
            int result=app.getOrder().abortOrder();
            if (result==1){
                app.getMainController().setLoadingSceneController(null);
            }
        });
    }

    private void findPhotos(String path){

        File f=new File(path);
        File[] tab=f.listFiles();
        DirectoryInfo directoryInfo=new DirectoryInfo(f.getName(),f.getPath());
        if (tab != null) {
            for (int i=0;i<tab.length;i++){
                if (tab[i].isDirectory()){
                    findPhotos(tab[i].getPath());
                }else{
                    String extension=tab[i].getName().substring(tab[i].getName().lastIndexOf("."));
                    if (extension.equalsIgnoreCase(".jpeg")||extension.equalsIgnoreCase(".jpg")){
                        ImageInformation imageInformation=ImageInformation.createImageInformation(path);
                        directoryInfo.getPathToImagesInDirectory().add(imageInformation);
                        photoCountProperty.setValue(photoCountProperty.getValue()+1);
                    }
                }
            }
        }
        if (directoryInfo.getPathToImagesInDirectory().size()>0){
            app.getOrder().getDirectoryList().add(directoryInfo);
        }
    }

    public LoadingSceneView getLoadingSceneView() {
        return loadingSceneView;
    }
}

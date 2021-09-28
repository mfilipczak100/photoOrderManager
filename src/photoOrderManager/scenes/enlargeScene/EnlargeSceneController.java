package photoOrderManager.scenes.enlargeScene;

import photoOrderManager.App;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import photoOrderManager.model.ImageInformation;

public class EnlargeSceneController {

    private EnlargeSceneView enlargeSceneView;
    private App app;

    public EnlargeSceneController(App app) {
        this.app=app;
        this.enlargeSceneView = new EnlargeSceneView();
        initializeReturnButton();
    }

    public void setImage(ImageInformation imageInformation){
        Image img=new Image("file:"+imageInformation.getPathToImage());
        app.getMainController().rotateImage(enlargeSceneView.getImageView(),imageInformation);
        enlargeSceneView.getImageView().setImage(img);
    }

    private void initializeReturnButton(){
        Button returnButton=enlargeSceneView.getReturnButton();
        returnButton.setOnAction(e->{
            app.getMainController().setEnlargeSceneController(null);
            app.getScene().setRoot(app.getMainController().getChooseSceneController().getChooseSceneView());
        });
    }

    public EnlargeSceneView getEnlargeSceneView() {
        return enlargeSceneView;
    }
}

package photoOrderManager.scenes.paperScene;

import photoOrderManager.App;
import photoOrderManager.model.OrderItem;
import photoOrderManager.scenes.cropScene.CropSceneController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PaperSceneController {

    private App app;
    private PaperSceneView paperSceneView;
    private Label infoLabel;
    private Button glossButton;
    private Button matButton;

    public PaperSceneController(App app, OrderItem orderItem){

        this.app=app;
        paperSceneView=new PaperSceneView();

        infoLabel=paperSceneView.getInfoLabel();
        glossButton=paperSceneView.getGlossButton();
        matButton=paperSceneView.getMatButton();

        glossButton.setOnAction(e->{
            orderItem.setPaper("B");
            changeScene(orderItem);
        });

        matButton.setOnAction(e->{
            orderItem.setPaper("M");
            changeScene(orderItem);
        });
    }

    private void changeScene(OrderItem orderItem){
        CropSceneController cropSceneController=new CropSceneController(app,orderItem);
        app.getMainController().setCropSceneController(cropSceneController);
        app.getMainController().setPaperSceneController(null);
        app.getScene().setRoot(cropSceneController.getCropSceneView());
    }

    public PaperSceneView getPaperSceneView() {
        return paperSceneView;
    }
}

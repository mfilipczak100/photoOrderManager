package photoOrderManager.scenes.endScene;

import photoOrderManager.App;
import photoOrderManager.scenes.newOrderScene.NewOrderSceneController;
import javafx.scene.control.Button;

public class EndSceneController {

    private App app;
    private EndSceneView endSceneView;

    public EndSceneController(App app) {
        this.app=app;
        endSceneView=new EndSceneView();
        initializeEndButton();
    }

    private void initializeEndButton(){
        Button endButton=endSceneView.getEndButton();
        endButton.setOnAction(e->{
            app.getMainController().setEndSceneController(null);
            NewOrderSceneController newOrderSceneController=new NewOrderSceneController(app);
            app.getMainController().setNewOrderSceneController(newOrderSceneController);
            app.getScene().setRoot(newOrderSceneController.getNewOrderSceneView());
        });
    }

    public EndSceneView getEndSceneView() {
        return endSceneView;
    }
}

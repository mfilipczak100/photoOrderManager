package photoOrderManager.scenes.startScene;

import photoOrderManager.App;
import photoOrderManager.scenes.newOrderScene.NewOrderSceneController;
import photoOrderManager.scenes.settingsScene.SettingsSceneController;

public class StartSceneController {

    private App app;
    private StartSceneView startSceneView;

    public StartSceneController(App app) {
        this.app = app;
        startSceneView=new StartSceneView();

        startSceneView.getStartButton().setOnAction(e->{
            NewOrderSceneController newOrderSceneController=new NewOrderSceneController(app);
            app.getMainController().setNewOrderSceneController(newOrderSceneController);
            app.getMainController().setStartSceneController(null);
            app.getScene().setRoot(newOrderSceneController.getNewOrderSceneView());
        });

        startSceneView.getOptionsButton().setOnAction(e->{
            SettingsSceneController settingsSceneController=new SettingsSceneController(app);
            app.getMainController().setSettingsSceneController(settingsSceneController);
            app.getMainController().setStartSceneController(null);
            app.getScene().setRoot(settingsSceneController.getSettingsSceneView());
        });
    }

    public StartSceneView getStartSceneView() {
        return startSceneView;
    }
}

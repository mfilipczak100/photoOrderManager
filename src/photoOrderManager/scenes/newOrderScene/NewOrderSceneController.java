package photoOrderManager.scenes.newOrderScene;

import photoOrderManager.App;
import photoOrderManager.model.Order;
import photoOrderManager.scenes.deviceScene.DeviceSceneController;

public class NewOrderSceneController {
    private App app;
    private NewOrderSceneView newOrderSceneView;

    public NewOrderSceneController(App app) {
        this.app = app;
        newOrderSceneView=new NewOrderSceneView();

        newOrderSceneView.getNewOrderButton().setOnAction(e->{
            DeviceSceneController deviceSceneController=new DeviceSceneController(app);
            app.getMainController().setDeviceSceneController(deviceSceneController);
            app.getMainController().setNewOrderSceneController(null);
            app.setOrder(new Order(app));
            app.getScene().setRoot(deviceSceneController.getDeviceSceneView());
        });
    }

    public NewOrderSceneView getNewOrderSceneView() {
        return newOrderSceneView;
    }
}

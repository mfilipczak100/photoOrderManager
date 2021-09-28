package photoOrderManager.scenes.cropScene;

import photoOrderManager.App;
import photoOrderManager.model.OrderItem;
import photoOrderManager.scenes.chooseScene.ChooseSceneController;
import photoOrderManager.scenes.chooseScene.ChooseSceneView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CropSceneController {

    private App app;
    private CropSceneView cropSceneView;
    private Label infoLabel;
    private Button fillButton;
    private Button fitButton;

    public CropSceneController(App app,  OrderItem orderItem){
        this.app=app;
        cropSceneView=new CropSceneView();
        infoLabel=cropSceneView.getInfoLabel();
        fillButton=cropSceneView.getFillButton();
        fitButton=cropSceneView.getFitButton();

        fillButton.setOnAction(e->{
            orderItem.setCrop("Fill");
            addOrderItemToOrder(orderItem);
            changeScene();
        });

        fitButton.setOnAction(e->{
            orderItem.setCrop("Fit");
            addOrderItemToOrder(orderItem);
            changeScene();
        });
    }

    private void addOrderItemToOrder(OrderItem orderItem){
        if (!app.getOrder().getOrderItemObservableList().contains(orderItem)){
            app.getOrder().getOrderItemObservableList().add(orderItem);
        }
    }

    private void changeScene(){
        ChooseSceneController chooseSceneController=new ChooseSceneController(app);
        app.getMainController().setChooseSceneController(chooseSceneController);
        updateChooseScene(chooseSceneController);
        app.getScene().setRoot(chooseSceneController.getChooseSceneView());
    }

    private void updateChooseScene(ChooseSceneController chooseSceneController){
        ChooseSceneView chooseSceneView=chooseSceneController.getChooseSceneView();
        int selectedIndex=app.getOrder().getDirectoryListSelectedIndex();
        if (selectedIndex==-1){
            chooseSceneView.getDirectoryList().getSelectionModel().selectFirst();
            app.getOrder().setDirectoryListSelectedIndex(0);
        }else{
            chooseSceneView.getDirectoryList().getSelectionModel().select(selectedIndex);
        }
        chooseSceneView.getFormatList().getSelectionModel().selectLast();
        chooseSceneController.updatePanels();
        chooseSceneController.updateTopLabel();
    }

    public CropSceneView getCropSceneView() {
        return cropSceneView;
    }
}

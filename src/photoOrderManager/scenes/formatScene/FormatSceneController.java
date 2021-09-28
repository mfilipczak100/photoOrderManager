package photoOrderManager.scenes.formatScene;

import photoOrderManager.App;
import photoOrderManager.model.OrderItem;
import photoOrderManager.scenes.paperScene.PaperSceneController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class FormatSceneController {
    private FormatSceneView formatScene;
    private App app;
    private Label formatLabel;
    private ArrayList<Button> buttonList;
    private Button backButton;

    public FormatSceneController(App app){
        formatScene=new FormatSceneView(app);
        this.app=app;
        formatLabel=formatScene.getFormatLabel();
        buttonList=formatScene.getButtonList();
        backButton=formatScene.getBackButton();

        backButton.setOnAction(e->{
            int result=app.getOrder().abortOrder();
            if (result==1){
                app.getMainController().setFormatSceneController(null);
            }
        });

        for (int i=0;i<buttonList.size();i++){

            int index=i;
            buttonList.get(i).setOnAction(e->{
                OrderItem orderItem=new OrderItem();
                orderItem.setFormat(buttonList.get(index).getText());
                changeScene(orderItem);
            });
        }
    }

    private void changeScene(OrderItem orderItem){
        PaperSceneController paperSceneController=new PaperSceneController(app,orderItem);
        app.getMainController().setPaperSceneController(paperSceneController);
        app.getMainController().setFormatSceneController(null);
        app.getScene().setRoot(paperSceneController.getPaperSceneView());
    }

    public FormatSceneView getFormatScene() {
        return formatScene;
    }
}

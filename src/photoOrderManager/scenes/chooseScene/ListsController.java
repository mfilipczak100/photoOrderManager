package photoOrderManager.scenes.chooseScene;

import photoOrderManager.model.DirectoryInfo;
import photoOrderManager.model.OrderItem;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ListView;

public class ListsController {

    private ChooseSceneController chooseSceneController;

    public ListsController(ChooseSceneController chooseSceneController) {
        this.chooseSceneController = chooseSceneController;
        initializeDirectoryList();
        initializeFormatList();
    }

    private void initializeDirectoryList(){

        ListView<DirectoryInfo> directoryList=chooseSceneController.getChooseSceneView().getDirectoryList();
        directoryList.setItems(chooseSceneController.getApp().getOrder().getDirectoryList());
        ChangeListener<DirectoryInfo> change= ((observable, oldValue, newValue) ->{
            ListView<OrderItem> formatList=chooseSceneController.getChooseSceneView().getFormatList();
            if (!directoryList.getSelectionModel().isEmpty()&&!formatList.getSelectionModel().isEmpty()){
                if (!chooseSceneController.getChooseSceneView().getPlus1Button().isVisible()) {
                    chooseSceneController.getButtonsController().showBottomButtons();
                }
            }else{
                if (chooseSceneController.getChooseSceneView().getPlus1Button().isVisible()){
                    chooseSceneController.getButtonsController().hideBottomButtons();
                }
            }
            chooseSceneController.getApp().getOrder().setDirectoryListSelectedIndex(
                    directoryList.getSelectionModel().getSelectedIndex()
            );
            chooseSceneController.updatePanels();
            chooseSceneController.updateTopLabel();
        });
        directoryList.getSelectionModel().selectedItemProperty().addListener(change);
    }

    private void initializeFormatList(){
        ListView<OrderItem> formatList=chooseSceneController.getChooseSceneView().getFormatList();
        formatList.setItems(chooseSceneController.getApp().getOrder().getOrderItemObservableList());
        ChangeListener<OrderItem> change= ((observable, oldValue, newValue) ->{
            ListView<DirectoryInfo> directoryList=chooseSceneController.getChooseSceneView().getDirectoryList();
            if (!directoryList.getSelectionModel().isEmpty()&&!formatList.getSelectionModel().isEmpty()){
                if (!chooseSceneController.getChooseSceneView().getPlus1Button().isVisible()) {
                    chooseSceneController.getButtonsController().showBottomButtons();
                }
            }else{
                if (chooseSceneController.getChooseSceneView().getPlus1Button().isVisible()){
                    chooseSceneController.getButtonsController().hideBottomButtons();
                }
            }
            chooseSceneController.updatePanels();
            chooseSceneController.updateTopLabel();
        });
        formatList.getSelectionModel().selectedItemProperty().addListener(change);
    }
}

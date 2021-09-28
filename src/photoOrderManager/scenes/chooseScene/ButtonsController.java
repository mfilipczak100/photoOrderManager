package photoOrderManager.scenes.chooseScene;

import photoOrderManager.model.DirectoryInfo;
import photoOrderManager.model.ImageInformation;
import photoOrderManager.model.OrderItem;
import photoOrderManager.scenes.chooseScene.photoPanel.PhotoPanelController;
import photoOrderManager.scenes.formatScene.FormatSceneController;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.controlsfx.dialog.ProgressDialog;

import java.util.Map;

public class ButtonsController {

    private ChooseSceneController chooseSceneController;

    public ButtonsController(ChooseSceneController chooseSceneController) {
        this.chooseSceneController = chooseSceneController;
        initializeAddFormatButton();
        initializeRemoveFormatButton();
        hideBottomButtons();
        initializeNextPageButton();
        initializePreviousPageButton();
        initializePlus1Button();
        initializeMinus1Button();
        initializeAbortButton();
        initializeOrderButton();
        initializeFirstPageButton();
        initializeLastPageButton();
        initializePlus1AllButton();
        initializeMinus1AllButton();
    }

    private void initializeAbortButton(){
        Button abortButton=chooseSceneController.getChooseSceneView().getAbortButton();
        abortButton.setOnAction(e->{
            int result=chooseSceneController.getApp().getOrder().abortOrder();
            if (result==1){
                chooseSceneController.getApp().getMainController().setChooseSceneController(null);
            }
        });
    }

    private void initializeAddFormatButton(){
        Button addFormatButton=chooseSceneController.getChooseSceneView().getAddNewFormat();
        addFormatButton.setOnAction(e->{
            FormatSceneController formatSceneController=new FormatSceneController(chooseSceneController.getApp());
            chooseSceneController.getApp().getMainController().setFormatSceneController(formatSceneController);
            chooseSceneController.getApp().getScene().setRoot(formatSceneController.getFormatScene());
        });
    }

    private void initializeRemoveFormatButton(){
        Button removeFormatButton=chooseSceneController.getChooseSceneView().getRemoveFormat();
        removeFormatButton.setOnAction(e->{
            ListView<OrderItem> formatList=chooseSceneController.getChooseSceneView().getFormatList();
            if (!formatList.getSelectionModel().isEmpty()) {
                OrderItem orderItem=chooseSceneController.getChooseSceneView().getFormatList().getSelectionModel().getSelectedItem();
                chooseSceneController.getApp().getOrder().removeOrderItem(orderItem);
                chooseSceneController.getChooseSceneView().getFormatList().getSelectionModel().clearSelection();
                chooseSceneController.getChooseSceneView().getFormatList().getSelectionModel().selectLast();
            }
        });
    }

    void hideBottomButtons(){
        Button minus1Button=chooseSceneController.getChooseSceneView().getMinus1Button();
        Button plus1Button=chooseSceneController.getChooseSceneView().getPlus1Button();
        Button minus1AllButton=chooseSceneController.getChooseSceneView().getMinus1AllButton();
        Button plus1AllButton=chooseSceneController.getChooseSceneView().getPlus1AllButton();
        minus1Button.setVisible(false);
        plus1Button.setVisible(false);
        minus1AllButton.setVisible(false);
        plus1AllButton.setVisible(false);
    }

    void showBottomButtons(){
        Button minus1Button=chooseSceneController.getChooseSceneView().getMinus1Button();
        Button plus1Button=chooseSceneController.getChooseSceneView().getPlus1Button();
        Button minus1AllButton=chooseSceneController.getChooseSceneView().getMinus1AllButton();
        Button plus1AllButton=chooseSceneController.getChooseSceneView().getPlus1AllButton();
        minus1Button.setVisible(true);
        plus1Button.setVisible(true);
        minus1AllButton.setVisible(true);
        plus1AllButton.setVisible(true);
    }

    private void initializeNextPageButton(){
        Button nextPageButton=chooseSceneController.getChooseSceneView().getNextPageButton();
        nextPageButton.setOnAction(e->{
            DirectoryInfo directoryInfo=chooseSceneController.getChooseSceneView().getDirectoryList().getSelectionModel().getSelectedItem();
            if (directoryInfo.getIndex()+chooseSceneController.getApp().getSettings().getNumberOfPanels()<directoryInfo.getPathToImagesInDirectory().size()){
                directoryInfo.setIndex(directoryInfo.getIndex()+chooseSceneController.getApp().getSettings().getNumberOfPanels());
                chooseSceneController.updatePanels();
            }
        });
    }

    private void initializePreviousPageButton(){
        Button previousPageButton=chooseSceneController.getChooseSceneView().getPreviousPageButton();
        previousPageButton.setOnAction(e->{
            DirectoryInfo directoryInfo=chooseSceneController.getChooseSceneView().getDirectoryList().getSelectionModel().getSelectedItem();
            if (directoryInfo.getIndex()-chooseSceneController.getApp().getSettings().getNumberOfPanels()>=0){
                directoryInfo.setIndex(directoryInfo.getIndex()-chooseSceneController.getApp().getSettings().getNumberOfPanels());
                chooseSceneController.updatePanels();
            }
        });
    }

    private void initializeFirstPageButton(){
        Button firstPageButton=chooseSceneController.getChooseSceneView().getFirstPageButton();
        firstPageButton.setOnAction(e->{
            DirectoryInfo directoryInfo=chooseSceneController.getChooseSceneView().getDirectoryList().getSelectionModel().getSelectedItem();
            if (directoryInfo.getIndex()!=0){
                directoryInfo.setIndex(0);
                chooseSceneController.updatePanels();
            }
        });
    }

    private void initializeLastPageButton(){
        Button lastPageButton=chooseSceneController.getChooseSceneView().getLastPageButton();
        lastPageButton.setOnAction(e->{
            DirectoryInfo directoryInfo=chooseSceneController.getChooseSceneView().getDirectoryList().getSelectionModel().getSelectedItem();
            int modulo=(directoryInfo.getPathToImagesInDirectory().size()-1)%chooseSceneController.getApp().getSettings().getNumberOfPanels();
            int newIndex=(directoryInfo.getPathToImagesInDirectory().size()-1)-modulo;
            if (directoryInfo.getIndex()!=newIndex){
                directoryInfo.setIndex(newIndex);
                chooseSceneController.updatePanels();
            }
        });
    }

    private void initializePlus1Button(){
        Button plus1Button=chooseSceneController.getChooseSceneView().getPlus1Button();
        plus1Button.setOnAction(e->{
            for(PhotoPanelController photoPanel:chooseSceneController.getPhotoPanels()){
                photoPanel.addPhotoToOrder();
            }
        });
    }

    private void initializeMinus1Button(){
        Button minus1Button=chooseSceneController.getChooseSceneView().getMinus1Button();
        minus1Button.setOnAction(e->{
            for(PhotoPanelController photoPanel:chooseSceneController.getPhotoPanels()){
                photoPanel.removePhotoFromOrder();
            }
        });
    }

    private void initializePlus1AllButton(){
        Button plus1AllButton=chooseSceneController.getChooseSceneView().getPlus1AllButton();
        plus1AllButton.setOnAction(e->{
            DirectoryInfo directoryInfo = chooseSceneController.getChooseSceneView().getDirectoryList().getSelectionModel().getSelectedItem();
            int index = chooseSceneController.getChooseSceneView().getFormatList().getSelectionModel().getSelectedIndex();
            Map<String,Integer> map=chooseSceneController.getApp().getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap();
            for (ImageInformation image:directoryInfo.getPathToImagesInDirectory()){
                if (map.containsKey(image.getPathToImage())){
                    map.put(image.getPathToImage(),map.get(image.getPathToImage())+1);
                }else {
                    map.put(image.getPathToImage(),1);
                }
                String format=chooseSceneController.getChooseSceneView().getFormatList().getSelectionModel().getSelectedItem().getFormat();
                chooseSceneController.getApp().getOrder().addPhotoToOrderAndCalculatePrice(format);
                chooseSceneController.getChooseSceneView().getFormatList().getSelectionModel().getSelectedItem().setPhotosPickedCount(
                        chooseSceneController.getChooseSceneView().getFormatList().getSelectionModel().getSelectedItem().getPhotosPickedCount()+1
                );
            }
            chooseSceneController.getChooseSceneView().getFormatList().refresh();
            for (PhotoPanelController photoPanel:chooseSceneController.getPhotoPanels()){
                photoPanel.updatePhotoCount();
            }
            chooseSceneController.updateTopLabel();
        });
    }

    private void initializeMinus1AllButton(){
        Button minus1AllButton=chooseSceneController.getChooseSceneView().getMinus1AllButton();
        minus1AllButton.setOnAction(e->{
            DirectoryInfo directoryInfo = chooseSceneController.getChooseSceneView().getDirectoryList().getSelectionModel().getSelectedItem();
            int index = chooseSceneController.getChooseSceneView().getFormatList().getSelectionModel().getSelectedIndex();
            Map<String,Integer> map=chooseSceneController.getApp().getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap();
            for (ImageInformation image:directoryInfo.getPathToImagesInDirectory()){
                if (map.containsKey(image.getPathToImage())){
                    int count=map.get(image.getPathToImage());
                    if (count>1){
                        map.put(image.getPathToImage(),count-1);
                    }else {
                        map.remove(image.getPathToImage());
                    }
                    String format=chooseSceneController.getChooseSceneView().getFormatList().getSelectionModel().getSelectedItem().getFormat();
                    chooseSceneController.getApp().getOrder().removePhotoFromOrderAndCalculatePrice(format);
                    chooseSceneController.getChooseSceneView().getFormatList().getSelectionModel().getSelectedItem().setPhotosPickedCount(
                            chooseSceneController.getChooseSceneView().getFormatList().getSelectionModel().getSelectedItem().getPhotosPickedCount()-1
                    );
                }
            }
            chooseSceneController.getChooseSceneView().getFormatList().refresh();
            for (PhotoPanelController photoPanel:chooseSceneController.getPhotoPanels()){
                photoPanel.updatePhotoCount();
            }
            chooseSceneController.updateTopLabel();
        });
    }

    private void initializeOrderButton(){
        Button orderButton=chooseSceneController.getChooseSceneView().getOrderButton();
        orderButton.setOnAction(e->{
            boolean confirmed=chooseSceneController.getApp().getMainController().showConfirmationWindow("Czy napewno chcesz złożyć zamówienie?");
            if (confirmed) {
                Task<Void> task = chooseSceneController.getApp().getOrder().completeOrder();
                ProgressDialog progressDialog = new ProgressDialog(task);
                progressDialog.setHeaderText("Trwa wczytywanie zdjęć. Proszę czekać.");
                new Thread(task).start();
            }
        });
    }
}

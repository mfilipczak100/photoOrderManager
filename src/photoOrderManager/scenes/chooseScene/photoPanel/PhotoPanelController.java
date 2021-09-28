package photoOrderManager.scenes.chooseScene.photoPanel;

import photoOrderManager.model.DirectoryInfo;
import photoOrderManager.model.ImageInformation;
import photoOrderManager.scenes.chooseScene.ChooseSceneController;
import photoOrderManager.scenes.chooseScene.ChooseSceneView;
import photoOrderManager.scenes.enlargeScene.EnlargeSceneController;
import javafx.scene.control.Button;

import java.util.Map;

public class PhotoPanelController {

    private PhotoPanelView photoPanelView;
    private final int id;
    private ChooseSceneController chooseSceneController;
    private ChooseSceneView chooseSceneView;

    public PhotoPanelController(int id,ChooseSceneController chooseSceneController) {
        photoPanelView = new PhotoPanelView();
        this.id=id;
        this.chooseSceneController=chooseSceneController;
        chooseSceneView=chooseSceneController.getChooseSceneView();
        initializePlusButton();
        initializeMinusButton();
        initializeEnlargeButton();
    }

    private boolean verifyIfAddingOrRemovingIsPossible(){
        if (!chooseSceneView.getDirectoryList().getSelectionModel().isEmpty()&&!chooseSceneView.getFormatList().getSelectionModel().isEmpty()){
            DirectoryInfo directoryInfo = chooseSceneView.getDirectoryList().getSelectionModel().getSelectedItem();
            if (directoryInfo.getIndex() + id < directoryInfo.getPathToImagesInDirectory().size()) {
                return true;
            }
        }
        return false;
    }

    public void addPhotoToOrder(){
        boolean verified=verifyIfAddingOrRemovingIsPossible();
        if (verified){
            DirectoryInfo directoryInfo = chooseSceneView.getDirectoryList().getSelectionModel().getSelectedItem();
            int index = chooseSceneView.getFormatList().getSelectionModel().getSelectedIndex();
            String path = directoryInfo.getPathToImagesInDirectory().get(directoryInfo.getIndex() + id).getPathToImage();
            if (chooseSceneController.getApp().getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap().containsKey(path)) {
                chooseSceneController.getApp().getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap().put(path, chooseSceneController.getApp().getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap().get(path) + 1);
                photoPanelView.getCountLabel().setText(chooseSceneController.getApp().getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap().get(path).toString());
            } else {
                chooseSceneController.getApp().getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap().put(path, 1);
                photoPanelView.getCountLabel().setText("1");
            }
            updatePhotoCountAndPriceInOrder(true);
            chooseSceneController.updateTopLabel();
        }
    }

    public void removePhotoFromOrder(){
        boolean verified=verifyIfAddingOrRemovingIsPossible();
        if (verified){
            int index = chooseSceneView.getFormatList().getSelectionModel().getSelectedIndex();
            DirectoryInfo directoryInfo = chooseSceneView.getDirectoryList().getSelectionModel().getSelectedItem();
            String path = directoryInfo.getPathToImagesInDirectory().get(directoryInfo.getIndex() + id).getPathToImage();
            if (chooseSceneController.getApp().getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap().containsKey(path)){
                int pickedPhotosCount=chooseSceneController.getApp().getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap().get(path);
                if (pickedPhotosCount>1){
                    chooseSceneController.getApp().getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap().put(path,pickedPhotosCount-1);
                    photoPanelView.getCountLabel().setText(Integer.toString(pickedPhotosCount-1));
                }else{
                    chooseSceneController.getApp().getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap().remove(path);
                    photoPanelView.getCountLabel().setText("0");
                }
                updatePhotoCountAndPriceInOrder(false);
                chooseSceneController.updateTopLabel();
            }
        }
    }

    private void updatePhotoCountAndPriceInOrder(boolean add){
        if(add){
            chooseSceneView.getFormatList().getSelectionModel().getSelectedItem().setPhotosPickedCount(
                    chooseSceneView.getFormatList().getSelectionModel().getSelectedItem().getPhotosPickedCount()+1
            );
            chooseSceneController.getApp().getOrder().addPhotoToOrderAndCalculatePrice(
                    chooseSceneView.getFormatList().getSelectionModel().getSelectedItem().getFormat()
            );
        }else {
            chooseSceneView.getFormatList().getSelectionModel().getSelectedItem().setPhotosPickedCount(
                    chooseSceneView.getFormatList().getSelectionModel().getSelectedItem().getPhotosPickedCount() - 1
            );
            chooseSceneController.getApp().getOrder().removePhotoFromOrderAndCalculatePrice(
                    chooseSceneView.getFormatList().getSelectionModel().getSelectedItem().getFormat()
            );
        }
        chooseSceneView.getFormatList().refresh();
    }

    private void initializePlusButton(){
        Button plusButton=photoPanelView.getPlusButton();
        plusButton.setOnAction(e->{
            addPhotoToOrder();
        });
    }

    private void initializeMinusButton(){
        Button minusButton=photoPanelView.getMinusButton();
        minusButton.setOnAction(e->{
            removePhotoFromOrder();
        });
    }

    private void initializeEnlargeButton(){
        Button enlargeButton=photoPanelView.getEnlargeButton();
        enlargeButton.setOnAction(e->{
            EnlargeSceneController enlargeSceneController=new EnlargeSceneController(chooseSceneController.getApp());
            chooseSceneController.getApp().getMainController().setEnlargeSceneController(enlargeSceneController);
            DirectoryInfo directoryInfo = chooseSceneView.getDirectoryList().getSelectionModel().getSelectedItem();
            ImageInformation imageInformation = directoryInfo.getPathToImagesInDirectory().get(directoryInfo.getIndex() + id);
            enlargeSceneController.setImage(imageInformation);
            chooseSceneController.getApp().getScene().setRoot(enlargeSceneController.getEnlargeSceneView());
        });
    }

    void setTextOnImageLabel(String fileName){
        photoPanelView.getImageLabel().setText(fileName);
    }

    public PhotoPanelView getPhotoPanelView() {
        return photoPanelView;
    }

    public int getId() {
        return id;
    }

    public void hidePhotoPanel(){
        photoPanelView.getCountLabel().setVisible(false);
        photoPanelView.getImageLabel().setVisible(false);
        photoPanelView.getImageView().setVisible(false);
        photoPanelView.getMinusButton().setVisible(false);
        photoPanelView.getPlusButton().setVisible(false);
        photoPanelView.getEnlargeButton().setVisible(false);
        photoPanelView.setStyle("-fx-border-color: none");
    }

    public void showPhotoPanel(){
        photoPanelView.getCountLabel().setVisible(true);
        photoPanelView.getImageLabel().setVisible(true);
        photoPanelView.getImageView().setVisible(true);
        photoPanelView.getMinusButton().setVisible(true);
        photoPanelView.getPlusButton().setVisible(true);
        photoPanelView.getEnlargeButton().setVisible(true);
        photoPanelView.setStyle("-fx-background-color: whitesmoke;-fx-background-radius: 50;-fx-effect: innershadow( gaussian, rgba(0, 0, 0, 0.3), 20, 0, 0, 0)");
    }

    public void updatePhotoCount(){
        DirectoryInfo directoryInfo = chooseSceneController.getChooseSceneView().getDirectoryList().getSelectionModel().getSelectedItem();
        if (directoryInfo.getIndex()+id<directoryInfo.getPathToImagesInDirectory().size()) {
            int index = chooseSceneController.getChooseSceneView().getFormatList().getSelectionModel().getSelectedIndex();
            Map<String, Integer> map = chooseSceneController.getApp().getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap();
            String path = directoryInfo.getPathToImagesInDirectory().get(directoryInfo.getIndex() + id).getPathToImage();
            if (map.containsKey(path)) {
                photoPanelView.getCountLabel().setText(map.get(path).toString());
            } else {
                photoPanelView.getCountLabel().setText("0");
            }
        }
    }

}

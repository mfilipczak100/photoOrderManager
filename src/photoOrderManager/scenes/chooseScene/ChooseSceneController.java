package photoOrderManager.scenes.chooseScene;

import photoOrderManager.App;
import photoOrderManager.model.DirectoryInfo;
import photoOrderManager.model.ImageInformation;
import photoOrderManager.model.OrderItem;
import photoOrderManager.scenes.chooseScene.photoPanel.PhotoPanelController;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class ChooseSceneController {

    private App app;
    private ChooseSceneView chooseSceneView;
    private ListsController listsController;
    private ButtonsController buttonsController;
    private ArrayList<PhotoPanelController> photoPanels;
    Thread thread;

    public ChooseSceneController(App app) {
        this.app = app;
        chooseSceneView = new ChooseSceneView();
        createPhotoPanels();
        chooseSceneView.createCenter(photoPanels,app.getSettings().getNumberOfPanels());
        listsController = new ListsController(this);
        buttonsController = new ButtonsController(this);
        updateTopLabel();
    }

    private void createPhotoPanels(){
        photoPanels=new ArrayList<>();
        for (int i=0;i<app.getSettings().getNumberOfPanels();i++){
            PhotoPanelController photoPanelController=new PhotoPanelController(i,this);
            photoPanels.add(photoPanelController);
        }
    }

    public void updateTopLabel() {
        Label label = chooseSceneView.getInfoLabel();
        ListView<DirectoryInfo> directoryInfoListView = chooseSceneView.getDirectoryList();
        ListView<OrderItem> orderItemListView = chooseSceneView.getFormatList();
        if (!directoryInfoListView.getSelectionModel().isEmpty() && !orderItemListView.getSelectionModel().isEmpty()) {
            String formattedPrice = String.format("%.2f", app.getOrder().getPrice().doubleValue());
            label.setText("Liczba odbitek: " + app.getOrder().getTotalPickedPhotos() + "\tCena: " + formattedPrice + " zł");
        } else if (directoryInfoListView.getSelectionModel().isEmpty() && !orderItemListView.getSelectionModel().isEmpty()) {
            label.setText("Wybierz folder ze zdjęciami.");
        } else if (!directoryInfoListView.getSelectionModel().isEmpty() && orderItemListView.getSelectionModel().isEmpty()) {
            label.setText("Wybierz format zdjęć.");
        } else {
            label.setText("Wybierz folder ze zdjęciami i format.");
        }
    }

    private void setImageNameOnPhotoPanelLabel(PhotoPanelController photoPanel, String path) {
        if (path != null) {
            int index = path.lastIndexOf("\\");
            String fileName = path.substring(index + 1);
            photoPanel.getPhotoPanelView().getImageLabel().setText(fileName);
        } else {
            photoPanel.getPhotoPanelView().getImageLabel().setText(path);
        }
    }

    private void setImageOnPhotoPanel(PhotoPanelController photoPanel, ImageInformation imageInformation) {
        if (imageInformation != null) {
            Image img = new Image("file:" + imageInformation.getPathToImage(), 200, 0, true, false);

            photoPanel.getPhotoPanelView().getImageView().setRotate(0);
            photoPanel.getPhotoPanelView().getImageView().setImage(img);
            app.getMainController().rotateImage(photoPanel.getPhotoPanelView().getImageView(), imageInformation);

        } else {
            photoPanel.getPhotoPanelView().getImageView().setImage(null);
        }
    }

    private void updatePhotoPanel(PhotoPanelController photoPanel) {
        DirectoryInfo directoryInfo = chooseSceneView.getDirectoryList().getSelectionModel().getSelectedItem();
        int index = directoryInfo.getIndex() + photoPanel.getId();
        if (index < directoryInfo.getPathToImagesInDirectory().size()) {
            if (!photoPanel.getPhotoPanelView().getPlusButton().isVisible()){
                photoPanel.showPhotoPanel();
            }
            setImageNameOnPhotoPanelLabel(photoPanel, directoryInfo.getPathToImagesInDirectory().get(index).getPathToImage());
            updateCountLabel(photoPanel, directoryInfo.getPathToImagesInDirectory().get(index).getPathToImage());
        } else {
            setImageNameOnPhotoPanelLabel(photoPanel, null);
            photoPanel.getPhotoPanelView().getCountLabel().setText(null);
            photoPanel.hidePhotoPanel();
        }
        setImageOnPhotoPanel(photoPanel,null);
    }


    private void updatePageLabel() {
        if (!chooseSceneView.getDirectoryList().getSelectionModel().isEmpty() && !chooseSceneView.getFormatList().getSelectionModel().isEmpty()) {
            DirectoryInfo directoryInfo = chooseSceneView.getDirectoryList().getSelectionModel().getSelectedItem();
            double totalPagesDouble = ((double)directoryInfo.getPathToImagesInDirectory().size() / app.getSettings().getNumberOfPanels());
            int totalPages=(int)(Math.ceil(totalPagesDouble));
            int currentPage = (directoryInfo.getIndex() / app.getSettings().getNumberOfPanels()) + 1;
            chooseSceneView.getPageLabel().setText(currentPage + "/" + totalPages);
        } else {
            chooseSceneView.getPageLabel().setText(null);
        }
    }

    private void updateCountLabel(PhotoPanelController photoPanel, String path) {
        int index = chooseSceneView.getFormatList().getSelectionModel().getSelectedIndex();
        if (app.getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap().containsKey(path)) {
            photoPanel.getPhotoPanelView().getCountLabel().setText(Integer.toString(app.getOrder().getOrderItemObservableList().get(index).getPickedPhotosMap().get(path)));
        } else {
            photoPanel.getPhotoPanelView().getCountLabel().setText("0");
        }
    }

    public void updatePanels() {
        if (thread!=null&&thread.isAlive()){
            thread.interrupt();
        }
        if (!chooseSceneView.getFormatList().getSelectionModel().isEmpty()&&!chooseSceneView.getDirectoryList().getSelectionModel().isEmpty()) {
            if (!chooseSceneView.getPreviousPageButton().isVisible()) {
                showPageButtonsAndLabel();
            }
            for (PhotoPanelController photoPanel : photoPanels) {
                updatePhotoPanel(photoPanel);
            }
            updatePageLabel();
        }else {
            for (PhotoPanelController photoPanel : photoPanels) {
                photoPanel.hidePhotoPanel();
            }
            hidePageButtonsAndLabel();
        }
        if (thread!=null&&thread.isAlive()){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread=new Thread(createImageLoadingForAllPanelsTask());
        thread.setDaemon(true);
        thread.start();
    }

    private Task<Void> createImageLoadingForAllPanelsTask(){
        Task <Void>task=new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                DirectoryInfo directoryInfo = chooseSceneView.getDirectoryList().getSelectionModel().getSelectedItem();
                int index = directoryInfo.getIndex();
                int x=0;
                while (!thread.isInterrupted()&&x<photoPanels.size()){
                    int panelIndex=index+photoPanels.get(x).getId();
                    if (panelIndex<directoryInfo.getPathToImagesInDirectory().size()&&!thread.isInterrupted()) {
                        setImageOnPhotoPanel(photoPanels.get(x), directoryInfo.getPathToImagesInDirectory().get(panelIndex));
                    }
                    x++;
                }
                return null;
            }
        };
        return task;
    }

    private void hidePageButtonsAndLabel() {
        chooseSceneView.getPageLabel().setVisible(false);
        chooseSceneView.getNextPageButton().setVisible(false);
        chooseSceneView.getPreviousPageButton().setVisible(false);
        chooseSceneView.getFirstPageButton().setVisible(false);
        chooseSceneView.getLastPageButton().setVisible(false);
    }

    private void showPageButtonsAndLabel() {
        chooseSceneView.getPageLabel().setVisible(true);
        chooseSceneView.getNextPageButton().setVisible(true);
        chooseSceneView.getPreviousPageButton().setVisible(true);
        chooseSceneView.getFirstPageButton().setVisible(true);
        chooseSceneView.getLastPageButton().setVisible(true);
    }

    public App getApp() {
        return app;
    }

    public ChooseSceneView getChooseSceneView() {
        return chooseSceneView;
    }

    public ListsController getListsController() {
        return listsController;
    }

    public ButtonsController getButtonsController() {
        return buttonsController;
    }

    public ArrayList<PhotoPanelController> getPhotoPanels() {
        return photoPanels;
    }
}

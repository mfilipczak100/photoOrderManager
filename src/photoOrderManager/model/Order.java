package photoOrderManager.model;

import photoOrderManager.App;
import photoOrderManager.scenes.endScene.EndSceneController;
import photoOrderManager.scenes.newOrderScene.NewOrderSceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private App app;
    private ObservableList<OrderItem> orderItemObservableList=FXCollections.observableArrayList();
    private ObservableList<DirectoryInfo> directoryList= FXCollections.observableArrayList();
    private int totalPickedPhotos=0;
    private BigDecimal price=BigDecimal.valueOf(0);
    private int directoryListSelectedIndex=-1;

    public Order(App app) {
        this.app = app;
    }

    public void removeOrderItem(OrderItem orderItem){
        if (orderItemObservableList.contains(orderItem)){
            totalPickedPhotos=totalPickedPhotos-orderItem.getPhotosPickedCount();
            BigDecimal formatPrice=app.getSettings().getFormatToOrder().get(orderItem.getFormat());
            formatPrice=formatPrice.multiply(BigDecimal.valueOf(orderItem.getPhotosPickedCount()));
            price=price.subtract(formatPrice);
            orderItemObservableList.remove(orderItem);
        }
    }

    public Task<Void> completeOrder() {
        Task<Void>task=new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                    int photosCopied=0;
                    LocalDateTime data=LocalDateTime.now();
                    int year=data.getYear();
                    int month=data.getMonthValue();
                    int day=data.getDayOfMonth();
                    String dateString=day+"-"+month+"-"+year;
                    String pathToDirectory=app.getSettings().getPathToOrdersDirectory();
                    File f=new File(pathToDirectory+"\\"+dateString);

                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    String pathToOrder=pathToDirectory+"\\"+dateString;
                    int ordernumber = Objects.requireNonNull(f.listFiles()).length + 1;
                    f=new File(pathToDirectory+dateString+"\\"+ordernumber);
                    if (f.exists()){
                        int hour=data.getHour();
                        int min=data.getMinute();
                        int second=data.getSecond();
                        String orderno=ordernumber+"_"+hour+"-"+min+"-"+second;
                        f=new File(pathToDirectory+dateString+"\\"+orderno);
                        pathToOrder=pathToOrder+"\\Order_nr_"+orderno;
                    }else{
                        pathToOrder=pathToOrder+"\\Order_nr_"+ordernumber;
                    }
                    for(OrderItem orderItem:orderItemObservableList){
                        String directoryPath=pathToOrder+"\\"+orderItem.getFormat()+"\\"+orderItem.getPaper()+"\\"+orderItem.getCrop();
                        File file=new File(directoryPath);
                        file.mkdirs();
                        for(String path:orderItem.getPickedPhotosMap().keySet()){
                            Path path2=Paths.get(directoryPath+"\\"+orderItem.getPickedPhotosMap().get(path));
                            if(!Files.exists(path2)){
                                try {
                                    Files.createDirectories(path2);
                                } catch (IOException e) {

                                }
                            }
                            Path sourcePath=Paths.get(path);
                            path2=Paths.get(path2.toString()+"\\"+sourcePath.getFileName().toString());
                            try {
                                Files.copy(sourcePath,path2, StandardCopyOption.REPLACE_EXISTING);
                                photosCopied++;
                                updateProgress(photosCopied,totalPickedPhotos);
                            } catch (IOException e) {

                            }
                        }
                    }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                EndSceneController endSceneController=new EndSceneController(app);
                app.getMainController().setEndSceneController(endSceneController);
                app.getMainController().setChooseSceneController(null);
                app.getScene().setRoot(endSceneController.getEndSceneView());
            }

            @Override
            protected void failed() {
                super.failed();
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Wystąpił błąd.");
                alert.showAndWait();
            }
        };
        return task;
    }

    public int abortOrder(){
        boolean confirmed=app.getMainController().showConfirmationWindow("Czy napewno chcesz przerwać zamówienie?");
        if (confirmed) {
            app.setOrder(null);
            NewOrderSceneController newOrderSceneController = new NewOrderSceneController(app);
            app.getMainController().setNewOrderSceneController(newOrderSceneController);
            app.getScene().setRoot(newOrderSceneController.getNewOrderSceneView());
            return 1;
        }
        return 0;
    }

    public void addPhotoToOrderAndCalculatePrice(String format){
        totalPickedPhotos++;
        price=price.add(app.getSettings().getFormatToOrder().get(format));
    }

    public void removePhotoFromOrderAndCalculatePrice(String format){
        totalPickedPhotos--;
        price=price.subtract(app.getSettings().getFormatToOrder().get(format));
    }

    public ObservableList<OrderItem> getOrderItemObservableList() {
        return orderItemObservableList;
    }

    public ObservableList<DirectoryInfo> getDirectoryList() {
        return directoryList;
    }

    public int getTotalPickedPhotos() {
        return totalPickedPhotos;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getDirectoryListSelectedIndex() {
        return directoryListSelectedIndex;
    }

    public void setDirectoryListSelectedIndex(int directoryListSelectedIndex) {
        this.directoryListSelectedIndex = directoryListSelectedIndex;
    }
}

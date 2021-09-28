package photoOrderManager.scenes.chooseScene;

import photoOrderManager.model.DirectoryInfo;
import photoOrderManager.model.OrderItem;
import photoOrderManager.scenes.chooseScene.photoPanel.PhotoPanelController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;

import java.util.List;

public class ChooseSceneView extends BorderPane{

    private Label infoLabel;
    private Button abortButton;
    private ListView<DirectoryInfo> directoryList;
    private Button minus1AllButton;
    private Button plus1AllButton;
    private Button minus1Button;
    private Button plus1Button;
    private Button orderButton;
    private ListView <OrderItem> formatList;
    private Button addNewFormat;
    private Button removeFormat;
    private VBox centerVBox;
    private Button firstPageButton;
    private Button previousPageButton;
    private Label pageLabel;
    private Button nextPageButton;
    private Button lastPageButton;

    public ChooseSceneView(){
        infoLabel=new Label("ilość zdjęć");
        infoLabel.getStyleClass().add("infoLabel");

        VBox topPane=new VBox(infoLabel);
        topPane.getStyleClass().add("labelBox");

        setTop(topPane);

        directoryList=new ListView<>();
        directoryList.getStyleClass().add("customList");
        VBox vBox=new VBox(directoryList);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        VBox.setVgrow(directoryList,Priority.ALWAYS);
        vBox.setPadding(new Insets(15));
        setLeft(vBox);

        GridPane bottomBar=new GridPane();
        HBox bottomLeft=new HBox();
        bottomLeft.setAlignment(Pos.CENTER_LEFT);
        bottomLeft.setPadding(new Insets(15));

        abortButton=new Button("Przerwij");
        abortButton.getStyleClass().add("bigButton");
        bottomLeft.getChildren().add(abortButton);

        HBox bottomCenter=new HBox();
        bottomCenter.setAlignment(Pos.CENTER);
        bottomCenter.setPadding(new Insets(15));
        bottomCenter.setSpacing(15);

        minus1AllButton=new Button("-1 cały folder");
        minus1AllButton.getStyleClass().add("mediumButton");
        bottomCenter.getChildren().add(minus1AllButton);

        minus1Button=new Button("-1 cała strona");
        minus1Button.getStyleClass().add("mediumButton");
        bottomCenter.getChildren().add(minus1Button);

        plus1Button=new Button("+1 cała strona");
        plus1Button.getStyleClass().add("mediumButton");
        bottomCenter.getChildren().add(plus1Button);

        plus1AllButton=new Button("+1 cały folder");
        plus1AllButton.getStyleClass().add("mediumButton");
        bottomCenter.getChildren().add(plus1AllButton);

        HBox bottomRight=new HBox();
        bottomRight.setAlignment(Pos.CENTER_RIGHT);
        bottomRight.setPadding(new Insets(15));

        orderButton=new Button("Zamów");
        orderButton.getStyleClass().add("bigButton");
        bottomRight.getChildren().add(orderButton);

        bottomBar.add(bottomLeft,0,0);
        bottomBar.add(bottomCenter,1,0);
        bottomBar.add(bottomRight,2,0);

        ColumnConstraints c1=new ColumnConstraints();
        c1.setPercentWidth(25);

        ColumnConstraints c2=new ColumnConstraints();
        c2.setPercentWidth(50);

        bottomBar.getColumnConstraints().add(c1);
        bottomBar.getColumnConstraints().add(c2);
        bottomBar.getColumnConstraints().add(c1);

        setBottom(bottomBar);

        VBox formatButtonsVBox=new VBox();
        formatButtonsVBox.setSpacing(15);

        formatList=new ListView<>();
        formatList.getStyleClass().add("customList");
        formatButtonsVBox.getChildren().add(formatList);

        VBox addNewBox=new VBox();
        addNewBox.setSpacing(15);
        addNewBox.setFillWidth(true);
        addNewFormat=new Button("Dodaj format");
        addNewFormat.getStyleClass().add("formatButton");
        addNewBox.getChildren().add(addNewFormat);

        removeFormat=new Button("Usuń format");
        removeFormat.getStyleClass().add("formatButton");
        addNewBox.getChildren().add(removeFormat);

        VBox rightBar=new VBox();
        rightBar.setPadding(new Insets(15));
        rightBar.setSpacing(15);
        rightBar.getChildren().add(formatButtonsVBox);
        rightBar.getChildren().add(addNewBox);

        VBox.setVgrow(formatButtonsVBox,Priority.ALWAYS);
        VBox.setVgrow(formatList,Priority.ALWAYS);

        setRight(rightBar);

        getStylesheets().add("style.css");
    }

    void createCenter(List<PhotoPanelController> photoPanels,int numberOfPanels){
        centerVBox=new VBox();

        GridPane centerGridPane=new GridPane();
        int x=0;
        int y=0;
        for (PhotoPanelController photoPanel : photoPanels){
            centerGridPane.add(photoPanel.getPhotoPanelView(),x,y);
            x++;
            if (x==numberOfPanels/2){
                x=0;
                y++;
            }
        }
        centerGridPane.setPadding(new Insets(15));
        centerGridPane.setVgap(10);
        centerGridPane.setHgap(10);
        centerGridPane.setAlignment(Pos.CENTER);
        for (PhotoPanelController photoPanel :photoPanels){
            GridPane.setVgrow(photoPanel.getPhotoPanelView(),Priority.ALWAYS);
        }
        VBox.setVgrow(centerGridPane,Priority.ALWAYS);

        ColumnConstraints columnConstraints =new ColumnConstraints();
        if (numberOfPanels==4){
            columnConstraints.setPercentWidth(50.0);
            centerGridPane.getColumnConstraints().add(columnConstraints);
            centerGridPane.getColumnConstraints().add(columnConstraints);
        }else {
            columnConstraints.setPercentWidth(33.33);
            centerGridPane.getColumnConstraints().add(columnConstraints);
            centerGridPane.getColumnConstraints().add(columnConstraints);
            centerGridPane.getColumnConstraints().add(columnConstraints);
        }
        RowConstraints rowConstraints=new RowConstraints();
        rowConstraints.setPercentHeight(50);
        centerGridPane.getRowConstraints().add(rowConstraints);
        centerGridPane.getRowConstraints().add(rowConstraints);

        HBox centerHBox=new HBox();
        centerHBox.setPadding(new Insets(15));
        centerHBox.setSpacing(15);
        centerHBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(centerHBox,Priority.NEVER);

        firstPageButton=new Button("Pierwsza strona");
        firstPageButton.getStyleClass().add("formatButton");

        previousPageButton=new Button("Poprzednia strona");
        previousPageButton.getStyleClass().add("formatButton");

        pageLabel=new Label("0/0");
        pageLabel.getStyleClass().add("photoLabelText");

        nextPageButton=new Button("Następna strona");
        nextPageButton.getStyleClass().add("formatButton");

        lastPageButton=new Button("Ostatnia strona");
        lastPageButton.getStyleClass().add("formatButton");

        centerHBox.getChildren().addAll(firstPageButton,previousPageButton,pageLabel,nextPageButton,lastPageButton);

        centerVBox.getChildren().addAll(centerGridPane,centerHBox);

        setCenter(centerVBox);
    }

    public Label getInfoLabel() {
        return infoLabel;
    }

    public Button getAbortButton() {
        return abortButton;
    }

    public ListView<DirectoryInfo> getDirectoryList() {
        return directoryList;
    }

    public Button getMinus1Button() {
        return minus1Button;
    }

    public Button getPlus1Button() {
        return plus1Button;
    }

    public Button getOrderButton() {
        return orderButton;
    }

    public ListView<OrderItem> getFormatList() {
        return formatList;
    }

    public Button getAddNewFormat() {
        return addNewFormat;
    }

    public Button getRemoveFormat() {
        return removeFormat;
    }

    public Button getPreviousPageButton() {
        return previousPageButton;
    }

    public Label getPageLabel() {
        return pageLabel;
    }

    public Button getNextPageButton() {
        return nextPageButton;
    }

    public Button getFirstPageButton() {
        return firstPageButton;
    }

    public Button getLastPageButton() {
        return lastPageButton;
    }

    public Button getMinus1AllButton() {
        return minus1AllButton;
    }

    public Button getPlus1AllButton() {
        return plus1AllButton;
    }
}

package photoOrderManager.scenes.settingsScene;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsSceneView extends BorderPane {

    private Label pendrivePathLabel;
    private Button choosePendrivePathButton;
    private ListView<String> formatList;
    private TextField formatTextField1;
    private TextField formatTextField2;
    private TextField priceTextField1;
    private TextField priceTextField2;
    private Button addFormatButton;
    private Button removeFormatButton;
    private Label orderPathlabel;
    private Button chooseOrderPathButton;
    private Button saveButton;
    private Button defaultsButton;
    private Button exitButton;


    public SettingsSceneView(){
        VBox mainVbox=new VBox();
        HBox hBox1=createPathToPendriveHBox();
        HBox hBox=createFormatPanelHBox();
        HBox hBox2=createPathToOrderPanel();

        mainVbox.getChildren().addAll(hBox1,hBox,hBox2);
        mainVbox.setSpacing(100);
        mainVbox.setAlignment(Pos.CENTER);
        setCenter(mainVbox);

        HBox bottomPanel=createBottomPanel();
        setBottom(bottomPanel);

        getStylesheets().add("style.css");
    }

    private HBox createPathToPendriveHBox(){
        HBox hBox=new HBox();

        Label pathToPendriveInfoLabel=new Label("Scieżka do pendrive:");
        pendrivePathLabel=new Label();
        choosePendrivePathButton=new Button("Wybierz ścieżkę do pendrive");

        hBox.getChildren().addAll(pathToPendriveInfoLabel,pendrivePathLabel,choosePendrivePathButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setMaxWidth(1000);
        hBox.getStyleClass().add("decoratedPanel");

        return hBox;
    }

    private HBox createFormatPanelHBox(){
        HBox hBox=new HBox();
        formatList=new ListView<>();
        formatList.setPrefSize(200,200);

        HBox formatHbox=new HBox();
        formatTextField1=new TextField();
        formatTextField1.setPrefWidth(40);
        formatTextField2=new TextField();
        formatTextField2.setPrefWidth(40);
        Label label1=new Label("x");
        formatHbox.getChildren().addAll(formatTextField1,label1,formatTextField2);
        formatHbox.setAlignment(Pos.CENTER);

        HBox priceHbox=new HBox();
        priceTextField1=new TextField();
        priceTextField1.setPrefWidth(40);
        priceTextField2=new TextField();
        priceTextField2.setPrefWidth(40);
        Label label2=new Label(", ");
        addFormatButton=new Button("Dodaj format");
        priceHbox.getChildren().addAll(priceTextField1,label2,priceTextField2,addFormatButton);
        priceHbox.setAlignment(Pos.CENTER);

        HBox hBox1=new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(20);
        hBox1.getChildren().addAll(formatHbox,priceHbox);

        HBox hBox2=new HBox();
        removeFormatButton=new Button("Usuń format");
        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().add(removeFormatButton);

        VBox vBox=new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(hBox1,hBox2);

        hBox.getChildren().addAll(formatList,vBox);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(40);
        hBox.setMaxWidth(1000);
        hBox.getStyleClass().add("decoratedPanel");

        return hBox;
    }

    private HBox createPathToOrderPanel(){
        HBox hBox=new HBox();
        Label label=new Label("Scieżka do folderu z zamówieniami:");
        orderPathlabel=new Label();
        chooseOrderPathButton=new Button("Wybierz folder");

        hBox.getChildren().addAll(label,orderPathlabel,chooseOrderPathButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setMaxWidth(1000);
        hBox.getStyleClass().add("decoratedPanel");
        return hBox;
    }

    private HBox createBottomPanel(){
        HBox hBox=new HBox();
        saveButton=new Button("Zapisz ustawienia");
        saveButton.getStyleClass().add("bigButton");
        defaultsButton=new Button("Domyślne");
        defaultsButton.getStyleClass().add("bigButton");
        exitButton=new Button("Wyjdź");
        exitButton.getStyleClass().add("bigButton");
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);
        hBox.getChildren().addAll(exitButton,defaultsButton,saveButton);
        return hBox;
    }

    public Label getPendrivePathLabel() {
        return pendrivePathLabel;
    }

    public Button getChoosePendrivePathButton() {
        return choosePendrivePathButton;
    }

    public ListView<String> getFormatList() {
        return formatList;
    }

    public TextField getFormatTextField1() {
        return formatTextField1;
    }

    public TextField getFormatTextField2() {
        return formatTextField2;
    }

    public TextField getPriceTextField1() {
        return priceTextField1;
    }

    public TextField getPriceTextField2() {
        return priceTextField2;
    }

    public Button getAddFormatButton() {
        return addFormatButton;
    }

    public Button getRemoveFormatButton() {
        return removeFormatButton;
    }

    public Label getOrderPathlabel() {
        return orderPathlabel;
    }

    public Button getChooseOrderPathButton() {
        return chooseOrderPathButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public Button getDefaultsButton() {
        return defaultsButton;
    }
}

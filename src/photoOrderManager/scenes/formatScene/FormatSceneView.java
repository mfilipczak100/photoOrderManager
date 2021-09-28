package photoOrderManager.scenes.formatScene;

import photoOrderManager.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class FormatSceneView extends BorderPane {

    private App app;
    private Label formatLabel;
    private ArrayList<Button> buttonList=new ArrayList<>();
    private Button backButton;

    public FormatSceneView(App app){

        this.app=app;

        formatLabel=new Label("Wybierz format zdjęć");
        formatLabel.getStyleClass().add("infoLabel");

        VBox northPanel=new VBox();
        northPanel.getStyleClass().add("labelBox");
        northPanel.getChildren().add(formatLabel);

        VBox vBox=new VBox();
        vBox.setPadding(new Insets(50));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        addButtons();
        vBox.getChildren().addAll(buttonList);

        backButton=new Button("Przerwij");
        backButton.getStyleClass().add("bigButton");

        HBox hBox=new HBox();
        hBox.setPadding(new Insets(30));
        hBox.getChildren().add(backButton);

        setCenter(vBox);
        setTop(northPanel);
        setBottom(hBox);

        getStylesheets().add("style.css");
    }

    void addButtons(){

        for (String s:app.getSettings().getFormatToOrder().keySet()){
            Button button=new Button(s);
            button.getStyleClass().add("bigButton");
            buttonList.add(button);
        }
    }

    public Label getFormatLabel() {
        return formatLabel;
    }

    public ArrayList<Button> getButtonList() {
        return buttonList;
    }

    public Button getBackButton() {
        return backButton;
    }
}

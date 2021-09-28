package photoOrderManager.scenes.startScene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class StartSceneView extends BorderPane {

    private Label titleLabel;
    private Button startButton;
    private Button optionsButton;

    public StartSceneView(){

        titleLabel=new Label("Photo Order Manager");
        titleLabel.getStyleClass().add("infoLabel");

        VBox labelBox=new VBox();
        labelBox.getStyleClass().add("labelBox");
        labelBox.getChildren().addAll(titleLabel);


        VBox centerBox=new VBox();
        startButton=new Button("Start");
        startButton.getStyleClass().add("bigButton");

        optionsButton=new Button("Ustawienia");
        optionsButton.getStyleClass().add("bigButton");

        centerBox.getChildren().addAll(startButton,optionsButton);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(25);

        setTop(labelBox);
        setCenter(centerBox);

        getStylesheets().add("style.css");
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getOptionsButton() {
        return optionsButton;
    }
}

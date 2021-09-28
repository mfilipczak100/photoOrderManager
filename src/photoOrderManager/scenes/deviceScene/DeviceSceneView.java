package photoOrderManager.scenes.deviceScene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class DeviceSceneView extends BorderPane {
    private Label infoLabel;
    private Button pendriveButton;
    private Button otherButton;

    public DeviceSceneView(){

        infoLabel=new Label("Wybierz nośnik");
        infoLabel.getStyleClass().add("infoLabel");

        pendriveButton=new Button("Pendrive");
        pendriveButton.getStyleClass().add("bigButton");

        otherButton=new Button("Inne");
        otherButton.getStyleClass().add("bigButton");

        VBox northPanel=new VBox();
        northPanel.getStyleClass().add("labelBox");
        northPanel.getChildren().add(infoLabel);

        VBox centerPanel=new VBox();
        centerPanel.setAlignment(Pos.CENTER);
        centerPanel.setSpacing(25);

        centerPanel.getChildren().add(pendriveButton);
        centerPanel.getChildren().add(otherButton);

        setTop(northPanel);
        setCenter(centerPanel);

        getStylesheets().add("style.css");
    }

    public Label getInfoLabel() {
        return infoLabel;
    }

    public Button getPendriveButton() {
        return pendriveButton;
    }

    public Button getOtherButton() {
        return otherButton;
    }
}

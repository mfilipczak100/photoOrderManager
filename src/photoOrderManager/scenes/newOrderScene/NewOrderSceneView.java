package photoOrderManager.scenes.newOrderScene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class NewOrderSceneView extends BorderPane {

    private Button newOrderButton;

    public NewOrderSceneView(){
        VBox labelBox=new VBox();
        labelBox.getStyleClass().add("labelBox");


        VBox centerBox=new VBox();
        newOrderButton=new Button("Nowe zamówienie");
        newOrderButton.getStyleClass().add("bigButton");

        centerBox.getChildren().add(newOrderButton);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(25);

        setCenter(centerBox);

        getStylesheets().add("style.css");
    }

    public Button getNewOrderButton() {
        return newOrderButton;
    }
}

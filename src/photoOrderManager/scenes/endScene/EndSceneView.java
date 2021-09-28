package photoOrderManager.scenes.endScene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EndSceneView extends VBox {

    private Label endLabel;
    private Button endButton;

    public EndSceneView(){
        endLabel=new Label("Dziękujemy za złożenie zamówienia.");
        endLabel.getStyleClass().add("loadingLabel");

        endButton=new Button("Zakończ");
        endButton.getStyleClass().add("bigButton");

        getChildren().add(endLabel);
        getChildren().add(endButton);
        setSpacing(120);
        setAlignment(Pos.CENTER);

        getStylesheets().add("style.css");
    }

    public Button getEndButton() {
        return endButton;
    }
}

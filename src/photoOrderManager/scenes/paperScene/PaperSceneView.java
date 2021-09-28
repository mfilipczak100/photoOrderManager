package photoOrderManager.scenes.paperScene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class PaperSceneView extends BorderPane {

    private Label infoLabel;
    private Button glossButton;
    private Button matButton;

    public PaperSceneView() {

        infoLabel=new Label("Wybierz papier");
        infoLabel.getStyleClass().add("infoLabel");

        VBox vBox=new VBox();
        vBox.getStyleClass().add("labelBox");
        vBox.getChildren().add(infoLabel);

        setTop(vBox);

        glossButton=new Button("Błyszczący");
        glossButton.getStyleClass().add("bigButton");

        matButton=new Button("Matowy");
        matButton.getStyleClass().add("bigButton");

        VBox vBox1=new VBox();
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(25);
        vBox1.getChildren().addAll(glossButton,matButton);

        setCenter(vBox1);

        getStylesheets().add("style.css");
    }

    public Label getInfoLabel() {
        return infoLabel;
    }

    public Button getGlossButton() {
        return glossButton;
    }

    public Button getMatButton() {
        return matButton;
    }
}

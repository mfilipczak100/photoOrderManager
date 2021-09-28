package photoOrderManager.scenes.cropScene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CropSceneView extends BorderPane {

    private Label infoLabel;
    private Button fillButton;
    private Button fitButton;

    public CropSceneView() {

        infoLabel=new Label("Wybierz sposób kadrowania");
        infoLabel.getStyleClass().add("infoLabel");

        VBox vBox=new VBox();
        vBox.getStyleClass().add("labelBox");
        vBox.getChildren().add(infoLabel);

        setTop(vBox);

        fillButton=new Button("Pełny papier");
        fillButton.getStyleClass().add("bigButton");

        fitButton=new Button("Pełny kadr");
        fitButton.getStyleClass().add("bigButton");

        VBox vBox1=new VBox();
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(25);
        vBox1.getChildren().addAll(fillButton,fitButton);

        setCenter(vBox1);

        getStylesheets().add("style.css");
    }

    public Label getInfoLabel() {
        return infoLabel;
    }

    public Button getFillButton() {
        return fillButton;
    }

    public Button getFitButton() {
        return fitButton;
    }

}

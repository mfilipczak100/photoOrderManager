package photoOrderManager.scenes.loadingScene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoadingSceneView extends VBox {
    private Label loadinglabel;
    private Button nextButton;
    private Button stopButton;

    public LoadingSceneView(){

        loadinglabel=new Label();
        loadinglabel.getStyleClass().add("loadingLabel");

        nextButton=new Button("Wyszukaj");
        nextButton.getStyleClass().add("bigButton");

        stopButton=new Button("Przerwij");
        stopButton.getStyleClass().add("bigButton");

        HBox hbox=new HBox();
        hbox.setSpacing(45);
        hbox.getChildren().addAll(stopButton,nextButton);
        hbox.setAlignment(Pos.CENTER);

        getChildren().add(loadinglabel);
        getChildren().add(hbox);
        setSpacing(120);
        setAlignment(Pos.CENTER);

        getStylesheets().add("style.css");
    }

    public Label getLoadinglabel() {
        return loadinglabel;
    }

    public Button getNextButton() {
        return nextButton;
    }

    public Button getStopButton() {
        return stopButton;
    }
}

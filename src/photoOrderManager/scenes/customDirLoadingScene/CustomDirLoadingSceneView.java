package photoOrderManager.scenes.customDirLoadingScene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;

public class CustomDirLoadingSceneView extends BorderPane {

    private ListView<String> directoriesList;
    private Label loadingLabel;
    private Button nextButton;
    private Button stopButton;
    private Button addDirButton;
    private Button removeDirButton;

    public CustomDirLoadingSceneView(){

        directoriesList=new ListView<>();
        directoriesList.setMaxWidth(250);
        directoriesList.setMaxHeight(350);

        loadingLabel=new Label("Wybierz foldery ze zdjęciami, a następnie wciśnij przycisk \"Wyszukaj\"");
        loadingLabel.setWrapText(true);
        loadingLabel.setMaxWidth(700);
        loadingLabel.getStyleClass().add("loadingLabel");

        HBox hBox=new HBox();
        hBox.setSpacing(100);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(directoriesList,loadingLabel);

        addDirButton=new Button("Dodaj folder");
        addDirButton.getStyleClass().add("bigButton");

        removeDirButton=new Button("Usuń folder");
        removeDirButton.getStyleClass().add("bigButton");

        HBox hBox2=new HBox();
        hBox2.setSpacing(100);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().addAll(removeDirButton,addDirButton);

        VBox vBox=new VBox();
        vBox.setSpacing(100);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBox,hBox2);

        setCenter(vBox);

        nextButton=new Button("Wyszukaj");
        nextButton.getStyleClass().add("bigButton");

        stopButton=new Button("Przerwij");
        stopButton.getStyleClass().add("bigButton");

        GridPane bottomBar=new GridPane();
        HBox bottomLeft=new HBox();
        bottomLeft.setAlignment(Pos.CENTER_LEFT);
        bottomLeft.setPadding(new Insets(15));
        bottomLeft.getChildren().add(stopButton);

        HBox bottomRight=new HBox();
        bottomRight.setAlignment(Pos.CENTER_RIGHT);
        bottomRight.setPadding(new Insets(15));
        bottomRight.getChildren().add(nextButton);

        bottomBar.add(bottomLeft,0,0);
        bottomBar.add(bottomRight,1,0);

        ColumnConstraints c=new ColumnConstraints();
        c.setPercentWidth(50);

        bottomBar.getColumnConstraints().add(c);
        bottomBar.getColumnConstraints().add(c);

        setBottom(bottomBar);

        getStylesheets().add("style.css");
    }

    public ListView<String> getDirectoriesList() {
        return directoriesList;
    }

    public Label getLoadingLabel() {
        return loadingLabel;
    }

    public Button getNextButton() {
        return nextButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public Button getAddDirButton() {
        return addDirButton;
    }

    public Button getRemoveDirButton() {
        return removeDirButton;
    }
}

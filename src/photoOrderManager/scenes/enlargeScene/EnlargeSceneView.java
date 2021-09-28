package photoOrderManager.scenes.enlargeScene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class EnlargeSceneView extends BorderPane {

    private ImageView imageView;
    private Button returnButton;

    public EnlargeSceneView(){

        imageView=new ImageView();
        imageView.setFitWidth(1400);
        imageView.setFitHeight(650);
        imageView.setPreserveRatio(true);
        imageView.setCache(false);
        imageView.setSmooth(true);
        setCenter(imageView);

        HBox hBox=new HBox();

        returnButton=new Button("Powrót");
        returnButton.getStyleClass().add("bigButton");
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(15));
        hBox.getChildren().add(returnButton);
        setBottom(hBox);

        getStylesheets().add("style.css");
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Button getReturnButton() {
        return returnButton;
    }
}

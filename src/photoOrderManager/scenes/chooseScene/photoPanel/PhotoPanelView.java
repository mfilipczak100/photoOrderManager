package photoOrderManager.scenes.chooseScene.photoPanel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PhotoPanelView extends VBox {

    private Label imageLabel;
    private ImageView imageView;
    private Button enlargeButton;
    private Button minusButton;
    private Label countLabel;
    private Button plusButton;
    private HBox hBox1;
    private HBox hBox2;
    private HBox hBox3;

    public PhotoPanelView(){

        hBox1=new HBox();
        imageLabel=new Label("IMG1000");
        imageLabel.getStyleClass().add("photoLabelText");

        hBox1.getChildren().add(imageLabel);
        hBox1.setPadding(new Insets(15));
        hBox1.setAlignment(Pos.CENTER);

        hBox2=new HBox();
        imageView=new ImageView();
        imageView.setFitHeight(150);
        imageView.setFitWidth(240);
        imageView.setSmooth(false);
        imageView.setPreserveRatio(true);
        imageView.setCache(false);

        hBox2.getChildren().add(imageView);
        hBox2.setAlignment(Pos.CENTER);

        hBox3=new HBox();

        enlargeButton=new Button("Powiększ");
        enlargeButton.getStyleClass().add("enlargeButton");

        minusButton=new Button("-");
        minusButton.getStyleClass().add("roundButton");

        countLabel=new Label("0");
        countLabel.getStyleClass().add("photoLabelText");

        plusButton=new Button("+");
        plusButton.getStyleClass().add("roundButton");

        hBox3.setPadding(new Insets(15));
        hBox3.setSpacing(15);
        hBox3.setAlignment(Pos.CENTER);
        hBox3.getChildren().addAll(minusButton,countLabel,plusButton,enlargeButton);

        getChildren().addAll(hBox1,hBox2,hBox3);
        VBox.setVgrow(hBox1, Priority.NEVER);
        VBox.setVgrow(hBox2, Priority.ALWAYS);
        VBox.setVgrow(hBox3, Priority.NEVER);

        getStyleClass().add("photoLabel");
        getStylesheets().add("style.css");
    }

    public Label getImageLabel() {
        return imageLabel;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Button getMinusButton() {
        return minusButton;
    }

    public Label getCountLabel() {
        return countLabel;
    }

    public Button getPlusButton() {
        return plusButton;
    }

    public Button getEnlargeButton() {
        return enlargeButton;
    }
}

package com.ruimo.crop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
 
public class Crop extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create Image and ImageView objects
        Image image = new Image("file:///home/shanai/Pictures/229.png");
        inspectImage(image);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        ScrollPane sp = new ScrollPane();
        sp.setContent(imageView);
      
        // Display image on screen
        StackPane root = new StackPane();
        root.getChildren().add(sp);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Image Read Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    void inspectImage(Image img) {
        System.err.println("width: " + img.getWidth());
        System.err.println("height: " + img.getHeight());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

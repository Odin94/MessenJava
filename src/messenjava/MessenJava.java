/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenjava;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Odin
 */
public class MessenJava extends Application {

    //hallo :) ;
    @Override
    public void start(Stage primaryStage) {
        Label label1 = new Label("Message:");
        TextField messageField = new TextField();
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, messageField);
        hb.setSpacing(10);

        Button btn = new Button();
        btn.setText("'Print what I said, please!'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("You said: " + messageField.getText());
            }
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(hb, btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

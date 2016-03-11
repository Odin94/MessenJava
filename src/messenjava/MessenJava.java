/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenjava;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Odin
 */
public class MessenJava extends Application {

    int windowWidth = 600;
    int windowheight = 400;
    Label ChatMessages = new Label("start Text");
    Label chatingWith=new Label();
    ObservableList<Peer> data;

    @Override
    public void start(Stage primaryStage) {
        
        ListView<Peer> list = new ListView<>();
        data = FXCollections.observableList(new ArrayList());
        list.setItems(data);
        
        
        list.setCellFactory(new Callback<ListView<Peer>,ListCell<Peer>>(){
            
            @Override
            public ListCell<Peer> call(ListView<Peer> param) {
                return new ListElement();
            }
        });
        
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                String name=list.getSelectionModel().getSelectedItem().getName();
                System.out.println("clicked on " + name);
                chatingWith.setText("Chating with "+name);
            }
        });
        
        HBox BackgroundSeperation = new HBox();

        VBox PeopleSection = new VBox();
        PeopleSection.setStyle("-fx-background-color: #336699;");
        PeopleSection.setPadding(new Insets(10, 10, 10, 10));

        Label Header_PeopleSection = new Label("People");
        Header_PeopleSection.setFont(new Font("Arial", 20));

        PeopleSection.getChildren().add(Header_PeopleSection);
        PeopleSection.getChildren().add(list);
        
        Label messageLabel = new Label("Message:");
        TextField messageField = new TextField();

        VBox oneChat = new VBox();
        oneChat.setStyle("-fx-background-color: #119911;");
        oneChat.setPadding(new Insets(10, 10, 10, 10));

        HBox ChatInputSection = new HBox();

        Button sendButton = new Button();
        sendButton.setText("Send");
        sendButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                send();
            }
        });

        ChatInputSection.getChildren().addAll(messageLabel, messageField, sendButton);
        ChatInputSection.setSpacing(10);

        oneChat.getChildren().addAll(chatingWith,ChatMessages, ChatInputSection);

        BackgroundSeperation.getChildren().addAll(PeopleSection, oneChat);

        Scene scene = new Scene(BackgroundSeperation, windowWidth, windowheight);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

        updateStatus();
        
    }

    /**
     *
     */
    private void send() {
        //Log
        System.out.print("Log; MessenJava; send; Message: ");

        String message;
        //Get message from inputFiel
        message = "hallo von Os";
        /*Log*/System.out.println(message);

        //Display message
        display(message, true);

        //Send message
        //peer.send
    }

    public void display(String message, boolean ownMessage) {
        ChatMessages.setText(message);
    }

    private void updateStatus() {
        //data.clear();
        //dummi Liste
        //Peer a=new Peer("anton","111",3,"pubKey","privKey");
        //data.add(new Peer("anton","111",3,"pubKey","privKey"));
        //data.add(new Peer("peter","111",3,"pubKey","privKey"));
    }

    //Todo: attach to onClose event
    private void disconect() {
        System.out.println("disconect");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            CryptoAsym.testMe();
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch(args);
    }

    class ListElement extends ListCell<Peer>{
        @Override
        protected void updateItem(Peer peer, boolean empty){
            super.updateItem(peer, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(null);

                HBox seperation=new HBox();
                Label name=new Label(peer.getName());
                Label onOff=new Label("off");
                onOff.setStyle("-fx-background-color: #ff1111;");
                seperation.getChildren().addAll(name,onOff);

                setGraphic(seperation);
            }
        }
    }
}

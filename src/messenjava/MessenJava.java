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
    
    //gui
    int windowWidth = 600;
    int windowheight = 400;
    
    // a field containing the latest message
    Label ChatMessages = new Label("start Text");
    //a field containing the name of the person the user is chating with at the moment, can be updated
    Label chatingWith=new Label();
    
    //
    // the List of Peers witch a showen in the people section
    ObservableList<Peer> data;

    @Override
    public void start(Stage primaryStage) {
    //GUI of The List of peers---------------------------------------------------------
        
        ListView<Peer> list = new ListView<>();
        //initialise the list of peers; empty
        data = FXCollections.observableList(new ArrayList());
        //connect the data with the listView
        list.setItems(data);
        
        //set the Designe of one element in the list View; every element will get one dataElement (Peer) to fill itself with information
        list.setCellFactory(new Callback<ListView<Peer>,ListCell<Peer>>(){
            
            @Override
            public ListCell<Peer> call(ListView<Peer> param) {
                // the peer dont has to be passed to the element of the list; the methode updateItem() of class ListElement is automatically called and defines the element; To change the stile change the implementation of this methode
                return new ListElement();
            }
        });
        
        
        // add a listener to the listView which handles the event of clicking on one element; This is used to switch between different people to chat with
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                String name=list.getSelectionModel().getSelectedItem().getName();
                System.out.println("clicked on " + name);
                chatingWith.setText("Chating with "+name);
            }
        });
    //end listview------------------------------------------------------------------------------
        
        //seperates the background into a section for people and a sectionfor chating
        HBox BackgroundSeperation = new HBox();

        VBox PeopleSection = new VBox();
        PeopleSection.setMaxWidth(110);
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
        data.clear();
        //dummi Liste
        data.add(new Peer("anton","192.168.1.33",3,"pubKey","privKey"));
        data.add(new Peer("peter","192.168.1.34",3,"pubKey","privKey"));
    }

    //Todo: attach to onClose event
    private void disconect() {
        System.out.println("disconect");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        try {
            CryptoAsym.testMe();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Config.forwardPort(1337);
        System.out.println("port mapped");
        Connection con = new Connection(Util.StringToInetAddress("178.2.48.214"),1338);
        con.connect();
        System.out.println("Con established");
        //con.receive();
        //con.send("blub");
        
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
                
                //creation of the surface of one Listelement using the according Peer object
                HBox seperation=new HBox();
                Label name=new Label(peer.getName());
                Label onOff=new Label("off");
                onOff.setStyle("-fx-background-color: #ff1111;");
                seperation.getChildren().addAll(name,onOff);
                //----------------------------------------------------------------------
                
                setGraphic(seperation);
            }
        }
    }
}

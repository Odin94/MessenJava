/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenjava;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Odin
 */
public class MessenJava extends Application {
    int windowWidth=600;
    int windowheight=400;
    Label ChatMessages=new Label("start Text");
    HBox onePerson=new HBox();
    Label name=new Label();
    Label onOff=new Label();
    
    @Override
    public void start(Stage primaryStage) {
        HBox BackgroundSeperation = new HBox();
        
        VBox PeopleSection=new VBox();
        PeopleSection.setStyle("-fx-background-color: #336699;");
        PeopleSection.setPadding(new Insets(10, 10, 10, 10));
        
        onePerson.getChildren().addAll(name,onOff);
        
        Label Header_PeopleSection=new Label("People");
        Header_PeopleSection.setFont(new Font("Arial", 20));
        
        PeopleSection.getChildren().addAll(Header_PeopleSection,onePerson);
        
        Label messageLabel = new Label("Message:");
        TextField messageField = new TextField();
        
        VBox oneChat = new VBox();
        oneChat.setStyle("-fx-background-color: #119911;");
        oneChat.setPadding(new Insets(10, 10, 10, 10));
        
        HBox ChatInputSection=new HBox();
        
        Button sendButton = new Button();
        sendButton.setText("Send");
        sendButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                send();
            }
        });

        ChatInputSection.getChildren().addAll(messageLabel, messageField,sendButton);
        ChatInputSection.setSpacing(10);
        
        oneChat.getChildren().addAll(ChatMessages,ChatInputSection);
        
        BackgroundSeperation.getChildren().addAll(PeopleSection,oneChat);
        
        Scene scene = new Scene(BackgroundSeperation, windowWidth, windowheight);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        updateStatus(null,null);
    }
    
    /**
     * 
     */
    private void send(){
        //Log
        System.out.print("Log; MessenJava; send; Message: ");
        
        String message;
        //Get message from inputFiel
        message="hallo von Os";
        /*Log*/System.out.println(message);
       
        
        //Display message
        display(message,true);
        
        //Send message
        //peer.send
    }

    public void display(String message, boolean ownMessage){
        ChatMessages.setText(message);
    }
    
    private void updateStatus(String name, String online){
        name="heinz ";
        online=("on");
        this.name.setText(name);
        this.onOff.setText(online);

    }
    
    //Todo: attach to onClose event
    private void disconect(){
    
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CryptoAsym.testMe();
        
        launch(args);
    }

}

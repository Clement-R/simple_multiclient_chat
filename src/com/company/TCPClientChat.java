package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

class TCPClientChat implements Observer {
    public static void main() throws IOException, ClassNotFoundException {
        // Connect to the system
        Socket communicationSocket = new Socket("localhost", 8100);
        System.out.println("Connected !");

        // Ask the client name
        Scanner sc = new Scanner(System.in);
        System.out.println("Username :");
        String username = sc.nextLine();

        // Create streams objects
        OutputStream os = communicationSocket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);

        InputStream is = communicationSocket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);

        while(true) {
            // Ask the client for a message
            System.out.println("Write your message :");
            sc = new Scanner(System.in);
            String message = sc.nextLine();

            // Send message
            Message messageObject = new Message(username, message);
            oos.writeObject(messageObject);

            // Get response from server and display last message
            Message msg = (Message) ois.readObject();
            String received_message = msg.getMessage();
            username = msg.getUsername();
            System.out.println(username + " : " + received_message);
        }
    }

    @Override
    public void update(Observable obs, Object obj) {
        // Get response from server and display last message

        /*
        if(obs instanceof TCPServerChat){
            Message msg = (Message) ois.readObject();
        }
        */
        /*Message msg = (Message) ois.readObject();
        String received_message = msg.getMessage();
        username = msg.getUsername();
        System.out.println(username + " : " + received_message);*/
    }
}

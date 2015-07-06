package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class TCPClientChat  {
    private ObjectInputStream ois;
    private Client client;

    public void main() throws IOException, ClassNotFoundException {
        // Connect to the system
        Socket communicationSocket = new Socket("localhost", 8100);
        System.out.println("Connected !");

        // Create streams objects
        OutputStream os = communicationSocket.getOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(os);

        InputStream is = communicationSocket.getInputStream();
        final ObjectInputStream ois = new ObjectInputStream(is);

        Thread writer = new Thread(new Runnable() {
            @Override
            public void run() {
                // Ask the client name
                Scanner sc = new Scanner(System.in);
                System.out.println("Username :");
                String username = sc.nextLine();

                while(true) {
                    // Ask the client for a message
                    System.out.println("Write your message :");
                    sc = new Scanner(System.in);
                    String message = sc.nextLine();

                    // Send message
                    Message messageObject = new Message(username, message);
                    try {
                        oos.writeObject(messageObject);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        writer.start();

        Thread reader = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    // Get response from server and display last message
                    Message msg = null;
                    try {
                        msg = (Message) ois.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    String received_message = msg.getMessage();
                    String username = msg.getUsername();
                    System.out.println(username + " : " + received_message);
                }
            }
        });
        reader.start();
    }
}

package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

class TCPServerChat extends Observable {
    public void main() throws IOException {
        ServerSocket server = new ServerSocket(8100);
        final ArrayList<ObjectOutputStream> clientList = new ArrayList<>();

        while(true) {
            // Wait for a client
            final Socket clientSocket = server.accept();
            System.out.println("One client just connect");

            // Create streams objects
            InputStream is = clientSocket.getInputStream();
            final ObjectInputStream ois = new ObjectInputStream(is);

            OutputStream os = clientSocket.getOutputStream();
            final ObjectOutputStream oos = new ObjectOutputStream(os);
            clientList.add(oos);
            System.out.println(clientList);

            // Create and start thread that get user messages and display them
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread launched");

                    while(true) {
                        try {
                            Message msg = (Message) ois.readObject();
                            String message_received = msg.getMessage();
                            String username = msg.getUsername();

                            // Send message to client
                            Message messageObject = new Message(username, message_received);
                            // notifyObservers(messageObject);
                            for(ObjectOutputStream oos : clientList) {
                                oos.writeObject(messageObject);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            th.start();
        }
    }
}

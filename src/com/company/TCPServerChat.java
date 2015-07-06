package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

class TCPServerChat extends Observable {
    public void main() throws IOException {
        ServerSocket server = new ServerSocket(8100);

        while(true) {
            // Wait for a client
            final Socket clientSocket = server.accept();
            System.out.println("One client just connect");

            // Create streams objects
            InputStream is = clientSocket.getInputStream();
            final ObjectInputStream ois = new ObjectInputStream(is);

            OutputStream os = clientSocket.getOutputStream();
            final ObjectOutputStream oos = new ObjectOutputStream(os);

            Client client = new Client(oos, ois);
            addObserver(client);

            // Create and start thread that get user messages and display them
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread launched");

                    while(true) {
                        try {
                            /*
                            TODO:
                            - Change to use getter and setter of client class
                            */
                            Message msg = (Message) ois.readObject();
                            String message_received = msg.getMessage();
                            String username = msg.getUsername();

                            // Send message to all clients
                            Message messageObject = new Message(username, message_received);
                            setChanged();
                            notifyObservers(messageObject);
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

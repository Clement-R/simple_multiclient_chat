package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class TCPServerChat {
    public static void main() throws IOException {
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
                            oos.writeObject(messageObject);

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

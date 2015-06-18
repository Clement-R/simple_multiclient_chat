package com.company;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by IMIE on 18/06/2015.
 */
class TCPServer {
    public static void main() throws IOException {
        ServerSocket server = new ServerSocket(8100);

        while(true) {
            // Wait for a client
            final Socket clientSocket = server.accept();
            System.out.println("One client just connect");

            // Get user name
            final Scanner sc = new Scanner(clientSocket.getInputStream());
            final String username = sc.nextLine();

            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        // Read client message
                        try {
                            Scanner sc = new Scanner(clientSocket.getInputStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String message = sc.nextLine();
                        System.out.println(message);

                        // Send message to client
                        PrintStream outStream = null;
                        try {
                            outStream = new PrintStream(clientSocket.getOutputStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        outStream.println(username + " : " + message);
                    }
                }
            });
            th.start();
        }
    }
}

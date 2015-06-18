package com.company;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by IMIE on 18/06/2015.
 */
class TCPClient {
    public static void main() throws IOException {
        // Connect to the system
        Socket communicationSocket = new Socket("localhost", 8100);
        System.out.println("Connected !");

        // Ask the client name
        Scanner sc = new Scanner(System.in);
        System.out.println("Username :");
        String username = sc.nextLine();

        // Send message
        PrintStream outStream = new PrintStream(communicationSocket.getOutputStream());
        outStream.println(username);

        while(true) {
            // Ask the client for a message
            sc = new Scanner(System.in);
            System.out.println("Write your message :");
            String message = sc.nextLine();

            // Send message
            outStream = new PrintStream(communicationSocket.getOutputStream());
            outStream.println(message);

            // Get response from server
            sc = new Scanner(communicationSocket.getInputStream());
            message = sc.nextLine();
            System.out.println(message);
        }
    }
}

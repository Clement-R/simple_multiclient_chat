package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    private static final String EXIT = "exit";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Server : 0");
        System.out.println("Client : 1");
        Scanner sc = new Scanner(System.in);
        String saisie = sc.next();
        if(Integer.parseInt(saisie) == 0) {
            TCPServerChat server = new TCPServerChat();
            server.main();
        } else if (Integer.parseInt(saisie) == 1) {
            TCPClientChat client = new TCPClientChat();
            client.main();
        }
    }
}


/*
* How to implement observer pattern for displaying messages when they are send by the server
*
http://blog.netapsys.fr/pratiquer-le-design-pattern-observer-en-15-min/
*
* */
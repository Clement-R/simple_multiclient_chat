package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

public class Client implements Observer {
    private String username;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Client(ObjectOutputStream oos, ObjectInputStream ois) {
        this.oos = oos;
        this.ois = ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void update(Observable obs, Object obj) {
        // Get response from server and display last message
        if(obs instanceof TCPServerChat){
            try {
                this.oos.writeObject(obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

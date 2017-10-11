package model;

import java.util.UUID;

public class User {

    public static int id;
    public final int myId;
    public String name;

    public User() {
        this.myId = id++;
        this.name = UUID.randomUUID().toString().substring(0,7);
    }
}

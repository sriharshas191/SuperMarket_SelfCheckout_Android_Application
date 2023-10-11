package com.example.idpfinal;

public class UserName {
     static String user;

     public UserName()
     {

     }
    public UserName(String user) {
        this.user = user;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        UserName.user = user;
    }
}

package com.muxistudio.catpel.Model;


public class UsrInfo {


    /**
     * username : string
     * password : string
     */

    private String username;
    private String password;
    private int userId;
    private String userToken;



    public UsrInfo(String username,String password){
        this.username = username;
        this.password = password;
    }

    public int getUserId(){
        return userId;
    }

    public String getUserToken(){
        return userToken;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

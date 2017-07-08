package com.muxistudio.catpel.Model;



public class UserInfo0 {

    /**
     * username : 1234
     * password : 1234
     */

    private String username;
    private String password;

    public UserInfo0(String username,String password){
        this.username = username;
        this.password = password;

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

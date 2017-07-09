package com.muxistudio.catpel.POJO;


public class UserInfo0 {

    /**
     * username : szy
     * password : 1234
     * bind_name : zk
     */

    private String username;
    private String password;
    private String bind_name;

    public UserInfo0(String username,String password,String bind_name){
        this.username = username;
        this.password = password;
        this.bind_name = bind_name;
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

    public String getBind_name() {
        return bind_name;
    }

    public void setBind_name(String bind_name) {
        this.bind_name = bind_name;
    }
}

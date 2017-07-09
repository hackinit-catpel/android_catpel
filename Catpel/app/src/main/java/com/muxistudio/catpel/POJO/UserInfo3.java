package com.muxistudio.catpel.POJO;


public class UserInfo3 {
    /**
     * my_id : 1
     * time : 2
     */

    private int my_id;
    private int time;

    public UserInfo3(int my_id,int time){
        this.time = time;
        this.my_id = my_id;
    }

    public int getMy_id() {
        return my_id;
    }

    public void setMy_id(int my_id) {
        this.my_id = my_id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    //used in upload a user's time



}

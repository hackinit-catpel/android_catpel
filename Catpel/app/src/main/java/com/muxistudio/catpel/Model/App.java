package com.muxistudio.catpel.Model;

import android.app.Application;

import java.util.Stack;


public class App extends Application{

    public static Stack<String> bannedApplicationList  = new Stack<>();

    public static int FORGIVE_TIME ;
    public static int SET_SECONDS = 0;

    //this time refer to user saved
    public static int TIME = 0;
    public static int TIME_REF = 0;

    public static int alertDialogHintTimes = 1;

    public static String userName;
    public static String userToken;
    public static int userId;
    public static String son;

    public static void removeAll(Stack<String> stringStack){
        stringStack.clear();
    }
}

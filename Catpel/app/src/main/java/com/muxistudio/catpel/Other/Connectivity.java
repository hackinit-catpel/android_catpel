package com.muxistudio.catpel.Other;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Connectivity {
    private Context context;

    public Connectivity(Context context) {
        this.context = context;
    }

    public boolean ifConnected(){
        boolean flag = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo [] net = manager.getAllNetworkInfo();
        for(int i=0;i<net.length;i++) {
            if (net[i].getState() == NetworkInfo.State.CONNECTED) {
                flag = true;
            }
        }
        return flag;
    }
}


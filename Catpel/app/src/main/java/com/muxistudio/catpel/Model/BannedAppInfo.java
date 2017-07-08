package com.muxistudio.catpel.Model;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;


public class BannedAppInfo {
    public int resourceId;
    public Drawable icon;
    public String packageName;
    public Intent appIntent;
    public boolean ifChecked = false;
    public static List<Boolean> isCheckList = new ArrayList<>();
    //public String appName;

    public BannedAppInfo(int resourceId,String packageName){
        this.resourceId = resourceId;
        this.packageName = packageName;
    }


    public BannedAppInfo(Drawable icon,String PackageName){
        this.packageName = PackageName;
        this.icon = icon;
    }
}

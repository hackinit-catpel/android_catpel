package com.muxistudio.catpel.CompelSystem;

import static com.muxistudio.catpel.Model.App.SET_SECONDS;


public class Rank {

    public static int YOUNG_CAT = 1000;
    public static int ADULT_CAT = 5000;

    public static String status;

    public static String setPetStatus(int SET_SECONDS){
        String status = null;
        if(SET_SECONDS<YOUNG_CAT){
            status = "未孵化";
        }
        if(SET_SECONDS>=YOUNG_CAT&&SET_SECONDS<=ADULT_CAT){
            status = "幼年";
        }
        if(SET_SECONDS>ADULT_CAT){
            status = "成年";
        }
        return status;
    }
    public static int setTimeContainer2(int SET_SETCONDS){
        int time2 = 0;
        if(SET_SECONDS<YOUNG_CAT){
            time2 = YOUNG_CAT;
        }
        if(SET_SECONDS>=YOUNG_CAT&&SET_SECONDS<=ADULT_CAT){
            time2 = ADULT_CAT;
        }
        if(SET_SECONDS>ADULT_CAT){
            time2 = ADULT_CAT;
        }
        return time2;
    }
}

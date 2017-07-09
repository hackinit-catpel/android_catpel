package com.muxistudio.catpel.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.muxistudio.catpel.R;

/**
 * Created by kolibreath on 17-7-8.
 */

public class PopUpWindowUtils {
    private View view;
    private Context context;

    public PopUpWindowUtils(View view,Context context){
        this.view = view;
        this.context = context;
    }

    public void setPopUpView(View view){
        View contentView = LayoutInflater.from(context).
                inflate(R.layout.layout_popupwindow,null);
        TextView textView = contentView.findViewById(R.id.cat_hint);
        textView.setText("别碰我，去工作");
        textView.setTextSize(20);
        final PopupWindow popupWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT
        , WindowManager.LayoutParams.WRAP_CONTENT,true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });


        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.mipmap.bubble));

        //popupWindow.showAsDropDown(view);

        popupWindow.showAtLocation(view, Gravity.LEFT|Gravity.BOTTOM,400,300);

    }
}

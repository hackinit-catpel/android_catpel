package com.muxistudio.catpel.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.muxistudio.catpel.R;

import java.util.List;



public class MyAdapter extends ArrayAdapter {

    private Context context;
    private int itemId;
    private List<BannedAppInfo> list;
    private boolean checkingArray[] = new boolean[200];
    private int checkingStatus[] = new int[200];

    public MyAdapter(Context context, int itemId, List<BannedAppInfo> list){
        super(context,itemId,list);
        this.context = context;
        this.itemId = itemId;
        this.list = list;
    }

    static class ViewHolder{
        private TextView textView;
        private CheckBox checkBox;
        private ImageView imageView;
    }



    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final BannedAppInfo info = list.get(i);
        ViewHolder viewHolder;
        if(view==null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(itemId, null);
            viewHolder.imageView = view.findViewById(R.id.app_icon);
            viewHolder.textView =  view.findViewById(R.id.app_packagename);
            viewHolder.checkBox =  view.findViewById(R.id.app_chechbox);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        if(checkingArray[i]==true){
            viewHolder.checkBox.setChecked(true);
            Log.d("click"+i+"", " set checkbox true");
        }else{
            viewHolder.checkBox.setChecked(false);
        }

        viewHolder.imageView.setImageDrawable(info.icon);
        viewHolder.textView.setText(info.packageName);
        final int a =i;
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!info.ifChecked) {
                    checkingArray[a] = true;
                    info.ifChecked = true;
                    Log.d("click" + a, "onClick: ");
                    App.bannedApplicationList.add(info.packageName);
                }else{
                    checkingArray[a] = false;
                    info.ifChecked = false;
                    App.bannedApplicationList.pop();
                    Log.d("click"+a, "cancle checked");
                }
            }
        });
        return view;
    }

}

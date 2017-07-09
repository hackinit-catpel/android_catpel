package com.muxistudio.catpel.Other;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.muxistudio.catpel.Model.App;
import com.muxistudio.catpel.Model.BannedAppInfo;
import com.muxistudio.catpel.Model.MyAdapter;
import com.muxistudio.catpel.R;

import java.util.ArrayList;
import java.util.List;


public class SelectBannedAppsActivity extends AppCompatActivity {

    private ListView listView;
    private Toolbar toolbar;
    private Button finButton;

    private String[] society = {"QQ","微信","世纪佳缘","陌陌","探探","微博","百度贴吧","钉钉","QQ空间","知乎","派派","盘丝洞","知聊","最右","拜拜","豆瓣","百度知道","抱抱"
    };
    private String[] games ={"魔法门","决斗之城","魂斗罗归来","时空猎人","我叫MT","天天炫斗","腾讯英雄杀","qq欢乐斗地主","萌格斗","奔跑吧兄弟","天天酷跑","天天爱消除","俄罗斯方块","网易梦幻西游"};
    private String[] study={"作业帮","小猿搜题","驾校一点通", "有道词典","高考帮","学霸君","英语流利说","有道翻译官","猿题库","阿凡题","完美志愿","学习通","金山词霸","扇贝单词","超级课程表","考研帮","中国大学MOOC", "有道精品课","手机知网","小站雅思","小站托福", "猿辅导","猿题库", "小猿搜题","扇贝听力", "沪江网校","学而思", "一起考教师"};
    private String[] music={"KK直播","熊猫直播","搜狐视频","爱奇艺","酷狗音乐","优酷","腾讯视频","全民K歌","哔哩哔哩","虎牙直播","酷我音乐","迅雷","乐视视频","芒果TV","虾米音乐", "暴风影音","熊猫直播","土豆视频","YY", "百度视频","唱吧","爱奇艺PPS","百度音乐","新浪体育"};
    private String[] shopping ={"1元乐购","美团外卖","蘑菇街","一元夺宝","手机淘宝","拼多多","手机京东","苏宁易购","菜鸟裹裹","天猫","大众点评","百度外卖","卷皮折扣","当当","亚马逊","天天夺宝","飞猪","飞凡","百度糯米", "贝", "聚美优品","天天特价","华为商城","分期乐","支付宝","1号店","唯品会"};

    private String intentExtra ;
    private List<BannedAppInfo> list;
    public List<BannedAppInfo> getAppInfos() {
        List<BannedAppInfo> appInfosList = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for (PackageInfo pinfo : packageInfoList) {
            if ((pinfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String name = packageManager.getApplicationLabel(pinfo.applicationInfo).toString();
                if (!name.equals("Catpel")) {
                    BannedAppInfo infos = new
                            BannedAppInfo(pinfo.applicationInfo.loadIcon(packageManager), name);
                    infos.appIntent = packageManager.getLaunchIntentForPackage(pinfo.packageName);
                    appInfosList.add(infos);
                }
            }
        }

        return appInfosList;
    }


    private List<BannedAppInfo> optionModify(List<BannedAppInfo> appInfosList){
        List<BannedAppInfo> resortList = new ArrayList<>();
        for (BannedAppInfo info : appInfosList) {
            String name = info.packageName;
            for (int i = 0; i < games.length; i++) {
                if (name.equals(games[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < society.length; i++) {
                if (name.equals(society[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < music.length; i++) {
                if (name.equals(music[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < study.length; i++) {
                if (name.equals(study[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < shopping.length; i++) {
                if (name.equals(shopping[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            resortList.add(new BannedAppInfo(info.icon,name));
        }
        return resortList;
    }

    private List<BannedAppInfo> optionLearning(List<BannedAppInfo> appInfosList){
        List<BannedAppInfo> resortList = new ArrayList<>();
        for (BannedAppInfo info : appInfosList) {
            String name = info.packageName;
            for (int i = 0; i < games.length; i++) {
                if (name.equals(games[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < study.length; i++) {
                if (name.equals(study[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < society.length; i++) {
                if (name.equals(society[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < music.length; i++) {
                if (name.equals(music[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < study.length; i++) {
                if (name.equals(study[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < shopping.length; i++) {
                if (name.equals(shopping[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
        }
        return resortList;
    }

    private List<BannedAppInfo> optionFitting(List<BannedAppInfo> appInfosList){
        List<BannedAppInfo> resortList = new ArrayList<>();
        for (BannedAppInfo info : appInfosList) {
            String name = info.packageName;
            for (int i = 0; i < games.length; i++) {
                if (name.equals(games[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < society.length; i++) {
                if (name.equals(society[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < music.length; i++) {
                if (name.equals(music[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < study.length; i++) {
                if (name.equals(study[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < shopping.length; i++) {
                if (name.equals(shopping[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
        }
        return resortList;
    }

    private List<BannedAppInfo> optionClass(List<BannedAppInfo> appInfosList){
        List<BannedAppInfo> resortList = new ArrayList<>();
        for (BannedAppInfo info : appInfosList) {
            String name = info.packageName;
            for (int i = 0; i < games.length; i++) {
                if (name.equals(games[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < study.length; i++) {
                if (name.equals(study[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < society.length; i++) {
                if (name.equals(society[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < music.length; i++) {
                if (name.equals(music[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < study.length; i++) {
                if (name.equals(study[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
            for (int i = 0; i < shopping.length; i++) {
                if (name.equals(shopping[i])) {
                    resortList.add(new BannedAppInfo(info.icon, name));
                }
            }
        }
        return resortList;
    }

    private  void initview(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        finButton = (Button) findViewById(R.id.fin_banned);
        finButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderAlertDialog();
            }
        });
        listView = (ListView) findViewById(R.id.select_banedapp_list);
        list = getAppInfos();
        TextView textView = new TextView(this);
        textView.setText("推荐禁用的App");
        textView.setTextSize(35);
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        listView.addHeaderView(textView);
        MyAdapter adapter = new MyAdapter(SelectBannedAppsActivity.this,R.layout.banned_app_item,
                list);
        listView.setAdapter(adapter);


    }

    private void builderAlertDialog(){
        final List<String> list = App.bannedApplicationList;
        Log.d("list", list.size()+"");
        String names = "";
        int count = 0;
        for (String appname:list){
            names += appname+" ";
        }
        Log.d("names",  names);
        new AlertDialog.Builder(SelectBannedAppsActivity.this).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(SelectBannedAppsActivity.this
                        , com.muxistudio.catpel.CompelSystem.LoginActivity.class);
                        dialogInterface.dismiss();
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("重新选择", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        App.removeAll(App.bannedApplicationList);
                        Log.d("list", App.bannedApplicationList.size()+"");
                        dialogInterface.dismiss();
                    }
                })
                .setTitle("选择禁用的应用的列表")
                .setMessage(names).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_banned_apps);

        initview();

        Intent intent = getIntent();
        intentExtra = intent.getStringExtra("intent");

        if(intentExtra.equals("1")){
            optionLearning(list);
        }
        if(intentExtra.equals("2")){
            optionFitting(list);
        }
        if(intentExtra.equals("3")){
            optionClass(list);
        }
        if(intentExtra.equals("4")){
            optionModify(list);
        }
    }
}

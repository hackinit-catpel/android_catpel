package com.muxistudio.catpel.CompelSystem;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.muxistudio.catpel.Model.App;
import com.muxistudio.catpel.Model.BannedAppInfo;
import com.muxistudio.catpel.Model.IRetrofit;
import com.muxistudio.catpel.POJO.GetBackData;
import com.muxistudio.catpel.POJO.SendFatherInfo;
import com.muxistudio.catpel.Utils.SensorUtils;
import com.muxistudio.catpel.POJO.UserInfo4;
import com.muxistudio.catpel.POJO.UserPetInfo;
import com.muxistudio.catpel.R;
import com.muxistudio.catpel.Utils.HomeWatcher;
import com.muxistudio.catpel.Utils.PopUpWindowUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static com.muxistudio.catpel.CompelSystem.MyService.timer;
import static com.muxistudio.catpel.R.menu.login;


public class LoginActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener{

    //this virable aims to upload and for the ranking / pet system

    private SensorUtils sensorUtils;
    private Toolbar toolbar;
    private int SET_;
    private boolean flag = true;
    private int LAYOUT_WIDTH;
    private boolean sonOffLine;

    private boolean isSet = false;

    private ImageView catAvatar,shader;

    private int drawables[] = {
            R.mipmap.categg,R.mipmap.claw2,R.mipmap.egg,
            R.mipmap.avatar,R.mipmap.ic_launcher
    };

    private int getRandom(){
        long index = System.currentTimeMillis()%100%5;
        return drawables[(int) index];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sensorUtils = new SensorUtils(LoginActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.nav_header_login,
                (ViewGroup) findViewById(R.id.nav_header));
        TextView username = dialog.findViewById(R.id.user_mimutes_display);

        username.setText("fuck");

        //setReceiver();

        setRetrofit();

        initView();


        getUserPetInfo();

        watcher = new HomeWatcher(this);
        watcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                getForgive();
            }

            @Override
            public void onHomeLongPressed() {

            }
        });

        watcher.startWatch();

        excuteAnimation();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(0);
        toolbar = (Toolbar) findViewById(R.id._toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.watch));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        inflateMenuWithAccessiabelApps(navigationView);
    }

    private void setToolbar(int timeSet,int timeLeft){
        ViewGroup.LayoutParams layoutParams  = toolbar.getLayoutParams();
        float per = timeLeft/timeSet;
        if(flag){
            LAYOUT_WIDTH = (layoutParams.width)/timeSet;
            flag = false;
        }
        layoutParams.width = layoutParams.width - LAYOUT_WIDTH;
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
        toolbar.setLayoutParams(layoutParams);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(timer!=null) {
            MyService.timer.cancel();
        }
        isSet = false;
        MyService.COUNT_DOWN = 2;
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
        buildDialogWithForgive();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorUtils.registerSensor();
        setHandler();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorUtils.unRegisterSensor();
    }

    @Override
    protected void onStop() {
        Intent intent = new Intent (LoginActivity.this,MyService.class);
        if(!isSet) {
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        }
        bindService(intent,serviceConnection3,BIND_AUTO_CREATE);
        super.onStop();
    }


    private static  final int UP_DATE_TIME = 5000;
    private int lastUpData = 0 ;
    private int SET_HOUR;
    private int SET_MINUTE;
    private String PET_STATUS = "未孵化";

    private int timeMillis = 0;

    private boolean isMode1 ;


    private DrawerLayout loginLayout;
    //to display time in fractionget

    private static TextView timeContainer;
    private TextView timeSlash;
    private TextView timeContainer2;
    private TextView pertatus;
    private TextView userName;
    private TextView userTime;
    private TextView userPetStatus;

    private TextView fiveM,tenM,tFM;

  //  private MyBroastReceiver receiver;

    private  static ImageView animalImage;

    private int seconds;

    private Handler handler;

    private String exitMessage0[] = {
            "主人主人，我还没有孵化呢～",
            "看着我孵化成功你再走，求你了！"
    };
    private String exitMessage1[] = {
            "别走，别走，保持注意力集中～喵～",
            "你就想这样退出然后留下人家喵？！",
            "规定时间还没到呢，主人～喵～",
    };

    private String exitMessage2[] = {
            "走开！铲屎的.，放下本喵，安心工作！"
    };

    private MyService.MyBinder myBinder,myBinder2,myBinder3;

    private HomeWatcher watcher;

    private Retrofit retrofit;
    private SensorManager sensorManager;
    private Sensor sensor;
    private IRetrofit iRetrofit;
    private OkHttpClient client;
    HttpLoggingInterceptor interceptor =
            new HttpLoggingInterceptor();


    private void setHandler(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getUserPetInfo();
                getChildrenOffLine();

            }
        },6000,6000);
    }


    private void setRetrofit(){

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://119.29.147.14/api/v1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        iRetrofit = retrofit.create(IRetrofit.class);

    }

    private void buildDialogWithForgive(){
        switch (App.FORGIVE_TIME){
            case 4:
                buildDialog("亲爱的主人","点击home按键挂起我是会让我减一秒的哟","我错了",null);
                break;
            case 3:
                buildDialog("智障铲屎官！！！","不要点击home按键了，我会减少成长值！！","我错了",null);
                break;
            case 2:
                buildDialog("你的主子hin不高兴","不要点击Home键,好好学习,行不行！！","我错了",null);
                break;
            case 1:
                buildDialog("你的主子危在旦夕","你要是在点击home键,我就要死啦QAQ","我错了","剩余次数1");
                break;
            case 0:
                buildDialog("你的主子就这样和你惜别了","see you~~","我错了",null);
        }
    }

    private void getUserPetInfo(){

        String userIdString =String.valueOf(App.userId).concat("/") ;
        Call<UserPetInfo> call = iRetrofit.getUserPetInfo(App.userId);
        call.enqueue(new Callback<UserPetInfo>() {
            @Override
            public void onResponse(Call<UserPetInfo> call, Response<UserPetInfo> response) {

                UserPetInfo petInfo = response.body();
                if(response.code()==200) {
                    App.TIME = petInfo.getTime();
                    App.FORGIVE_TIME = petInfo.getForgive_time();
                    App.TIME_REF = petInfo.getTime();

                }

                float timeF = petInfo.getTime()/60;
                if(timeF==0){

                }
                Log.d("timeFFF", timeF+"");
                timeContainer.setText(timeF+"");
                timeContainer2.setText(Rank.setTimeContainer2((int)petInfo.getTime())+"");


                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View view =  navigationView.getHeaderView(0);

                userName = view.findViewById(R.id.user_name_display);
                userTime = view.findViewById(R.id.user_mimutes_display);
                userPetStatus = view.findViewById(R.id.user_pet_status_display);

              //  userName.setText(App.userName);

                int seconds = App.TIME%60;
                int minutes = App.TIME/60;
                userTime.setText(minutes+"min"+seconds+"sec");
                userPetStatus.setText(Rank.setPetStatus(petInfo.getTime())+"");
                userName.setText(App.userName);

            }

            @Override
            public void onFailure(Call<UserPetInfo> call, Throwable t) {
                Log.d("getinfo failed", "onFailure: ");
            }
        });


    }


    private void getChildrenOffLine(){
        Log.d("getChildsoffline", "getChildrenOffLine: ");
        SendFatherInfo info = new SendFatherInfo(App.userId);
        Call<GetBackData> call = iRetrofit.getOffLineInfo(info);
        call.enqueue(new Callback<GetBackData>() {
            @Override
            public void onResponse(Call<GetBackData> call, Response<GetBackData> response) {
                if(!sonOffLine){
                    sonOffLine = true;
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("您的孩子退出了CatPel")
                            .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();

                }
            }

            @Override
            public void onFailure(Call<GetBackData> call, Throwable t) {

            }
        });
    }

    private void getForgive(){
        Log.d("forgive", "getForgive: ");
        UserInfo4 info4 = new UserInfo4(App.userId);
        Call<Integer> call = iRetrofit.forgiveMe(info4);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("success", "onResponse: ");
                int i = response.body();
                App.FORGIVE_TIME = i;
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    private String getRandomMessage() {
        long randomRoot = System.currentTimeMillis();
        String message  = new String();
        if (App.SET_SECONDS < Rank.YOUNG_CAT) {
            int random = (int) ((randomRoot % 100) % (exitMessage0.length));
            message = exitMessage0[random];
        }
        if(App.SET_SECONDS>= Rank.YOUNG_CAT && App.SET_SECONDS<=Rank.ADULT_CAT){
            int random = (int) ((randomRoot % 100) % (exitMessage1.length));
            message = exitMessage1[random];
        }
        if(App.SET_SECONDS>Rank.ADULT_CAT){
            int random = (int) ((randomRoot % 100) % (exitMessage2.length));
            message = exitMessage2[random];
        }
        return message;
    }


    private ServiceConnection serviceConnection3 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder3 = (MyService.MyBinder) iBinder;
            myBinder3.notifyOffLine();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    } ;

    //this connection aims to count down for the time the app suspended
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder = (MyService.MyBinder) iBinder;
            myBinder.setScheduel(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    //this connection set countdown for add points to user's pet
    private ServiceConnection serviceConnection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder2 = (MyService.MyBinder) iBinder;
            myBinder2.setCountDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };



    private void initView(){
        shader = (ImageView) findViewById(R.id.avatar_shader);
        catAvatar = (ImageView) findViewById(R.id.cat_avatar);

        shader.bringToFront();
        catAvatar.bringToFront();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        SensorEventListener sensorEventListener = null;
        sensor = sensorManager.getDefaultSensor(TYPE_ACCELEROMETER);
        int time = (int) (System.currentTimeMillis() - lastUpData);
        if(time<UP_DATE_TIME) {
            sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    buildDialog("你的主子给你发送了一条消息", "放下手机，专心学习",
                            "好的,我知道了", null);
                    lastUpData = (int) System.currentTimeMillis();
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };

        }
        if(sensorEventListener!=null) {
            sensorManager.registerListener(sensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }

        animalImage = (ImageView) findViewById(R.id.animal_img);
        animalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpWindowUtils utils = new PopUpWindowUtils(view,LoginActivity.this);
                utils.setPopUpView(view);
            }
        });

        timeContainer = (TextView) findViewById(R.id.time_container);
        String s1 = App.SET_SECONDS +"";
        timeContainer.setText(s1);

        timeContainer2 = (TextView) findViewById(R.id.time_container2);
        String s2  =Rank.setTimeContainer2(App.TIME) +"";
        timeContainer2.setText(s2);

        pertatus = (TextView) findViewById(R.id.pet_status);
        pertatus.setText(Rank.setPetStatus(App.TIME));


        loginLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void setToDefault(TextView view){
        view.setTextSize(70);
        view.setTextColor(getResources().getColor(R.color.colorGrau));
    }

    private void highLight(TextView view){
        view.setTextSize(80);
        view.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void buildDialogWithPicker() {

        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.alertdialog_layout,
                (ViewGroup) findViewById(R.id.dialog));
        TimePicker AtimePicker = dialog.findViewById(R.id.alert_time_picker);
        AtimePicker.setIs24HourView(true);
        TextView AText = dialog.findViewById(R.id.cur_display);

        fiveM = dialog.findViewById(R.id.five_minutes);
        tenM = dialog.findViewById(R.id.ten_minutes);
        tFM = dialog.findViewById(R.id.tfive_minutes);

        fiveM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highLight(fiveM);
                setToDefault(tenM);
                setToDefault(tFM);
                seconds  = 5*60;
                isMode1 = true;
            }
        });


        tenM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highLight(tenM);
                setToDefault(fiveM);
                setToDefault(tFM);
                seconds = 10*60;
                isMode1 = true;
            }
        });

        tFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highLight(tFM);
                setToDefault(tenM);
                setToDefault(fiveM);
                seconds = 25*60;
                isMode1 = true;
            }
        });

        SimpleDateFormat df = new SimpleDateFormat("H:mm:ss");//设置日期格式
        AText.setText("当前时间为" + df.format(new Date()));
        AtimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {

                SimpleDateFormat formatter= new SimpleDateFormat ("HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str[] = formatter.format(curDate).split(":");
                int TIME_SECONDS = Integer.parseInt(str[0])*3600+
                        Integer.parseInt(str[1])*60 + Integer.parseInt(str[2]);
                App.SET_SECONDS =  (-TIME_SECONDS +(i * 3600 + i1 * 60));
                if(App.SET_SECONDS<0 ) {
                    if (App.alertDialogHintTimes == 1) {
                        App.alertDialogHintTimes--;
                        Toast.makeText(LoginActivity.this, "时间小于当前时间", Toast.LENGTH_SHORT).show();
                    }
                        App.SET_SECONDS = 0;
                }
                Log.d("SET_SECOND",App.SET_SECONDS+"");
                SET_HOUR = i;
                SET_MINUTE = i1;
            }
        });
        new AlertDialog.Builder(this)
                .setTitle("设置时间")
                .setView(dialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!isMode1) {
                            Toast.makeText(LoginActivity.this, "主人，你设置时间将于 " + SET_HOUR + "点 " +
                                    +SET_MINUTE + "分" + "结束", Toast.LENGTH_SHORT).show();
                            App.TIME += App.SET_SECONDS;
                            Intent intent = new Intent(LoginActivity.this, MyService.class);
                            bindService(intent, serviceConnection2, BIND_AUTO_CREATE);

                            SET_ = App.SET_SECONDS;
                            timeMillis = App.SET_SECONDS;
                            Log.d("time_", SET_+"");
                            initCountDownTimer();
                        } else {
                            App.SET_SECONDS = seconds;
                            App.TIME += App.SET_SECONDS;
                            Toast.makeText(LoginActivity.this, "主人，你设置时间将于 " +
                                    +seconds / 60 + "分" + "结束", Toast.LENGTH_LONG).show();
                            initCountDownTimer();
                            dialogInterface.dismiss();
                        }
                    }
                })
                .setNegativeButton("重新设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        App.SET_SECONDS = 0;
                        SET_HOUR = 0;
                        SET_MINUTE = 0;
                    }
                })
                .show();
    }

    private void excuteAnimation(){
        if(App.TIME<Rank.YOUNG_CAT){
            animalImage.setBackgroundResource(R.mipmap.egg);
            Animation rotateAnimation =AnimationUtils.loadAnimation(this,R.anim.egg_rotate);
            rotateAnimation.setRepeatCount(-1);
            animalImage.startAnimation(rotateAnimation);
        }
        if(App.TIME>=Rank.YOUNG_CAT&&App.TIME<=Rank.ADULT_CAT){
           // animalImage.setBackgroundResource(R.mipmap.youngcat);
            animalImage.setImageResource(R.drawable.youngcat_moving_ears);
            AnimationDrawable ani = (AnimationDrawable) animalImage.getDrawable();
            ani.start();
        }
        if(App.TIME>Rank.ADULT_CAT){
            //long random
            animalImage.setImageResource(R.drawable.adult_cat_walking);
            AnimationDrawable animationDrawable = (AnimationDrawable) animalImage.getDrawable();
            animationDrawable.start();
            Animation translationAnimation = AnimationUtils.loadAnimation(this,
                    R.anim.adult_cat_walking);
            animalImage.startAnimation(translationAnimation);

        }
    }
    //if press back, message will be something tell user to lay down his phone
    //and the time left
    //if press setting time, the message will be time will be set
    private void buildDialog(CharSequence title,CharSequence message,CharSequence pWords
    ,CharSequence nWords){
        if(nWords == (null)) {
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage(message)
                    .setTitle(title)
                    .setPositiveButton(pWords, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }else{
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage(message)
                    .setTitle(title)
                    .setPositiveButton(pWords, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(nWords, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        }
    }

    private void initCountDownTimer(){
        CountDownTimer timer = new CountDownTimer(timeMillis*1000+2000,1000) {
            @Override
            public void onTick(long l) {
                toolbar.setTitle(timeMillis+"");
                setToolbar(SET_,timeMillis);
                timeMillis--;
            }

            @Override
            public void onFinish() {
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                toolbar.setLayoutParams(params);
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        };
        timer.start();
    }

    private void inflateMenuWithAccessiabelApps(NavigationView navigationView){
        List<BannedAppInfo> accessiableAppList = getAppInfosWithoutBanned();
        int size = accessiableAppList.size();
        for(int i=0;i<size;i++){
            final BannedAppInfo info = accessiableAppList.get(i);
//            Drawable drawable = getResources().getDrawable(info.resourceId);
            navigationView.getMenu().add(info.packageName).setIcon(getRandom()).setOnMenuItemClickListener(
                    new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            isSet = true;
                            if(info.appIntent!=null) {
                                startActivity(info.appIntent);
                            }
                            Log.d("TTTTTT", "onMenuItemClick: ");
                            return true;
                        }
                    }
            );
        }
    }

    public List<BannedAppInfo> getAppInfosWithoutBanned(){
        List<BannedAppInfo> appInfosList = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for(PackageInfo pinfo:packageInfoList) {
            if ((pinfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String name = packageManager.getApplicationLabel(pinfo.applicationInfo).toString();
                if (!App.bannedApplicationList.contains(name)) {
                    BannedAppInfo infos = new
                            BannedAppInfo(pinfo.applicationInfo.loadIcon(packageManager), name);
                    infos.appIntent = packageManager.getLaunchIntentForPackage(pinfo.packageName);
                    appInfosList.add(infos);
                }
            }
        }
        return appInfosList;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            buildDialog("想要退出CatPel吗？",
                    getRandomMessage(),"我选择妥协！",null);
        }if(keyCode==KeyEvent.KEYCODE_MENU){
            buildDialog(null,"你以为点击menu键我就不知道了吗？","我选择妥协",null);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            buildDialogWithPicker();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

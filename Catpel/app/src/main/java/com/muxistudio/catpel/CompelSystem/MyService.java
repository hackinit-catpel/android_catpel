package com.muxistudio.catpel.CompelSystem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.muxistudio.catpel.Model.App;
import com.muxistudio.catpel.Model.IRetrofit;
import com.muxistudio.catpel.POJO.SendBackStatus;
import com.muxistudio.catpel.POJO.SendInfo;
import com.muxistudio.catpel.POJO.UserInfo3;
import com.muxistudio.catpel.R;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyService extends Service {

    public static Timer timer;

    public static int COUNT_DOWN = 2;

    private MyBinder myBinder = new MyBinder();

    private String notify[]= {"铲屎的！赶紧回到CatPel，饶你不死",
                              "就算你用home挂起我，我也可以暗中观察"
    };

    private String finishedNotify[] = {"主人，你完成了任务啦～～",
                                       "完成了任务了呢，铲屎官，那你不是很棒棒～～",
    };

    private boolean STOP_FLAG = false;
    private int HINT_TIMES = 2;
    private Retrofit retrofit;
    private IRetrofit iRetrofit;
    private OkHttpClient client;
    HttpLoggingInterceptor interceptor =
            new HttpLoggingInterceptor();



    private void upLoadTime(){
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://119.29.147.14/api/v1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        iRetrofit = retrofit.create(IRetrofit.class);

        UserInfo3 userInfo3 = new UserInfo3(App.userId,App.TIME);
        Call<Integer> call = iRetrofit.uploadTime(App.userId,userInfo3);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code()==200){
                    Log.d("success", "onResponse: ");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    private int offerRandomIndex(int textType){
        long randomRoot = System.currentTimeMillis();
        int index = 0;
        switch (textType){
            case 0:
                index = (int) (randomRoot%100%notify.length);
                break;
            case 1:
                index = (int) (randomRoot%finishedNotify.length%100);
        }
        return index;
    }


    class MyBinder extends Binder {

        public void setScheduel(boolean isSet) {
            if (isSet == true) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Log.d(COUNT_DOWN + "", COUNT_DOWN + "");
                        COUNT_DOWN--;
                        if (COUNT_DOWN < 0) {
                            HINT_TIMES--;
                            if (HINT_TIMES < 0) {
                                App.TIME = App.TIME + App.SET_SECONDS;
                                setNotify(notify[offerRandomIndex(0)],
                                        "你的主子给你发送了一条消息", "back");
                            }
                        }
                    }
                }, 1000, 1000);
            }
        }

        public void notifyOffLine(){
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://119.29.147.14/api/v1.0/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            iRetrofit = retrofit.create(IRetrofit.class);

            SendInfo info = new SendInfo(App.userId);
            Call<SendBackStatus> call = iRetrofit.sendOffLineInfo(info);
            call.enqueue(new Callback<SendBackStatus>() {
                @Override
                public void onResponse(Call<SendBackStatus> call, Response<SendBackStatus> response) {
                    Log.d("sendtoparents", "success");
                }

                @Override
                public void onFailure(Call<SendBackStatus> call, Throwable t) {

                }
            });
        }

        public void setCountDown() {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    App.SET_SECONDS--;
                    if(App.SET_SECONDS<0) {
                        if (!STOP_FLAG) {
                            upLoadTime();
                            setNotify(finishedNotify[offerRandomIndex(1)],
                                    "你的主子给你发送了一条消息", "back");
                        }
                        STOP_FLAG = true;
                        timer.cancel();
                    }
                }
            },1000, 1000);
        }

    }
        public void setNotify(CharSequence title,CharSequence text,CharSequence ticker){
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setContentTitle(title)
                    .setContentText(text)
                    .setTicker(ticker)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(false)
                    //.setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.mipmap.icon);

            Intent intent = new Intent(MyService.this,LoginActivity.class);
            PendingIntent pi = PendingIntent.getActivity(MyService.this,0,
                    intent,PendingIntent.FLAG_CANCEL_CURRENT);

            manager.notify(0,builder.build());
        }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    @Override
    public void onCreate() {
        timer = new Timer();
        super.onCreate();
    }
}

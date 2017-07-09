package com.muxistudio.catpel.Other;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muxistudio.catpel.Model.App;
import com.muxistudio.catpel.Model.IRetrofit;
import com.muxistudio.catpel.POJO.UsrInfo;
import com.muxistudio.catpel.POJO.UsrInfo2;
import com.muxistudio.catpel.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView regiText;
    private EditText usrPassword;
    private TextView faultHint;
    private EditText usrName;
    private Button loginBtn;

    private String usrNameStr;
    private String usrPassWordStr;

    private RelativeLayout relativeLayout;

    private Retrofit retrofit;
    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    private OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor).build();
    private IRetrofit iRetrofit;


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.regi_text:
                boolean isconnected = new Connectivity(LoginActivity.this).ifConnected();
                if(isconnected) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("主人～，检测到你没有联网")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setPositiveButton("使用离线登录模式", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(LoginActivity.this,ModifyActivity.class);
                                    startActivity(intent);
                                    dialogInterface.dismiss();
                                }
                            }).show();

                }
                finish();
                break;
            case R.id.login_button:
                usrPassWordStr = usrPassword.getText().toString();
                usrNameStr  = usrName.getText().toString();
                loginAUser();
                break;
        }
    }

    private void loginAUser(){
    //    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://119.29.147.14/api/v1.0/")
                .client(client)
                .build();
        iRetrofit = retrofit.create(IRetrofit.class);
        UsrInfo info = new UsrInfo(usrNameStr,usrPassWordStr);
        Call<UsrInfo2> call = iRetrofit.loginUser(info);
        call.enqueue(new Callback<UsrInfo2>() {
            @Override
            public void onResponse(Call<UsrInfo2> call, Response<UsrInfo2> response) {
                int status = response.code();
                if(status==403){
                    faultHint.setVisibility(View.VISIBLE);
                    usrPassword.setText("");
                    counterWrongHint();
                }
                if(status==200) {
                    UsrInfo2 usrInfo = response.body();
                    App.userToken = usrInfo.getToken();
                    App.userId = usrInfo.getId();
                    App.userName = usrNameStr;
                    Intent intent = new Intent(LoginActivity.this, ModifyActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UsrInfo2> call, Throwable t) {

            }
        });
    }

    private void counterWrongHint(){
        CountDownTimer timer = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long l) {
                usrPassword.setBackgroundResource(R.drawable.hint_fault_bkg);
            }

            @Override
            public void onFinish() {
                usrPassword.setBackgroundResource(R.drawable.pet_shape);
            }
        };
    }

    private void initView(){
        regiText = (TextView) findViewById(R.id.regi_text);
        regiText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        faultHint = (TextView) findViewById(R.id.hint_fault);

        usrPassword = (EditText) findViewById(R.id.user_password);
        usrName = (EditText) findViewById(R.id.user_name);
        loginBtn = (Button) findViewById(R.id.login_button);

        usrPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        relativeLayout = (RelativeLayout) findViewById(R.id.login_layout);

        regiText.setOnClickListener(this);
        loginBtn.setOnClickListener(this);


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        initView();
//        LitePal.getDatabase();
    }
}

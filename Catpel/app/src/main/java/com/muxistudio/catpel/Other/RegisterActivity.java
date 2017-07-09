package com.muxistudio.catpel.Other;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.muxistudio.catpel.Model.App;
import com.muxistudio.catpel.Model.CreateUserId;
import com.muxistudio.catpel.Model.IRetrofit;
import com.muxistudio.catpel.POJO.UserInfo0;
import com.muxistudio.catpel.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RegisterActivity extends AppCompatActivity{

    private EditText usrName;
    private EditText usrPassWord;
    private EditText retypeUsrpassword;

    private Button regiBtn;

    private RelativeLayout relativeLayout;

    private String iUsrpassword;
    private String iUsrName;

    private Retrofit retrofit;
    private HttpLoggingInterceptor interceptor;
    private IRetrofit iRetrofit;

    private void registerAUser() {
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://119.29.147.14/api/v1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        iRetrofit = retrofit.create(IRetrofit.class);
        String userName = iUsrName+"";
        String userPassWord = iUsrpassword+"";
        UserInfo0 info = new UserInfo0(userName,userPassWord,"zk");
        Call<CreateUserId> call = iRetrofit.registerUser(info);
        call.enqueue(new Callback<CreateUserId>() {
            @Override
            public void onResponse(Call<CreateUserId> call, Response<CreateUserId> response) {
                int status = response.code();
                if (status == 400) {
                    Snackbar.make(relativeLayout, "用户名重复", Snackbar.LENGTH_SHORT)
                            .show();
                }
                if (status == 200) {
                    App.userId = response.body().getCreated();
                    Intent intent = new
                            Intent(RegisterActivity.this, SelectBannedAppsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CreateUserId> call, Throwable t) {

            }
        });
    }

    private void initView(){
        usrName = (EditText) findViewById(R.id.user_name);;
        usrPassWord  = (EditText) findViewById(R.id.user_password);
        retypeUsrpassword = (EditText) findViewById(R.id.retype_user_password);
        regiBtn = (Button) findViewById(R.id.regi_button);

        relativeLayout = (RelativeLayout) findViewById(R.id.login_regi);

        regiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iUsrpassword = usrPassWord.getText().toString();
                iUsrName = usrName.getText().toString();

                if(!iUsrpassword.equals(retypeUsrpassword.getText().toString())){
                    Snackbar.make(relativeLayout,"两次输入的密码不相同",Snackbar.LENGTH_SHORT)
                            .show();
                }
                registerAUser();
            }
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }
}

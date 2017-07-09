package com.muxistudio.catpel.Model;

import com.muxistudio.catpel.POJO.GetBackData;
import com.muxistudio.catpel.POJO.SendBackStatus;
import com.muxistudio.catpel.POJO.SendFatherInfo;
import com.muxistudio.catpel.POJO.SendInfo;
import com.muxistudio.catpel.POJO.UserInfo0;
import com.muxistudio.catpel.POJO.UserInfo3;
import com.muxistudio.catpel.POJO.UserInfo4;
import com.muxistudio.catpel.POJO.UserPetInfo;
import com.muxistudio.catpel.POJO.UsrInfo;
import com.muxistudio.catpel.POJO.UsrInfo2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;



public interface IRetrofit {

   @POST("signup/")
    Call<CreateUserId> registerUser(@Body UserInfo0 userInfo0);

    @POST("signin/")
    Call<UsrInfo2> loginUser(@Body UsrInfo usrInfo);

    @GET("getinfo/{userid}/")
    Call<UserPetInfo> getUserPetInfo(@Path("userid")int userid);

    @PUT("uploadtime/{userid}/")
    Call<Integer> uploadTime(@Path("userid") int userid,
            @Body UserInfo3 info3);

    @POST("forgive/")
    Call<Integer> forgiveMe(@Body UserInfo4 info4);

    @POST("sendinfo/")
    Call<SendBackStatus> sendOffLineInfo(@Body SendInfo sendInfo);

    @POST("get/")
    Call<GetBackData> getOffLineInfo(@Body SendFatherInfo fatherInfo);

}

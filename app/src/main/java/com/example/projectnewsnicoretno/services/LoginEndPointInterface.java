package com.example.projectnewsnicoretno.services;

import com.example.projectnewsnicoretno.model.UserData;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginEndPointInterface {

    // Trailing slash is needed
    String BASE_URL = "https://talentpool.oneindonesia.id";

    @Headers("X-API-Key: 454041184B0240FBA3AACD15A1F7A8BB")
    @POST ("/api/user/login")
    Call<UserData> getUserDetail(@Field("username") String username,
                                   @Field("password") String password);

}

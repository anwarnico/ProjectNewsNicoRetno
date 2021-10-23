package com.example.projectnewsnicoretno.ui;

import com.example.projectnewsnicoretno.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginEndPointInterface {

    // Trailing slash is needed
    String URL = "https://talentpool.oneindonesia.id/api/user";

    @POST ("/login")
    Call<List<User>> checkUserCredential();

}

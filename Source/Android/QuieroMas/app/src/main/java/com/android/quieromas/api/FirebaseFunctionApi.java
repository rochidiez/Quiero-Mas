package com.android.quieromas.api;

import com.android.quieromas.BuildConfig;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lucas on 23/5/17.
 */

public interface FirebaseFunctionApi {

    String SERVICE_ENDPOINT = "https://us-central1-quiero-mas.cloudfunctions.net/";

    @GET("registrar")
    Observable<ResponseBody> register(@Query("text") String text);
}

package com.android.quieromas.api;

import com.android.quieromas.BuildConfig;
import com.android.quieromas.model.api.ShareParams;
import com.android.quieromas.model.api.ShoppingListParams;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lucas on 23/5/17.
 */

public interface FirebaseFunctionApi {

    String SERVICE_ENDPOINT = "https://us-central1-quiero-mas.cloudfunctions.net/";

    @GET("registrar")
    Observable<ResponseBody> register(@Query("text") String text);

    @POST("enviarLista")
    Observable<ResponseBody> sendList(@Body ShoppingListParams shoppingListParams);

    @POST("recomendar")
    Observable<ResponseBody> share(@Body ShareParams shareParams);
}

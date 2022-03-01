package com.mertkadir.cryptoreview.service;

import android.hardware.lights.LightState;

import com.mertkadir.cryptoreview.model.CryptoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {
    //https://api.nomics.com/v1/currencies/ticker?key=f7088d9112d20d3f4ac37ab72000c247055e985c


    @GET("ticker?key=f7088d9112d20d3f4ac37ab72000c247055e985c")
    Call<List<CryptoModel>> getData();

}

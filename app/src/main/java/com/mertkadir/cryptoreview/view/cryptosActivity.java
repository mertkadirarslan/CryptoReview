package com.mertkadir.cryptoreview.view;

import static android.os.Build.VERSION_CODES.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import android.os.Handler;

import android.view.View;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mertkadir.cryptoreview.R;
import com.mertkadir.cryptoreview.adapter.RecyclerViewAdapter;
import com.mertkadir.cryptoreview.databinding.ActivityCryptosBinding;
import com.mertkadir.cryptoreview.model.CryptoModel;
import com.mertkadir.cryptoreview.service.CryptoAPI;


import java.util.ArrayList;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class cryptosActivity extends AppCompatActivity {

    private ActivityCryptosBinding binding;
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://api.nomics.com/v1/currencies/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    private Handler handler;
    private Runnable runnable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCryptosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        recyclerView = binding.recyclerView;
        //Retrofit & JSON


        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();



        new Thread(new Runnable() {
            @Override
            public void run() {
               try {

                   Timer timer = new Timer();

                   timer.scheduleAtFixedRate(new TimerTask() {
                                                 @Override
                                                 public void run() {
                                                     loadData();
                                                 }
                                             },
                           6000, 6000);

               }catch (Exception e) {

               }
            }
        }).start();


}


    private void  loadData() {


        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        Call<List<CryptoModel>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override

            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if(response.isSuccessful()) {
                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);

                    //RecyclerView
                    recyclerView.setLayoutManager(new LinearLayoutManager(cryptosActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);


                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void cryptoDetail(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DetailsFragment detailsFragment = new DetailsFragment();
        fragmentTransaction.add(com.mertkadir.cryptoreview.R.id.frame_layout,detailsFragment).commit();


    }


}
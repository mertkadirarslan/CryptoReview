package com.mertkadir.cryptoreview.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mertkadir.cryptoreview.databinding.ActivityMainBinding;
import com.mertkadir.cryptoreview.service.CryptoAPI;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


    }

    public void cryptos(View view) {
        Intent cryptos = new Intent(MainActivity.this, cryptosActivity.class);
        startActivity(cryptos);
    }
    public void favoritecryptos(View view) {
        Intent favoriteCryptos = new Intent(MainActivity.this,FavoriteCrypto.class);
        startActivity(favoriteCryptos);
    }

}
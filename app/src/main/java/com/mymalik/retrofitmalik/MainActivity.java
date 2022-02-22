package com.mymalik.retrofitmalik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mymalik.retrofitmalik.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //
        String test = "abood";
        String newBranch = "newBranch ";
        String malik = "malik AL Asttal";
        String osama = "osama Abood" ;
        String yahya = " yahya 22" ;
        //
        String base = "https://jsonplaceholder.typicode.com/" ;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(base).addConverterFactory(GsonConverterFactory.create()).build();

        MyInterface request = retrofit.create(MyInterface.class);

        request.getMyList().enqueue(new Callback<ArrayList<MyJsonObject>>() {
            @Override
            public void onResponse(Call<ArrayList<MyJsonObject>> call, Response<ArrayList<MyJsonObject>> response) {

                RecyclerMain main = new RecyclerMain(response.body(), new MyListener() {
                    @Override
                    public void onListener(MyJsonObject object) {
                        Intent intent = new Intent(getApplicationContext() , UserDetailsActivity.class);
                        intent.putExtra("key" , object );
                        startActivity(intent);
                    }
                });
                binding.recycler.setAdapter(main);
                binding.recycler.setHasFixedSize(true);
                binding.recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            }
            @Override
            public void onFailure(Call<ArrayList<MyJsonObject>> call, Throwable t) {
                binding.ttt.setText(t.getMessage());
            }
        });

    }
}
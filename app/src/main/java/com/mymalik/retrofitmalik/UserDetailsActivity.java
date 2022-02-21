package com.mymalik.retrofitmalik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mymalik.retrofitmalik.databinding.ActivityUserDetailsBinding;

public class UserDetailsActivity extends AppCompatActivity {

    ActivityUserDetailsBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MyJsonObject object = (MyJsonObject) getIntent().getSerializableExtra("key");

        binding.ttt.setText(object.toString());

        binding.addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , MapsActivity.class);
                intent.putExtra("key" , object);
                startActivity(intent);
            }
        });
    }
}
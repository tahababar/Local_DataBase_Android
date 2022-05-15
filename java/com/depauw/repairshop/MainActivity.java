package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.depauw.repairshop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonAddRepair.setOnClickListener(this);
        binding.buttonAddVehicle.setOnClickListener(this);
        binding.buttonSearchRepairs.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_add_repair:{
                Intent intent = new Intent(this, AddRepairActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_add_vehicle:{
                Intent intent = new Intent(this, AddVehicleActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_search_repairs:{
                Intent intent = new Intent(this, SearchRepairsActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
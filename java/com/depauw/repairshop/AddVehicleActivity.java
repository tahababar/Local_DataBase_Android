package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivityAddVehicleBinding;

public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityAddVehicleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVehicleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonAddVehicle.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        if (!TextUtils.isEmpty(binding.edittextYear.getText().toString()) && !TextUtils.isEmpty(binding.edittextPrice.getText().toString()) && !TextUtils.isEmpty(binding.edittextMakeModel.getText().toString())){
            String year = binding.edittextYear.getText().toString();
            String makeAndModel = binding.edittextMakeModel.getText().toString();
            float purchasePrice = Float.valueOf(binding.edittextPrice.getText().toString());
            int isNew = 0;
            if(binding.checkboxIsNew.isChecked()){
                isNew = 1;
            }
            Vehicle newVehicle = new Vehicle(year, makeAndModel, purchasePrice, isNew);
            DBHelper helper = DBHelper.getInstance(this);
            long result = helper.insertVehicle(newVehicle);
            if(result >= 0){
                Toast.makeText(this, getResources().getString(R.string.vehicle_toast_text), Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }
}
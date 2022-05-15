package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Repair;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivityAddRepairBinding;

import java.util.Calendar;
import java.util.List;

public class AddRepairActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityAddRepairBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRepairBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonAddRepair.setOnClickListener(this);
        binding.edittextRepairDate.setOnClickListener(this);

        DBHelper helper = DBHelper.getInstance(this);
        List<Vehicle> vehicles = helper.getAllVehicles();

        ArrayAdapter<Vehicle> myAdapter = new ArrayAdapter<Vehicle>(this, android.R.layout.simple_spinner_item, vehicles);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerVehicles.setAdapter(myAdapter);
    }

    private DatePickerDialog.OnDateSetListener datepicker_daterepair_dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String month = "";
            String day = String.valueOf(i2);
            int monthValue = i1 + 1;
            if(monthValue < 10){
                month = "0" + monthValue;
            }
            if(i2 < 10){
                day = "0" + i2;
            }
            String date = i + "-" + month + "-" + day;
            binding.edittextRepairDate.setText(date);
        }
    };

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_add_repair:{
                if(!TextUtils.isEmpty(binding.edittextRepairDate.getText().toString()) && !TextUtils.isEmpty(binding.edittextRepairCost.getText().toString()) && !TextUtils.isEmpty(binding.edittextRepairDescription.getText().toString())){
                    String date = binding.edittextRepairDate.getText().toString();
                    float cost = Float.valueOf(binding.edittextRepairCost.getText().toString());
                    String description = binding.edittextRepairDescription.getText().toString();
                    Vehicle thisVehicle = (Vehicle) binding.spinnerVehicles.getSelectedItem();
                    int vehicleId = thisVehicle.getVid();

                    Repair newRepair = new Repair(date, cost, description, vehicleId);
                    DBHelper helper = DBHelper.getInstance(this);

                    long result = helper.insertRepair(newRepair);
                    if(result >= 0){
                        Toast.makeText(this, getResources().getString(R.string.repair_toast_text), Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
                break;
            }
            case R.id.edittext_repair_date:{
                Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog myPicker = new DatePickerDialog(this, datepicker_daterepair_dateSetListener,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                myPicker.show();
                break;
            }
        }

    }
}
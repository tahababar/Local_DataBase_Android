package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivitySearchRepairsBinding;

import java.util.List;

public class SearchRepairsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySearchRepairsBinding binding;
    private List<RepairWithVehicle> repairWithVehicles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchRepairsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper helper = DBHelper.getInstance(this);
        repairWithVehicles = helper.getRepairsWithVehicle("");
        CustomAdapter myAdapter = new CustomAdapter(this, repairWithVehicles);
        binding.listviewResults.setAdapter(myAdapter);
        binding.listviewResults.setOnItemLongClickListener(listviewRepairs_onItemLongClickListener);
        binding.buttonFindRepairs.setOnClickListener(this);


    }

    private AdapterView.OnItemLongClickListener listviewRepairs_onItemLongClickListener = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            RepairWithVehicle repairWithVehicle = (RepairWithVehicle) adapterView.getItemAtPosition(i);
            int rid = repairWithVehicle.getRepair().getRid();
            DBHelper helper = DBHelper.getInstance(SearchRepairsActivity.this);
            int numRowsAffected = helper.deleteRepairByRepairId(rid);
            if (numRowsAffected > 0){
                Toast.makeText(SearchRepairsActivity.this, getResources().getString(R.string.delete_toast_text), Toast.LENGTH_LONG).show();
                repairWithVehicles.clear();
                String search = binding.edittextSearchPhrase.getText().toString();
                DBHelper result = DBHelper.getInstance(SearchRepairsActivity.this);
                repairWithVehicles = result.getRepairsWithVehicle(search);
                CustomAdapter myAdapter = new CustomAdapter(SearchRepairsActivity.this, repairWithVehicles);
                binding.listviewResults.setAdapter(myAdapter);
                binding.listviewResults.invalidateViews();

            }
            return true;
        }
    };


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_find_repairs:{
                String search = binding.edittextSearchPhrase.getText().toString();
                DBHelper helper = DBHelper.getInstance(this);
                repairWithVehicles = helper.getRepairsWithVehicle(search);
                CustomAdapter myAdapter = new CustomAdapter(this, repairWithVehicles);
                binding.listviewResults.setAdapter(myAdapter);
                break;
            }
        }
    }
}
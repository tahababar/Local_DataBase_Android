package com.depauw.repairshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<RepairWithVehicle> repairWithVehicle;

    public CustomAdapter(Context context, List<RepairWithVehicle> repairWithVehicle) {
        this.context = context;
        this.repairWithVehicle = repairWithVehicle;
    }

    @Override
    public int getCount() {
        return repairWithVehicle.size();
    }

    @Override
    public Object getItem(int i) {
        return repairWithVehicle.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.listview_results_row, viewGroup, false);
        }
        //System.out.println(R.id.text_repair_date);
        RepairWithVehicle thisRepairWithVehicle = repairWithVehicle.get(i);
        TextView vehicleInfo = view.findViewById(R.id.text_year_make_model);
        TextView repairDate = view.findViewById(R.id.text_repair_date);
        TextView repairCost = view.findViewById(R.id.text_repair_cost);
        TextView repairDescription = view.findViewById(R.id.text_repair_description);

        vehicleInfo.setText(thisRepairWithVehicle.getVehicle().toString());
        String date = thisRepairWithVehicle.getRepair().getDate();
        String newDate = String.valueOf(date.charAt(5)) + String.valueOf(date.charAt(6)) + "/" + String.valueOf(date.charAt(8)) + String.valueOf(date.charAt(9)) + "/" + String.valueOf(date.charAt(0)) + String.valueOf(date.charAt(1)) + String.valueOf(date.charAt(2)) + String.valueOf(date.charAt(3));

        repairDate.setText(newDate);
        repairCost.setText(String.valueOf("$ " + thisRepairWithVehicle.getRepair().getCost()));
        repairDescription.setText(thisRepairWithVehicle.getRepair().getDescription());

        return view;
    }
}

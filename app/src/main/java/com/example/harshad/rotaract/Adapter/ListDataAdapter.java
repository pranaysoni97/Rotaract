package com.example.harshad.rotaract.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.harshad.rotaract.R;
import com.example.harshad.rotaract.model.add;
import com.example.harshad.rotaract.model.addevent;

import java.util.List;

/**
 * Created by Harshad on 1/20/2018.
 */

public class ListDataAdapter extends ArrayAdapter<add> {

    private Activity context;
    private List<add> data;

    public ListDataAdapter(@NonNull Activity context,List<add> data) {
        super(context, R.layout.list_layout,data);
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View listViewData=layoutInflater.inflate(R.layout.list_layout,null,true);
        //TextView name=listViewData.findViewById(R.id.event_heading);
        //TextView prize=listViewData.findViewById(R.id.event_prize);

     //   add eventData=data.get(position);
      //  name.setText(eventData.getEventName());

        //if(eventData.getEventDescription().equals("Free")){
         ////   prize.setTextColor(Color.GREEN);
          //  prize.setText(eventData.getEventDescription());
       // }else {
        //    prize.setText("Prize : " + getContext().getResources().getString(R.string.rupees_symbol) + eventData.getEventDescription());
       // }

        return listViewData;
    }
}

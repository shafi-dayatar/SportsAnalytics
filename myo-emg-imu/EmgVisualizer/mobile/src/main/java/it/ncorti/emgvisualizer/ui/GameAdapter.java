package it.ncorti.emgvisualizer.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import it.ncorti.emgvisualizer.DTO.Game;
import it.ncorti.emgvisualizer.R;

public class GameAdapter extends ArrayAdapter<Game> {
    public GameAdapter(Context context,int resource,int resource_id, ArrayList<Game> games){
        super(context,resource,resource_id, games);
    }
    @Override
    public View getView(int position,View convertView, ViewGroup parent){
        Game game = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_list,parent,false);
        }
        System.out.println("AGGGGGGG");
        TextView time = (TextView)convertView.findViewById(R.id.start_time);
        time.setTextColor(Color.WHITE);
        return convertView;
    }
}

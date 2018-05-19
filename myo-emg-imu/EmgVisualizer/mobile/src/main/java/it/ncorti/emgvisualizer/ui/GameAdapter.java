package it.ncorti.emgvisualizer.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.ncorti.emgvisualizer.DTO.Game;
import it.ncorti.emgvisualizer.R;

public class GameAdapter extends ArrayAdapter<Game> {
    private final Context context;
    private List<Game> objects;

    public GameAdapter(Context context,List<Game> objects) {
        super(context,R.layout.game_list, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.game_list, parent, false);
        TextView textView1 = (TextView) rowView.findViewById(R.id.label1);
        TextView textView2 = (TextView) rowView.findViewById(R.id.label2);
        Game game = getItem(position);
        textView1.setText("Session : "+ Integer.toString(game.getGameid()));
        textView1.setTextColor(Color.WHITE);
        textView1.setTypeface(null, Typeface.BOLD);
        textView2.setText("Start Time : " + game.getStartTime());
        textView2.setTextColor(Color.WHITE);
        return rowView;
    }

    public void changeData(List<Game> gameData) {
        this.objects = gameData;
        notifyDataSetChanged();
    }
}

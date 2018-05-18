package it.ncorti.emgvisualizer.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import java.util.Calendar;

import it.ncorti.emgvisualizer.R;

public class SelectGame extends AppCompatActivity {

    private CalendarView CP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);

        CP = (CalendarView)findViewById(R.id.calendar1);
        CP.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day_in_month) {
                
            }
        });
    }
}

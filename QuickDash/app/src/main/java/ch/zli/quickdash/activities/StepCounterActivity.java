package ch.zli.quickdash.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import ch.zli.quickdash.R;

public class StepCounterActivity extends AppCompatActivity  {

    TextView counter;
    TextView dailyGoal;
    TextView newGoal;
    Button reset;
    Button invite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        counter = findViewById(R.id.counter);
        reset = findViewById(R.id.reset);
        dailyGoal = findViewById(R.id.dailygoal);
        invite = findViewById(R.id.invite);
        newGoal = findViewById(R.id.newGoal);

        reset.setOnClickListener(v -> {

        });

        invite.setOnClickListener(v -> {
            //invite function to be implemented
        });


    }


}

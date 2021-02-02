package ch.zli.quickdash.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import ch.zli.quickdash.R;
import ch.zli.quickdash.models.SharedPref;
import ch.zli.quickdash.services.StepCounterService;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {

    TextView counter;
    TextView dailyGoal;
    TextView newGoal;
    Button reset;
    Button invite;

    private SensorManager sensorManager;
    private Sensor sensor;

    private StepCounterService stepService;
    boolean serviceBound = false;

    SharedPreferences sharedPref;
    SharedPref sharedPreferenceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        counter = findViewById(R.id.counter);
        reset = findViewById(R.id.reset);
        dailyGoal = findViewById(R.id.dailygoal);
        invite = findViewById(R.id.invite);
        newGoal = findViewById(R.id.newGoal);

        stepService = new StepCounterService();
        sharedPreferenceModel = new SharedPref();
        sharedPref = sharedPreferenceModel.getPref(StepCounterActivity.this);

        dailyGoal.setText(sharedPreferenceModel.readGoal(sharedPref, this));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //asks for permission in device
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 1);
        }

        reset.setOnClickListener(v -> {
                stepService.reset();
                sharedPreferenceModel.editCount(stepService.getDaily());

        });

        invite.setOnClickListener(v -> {
            //invite function to be implemented
        });

        newGoal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                stepService.setGoal(Integer.parseInt(s.toString()));
                sharedPreferenceModel.editGoal(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                dailyGoal.setText(String.valueOf(stepService.getGoal()));
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        //unbindService(connection);
        stepService = null;
        serviceBound = false;
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            StepCounterService.CounterBinder binder = (StepCounterService.CounterBinder) iBinder;
            stepService = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        stepService.increment(event.values[0]);
        sharedPreferenceModel.editCount(stepService.getDaily());
        counter.setText(String.valueOf(event.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}

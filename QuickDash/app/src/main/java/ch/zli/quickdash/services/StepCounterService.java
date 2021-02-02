package ch.zli.quickdash.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Binder;
import android.os.IBinder;

import ch.zli.quickdash.models.StepCounter;

public class StepCounterService extends Service implements SensorEventListener {

    private final StepCounter stepModel = new StepCounter();

    private final IBinder binder = new CounterBinder();

    public StepCounterService(){}

    public class CounterBinder extends Binder {
        public StepCounterService getService() {
            return StepCounterService.this;
        }
    }

    public void increment(float steps){
        stepModel.setSteps(steps);
    }

    public void setGoal(int goal){
        stepModel.setGoal(goal);
    }

    public int getGoal(){return stepModel.getGoal();}

    public void reset(){
        stepModel.setSteps(0);
    }

    public float getDaily(){
        return stepModel.getSteps();
    }

    public void invite(){

    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}

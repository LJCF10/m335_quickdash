package ch.zli.quickdash.models;

import android.content.Context;
import android.content.SharedPreferences;

import ch.zli.quickdash.R;

import static android.provider.Settings.System.getString;

public class SharedPref {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SharedPreferences getPref(Context context){
        preferences  = context.getApplicationContext().getSharedPreferences(context.getString(R.string.total_count), Context.MODE_PRIVATE);
        return this.preferences;
    }

    public void editCount(float value){
        editor = this.preferences.edit();
        editor.putString("total_count", String.valueOf(value));
        editor.apply();
    }

    public void editGoal(int goal){
        editor = this.preferences.edit();
        editor.putString("goal", String.valueOf(goal));
        editor.apply();
    }

    public String readCount(SharedPreferences pref, Context context){
        return pref.getString(context.getString(R.string.total_count), "0");
    }
    public String readGoal(SharedPreferences pref, Context context){
        return pref.getString("goal", "1000");
    }


}

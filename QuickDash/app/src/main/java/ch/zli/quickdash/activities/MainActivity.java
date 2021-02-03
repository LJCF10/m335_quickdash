package ch.zli.quickdash.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ch.zli.quickdash.R;
import ch.zli.quickdash.models.SharedPref;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public ImageView image;
    Button camera_button;
    Button counter;
    TextView total_steps;

    SharedPreferences preferences;
    SharedPref sharedPreferenceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera_button  = findViewById(R.id.cam_trigger);
        image = findViewById(R.id.imageView);
        counter = findViewById(R.id.stepcounter);
        total_steps = findViewById(R.id.totalsteps);

        sharedPreferenceModel = new SharedPref();
        preferences = sharedPreferenceModel.getPref(MainActivity.this);
        total_steps.setText(sharedPreferenceModel.readCount(preferences, MainActivity.this));

        camera_button.setOnClickListener(v -> {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
        });

        counter.setOnClickListener(v -> {
            startActivity(new Intent(this, StepCounterActivity.class));
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        total_steps.setText(preferences.getString("total_count", "0"));
    }
}
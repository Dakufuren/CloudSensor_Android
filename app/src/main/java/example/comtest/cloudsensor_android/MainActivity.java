package example.comtest.cloudsensor_android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SensorHandler sh;
    LocationHandler lh;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sh = new SensorHandler(this);

        //LocationHandler not functional yet
        lh = new LocationHandler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sh.regListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sh.unregListerner();
    }
}

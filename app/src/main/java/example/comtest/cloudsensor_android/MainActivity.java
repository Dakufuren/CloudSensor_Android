package example.comtest.cloudsensor_android;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SensorHandler sh;
    LocationHandler lh;
    TextView gpsStatusText;
    TextView gpsLocation;
    Context mContext;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sh = new SensorHandler(this);
        mContext = this;
        //LocationHandler not functional yet
        lh = new LocationHandler(this);
        Button btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        gpsStatusText = (TextView)this.findViewById(R.id.gpsTextStatus);
        gpsLocation = (TextView)this.findViewById(R.id.gpsLocation);

        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Register sensor Listener on application resume
        sh.regListener();

    }

    @Override
    protected void onPause() {
        super.onPause();

        //Unregister sensor listener on application pause
        sh.unregListerner();
    }
}

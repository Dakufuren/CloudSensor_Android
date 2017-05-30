package example.comtest.cloudsensor_android;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActionActivity extends AppCompatActivity {

    AccelHandler sh;
    LocationHandler lh;
    TextView accelText;
    TextView locationText;
    Context mContext;
    /**
     * Id to identity LOCATION permission request.
     */
    private static final int REQUEST_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        sh = new AccelHandler(this);
        lh = new LocationHandler(this);

        final Button btnAccel = (Button) findViewById(R.id.btnAccel);
        final Button btnLocation = (Button) findViewById(R.id.btnLocation);
        accelText = (TextView) findViewById(R.id.accelText);
        locationText = (TextView) findViewById(R.id.locationText);

        btnAccel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sh.getAccelListenerStatus()==false){
                    sh.regListener();
                    btnAccel.setText("Disable Accelerometer");
                }else{
                    sh.unregListerner();
                    btnAccel.setText("Enable Accelerometer");
                    accelText.setText("Accel Data");
                }

            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(lh.getListenerStatus()==false){
                    lh.regListener();
                    btnLocation.setText("Disable Location");
                }else{
                    lh.unregListener();
                    btnLocation.setText("Enable Location");
                    locationText.setText("Location Data");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Register sensor Listener on application resume
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Unregister sensor listener on application pause
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Update your location here
                // You should still wrap your location request in try/catch to
                // handle security exceptions

            }
        }
    }
}

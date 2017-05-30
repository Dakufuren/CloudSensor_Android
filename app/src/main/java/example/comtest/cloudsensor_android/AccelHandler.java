package example.comtest.cloudsensor_android;

import android.app.Activity;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


/**
 * Created by AlbinSkola on 2017-05-10.
 */

public class AccelHandler implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccel;
    private Context mContext;
    private Boolean isAccelListenerEnabled = false;
    private TextView accelText;
    private TextView listenText;

    public AccelHandler(Context context){
        this.mContext = context;

        //Init the SensorManager with the Context from MainActivity
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {


        // ((Activity)mContext) use that to point at the TextView from MainActivity
        accelText = (TextView)((Activity)mContext).findViewById(R.id.accelText);

        //ADD: IF STATEMENT Sensor exists
        accelText.setText("X :" + event.values[0] + " Y :" + event.values[1] + " Z :" + event.values[2]);
    }

    public void regListener() {
        //Registers the listener in onResume -> MainAcitivity
        //SENSOR_DELAY_NORMAL: rate (default) suitable for screen orientation changes
        mSensorManager.registerListener(this, mAccel, 5000000);

        isAccelListenerEnabled = true;
    }

    public void unregListerner() {
        //Unregisters the listener in onPause -> MainAcitivity
        mSensorManager.unregisterListener(this);

        isAccelListenerEnabled = false;
    }

    public boolean getAccelListenerStatus(){

        return isAccelListenerEnabled;
    }
}

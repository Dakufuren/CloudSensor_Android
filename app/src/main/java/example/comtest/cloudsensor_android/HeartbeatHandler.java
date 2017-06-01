package example.comtest.cloudsensor_android;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Sebastian on 2017-05-30.
 */

public class HeartbeatHandler implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mHeart;
    private Context mContext;
    private Boolean isHeartListenerEnabled = false;
    private TextView heartText;
    private int INDEX_SIZE = 20;
    private double[] hArr = new double[INDEX_SIZE];
    private int count = 0;


    public HeartbeatHandler(Context context){
        this.mContext = context;

        //Init the SensorManager with the Context from MainActivity
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mHeart = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {


        // ((Activity)mContext) use that to point at the TextView from MainActivity
        heartText = (TextView)((Activity)mContext).findViewById(R.id.heartText);

        //ADD: IF STATEMENT Sensor exists
        heartText.setText("Heart data: " + event.values[0]);

        if(count<INDEX_SIZE) {

            hArr[count] = event.values[0];
            count++;
        }else {

            Toast.makeText(mContext, "POSTING HEARTRATE DATA", Toast.LENGTH_SHORT).show();
            PostData pd = new PostData(mContext);

            String loggedEmail = PreferenceManager.getDefaultSharedPreferences(mContext).getString("EMAIL", "FAILED");
            pd.postHeartrate(loggedEmail, hArr);
            count = 0;
        }
    }

    public void regListener() {
        //Registers the listener in onResume -> MainAcitivity
        //SENSOR_DELAY_NORMAL: rate (default) suitable for screen orientation changes
        mSensorManager.registerListener(this, mHeart, SensorManager.SENSOR_DELAY_NORMAL);

        isHeartListenerEnabled = true;
    }

    public void unregListerner() {
        //Unregisters the listener in onPause -> MainAcitivity
        mSensorManager.unregisterListener(this);

        isHeartListenerEnabled = false;
    }

    public boolean getHeartListenerStatus(){

        return isHeartListenerEnabled;
    }

    public double[] gethArr() {
        return hArr;
    }
}


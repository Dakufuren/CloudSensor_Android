package example.comtest.cloudsensor_android;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;


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
    private int INDEX_SIZE = 100;
    private int count = 0;
    private double[] xArr = new double[INDEX_SIZE];
    private double[] yArr = new double[INDEX_SIZE];
    private double[] zArr = new double[INDEX_SIZE];


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

        if(count<INDEX_SIZE) {
            xArr[count] = event.values[0];
            yArr[count] = event.values[1];
            zArr[count] = event.values[2];
            count++;
        }else{
            Toast.makeText(mContext, "POSTING ACCEL DATA", Toast.LENGTH_SHORT).show();
            PostData pad = new PostData(mContext);
            pad.postAccel("Albin", xArr, yArr, zArr);
            count = 0;
        }
    }

    public void regListener() {
        //Registers the listener in onResume -> MainAcitivity
        //SENSOR_DELAY_NORMAL: rate (default) suitable for screen orientation changes
        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);

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

    public double[] getxArr() {
        return xArr;
    }

    public double[] getyArr() {
        return yArr;
    }

    public double[] getzArr() {
        return zArr;
    }
}

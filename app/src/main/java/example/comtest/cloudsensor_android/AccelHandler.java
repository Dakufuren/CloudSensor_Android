package example.comtest.cloudsensor_android;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;


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
    private ArrayList<Double> xList;
    private ArrayList<Double> yList;
    private ArrayList<Double> zList;

    public AccelHandler(Context context){
        this.mContext = context;

        //Init the SensorManager with the Context from MainActivity
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        xList = new ArrayList<>();
        yList = new ArrayList<>();
        zList = new ArrayList<>();
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
        getxList().add(event.values[0]);
        getyList().add(event.values[1]);
        getzList().add(event.values[2]);
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

    public ArrayList getxList() {
        return xList;
    }

    public ArrayList getyList() {
        return yList;
    }

    public ArrayList getzList() {
        return zList;
    }

    public Double[] convertArrayList(ArrayList<Double> tempArrayList) {
        int arrSize = tempArrayList.size();
        Double[] tempArr = tempArrayList.toArray(new Double[tempArrayList.size()]);

        return tempArr;
    }
}

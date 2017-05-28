package example.comtest.cloudsensor_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by optimus prime on 2017-05-27.
 */

public class VolleyTestActivity extends AppCompatActivity {

    private TextView resultView;
    private Button sendButton;
    private String id;
    private String owner;
    //private String[] x;
    //private String[] y;
    //private String[] z;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_test);
        resultView = (TextView)findViewById(R.id.resView);
        sendButton = (Button)findViewById(R.id.bn_send);
        id = "6";
        owner = "axel";
        //x[0] = "0";
        //y[0] = "0";
        //z[0] = "0";
        System.out.println("asdasda");
        System.out.println("heeeej");
        PostAccelData pad = new PostAccelData(this);

        String[] x = {"0"};
        String[] y = {"0"};
        String[] z = {"0"};
        System.out.println("hejigen");
        pad.postToServer(id, owner, x, y, z);

    }





}

package example.comtest.cloudsensor_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by optimus prime on 2017-05-27.
 */

public class VolleyTestActivity extends AppCompatActivity {

    private TextView resultView;
    private Button sendButton;
    private Button postButton;
    private String id;
    private String owner = "Axel";
    //private String[] x;
    //private String[] y;
    //private String[] z;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_test);
        resultView = (TextView) findViewById(R.id.resView);
        sendButton = (Button) findViewById(R.id.bn_send);
        postButton =  (Button) findViewById(R.id.bn_post);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();


            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postToServer(owner,null,null,null);
            }
        });

    }

    private void sendRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
       String JSON_URL = "http://datacollectapi20170528075302.azurewebsites.net/api/Accelerometer/AllCollections";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        resultView.setText(response);
                        Toast.makeText(VolleyTestActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VolleyTestActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }) ;




        requestQueue.add(stringRequest);
    }


        public void postToServer( final String owner, final String[] x, final String[] y, final String[] z){
            String URL = "http://datacollectapi20170528075302.azurewebsites.net/api/Accelerometer/PostCollection";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            try {
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("owner", owner.toString());


                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {


                                        Toast.makeText(VolleyTestActivity.this,"Posted:" + owner,Toast.LENGTH_LONG).show();

                                } catch (Exception e) {
                                    Toast.makeText(VolleyTestActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });

                requestQueue.add(req);
            }
            catch(Exception e){

            }
    }
}
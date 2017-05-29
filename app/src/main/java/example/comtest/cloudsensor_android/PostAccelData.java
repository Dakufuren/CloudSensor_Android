package example.comtest.cloudsensor_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by optimus prime on 2017-05-27.
 */

public class PostAccelData {

    String response;

    private JsonObjectRequest request2;
    private StringRequest stringRequest;
    String URL = "http://datacollectapi20170528075302.azurewebsites.net/api/Accelerometer/PostCollection";
    Context context;
    String finalResponse = "";
    MyCallBack mCallBack;
    //RequestQueue requestQueue = Volley.newRequestQueue(this.context);

    public PostAccelData(Context context) {
        this.context = context;
        this.mCallBack = (MyCallBack) this.context;

    }
    public void postToServer( final String owner, final double[] x, final double[] y, final double[] z){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        try {
            JSONObject jsObj = new JSONObject();



            jsObj.put("owner", owner);
            JSONArray xList = new JSONArray(x);
            JSONArray yList = new JSONArray(y);
            JSONArray zList = new JSONArray(z);
            jsObj.put("x", xList);
            jsObj.put("y", yList);
            jsObj.put("z", zList);
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, jsObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {


                                Toast.makeText(context,"Posted:" + owner,Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
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
    public void sendRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String JSON_URL = "http://datacollectapi20170528075302.azurewebsites.net/api/Accelerometer/AllCollections";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finalResponse = response;
                        //resultView.setText(response);
                        Update(response.toString());
                        Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        finalResponse = error.toString();
                        Update(error.toString());
                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }) ;




        requestQueue.add(stringRequest);
    }
    public void Update(String message){

        mCallBack.UpdateMyText(message);
    }

}

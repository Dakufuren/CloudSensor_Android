package example.comtest.cloudsensor_android;

import android.app.AlertDialog;
import android.content.Context;

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
    private RequestQueue requestQueue;
    private JsonObjectRequest request2;
    private StringRequest stringRequest;
    private static String URL = "http://10.0.2.2:64623/api/Accelerometer/PostCollection";
    Context context;


    public PostAccelData(Context context) {
        this.context = context;

    }
    public String postToServer(final String id, final String owner, final String[] x, final String[] y, final String[] z){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Message: " + response);
                    }
                }

                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("Owner", owner);

                return params;
            }
        };

        requestQueue.add(stringRequest);

        return response;
    }
}

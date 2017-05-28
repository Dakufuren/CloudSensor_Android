package example.comtest.cloudsensor_android;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
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
    private static String URL = "http://datacollectapi20170528075302.azurewebsites.net/api/Accelerometer/PostCollection";
    Context context;
    RequestQueue requestQueue = Volley.newRequestQueue(this.context);

    public PostAccelData(Context context) {
        this.context = context;

    }
    public void postToServer(final String id, final String owner, final String[] x, final String[] y, final String[] z){
        try {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("id", id.toString());
            params.put("owner", owner.toString());
            params.put("x", null);
            params.put("y", null);
            params.put("z", null);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.names().get(0).equals("success")) {


                                } else {

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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

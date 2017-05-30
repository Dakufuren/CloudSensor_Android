package example.comtest.cloudsensor_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Linus on 2017-05-29.
 */

public class RegisterUserActivity extends AppCompatActivity{
    TextView weight;
    TextView height;
    TextView mail;
    TextView password;
    TextView confirmPass;
    TextView age;
            CheckBox female;
    CheckBox male;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
         weight =(TextView) findViewById(R.id.weight);
         age =(TextView) findViewById(R.id.age);
         mail =(TextView) findViewById(R.id.mailText);
         password =(TextView) findViewById(R.id.password);
         confirmPass =(TextView) findViewById(R.id.confirmPass);
         height =(TextView) findViewById(R.id.height);
        female =(CheckBox) findViewById(R.id.femaleBox);
        male =(CheckBox) findViewById(R.id.maleBox);
        Button btnAction = (Button) findViewById(R.id.btn_register);




        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postToServer();


            }
        });


    }

    public void postToServer(){
        String URL = "http://datacollectapi20170528075302.azurewebsites.net/api/User/Register";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        try {


            JSONObject jsObj = new JSONObject();



            jsObj.put("email", mail.getText().toString());
            jsObj.put("password", password.getText().toString());
            jsObj.put("confirmPassword", confirmPass.getText().toString());
            jsObj.put("weight", weight.getText().toString());
            jsObj.put("height", height.getText().toString());
            jsObj.put("age", age.getText().toString());
            if(female.isChecked())
            jsObj.put("gender", "female");
            if(male.isChecked())
                jsObj.put("gender", "male");
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, jsObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {


                                Toast.makeText(RegisterUserActivity.this,"Register:" + mail.getText().toString(),Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                Toast.makeText(RegisterUserActivity.this,"Mail already registered",Toast.LENGTH_LONG).show();
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

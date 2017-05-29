package example.comtest.cloudsensor_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Linus on 2017-05-29.
 */

public class RegisterUserActivity extends AppCompatActivity{

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        TextView weight =(TextView) findViewById(R.id.weight);
        TextView age =(TextView) findViewById(R.id.age);
        TextView mail =(TextView) findViewById(R.id.email);
        TextView password =(TextView) findViewById(R.id.password);
        TextView confirmPass =(TextView) findViewById(R.id.confirmPass);
        TextView height =(TextView) findViewById(R.id.height);
        CheckBox female =(CheckBox) findViewById(R.id.femaleBox);
        CheckBox male =(CheckBox) findViewById(R.id.maleBox);
        Button btnAction = (Button) findViewById(R.id.btn_register);


        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }
}

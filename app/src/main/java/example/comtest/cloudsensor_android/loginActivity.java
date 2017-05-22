package example.comtest.cloudsensor_android;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by optimus prime on 2017-05-22.
 */

public class loginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private LinearLayout ProfSection;
    private Button SignOut;
    private SignInButton SignIn;
    private TextView Name, Email;
    private ImageView Prof_Pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;


    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ProfSection = (LinearLayout)findViewById(R.id.prof_section);
        SignOut = (Button)findViewById(R.id.bn_logout);
        SignIn = (SignInButton)findViewById(R.id.bn_login);
        Name = (TextView)findViewById(R.id.name);
        Email = (TextView)findViewById(R.id.email);
        Prof_Pic = (ImageView)findViewById(R.id.prof_pic);
        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);

        ProfSection.setVisibility(View.GONE);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();

    }

    @Override
    public void onClick(View v) {
        //SWITCH CASE FÖR ATT SE VILKEN KNAPP SOM KLICKASTS
        switch (v.getId()) {

            case R.id.bn_login:
                signIn();
                break;

            case R.id.bn_logout:
                signOut();
                break;

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {

        //SKAPAR INTENT FÖR INLOGGNING RESULTATET HAMNAR SEDAN I onActivityResult
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);

    }

    private void signOut() {

        //LOGGAR UT FRÅN GOOGLE OCH ÄNDRAR UIET

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });

    }

    private void handleResult(GoogleSignInResult result) {

        //BEROENDE PÅ OM OM INLOGGNINGEN ÄR SANN/FALSK
        if(result.isSuccess()) {
            //HÄMTAR INFORMATIONEN FRÅN KONTOT HÄR OCH SPARAR I VARIABLER INNAN DE LÄGGS IN I TEXTVIEWS OCH UPPDATERAR UI
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            //String img_url = account.getPhotoUrl().toString();
            Name.setText(name);
            Email.setText(email);
            //Glide.with(this).load(img_url).into(Prof_Pic);
            updateUI(true);
        }
        //OM DET ÄR FALSE SÅ UPDATERAS ENDAST UI
        else {
            updateUI(false);
        }
    }

    private void updateUI(boolean isLogin) {

        //OM LOGGAS IN SÅ VISAS PROFILDELEN OCH INLOGINKNAPPEN BLIR BORTTAGEN
        if (isLogin) {
            ProfSection.setVisibility(View.VISIBLE);
            SignIn.setVisibility(View.GONE);
        }
        //OM MAN LOGGAR UT SÅ VISAS INTE PROFILEN LÄNGRE UTAN BARA INLOGGNINGSKNAPPEN
        else {
            ProfSection.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //KOLLAR IFALL REQUESTKODEN STÄMMER ÖVERENS ISÅFALL SÅ HÄMTAR VI RESULTATET OCH KÖR SEDAN HANDLERESULT
        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
}

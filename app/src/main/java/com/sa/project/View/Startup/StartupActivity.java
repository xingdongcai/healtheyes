package com.sa.project.View.Startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sa.project.Presenter.Contract.StartupContract;
import com.sa.project.Presenter.StartupPresenter;
import com.sa.project.R;
import com.sa.project.View.Patients.MainActivity;

/**
 * the startup Activity view to show the login and identify the practitioner
 */
public class StartupActivity extends AppCompatActivity implements StartupContract.View{
    private StartupContract.Presenter startupPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        SharedPreferences ap = getSharedPreferences("AllPatients",MODE_PRIVATE);
//        SharedPreferences prac = getSharedPreferences("Practitioner",MODE_PRIVATE);
//        SharedPreferences toSe = getSharedPreferences("toSelected",MODE_PRIVATE);
//        SharedPreferences practitionerPreferences = getSharedPreferences("Frequency", Context.MODE_PRIVATE);
//
//        SharedPreferences bloodPressureMonitored = getSharedPreferences("BloodPressureMonitored",Context.MODE_PRIVATE);
//        SharedPreferences cholesterolMonitored = getSharedPreferences("CholesterolMonitored",Context.MODE_PRIVATE);
//        ap.edit().clear().apply();
//        prac.edit().clear().apply();
//        toSe.edit().clear().apply();
//        cholesterolMonitored.edit().clear().apply();
//        bloodPressureMonitored.edit().clear().apply();
//        practitionerPreferences.edit().clear().apply();

    }

    /**
     * the function of submit button to authenticate the id of practitioner
     * @param v: the startupActivity view
     */
    public void onClickAuthenticate(View v){
        EditText practitionerIDText = findViewById(R.id.practitionerID);
        String practitionerID = practitionerIDText.getText().toString();
        startupPresenter = new StartupPresenter(this);
        startupPresenter.checkPractitioner(practitionerID);
    }


    /**
     * get the verification result from presenter
     * @param isExist: bool value to check the exist of practitioner id
     */
    @Override
    public void verifyPractitioner(boolean isExist) {
        if(isExist){
            //to main interface
            Intent intentToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentToMainActivity);
        }else {
            Toast.makeText(getApplicationContext(),"Please enter valid identifier",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * get current view context
     * @return: context
     */
    @Override
    public Context getViewContext() {
        return getApplicationContext();
    }

}

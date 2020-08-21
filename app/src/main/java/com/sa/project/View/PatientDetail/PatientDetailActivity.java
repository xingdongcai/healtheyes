package com.sa.project.View.PatientDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sa.project.View.OptionMenu.SystolicDetailLineView;
import com.sa.project.Presenter.Contract.PatientDetailContract;
import com.sa.project.Presenter.PatientDetailPresenter;
import com.sa.project.R;

/**
 * user interface that display the patient's detail information
 */
public class PatientDetailActivity extends AppCompatActivity implements PatientDetailContract.View {
    PatientDetailContract.Presenter patientDetailPresenter;
    String patientID;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        Intent intent = getIntent();
        View v = findViewById(android.R.id.content).getRootView();

        /*
        get the intent from SelectedPatientsFragment when the user tap specific patient
        then tell presenter to request this patient's detail form server
        */
        patientID = intent.getStringExtra("patientID");
        patientDetailPresenter = new PatientDetailPresenter(this);
        patientDetailPresenter.getPatientDetail(patientID);


    }

    /**
     * @method used by PatientDetailPresenter to update the user interface
     * @param patient: PatientDetailCard which has been filled from PatientDetailPresenter
     */
    @Override
    public void updatePatientView(PatientDetailCard patient) {
        TextView nameTextView = findViewById(R.id.patientName);
        TextView dobTextView = findViewById(R.id.patientDob);
        TextView genderTextView = findViewById(R.id.patientGender);
        TextView cityTextView = findViewById(R.id.patientCity);
        TextView stateTextView = findViewById(R.id.patientState);
        TextView countryTextView = findViewById(R.id.patientCountry);

        nameTextView.setText(patient.getFullName());
        genderTextView.setText(patient.getGender());
        dobTextView.setText(patient.getBirthDate());
        cityTextView.setText(patient.getCity());
        stateTextView.setText(patient.getState());
        countryTextView.setText(patient.getCountry());
    }

    @Override
    public Context getViewContext() {
        return getApplicationContext();
    }
}

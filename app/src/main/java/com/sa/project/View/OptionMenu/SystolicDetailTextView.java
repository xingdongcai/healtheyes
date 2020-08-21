package com.sa.project.View.OptionMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sa.project.Presenter.Contract.SystolicDetailContract;
import com.sa.project.Presenter.SystolicDetailPresenter;
import com.sa.project.R;

import java.util.ArrayList;

/**
 * this function is to display the high systolic text view with at most five systolic details with the high
 * systolic blood pressure patients.
 */
public class SystolicDetailTextView extends AppCompatActivity implements SystolicDetailContract.TextView {

    private SystolicDetailContract.Presenter systolicDetailPresenter;
    ViewGroup insertPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_acitivity);
        insertPoint = (ViewGroup) findViewById(R.id.systolic_text_view_id);
        systolicDetailPresenter = new SystolicDetailPresenter(this,getApplicationContext(),1);
        systolicDetailPresenter.initHighSystolicPatients();
    }

    /**
     * remove all previous view so that when the request fetch data from server again, the same view would be updated
     */
    @Override
    public void clearTextViews() {
        insertPoint.removeAllViews();
    }
    /**
     * this function is to display the line char based on the blood pressure and patient name
     * @param bloodPressure: the systolic blood pressure to display
     * @param effectedTime: the effected time of blood pressure
     * @param patientName: the patient name
     */
    @Override
    public void initHighSystolicPatientCardsTextView(ArrayList<Integer> bloodPressure, ArrayList<String> effectedTime, String patientName) {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.text_view, null);
        //set view content
        TextView name = v.findViewById(R.id.systolic_patient_name_id);
        name.setText(patientName);
        String systolicBloodPressureInfo="";
        for(int i = 0; i < bloodPressure.size();i++){
            systolicBloodPressureInfo = systolicBloodPressureInfo + bloodPressure.get(i).toString() + " (" + effectedTime.get(i) + "), "+ "\n";

        }
        TextView info = v.findViewById(R.id.systolic_patient_bp_id);
        info.setText(systolicBloodPressureInfo);

        insertPoint.addView(v);

    }


}

package com.sa.project.View.OptionMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.sa.project.Presenter.Contract.SystolicDetailContract;

import com.sa.project.Presenter.SystolicDetailPresenter;
import com.sa.project.R;

import java.util.ArrayList;

/**
 * this function is to display the high systolic line view with at most five systolic details with the high
 * systolic blood pressure patients.
 */
public class SystolicDetailLineView extends AppCompatActivity implements SystolicDetailContract.LineView {

    private SystolicDetailContract.Presenter systolicDetailPresenter;
    ViewGroup insertPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        insertPoint = (ViewGroup) findViewById(R.id.highSystolic_layout);
        systolicDetailPresenter = new SystolicDetailPresenter(this,getApplicationContext(),0);
        systolicDetailPresenter.initHighSystolicPatients();


    }

    /**
     * remove all previous view so that when the request fetch data from server again, the same view would be updated
     */
    public void clearLineViews(){
        insertPoint.removeAllViews();
    }

    /**
     * this function is to display the line char based on the blood pressure and patient name
     * @param bloodPressure: the list of systolic blood pressure value
     * @param patientName: the patient name
     */
    @Override
    public void initHighSystolicPatientCardsLineView(ArrayList<Integer> bloodPressure, String patientName) {

        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        if(bloodPressure.size()>0){
            for(int j = 0;j < bloodPressure.size();j++){
                dataVals.add(new Entry(j,bloodPressure.get(j)));
            }
            LayoutInflater inflater = getLayoutInflater();

            View v = inflater.inflate(R.layout.line_chart, null);
            //set view content
            TextView name = v.findViewById(R.id.highSystolic_patient_name);
            name.setText(patientName);
            LineChart lineChart = v.findViewById(R.id.LineChart);
            LineDataSet lineDataSet = new LineDataSet(dataVals,"Systolic Blood Pressure");

            lineDataSet.setColor(Color.parseColor("#FFA500"));
            lineDataSet.setValueTextColor(Color.parseColor("purple"));
            lineDataSet.setValueTextSize(20f);
            lineDataSet.setLineWidth(5f);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet);


            LineData data = new LineData(dataSets);

            lineChart.setData(data);
            lineChart.invalidate();
            //add view to Systolic activity view
            insertPoint = (ViewGroup) findViewById(R.id.highSystolic_layout);
            insertPoint.addView(v);



        }
    }
}

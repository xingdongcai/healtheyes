package com.sa.project.View.OptionMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sa.project.Presenter.Contract.SelectedPatientsContract;
import com.sa.project.Presenter.SelectedPatientsPresenter;
import com.sa.project.R;
import com.sa.project.View.Patients.PatientCard;

import java.util.ArrayList;

/**
 * this is the bar chart view to show the cholesterol value for all selected patients
 */
public class CholesterolDetailBarView extends AppCompatActivity implements SelectedPatientsContract.BarView {


    private View v;
    private SelectedPatientsContract.Presenter selectedPatientsPresenter;

    final private int BAR_VIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        selectedPatientsPresenter = new SelectedPatientsPresenter(this,getApplication(),BAR_VIEW);
        selectedPatientsPresenter.initSelectedPatients();



    }

    /**
     * this is the value formatter from MPAndroid chart which can apply the string to the label
     * this function would apply the patient name to the label which binds to cholesterol value
     */
    private class xAxisValueFormatter extends ValueFormatter {
        ArrayList<String> patientsName;


        xAxisValueFormatter(ArrayList<String> patientsName){

            this.patientsName = patientsName;

        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return patientsName.get((int)Math.round(value/5));
        }
    }

    /**
     * this function would filter the patient name
     * @param name: patient name
     * @return: filtered patient name
     */
    private String filterPatientName(String name){
        String names[] = name.split(" ");
        return names[1].replaceAll("\\d+(?:[.,]\\d+)*\\s*", "");

    }

    /**
     * this function would init the bar view by using bar chart
     * @param selectedPatientCards: the selected patient card
     */
    @Override
    public void initSelectedPatientsBarView(ArrayList<PatientCard> selectedPatientCards) {
        ArrayList<PatientCard> myList = selectedPatientCards;
        ArrayList<String> patientNames = new ArrayList<>();
        for(int i = 0; i<selectedPatientCards.size(); i++){
            patientNames.add(filterPatientName(selectedPatientCards.get(i).getPatientFullName()));
        }


        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> visitors = new ArrayList<>();

        for(int i = 0; i<myList.size();i++){
            visitors.add(new BarEntry(i*5, (float) (myList.get(i).getTotalCholesterol())));

        }
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new xAxisValueFormatter(patientNames));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setLabelCount(patientNames.size(), /*force: */true);
        //xAxis.setSpaceMin(1.5f);
        //xAxis.mAxisRange = 100;
        BarDataSet barDataSet = new BarDataSet(visitors,"visitors");
        barDataSet.setColors(Color.parseColor("#FFA500"));
        barDataSet.setValueTextColor(Color.RED);
        barDataSet.setValueTextSize(20f);
        //barDataSet.setValueFormatter(new xAxisValueFormatter(patientNames));
        BarData barData = new BarData(barDataSet);
        barChart.setHorizontalScrollBarEnabled(true);
        barChart.setFitBars(false);
        barChart.setData(barData);
        barChart.invalidate();
        //\barChart.animateY(2000);
    }
}

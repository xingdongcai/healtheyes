package com.sa.project.Presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sa.project.Model.Callbacks.SystolicDetailCallback;
import com.sa.project.Model.DataHolder.SystolicDetailMonitor;
import com.sa.project.Model.Local.LocalStorage;
import com.sa.project.Model.ServerSources.SystolicDetailMonitorContainer;
import com.sa.project.Presenter.Contract.SystolicDetailContract;
import com.sa.project.View.Patients.PatientCard;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * this is the systolic detail presenter which handle the high systolic view such as line view and text view.
 */
public class SystolicDetailPresenter implements SystolicDetailContract.Presenter{
    private SystolicDetailContract.LineView lineView;
    private SystolicDetailContract.TextView textView;
    private int mode;
    private Context context;
    final int LINE_VIEW = 0;
    final int TEXT_VIEW = 1;


    /**
     * constructor of class
     * @param lineView: the view to print the line chart
     * @param context: current context
     * @param mode: to identify the view
     */
    public SystolicDetailPresenter(SystolicDetailContract.LineView lineView, Context context, int mode){
        this.lineView = lineView;
        this.context = context;
        this.mode = mode;
    }

    /**
     * constructor of class
     * @param textView: the view to print the text chart
     * @param context: current context
     * @param mode: to identify the view
     */
    public SystolicDetailPresenter(SystolicDetailContract.TextView textView,Context context, int mode){
        this.textView = textView;
        this.context = context;
        this.mode = mode;

    }

    /**
     * the init function to display the line view and bar view as well
     * which would get the high systolic patient ids from local storage
     * and request the server for latest five effected time and the systolic value
     * finally, it displays them by starting the new view.
     */
    @Override
    public void initHighSystolicPatients() {

        ArrayList<PatientCard> highSystolicPatientCards = new ArrayList<PatientCard>();
        ArrayList<String> patientNames = new ArrayList<>();
        ArrayList<String> patientIDs = new ArrayList<>();
        String highSystolicPatientCardsString = LocalStorage.getHighSystolicPatients(this.context);
        if(!highSystolicPatientCardsString.equals("")){
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<PatientCard>>(){}.getType();
            highSystolicPatientCards = gson.fromJson(highSystolicPatientCardsString,type);

        }
        for(PatientCard patientCard:highSystolicPatientCards){
            System.out.print("systolic check"+patientCard.getPatientID());
            patientIDs.add(patientCard.getPatientID());
            patientNames.add(patientCard.getPatientFullName());
        }

        int frequency = LocalStorage.getMonitorFrequency(context);

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {

                        SystolicDetailMonitorContainer systolicDetailMonitorContainer = new SystolicDetailMonitorContainer(context);
                        systolicDetailMonitorContainer.requestSystolicDetailMonitors(patientIDs, new SystolicDetailCallback() {
                            @Override
                            public void onSuccess(ArrayList<SystolicDetailMonitor> systolicDetailMonitors) {
                                if(mode == LINE_VIEW){
                                    lineView.clearLineViews();
                                }else if(mode == TEXT_VIEW){
                                    textView.clearTextViews();
                                }
                                ArrayList<Integer> bloodPressure = new ArrayList<>();
                                ArrayList<String> effectedTime = new ArrayList<>();
                                String patientName;
                                for(int i = 0; i < systolicDetailMonitors.size(); i++){
                                    bloodPressure = systolicDetailMonitors.get(i).getSystolicBloodPressures();
                                    patientName = patientNames.get(i);
                                    effectedTime = systolicDetailMonitors.get(i).getEffectiveDateTimes();
                                    if(mode == LINE_VIEW){

                                        lineView.initHighSystolicPatientCardsLineView(bloodPressure,patientName);

                                    }else if(mode == TEXT_VIEW){
                                        textView.initHighSystolicPatientCardsTextView(bloodPressure,effectedTime,patientName);
                                    }
                                }

                                System.out.println("=======");
                                System.out.println("Request Server...");

                            }
                        });


                    }
                    //the N value is set up here
                }, 0, frequency, TimeUnit.SECONDS);

    }
}

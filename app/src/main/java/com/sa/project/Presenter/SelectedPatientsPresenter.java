package com.sa.project.Presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sa.project.Model.Callbacks.BloodPressureCallback;
import com.sa.project.Model.DataHolder.BloodPressureMonitor;
import com.sa.project.Model.ServerSources.BloodPressureMonitorContainer;
import com.sa.project.Model.ServerSources.CholesterolMonitorContainer;
import com.sa.project.Presenter.Contract.SelectedPatientsContract;
import com.sa.project.Model.Callbacks.CholesterolCallback;
import com.sa.project.Model.DataHolder.CholesterolMonitor;
import com.sa.project.Model.Local.LocalStorage;
import com.sa.project.Model.DataHolder.Patient;
import com.sa.project.View.Patients.PatientCard;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * this class is to init the selected patient fragment view and bar view
 * and handle the logic interaction from the selected patient fragment as well as bar view
 */
public class SelectedPatientsPresenter implements SelectedPatientsContract.Presenter {
    private ArrayList<Patient> selected_patients = new ArrayList<Patient>() ;
    private SelectedPatientsContract.View view;
    private SelectedPatientsContract.BarView barView;
    private Context context;
    private ScheduledFuture<?> future;

    private int mode;
    final private int BAR_VIEW = 1;
    final private int CARD_VIEW = 0;

    /**
     * constructor of class
     * @param view: selectedPatientFragment contract view
     * @param context:context
     * @param mode:identify to selectedPatientFragment
     */
    public SelectedPatientsPresenter(SelectedPatientsContract.View view, Context context,int mode){
        this.view = view;
        this.context = context;
        this.mode = mode;
    }

    /**
     * constructor of class
     * @param barView: CholesterolDetailBar contract view
     * @param context: context
     * @param mode: identify to CholesterolDetailBar
     */
    public SelectedPatientsPresenter(SelectedPatientsContract.BarView barView,Context context, int mode) {
        this.barView = barView;
        this.context = context;
        this.mode = mode;

    }

    /**
     * check the current update frequency exist
     * @return return the ScheduledFuture
     */
    @Override
    public ScheduledFuture checkUpdateTimeExist(){
        return future;
    }

    /**
     * save the current frequency and inform to view
     * @param frequency: the frequency to update
     */
    @Override
    public void informFrequency(int frequency) {
        LocalStorage.saveMonitorFrequency(this.context,frequency);
    }

    /**
     * init list of the blood pressure monitor Id from local storage
     * and inform the view to display the the patient has been monitored.
     */
    @Override
    public void initSelectedPatientsBloodPressureMonitorIds() {
        ArrayList<String> sp_bp_list = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        String sp = LocalStorage.getBloodPressureMonitoredPatients(this.context);
        sp_bp_list = gson.fromJson(sp,type);
        if(sp_bp_list == null){
            view.initSelectedPatientsBloodPressureMonitorCheckbox(new ArrayList<String>());
        }else{
            view.initSelectedPatientsBloodPressureMonitorCheckbox(sp_bp_list);
        }

    }
    /**
     * init list of the total cholesterol monitor Id from local storage
     * and inform the view to display the the patient has been monitored.
     */
    @Override
    public void initSelectedPatientsCholesterolMonitorIds() {
        ArrayList<String> sp_tc_list = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        String sp = LocalStorage.getCholesterolMonitoredPatients(this.context);
        sp_tc_list = gson.fromJson(sp,type);
        if(sp_tc_list == null){
            view.initSelectedPatientsCholesterolMonitorCheckbox(new ArrayList<String>());
        }else{
            view.initSelectedPatientsCholesterolMonitorCheckbox(sp_tc_list);
        }
    }

    /**
     * cancel the frequency to stop the instant update
     */
    @Override
    public void cancelUpdateFrequency(){
        future.cancel(true);
    }

    /**
     * save id of patients need to monitor the blood pressure to local storage
     * @param monitoredBloodPressurePatientsIDs: the id of patients need to monitor the blood pressure
     */
    @Override
    public void updateSelectedPatientsBloodPressureMonitorIds(ArrayList<String> monitoredBloodPressurePatientsIDs) {
        Gson gson = new Gson();
        String  monitoredBloodPressurePatientsIDsString =  gson.toJson(monitoredBloodPressurePatientsIDs);
        LocalStorage.saveBloodPressureMonitoredPatients(this.context,monitoredBloodPressurePatientsIDsString);

    }
    /**
     * save id of patients need to monitor the blood pressure to local storage
     * @param monitoredCholesterolPatientsIDs: the id of patients need to monitor the total cholesterol
     */
    @Override
    public void updateSelectedPatientsCholesterolMonitorIds(ArrayList<String> monitoredCholesterolPatientsIDs) {
        Gson gson = new Gson();
        String  monitoredCholesterolPatientsIDsString =  gson.toJson(monitoredCholesterolPatientsIDs);
        LocalStorage.saveCholesterolMonitoredPatients(this.context,monitoredCholesterolPatientsIDsString);
    }

    /**
     * init the selected patient and get their blood pressure value, total cholesterol value based on the monitor
     * and inform the selected patient fragment or bar view to display the related information
     */
    @Override
    public void initSelectedPatients(){
        int frequency = LocalStorage.getMonitorFrequency(context);
        String selectedPatientsString = LocalStorage.getSelectedPatientsOfCurrentPractitioner(context);

        if(mode==CARD_VIEW){
            initSelectedPatientsBloodPressureMonitorIds();
            initSelectedPatientsCholesterolMonitorIds();
        }

        /*
         if selected patients' id and full name store in local, use their id to query observations
         */
        if(!selectedPatientsString.equals("")){
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Patient>>(){}.getType();
            selected_patients = gson.fromJson(selectedPatientsString,type);



            String cholesterolMonitoredPatientsIDsString = LocalStorage.getCholesterolMonitoredPatients(context);
            String bloodPressureMonitoredPatientsIDsString = LocalStorage.getBloodPressureMonitoredPatients(context);


            Type typeString = new TypeToken<ArrayList<String>>(){}.getType();
            ArrayList<String> cholesterolMonitoredPatientsIDs = gson.fromJson(cholesterolMonitoredPatientsIDsString,typeString);
            ArrayList<String> bloodPressureMonitoredPatientsIDs = gson.fromJson(bloodPressureMonitoredPatientsIDsString,typeString);


            /*
            Set up Schedule to implement Frequency of N
             */
            ScheduledExecutorService scheduler =
                    Executors.newSingleThreadScheduledExecutor();
            future = scheduler.scheduleAtFixedRate
                    (new Runnable() {
                        public void run() {

                            if(mode == BAR_VIEW){
                                getCholesterolMonitors(cholesterolMonitoredPatientsIDs);
                            }else if(mode == CARD_VIEW){
                                if(cholesterolMonitoredPatientsIDs.size()!=0&&bloodPressureMonitoredPatientsIDs.size()!=0){
                                    getBothMonitors(cholesterolMonitoredPatientsIDs,bloodPressureMonitoredPatientsIDs);
                                }
                                if(cholesterolMonitoredPatientsIDs.size()==0){
                                    getBloodPressureMonitors(bloodPressureMonitoredPatientsIDs);
                                }else if(bloodPressureMonitoredPatientsIDs.size()==0){
                                    getCholesterolMonitors(cholesterolMonitoredPatientsIDs);
                                }
                            }


                        }
                        //the N value is set up here
                    }, 0, frequency, TimeUnit.SECONDS);
        }
        //if there is no selected patient , initialize again and clear the cards from selected fragment
        if(mode == CARD_VIEW){
            view.initSelectedPatientsCardView(patientsToPatientCards(selected_patients));
        }
    }


    /**
     * use the patient id to get the cholesterol value from the server
     * @param patientIDs: list of patient ids
     */
    private void getCholesterolMonitors(ArrayList<String> patientIDs){
        //get total cholesterol data of selected patients
        CholesterolMonitorContainer cholesterolMonitorContainer = new CholesterolMonitorContainer(context);
        cholesterolMonitorContainer.requestMultiplePatientsCholesterolMonitors(patientIDs, new CholesterolCallback() {
            @Override
            public void onSuccess(ArrayList<CholesterolMonitor> cholesterolMonitors) {


                ArrayList<PatientCard> patientCards;
                MonitorDataProcessor monitorDataProcessor = new MonitorDataProcessor();
                patientCards = monitorDataProcessor.mergeCholesterolToPatientCard(cholesterolMonitors,selected_patients);
                System.out.println("Requested Server----------------------------");

                if(mode == BAR_VIEW){
                    barView.initSelectedPatientsBarView(patientCards);
                }else if(mode == CARD_VIEW){
                    view.initSelectedPatientsCardView(patientCards);
                }

            }
        });
    }

    /**
     * use the patient id to get the blood pressure value from the server
     * @param patientIDs list of patient ids
     */
    private void getBloodPressureMonitors(ArrayList<String> patientIDs){
        //get blood pressure data of selected patients
        BloodPressureMonitorContainer bloodPressureContainer = new BloodPressureMonitorContainer(context);
        bloodPressureContainer.requestMultiplePatientsBloodPressureMonitors(patientIDs, new BloodPressureCallback() {
            @Override
            public void onSuccess(ArrayList<BloodPressureMonitor> bloodPressureMonitors) {
                int systolicLimit = LocalStorage.getSystolicLimitValue(context);
                int diastolicLimit = LocalStorage.getDiastolicLimitValue(context);

                ArrayList<PatientCard> patientCards;
                MonitorDataProcessor monitorDataProcessor = new MonitorDataProcessor();
                patientCards = monitorDataProcessor.mergeBloodPressureToPatientCard(bloodPressureMonitors,selected_patients,systolicLimit,diastolicLimit);

                filterHighSystolicPatientCard(patientCards);
                view.initSelectedPatientsCardView(patientCards);
            }
        });
    }

    /**
     * use two list of patient ids to get the cholesterol or blood pressure value from server respectively.
     * @param monitoredCholesterolPatientsIDs: list of patient ids to monitor cholesterol
     * @param monitoredBloodPressurePatientsIDs: list of patient ids to monitor blood pressure
     */
    private void getBothMonitors(ArrayList<String> monitoredCholesterolPatientsIDs,ArrayList<String> monitoredBloodPressurePatientsIDs){
        CholesterolMonitorContainer cholesterolMonitorContainer = new CholesterolMonitorContainer(context);
        cholesterolMonitorContainer.requestMultiplePatientsCholesterolMonitors(monitoredCholesterolPatientsIDs, new CholesterolCallback() {
            @Override
            public void onSuccess(ArrayList<CholesterolMonitor> cholesterolMonitors) {

                BloodPressureMonitorContainer bloodPressureContainer = new BloodPressureMonitorContainer(context);
                bloodPressureContainer.requestMultiplePatientsBloodPressureMonitors(monitoredBloodPressurePatientsIDs, new BloodPressureCallback() {
                    @Override
                    public void onSuccess(ArrayList<BloodPressureMonitor> bloodPressureMonitors) {
                        int systolicLimit = LocalStorage.getSystolicLimitValue(context);
                        int diastolicLimit = LocalStorage.getDiastolicLimitValue(context);

                        ArrayList<PatientCard> patientCards;
                        MonitorDataProcessor monitorDataProcessor = new MonitorDataProcessor();
                        patientCards = monitorDataProcessor.mergeBothMonitorsToPatientCard(bloodPressureMonitors,cholesterolMonitors,selected_patients,systolicLimit,diastolicLimit);


                        filterHighSystolicPatientCard(patientCards);

                        if(mode == BAR_VIEW){
                            barView.initSelectedPatientsBarView(patientCards);
                        }else if(mode == CARD_VIEW){
                            view.initSelectedPatientsCardView(patientCards);
                        }
                    }
                });
            }
        });
    }


    /**
     * filter the high systolic blood pressure patient ids based on patient cards information
     * @param patientCards: the patient card with patient's detail
     */
    private void filterHighSystolicPatientCard(ArrayList<PatientCard> patientCards){
        ArrayList<PatientCard> highSystolicPatientCards = new ArrayList<PatientCard>();
        for(PatientCard patientCard:patientCards){
            if(patientCard.isSystolicBloodPressureHighlighted()){
                highSystolicPatientCards.add(patientCard);
            }
        }
        Gson gson = new Gson();
        String highSystolicPatientCardsString =  gson.toJson(highSystolicPatientCards);
        LocalStorage.saveHighSystolicPatients(context,highSystolicPatientCardsString);
    }


    /**
     * the function is to convert a list of patients to a list of patients card
     * @param patients:a list of patients
     * @return: a list of patients
     */
    private ArrayList<PatientCard> patientsToPatientCards (ArrayList<Patient> patients){
        ArrayList<PatientCard> patientCards = new ArrayList<>();
        for(Patient patient:patients){
            PatientCard patientCard = new PatientCard(patient.getId(),patient.getFullName());
            patientCards.add(patientCard);
        }
        return patientCards;
    }

}

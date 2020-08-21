package com.sa.project.Presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sa.project.Presenter.Contract.AllPatientsContract;
import com.sa.project.Model.Local.LocalStorage;
import com.sa.project.Model.DataHolder.Patient;
import com.sa.project.Model.Callbacks.PatientListCallback;
import com.sa.project.Model.ServerSources.PatientListSource;
import com.sa.project.View.Patients.PatientCard;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Display all the patients of the practitioner by requesting Encounter from server
 */
public class AllPatientsPresenter implements AllPatientsContract.Presenter {
    private ArrayList<Patient> all_patients = new ArrayList<Patient>();
    private ArrayList<Patient> selected_patients = new ArrayList<Patient>() ;
    private AllPatientsContract.View view;
    private Context context;

    public AllPatientsPresenter(AllPatientsContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    /**
     * Initialize the page, request from server to get the latest all patients of the practitioner
     */
    @Override
    public void initAllPatients(){
        String allPatientsString = LocalStorage.getAllPatientsOfCurrentPractitioner(getContext());
        String selectedPatientsString = LocalStorage.getSelectedPatientsOfCurrentPractitioner(getContext());

        //if the patients list store in local
        if(!allPatientsString.equals("")){
            view.hideLoadingPanel();
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Patient>>(){}.getType();
            all_patients = gson.fromJson(allPatientsString,type);
            view.initAllPatientsCardView(patientsToPatientCards(all_patients));
            if(!selectedPatientsString.equals("")){
                selected_patients = gson.fromJson(selectedPatientsString,type);
                view.initSelectedPatientsCardView(patientsToPatientCards(selected_patients));
            }
        }else{
            updateAllPatient();
        }
    }


    /**
     * this function would get the PractitionerID from local storage and
     * use it to request all patients from this Practitioner from server
     */
    @Override
    public void updateAllPatient(){
        view.showLoadingPanel();
        String practitionerID = LocalStorage.getCurrentPractitionerID(getContext());
        PatientListSource patientListSource = new PatientListSource(getContext());
        patientListSource.requestPatientsList(practitionerID, new PatientListCallback() {
            @Override
            public void onSuccess(ArrayList<Patient> patients) {
                Gson gson = new Gson();
                String allPatientsString =  gson.toJson(patients);
                LocalStorage.saveAllPatientsOfCurrentPractitioner(getContext(),allPatientsString);
                all_patients = patients;
                view.initAllPatientsCardView(patientsToPatientCards(all_patients));
                view.initSelectedPatientsCardView(patientsToPatientCards(selected_patients));
                view.hideLoadingPanel();
            }

        });
    }


    /**
     * this function would get the selected patient card which selected by user, and save it to the
     * local storage so that it is easy for user to get it next time
     * @param selectedPatientCards: the patient with full detail to show the information in card view
     */
    @Override
    public void updateSelectPatients(ArrayList<PatientCard> selectedPatientCards){

        if(LocalStorage.clearAllMonitoredPatients(context)){
            LocalStorage.clearHighSystolicPatients(this.context);
            selected_patients = patientCardsToPatients(selectedPatientCards);
            Gson gson = new Gson();
            String selectedPatientsString =  gson.toJson(selected_patients);
            LocalStorage.saveSelectedPatientsOfCurrentPractitioner(getContext(),selectedPatientsString);
        }
    }

    /**
     * this function would provide a way to clear all selected patient by user and clear all from local storage as well.
     */
    @Override
    public void clearSelectPatients(){
        selected_patients.clear();
        Gson gson = new Gson();
        String selectedPatientsString =  gson.toJson(selected_patients);
        LocalStorage.saveSelectedPatientsOfCurrentPractitioner(getContext(),selectedPatientsString);
        view.initSelectedPatientsCardView(patientsToPatientCards(selected_patients));
    }

    /**
     * this function would clear all patients from current Practitioner  before re-requesting the
     * patients detail from server
     */
    @Override
    public void clearAllPatients(){
        all_patients.clear();
        Gson gson = new Gson();
        String allPatientsString =  gson.toJson(all_patients);
        LocalStorage.saveAllPatientsOfCurrentPractitioner(getContext(),allPatientsString);
        view.initAllPatientsCardView(patientsToPatientCards(all_patients));
    }

    /**
     * this function is to get the limit blood pressure value from local storage
     */
    @Override
    public void getBloodPressureLimitValue() {
        int systolicValue = LocalStorage.getSystolicLimitValue(getContext());
        int diastolicValue = LocalStorage.getDiastolicLimitValue(getContext());
        view.displayBloodPressureLimitValue(systolicValue,diastolicValue);
    }

    /**
     * this function would accept two blood pressure values and save it to local storage.
     * @param systolicValue: the systolic value which set by user
     * @param diastolicValue: the diastolic value which set by user
     */
    @Override
    public void setBloodPressureLimitValue(String systolicValue, String diastolicValue) {
        LocalStorage.saveSystolicLimitValue(getContext(),Integer.parseInt(systolicValue));
        LocalStorage.saveDiastolicLimitValue(getContext(),Integer.parseInt(diastolicValue));

    }

    /**
     * get the current context
     * @return return the current context
     */
    private Context getContext() {
        return this.context;
    }


    /**
     * this function is to convert the patients to patient cards
     * @param patients: list of patients
     * @return: list of patient cards
     */
    public ArrayList<PatientCard> patientsToPatientCards (ArrayList<Patient> patients){
        ArrayList<PatientCard> patientCards = new ArrayList<>();
        for(Patient patient:patients){
            PatientCard patientCard = new PatientCard(patient.getId(),patient.getFullName());
            patientCards.add(patientCard);
        }
        return patientCards;
    }

    /**
     * this function is to convert the patient cards to patient
     * @param patientCards: list of patient cards
     * @return: list of patients
     */
    public ArrayList<Patient> patientCardsToPatients (ArrayList<PatientCard> patientCards){
        ArrayList<Patient> patients = new ArrayList<>();
        for(PatientCard patientCard:patientCards){
            Patient patient = new Patient(patientCard.getPatientID(),patientCard.getPatientFullName());
            patients.add(patient);
        }
        return patients;
    }



}

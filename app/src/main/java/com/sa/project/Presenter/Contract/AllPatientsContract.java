package com.sa.project.Presenter.Contract;

import com.sa.project.View.Patients.PatientCard;

import java.util.ArrayList;

/**
 * Agent that building communication between displaying all patients page's AllPatientPresenter and AllPatientsFragment
 */
public interface AllPatientsContract {

    interface View{

        void initAllPatientsCardView(ArrayList<PatientCard> allPatientCards);
        void initSelectedPatientsCardView(ArrayList<PatientCard> selectedPatientCards);
        void showLoadingPanel();
        void hideLoadingPanel();
        void displayBloodPressureLimitValue(int systolicValue,int diastolicValue);

    }
    interface Presenter{
        void initAllPatients();
        void updateAllPatient();
        void updateSelectPatients(ArrayList<PatientCard> selectedPatientCards);
        void clearSelectPatients();
        void clearAllPatients();
        void getBloodPressureLimitValue();
        void setBloodPressureLimitValue(String systolicValue, String diastolicValue);
    }


}

package com.sa.project.Presenter.Contract;

import com.sa.project.View.Patients.PatientCard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

/**
 * Agent that building communication between SelectedPatientPresenter and two views: CholesterolDetailBarView and SelectedPatientsFragment
 */
public interface SelectedPatientsContract {

    interface View{
        void initSelectedPatientsCardView(ArrayList<PatientCard> selectedPatientCards);
        void initSelectedPatientsBloodPressureMonitorCheckbox(ArrayList<String> monitoredBloodPressurePatientsIDs);
        void initSelectedPatientsCholesterolMonitorCheckbox(ArrayList<String> monitoredCholesterolPatientsIDs);

    }

    interface BarView{
        void initSelectedPatientsBarView(ArrayList<PatientCard> selectedPatientCards);


    }

    interface Presenter{
        void initSelectedPatients();
        ScheduledFuture checkUpdateTimeExist();
        void informFrequency(int frequency);
        void initSelectedPatientsBloodPressureMonitorIds();
        void initSelectedPatientsCholesterolMonitorIds();
        void cancelUpdateFrequency();
        void updateSelectedPatientsBloodPressureMonitorIds(ArrayList<String> monitoredBloodPressurePatientsIDs);
        void updateSelectedPatientsCholesterolMonitorIds(ArrayList<String> monitoredCholesterolPatientsIDs);

    }





}

package com.sa.project.Presenter;

import com.sa.project.Presenter.Contract.PatientDetailContract;
import com.sa.project.Model.DataHolder.Patient;
import com.sa.project.Model.Callbacks.PatientDetailCallback;
import com.sa.project.Model.ServerSources.PatientDetailSource;
import com.sa.project.View.PatientDetail.PatientDetailCard;

/**
 * Request patient's detail information from server and send it to PatientDetailActivity
 */
public class PatientDetailPresenter implements PatientDetailContract.Presenter {
    private PatientDetailContract.View view;

    public PatientDetailPresenter(PatientDetailContract.View view){
        this.view = view;
    }

    public void getPatientDetail(String patientID){
        PatientDetailSource requestDetail = new PatientDetailSource(view.getViewContext());
        requestDetail.requestPatientDetail(patientID, new PatientDetailCallback() {
            @Override
            public void onSuccess(Patient patient) {

                String fullName = patient.getFullName();
                String birthDate = patient.getBirthDate();
                String gender = patient.getGender();
                String city = patient.getCity();
                String state = patient.getState();
                String country = patient.getCountry();

                PatientDetailCard patientDetailCard = new PatientDetailCard(fullName,birthDate,gender,city,state,country);
                view.updatePatientView(patientDetailCard);
            }
        });
    }


}

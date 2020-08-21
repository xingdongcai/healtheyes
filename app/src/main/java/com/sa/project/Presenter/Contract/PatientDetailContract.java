package com.sa.project.Presenter.Contract;

import android.content.Context;

import com.sa.project.View.PatientDetail.PatientDetailCard;

/**
 * Agent that building communication between PatientDetailPresenter and PatientDetailActivity
 */
public interface PatientDetailContract {
    interface View{
        void updatePatientView(PatientDetailCard patient);
        Context getViewContext();
    }

    interface Presenter{
        void getPatientDetail(String patientID);
    }
}

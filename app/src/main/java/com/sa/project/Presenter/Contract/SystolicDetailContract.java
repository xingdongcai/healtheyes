package com.sa.project.Presenter.Contract;

import android.content.Context;

import com.sa.project.View.PatientDetail.PatientDetailCard;

import java.util.ArrayList;

/**
 * Agent that building communication between SystolicDetail presenter and two views: SystolicDetailLineView and SystolicDetailTextView
 */
public interface SystolicDetailContract {
    interface TextView{
        void initHighSystolicPatientCardsTextView(ArrayList<Integer> bloodPressure, ArrayList<String> effectedTime, String patientName);
        void clearTextViews();
    }

    interface LineView{
        void initHighSystolicPatientCardsLineView(ArrayList<Integer> bloodPressure,String patientName);
        void clearLineViews();
    }

    interface Presenter{
        void initHighSystolicPatients();

    }
}

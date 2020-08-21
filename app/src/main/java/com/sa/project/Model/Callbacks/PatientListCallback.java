package com.sa.project.Model.Callbacks;


import com.sa.project.Model.DataHolder.Patient;

import java.util.ArrayList;

/**
 * Agent for PatientListSource and DataSource
 * After getting raw data from server, this callback deliver the result to PatientListSource to do the data encapsulation
 */
public interface PatientListCallback {
    void onSuccess(ArrayList<Patient> patients);
}

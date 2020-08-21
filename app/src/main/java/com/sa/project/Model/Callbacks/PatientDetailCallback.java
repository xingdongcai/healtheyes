package com.sa.project.Model.Callbacks;

import com.sa.project.Model.DataHolder.Patient;

/**
 * Agent for PatientListSource and DataSource
 * After getting raw data from server, this callback deliver the result to PatientDetailSource to do the data encapsulation
 */
public interface PatientDetailCallback {
    void onSuccess(Patient patient);
}

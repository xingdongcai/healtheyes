package com.sa.project.Model.Callbacks;

/**
 * Agent for PractitionerSource and DataSource
 * After getting raw data from server, this callback deliver the result to PractitionerSource to do the data encapsulation
 */
public interface PractitionerCallback {
    void onSuccess(boolean isExist);
}

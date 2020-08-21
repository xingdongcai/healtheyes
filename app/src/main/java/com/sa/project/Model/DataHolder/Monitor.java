package com.sa.project.Model.DataHolder;

/**
 * Represent abstract type of monitor of a patient
 * which need to be inherit as more specific monitor
 */
public abstract class Monitor {
    private String patientID;
    private String code;

    public Monitor(String patientID, String code) {
        this.patientID = patientID;
        this.code = code;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}

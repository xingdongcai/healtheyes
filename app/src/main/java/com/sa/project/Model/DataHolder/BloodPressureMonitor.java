package com.sa.project.Model.DataHolder;

/**
 * Contains blood pressure data of a patient
 */
public class BloodPressureMonitor extends Monitor {

    private int systolicBloodPressure;
    private int diastolicBloodPressure;
    private String effectiveDateTime;

    public BloodPressureMonitor(String patientID, String code) {
        super(patientID, code);
    }

    public BloodPressureMonitor(String patientID, String code, int systolicBloodPressure, int diastolicBloodPressure, String effectiveDateTime) {
        super(patientID, code);
        this.systolicBloodPressure = systolicBloodPressure;
        this.diastolicBloodPressure = diastolicBloodPressure;
        this.effectiveDateTime = effectiveDateTime;
    }

    public int getSystolicBloodPressure() {
        return systolicBloodPressure;
    }

    public void setSystolicBloodPressure(int systolicBloodPressure) {
        this.systolicBloodPressure = systolicBloodPressure;
    }

    public int getDiastolicBloodPressure() {
        return diastolicBloodPressure;
    }

    public void setDiastolicBloodPressure(int diastolicBloodPressure) {
        this.diastolicBloodPressure = diastolicBloodPressure;
    }

    public String getEffectiveDateTime() {
        return effectiveDateTime;
    }

    public void setEffectiveDateTime(String effectiveDateTime) {
        this.effectiveDateTime = effectiveDateTime;
    }
}

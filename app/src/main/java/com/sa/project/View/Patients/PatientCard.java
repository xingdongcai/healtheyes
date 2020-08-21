package com.sa.project.View.Patients;

/**
 * Data holder for storing patient's total cholesterol or/and blood pressure values in the card view
 */
public class PatientCard {
    private String patientID,patientFullName,cholesterolEffectiveDateTime,bloodPressureEffectiveDateTime;
    private boolean isCholesterolHighlighted,isCholesterolMonitored;
    private boolean isBloodPressureMonitored,isSystolicBloodPressureHighlighted,isDiastolicBloodPressureHighlighted;
    private double totalCholesterol;
    private int systolicBloodPressure;
    private int diastolicBloodPressure;

    public PatientCard(String patientID, String patientFullName, String cholesterolEffectiveDateTime, double totalCholesterol) {
        this.patientID = patientID;
        this.patientFullName = patientFullName;
        this.cholesterolEffectiveDateTime = cholesterolEffectiveDateTime;
        this.totalCholesterol = totalCholesterol;
    }

    public PatientCard(String patientID, String patientFullName, String bloodPressureEffectiveDateTime, int systolicBloodPressure, int diastolicBloodPressure) {
        this.patientID = patientID;
        this.patientFullName = patientFullName;
        this.bloodPressureEffectiveDateTime = bloodPressureEffectiveDateTime;
        this.systolicBloodPressure = systolicBloodPressure;
        this.diastolicBloodPressure = diastolicBloodPressure;
    }

    public PatientCard(String patientID, String patientFullName) {
        this.patientID = patientID;
        this.patientFullName = patientFullName;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientFullName() {
        return patientFullName;
    }

    public void setPatientFullName(String patientFullName) {
        this.patientFullName = patientFullName;
    }

    public String getCholesterolEffectiveDateTime() {
        return cholesterolEffectiveDateTime;
    }

    public void setCholesterolEffectiveDateTime(String cholesterolEffectiveDateTime) {
        this.cholesterolEffectiveDateTime = cholesterolEffectiveDateTime;
    }

    public boolean isCholesterolHighlighted() {
        return isCholesterolHighlighted;
    }

    public void setCholesterolHighlighted(boolean cholesterolHighlighted) {
        isCholesterolHighlighted = cholesterolHighlighted;
    }

    public boolean isCholesterolMonitored() {
        return isCholesterolMonitored;
    }

    public void setCholesterolMonitored(boolean cholesterolMonitored) {
        isCholesterolMonitored = cholesterolMonitored;
    }

    public double getTotalCholesterol() {
        return totalCholesterol;
    }

    public void setTotalCholesterol(double totalCholesterol) {
        this.totalCholesterol = totalCholesterol;
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

    public String getBloodPressureEffectiveDateTime() {
        return bloodPressureEffectiveDateTime;
    }

    public void setBloodPressureEffectiveDateTime(String bloodPressureEffectiveDateTime) {
        this.bloodPressureEffectiveDateTime = bloodPressureEffectiveDateTime;
    }

    public boolean isBloodPressureMonitored() {
        return isBloodPressureMonitored;
    }

    public void setBloodPressureMonitored(boolean bloodPressureMonitored) {
        isBloodPressureMonitored = bloodPressureMonitored;
    }

    public boolean isSystolicBloodPressureHighlighted() {
        return isSystolicBloodPressureHighlighted;
    }

    public void setSystolicBloodPressureHighlighted(boolean systolicBloodPressureHighlighted) {
        isSystolicBloodPressureHighlighted = systolicBloodPressureHighlighted;
    }

    public boolean isDiastolicBloodPressureHighlighted() {
        return isDiastolicBloodPressureHighlighted;
    }

    public void setDiastolicBloodPressureHighlighted(boolean diastolicBloodPressureHighlighted) {
        isDiastolicBloodPressureHighlighted = diastolicBloodPressureHighlighted;
    }
}

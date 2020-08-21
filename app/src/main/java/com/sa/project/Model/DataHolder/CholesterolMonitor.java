package com.sa.project.Model.DataHolder;

/**
 * Contains total cholesterol data of a patient
 */
public class CholesterolMonitor extends Monitor{
    private Double latestTotalCholesterol;
    private String effectiveDateTime;

    public CholesterolMonitor(String patientID, String code, Double latestTotalCholesterol, String effectiveDateTime) {
        super(patientID, code);
        this.latestTotalCholesterol = latestTotalCholesterol;
        this.effectiveDateTime = effectiveDateTime;
    }

    public Double getLatestTotalCholesterol() {
        return latestTotalCholesterol;
    }

    public void setLatestTotalCholesterol(Double latestTotalCholesterol) {
        this.latestTotalCholesterol = latestTotalCholesterol;
    }

    public String getEffectiveDateTime() {
        return effectiveDateTime;
    }

    public void setEffectiveDateTime(String effectiveDateTime) {
        this.effectiveDateTime = effectiveDateTime;
    }
}

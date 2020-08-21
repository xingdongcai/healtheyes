package com.sa.project.Model.DataHolder;

import java.util.ArrayList;

/**
 * Contains list of systolic blood pressure data of a patient
 * By default is latest 5 values, which has been proceed in SystolicDetailMonitorContainer
 */
public class SystolicDetailMonitor extends Monitor{
    private ArrayList<Integer> systolicBloodPressures;
    private ArrayList<String> effectiveDateTimes;

    public SystolicDetailMonitor(String patientID, String code) {
        super(patientID, code);
    }

    public SystolicDetailMonitor(String patientID, String code, ArrayList<Integer> systolicBloodPressures, ArrayList<String> effectiveDateTimes) {
        super(patientID, code);
        this.systolicBloodPressures = systolicBloodPressures;
        this.effectiveDateTimes = effectiveDateTimes;
    }

    public ArrayList<Integer> getSystolicBloodPressures() {
        return systolicBloodPressures;
    }

    public ArrayList<String> getEffectiveDateTimes() {
        return effectiveDateTimes;
    }
}

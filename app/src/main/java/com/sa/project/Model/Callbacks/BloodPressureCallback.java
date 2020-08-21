package com.sa.project.Model.Callbacks;

import com.sa.project.Model.DataHolder.BloodPressureMonitor;

import java.util.ArrayList;

/**
 * Agent for BloodPressureMonitorContainer and DataSource
 * After getting raw data from server, this callback deliver the result to BloodPressureMonitorContainer to do the data encapsulation
 */
public interface BloodPressureCallback {
    void onSuccess(ArrayList<BloodPressureMonitor> bloodPressureMonitors);

}

package com.sa.project.Model.Callbacks;

import com.sa.project.Model.DataHolder.CholesterolMonitor;

import java.util.ArrayList;

/**
 * Agent for CholesterolMonitorContainer and DataSource
 * After getting raw data from server, this callback deliver the result to CholesterolMonitorContainer to do the data encapsulation
 */
public interface CholesterolCallback {
    void onSuccess(ArrayList<CholesterolMonitor> cholesterolMonitors);
}

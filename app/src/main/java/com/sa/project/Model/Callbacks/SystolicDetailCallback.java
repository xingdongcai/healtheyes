package com.sa.project.Model.Callbacks;

import com.sa.project.Model.DataHolder.SystolicDetailMonitor;

import java.util.ArrayList;

/**
 * Agent for SystolicDetailMonitorContainer and DataSource
 * After getting raw data from server, this callback deliver the result to SystolicDetailMonitorContainer to do the data encapsulation
 */
public interface SystolicDetailCallback {
    void onSuccess(ArrayList<SystolicDetailMonitor> systolicDetailMonitors);
}
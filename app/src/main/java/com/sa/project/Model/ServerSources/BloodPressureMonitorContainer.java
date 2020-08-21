package com.sa.project.Model.ServerSources;

import android.content.Context;

import com.jayway.jsonpath.JsonPath;
import com.sa.project.Model.Callbacks.BloodPressureCallback;
import com.sa.project.Model.Callbacks.ServerCallback;
import com.sa.project.Model.DataHolder.BloodPressureMonitor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Request list of patients' blood pressure then store it to BloodPressureMonitors and return to Presenter
 */
public class BloodPressureMonitorContainer extends MonitorContainer {
    private ArrayList<BloodPressureMonitor> bloodPressureMonitors;

    public BloodPressureMonitorContainer(Context context) {
        super(context);
    }

    public void requestMultiplePatientsBloodPressureMonitors(ArrayList<String> patientsIDs, BloodPressureCallback bloodPressureCallback){
        bloodPressureMonitors = new ArrayList<BloodPressureMonitor>();
        this.patientsIDs = patientsIDs;
        iterator=0;
        size = patientsIDs.size();
        if(size!=0){
            requestBloodPressureMonitor(patientsIDs.get(iterator),bloodPressureCallback);
        }
    }

    //get the blood pressure observation data of the patient and encapsulate it to BloodPressureMonitor class
    private void requestBloodPressureMonitor(String patientID, BloodPressureCallback bloodPressureCallback){

        requestUrl = getServerUrl()+"Observation?code=55284-4&_sort=-date&patient="+patientID;
        requestServer(context, requestUrl, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject jsonResult) {
                String observationResultStr = jsonResult.toString();

                BloodPressureMonitor bloodPressureMonitor;
                int total = JsonPath.read(observationResultStr,"$.total");
                if(total != 0){
                    String effectiveDateTime = JsonPath.read(observationResultStr,"$.entry[0].resource.effectiveDateTime");
                    int latestDiastolicBloodPressure = JsonPath.read(observationResultStr,"$.entry[0].resource.component[0].valueQuantity.value");
                    int latestSystolicBloodPressure = JsonPath.read(observationResultStr,"$.entry[0].resource.component[1].valueQuantity.value");

                    bloodPressureMonitor = new BloodPressureMonitor(patientID,"55284-4",latestSystolicBloodPressure,latestDiastolicBloodPressure,effectiveDateTime);
                    bloodPressureMonitors.add(bloodPressureMonitor);
                }


                iterator++;

                //if the results reach the number of selected patients, send the results to presenter through the callback
                //if not, recursively request the data within the patients list
                if(iterator==size){

                    bloodPressureCallback.onSuccess(bloodPressureMonitors);
                }else{
                    requestBloodPressureMonitor(patientsIDs.get(iterator),bloodPressureCallback);
                }
            }
        });
    }



}

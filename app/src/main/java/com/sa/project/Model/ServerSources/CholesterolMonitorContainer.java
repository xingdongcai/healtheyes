package com.sa.project.Model.ServerSources;

import android.content.Context;

import com.jayway.jsonpath.JsonPath;
import com.sa.project.Model.Callbacks.CholesterolCallback;
import com.sa.project.Model.Callbacks.ServerCallback;
import com.sa.project.Model.DataHolder.CholesterolMonitor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Request list of patients' total cholesterol then store it to CholesterolMonitors and return to Presenter
 */
public class CholesterolMonitorContainer extends MonitorContainer {
    private ArrayList<CholesterolMonitor> cholesterolMonitors;


    public CholesterolMonitorContainer(Context context) {
        super(context);
    }

    //request list of selected patients cholesterol data by their ids
    public void requestMultiplePatientsCholesterolMonitors(ArrayList<String> patientsIDs, CholesterolCallback cholesterolCallback){
        this.patientsIDs = patientsIDs;
        cholesterolMonitors = new ArrayList<CholesterolMonitor>();

        iterator=0;
        size = patientsIDs.size();
        if(size!=0){
            requestCholesterolMonitor(patientsIDs.get(iterator),cholesterolCallback);
        }
    }
    //get the cholesterol observation data of the patient and encapsulate it to CholesterolMonitor class
    private void requestCholesterolMonitor(String patientID,CholesterolCallback cholesterolCallback){
        requestUrl = getServerUrl()+"Observation?code=2093-3&_sort=-date&patient="+patientID;
        requestServer(context, requestUrl, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject jsonResult) {
                String observationResultStr = jsonResult.toString();

                CholesterolMonitor cholesterolMonitor;
                int total = JsonPath.read(observationResultStr,"$.total");
                if(total != 0){
                    String effectiveDateTime = JsonPath.read(observationResultStr,"$.entry[0].resource.effectiveDateTime");
                    Double latestTotalCholesterol = JsonPath.read(observationResultStr,"$.entry[0].resource.valueQuantity.value");
                    cholesterolMonitor = new CholesterolMonitor(patientID,"2093-3",latestTotalCholesterol,effectiveDateTime);
                    cholesterolMonitors.add(cholesterolMonitor);

                }

                iterator++;

                //if the results reach the number of selected patients, send the results to presenter through the callback
                //if not, recursively request the data within the patients list
                if(iterator==size){
                    cholesterolCallback.onSuccess(cholesterolMonitors);
                }else{
                    requestCholesterolMonitor(patientsIDs.get(iterator),cholesterolCallback);
                }
            }
        });
    }

}

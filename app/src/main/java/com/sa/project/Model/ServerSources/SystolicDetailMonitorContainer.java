package com.sa.project.Model.ServerSources;

import android.content.Context;

import com.jayway.jsonpath.JsonPath;
import com.sa.project.Model.Callbacks.SystolicDetailCallback;
import com.sa.project.Model.Callbacks.ServerCallback;
import com.sa.project.Model.DataHolder.SystolicDetailMonitor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Request list of patients' latest 5 systolic blood pressures then store it to SystolicDetailMonitor and return to Presenter
 */
public class SystolicDetailMonitorContainer extends MonitorContainer {
    private ArrayList<SystolicDetailMonitor> systolicDetailMonitors;

    public SystolicDetailMonitorContainer(Context context) {
        super(context);
    }

    public void requestSystolicDetailMonitors(ArrayList<String> patientsIDs, SystolicDetailCallback systolicDetailCallback){
        systolicDetailMonitors = new ArrayList<SystolicDetailMonitor>();
        this.patientsIDs = patientsIDs;
        iterator=0;
        size = patientsIDs.size();
        if(size!=0){
            requestSystolicDetailMonitor(patientsIDs.get(iterator), systolicDetailCallback);
        }
    }

    //get the blood pressure observation data of the patient and encapsulate it to BloodPressureMonitor class
    private void requestSystolicDetailMonitor(String patientID, SystolicDetailCallback systolicDetailCallback){

        requestUrl = getServerUrl()+"Observation?code=55284-4&_sort=-date&patient="+patientID;
        requestServer(context, requestUrl, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject jsonResult) {
                String observationResultStr = jsonResult.toString();

                SystolicDetailMonitor systolicDetailMonitor;
                ArrayList<Integer> systolicBloodPressures = JsonPath.read(observationResultStr,"$.entry[:5].resource.component[1].valueQuantity.value");
                ArrayList<String> effectiveDateTimes = JsonPath.read(observationResultStr,"$.entry[:5].resource.effectiveDateTime");
                systolicDetailMonitor = new SystolicDetailMonitor(patientID,"55284-4",systolicBloodPressures,effectiveDateTimes);
                systolicDetailMonitors.add(systolicDetailMonitor);


                iterator++;

                //if the results reach the number of selected patients, send the results to presenter through the callback
                //if not, recursively request the data within the patients list
                if(iterator==size){

                    systolicDetailCallback.onSuccess(systolicDetailMonitors);
                }else{
                    requestSystolicDetailMonitor(patientsIDs.get(iterator), systolicDetailCallback);
                }
            }
        });
    }

}

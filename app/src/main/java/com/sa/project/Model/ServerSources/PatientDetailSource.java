package com.sa.project.Model.ServerSources;

import android.content.Context;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.sa.project.Model.Callbacks.PatientDetailCallback;
import com.sa.project.Model.Callbacks.ServerCallback;
import com.sa.project.Model.DataHolder.Patient;

import org.json.JSONObject;

/**
 * Request Patient's detail from server
 */
public class PatientDetailSource extends DataSource {
    private Context context;

    public PatientDetailSource(Context context){
        this.context = context;
    }

    public void  requestPatientDetail(String patientID, PatientDetailCallback patientDetailCallback){
        String requestUrl = getServerUrl()+"Patient/"+patientID;
        requestServer(context, requestUrl, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject jsonResult) {
                String patientJsonResult = jsonResult.toString();

                Object jsonDocument = Configuration.defaultConfiguration().jsonProvider().parse(patientJsonResult);
                String familyName = JsonPath.read(jsonDocument,"$.name[0].family");
                String givenName = JsonPath.read(jsonDocument,"$.name[0].given[0]");
                String birthDate = JsonPath.read(jsonDocument,"$.birthDate");
                String gender = JsonPath.read(jsonDocument,"$.gender");
                String city = JsonPath.read(jsonDocument,"$.address[0].city");
                String state = JsonPath.read(jsonDocument,"$.address[0].state");
                String country = JsonPath.read(jsonDocument,"$.address[0].country");
                String fullName = givenName+" "+familyName;
                Patient patient = new Patient(patientID,fullName,givenName,familyName,birthDate,gender,city,state,country);


                patientDetailCallback.onSuccess(patient);
            }
        });
    }
}

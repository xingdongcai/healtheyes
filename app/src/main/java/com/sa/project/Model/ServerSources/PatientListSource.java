package com.sa.project.Model.ServerSources;

import android.content.Context;

import com.jayway.jsonpath.JsonPath;
import com.sa.project.Model.Callbacks.PatientListCallback;
import com.sa.project.Model.Callbacks.ServerCallback;
import com.sa.project.Model.DataHolder.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Request list of patients from Encounter then filter to store ArrayList of Patients and return to Presenter
 */
public class PatientListSource extends DataSource {
    private Context context;
    private HashMap<String, String> patientsHashMap = new HashMap<String,String>();
    private String requestUrl;
    private ArrayList<String> linkRelations;




    public PatientListSource(Context context){
        this.context = context;
    }

    //request the patients list of the practitioner
    public void requestPatientsList(String practitionerID,final PatientListCallback patientListCallback){
        requestUrl = getServerUrl()+"Encounter?participant.identifier=http://hl7.org/fhir/sid/us-npi%7C"+practitionerID+"&_include=Encounter.participant.individual&_include=Encounter.patient&_count=100&_format=json";
        requestMultiPagePatientsList(requestUrl,patientListCallback);
    }

    //since there may have multi page of the result, we use recursive method to get the result of encounters and do the operation
    private void requestMultiPagePatientsList(String url, final PatientListCallback patientListCallback){
        requestServer(context, url, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject jsonResult) {
                String responseString = jsonResult.toString();
                try {
                    JSONArray entryArray = jsonResult.getJSONArray("entry");

                    JSONObject patientSubject;

                    for(int i=0;i<entryArray.length();i++){
                        try{
                            String patientName;
                            String patientID;
                            patientSubject = entryArray.getJSONObject(i).getJSONObject("resource").getJSONObject("subject");
                            patientID = patientSubject.getString("reference").substring(8);
                            patientName = patientSubject.getString("display");
                            System.out.println("ID: "+patientID);
                            System.out.println("Name: "+patientName+"\n");

                            //use hashMap to store patientID and full name,it will automatically filter the duplicate encounters
                            patientsHashMap.put(patientID,patientName);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    linkRelations = JsonPath.read(responseString,"$.link[*].relation");

                    //request next page if there has
                    if(linkRelations.contains("next")){
                        // get Url to next page
                        ArrayList<String> relationNext = JsonPath.read(responseString,"$.link[?(@.relation =~ /.*next/i)]");
                        requestUrl = JsonPath.read(relationNext,"$[0].url");
                        linkRelations = JsonPath.read(responseString,"$.link[*].relation");
                        requestMultiPagePatientsList(requestUrl,patientListCallback);
                    }else{
                        //final result, collect the data and fill the Patient arrayList with only id and full name
                        //this result will also be stored in local storage by using SharedPreferences: see handling detail in presenter and LocalStorage class
                        ArrayList<Patient> patientsArrayList = new ArrayList<Patient>();

                        Set set = patientsHashMap.entrySet();
                        Iterator iterator = set.iterator();
                        while(iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry)iterator.next();
                            System.out.print("key is: "+ entry.getKey() + " & Value is: ");
                            System.out.println(entry.getValue());
                            String patientID = entry.getKey().toString();
                            String patientFullName=entry.getValue().toString();
                            Patient patient = new Patient(patientID,patientFullName);
                            patientsArrayList.add(patient);
                        }
                        patientListCallback.onSuccess(patientsArrayList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }




}

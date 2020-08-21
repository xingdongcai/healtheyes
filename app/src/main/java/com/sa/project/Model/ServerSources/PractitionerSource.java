package com.sa.project.Model.ServerSources;

import android.content.Context;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.sa.project.Model.Callbacks.PractitionerCallback;

/**
 * Request practitioner detail from server
 * This class is just simply check if the entered identifier is existed in the server
 */
public class PractitionerSource extends DataSource {
    private Context context;
    private boolean isExist = false;

    public PractitionerSource(Context context){
        this.context = context;
    }

    //check if the entered practitioner id existing in server
    public void  requestPractitioner(String practitionerID, PractitionerCallback practitionerCallback){
        String requestUrl = getServerUrl()+"Practitioner?identifier="+practitionerID;
        requestServer(context, requestUrl, jsonResult -> {
            String practitionerJsonResult = jsonResult.toString();

            Object jsonDocument = Configuration.defaultConfiguration().jsonProvider().parse(practitionerJsonResult);
            int total = JsonPath.read(jsonDocument,"$.total");
            if(total != 0){
                isExist = true;
            }
            practitionerCallback.onSuccess(isExist);
        });
    }

}

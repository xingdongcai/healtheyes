package com.sa.project.Presenter;

import com.sa.project.Presenter.Contract.StartupContract;
import com.sa.project.Model.Local.LocalStorage;
import com.sa.project.Model.ServerSources.PractitionerSource;


/**
 * Presenter of the login page to verify practitioner's identifier
 */
public class StartupPresenter implements StartupContract.Presenter {
    private StartupContract.View view;
    private boolean isExist = false;

    public StartupPresenter(StartupContract.View view){
        this.view = view;
    }

    /**
     * @method check if the practitioner identifier existed in the server
     * @param practitionerID : Entered by practitioner
     */
    public void checkPractitioner(String practitionerID){
        PractitionerSource requestPractitioner = new PractitionerSource(view.getViewContext());
        requestPractitioner.requestPractitioner(practitionerID, isExist -> {
            if(isExist){
                if(LocalStorage.savePractitionerID(practitionerID,view.getViewContext())){
                    this.isExist = true;
                }else {
                    System.out.println("Fail: Save to SharedPreferences(Practitioner) with the key as practitionerID: "+practitionerID);
                }
            }else{
                this.isExist = false;
            }
            //send the verification result to view
            view.verifyPractitioner(this.isExist);


        });
    }
}

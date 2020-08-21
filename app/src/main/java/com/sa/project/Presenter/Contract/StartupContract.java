package com.sa.project.Presenter.Contract;

import android.content.Context;

/**
 * Agent that building communication between StartupPresenter and StartupActivity
 */
public interface StartupContract {

    interface View{
        void verifyPractitioner(boolean isExist);
        Context getViewContext();
    }

    interface Presenter{
        void checkPractitioner(String practitionerID);
       // Context getViewContext();
    }


}

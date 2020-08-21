package com.sa.project.View.Patients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.ArrayList;

/**
 * this class is a shareViewModel which is used to let two fragment connect each other in high level
 * instead of getting access to each other which would get the
 */
public class ShareViewModel extends ViewModel {
    private MutableLiveData<String> fragmentContainerAll;
    private MutableLiveData<String> fragmentContainerSelected;
    public void init()
    {
        fragmentContainerAll = new MutableLiveData<String>();
        fragmentContainerSelected = new MutableLiveData<String>();

    }

    public void sendMessageToSelected(String msg) {
        fragmentContainerSelected.setValue(msg);
    }
    public void sendMessageToAll(String msg){ fragmentContainerAll.setValue(msg); }
    public LiveData<String> getContainerSelected() {
        return fragmentContainerSelected;
    }
    public LiveData<String> getContainerAll() { return fragmentContainerAll; }

}

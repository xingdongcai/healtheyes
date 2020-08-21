package com.sa.project.View.Patients;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sa.project.Model.Local.LocalStorage;
import com.sa.project.Presenter.Contract.AllPatientsContract;
import com.sa.project.Presenter.AllPatientsPresenter;
import com.sa.project.R;

import java.util.ArrayList;

/**
 * the fragment to contain all current patients for the practitioner
 */
public class AllPatientsFragment extends ParentFragment implements AllPatientsContract.View {

    private AllPatientsContract.Presenter allPatientsPresenter;
    private View v;

    private AlertDialog alertDialog;
    private EditText systolicMonitorValue;
    private EditText diastolicMonitorValue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allPatientsPresenter = new AllPatientsPresenter(this,getContext());
        //allPatientsPresenter = new AllPatientsPresenter(this,getContext());
        //dialog = new Dialog(getContext());

    }


    /**
     * on create function for fragment view
     * @param inflater: inflater for the fragment view
     * @param container: the container of fragment view
     * @param savedInstanceState: the saved instanceState
     * @return: a fragment view
     */
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_all, container, false);
        recyclerView = v.findViewById(R.id.my_recycler_view_selected);
        layoutManager = new LinearLayoutManager(getActivity());  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
        Log.d("web","data check");
        adapter = new PatientRecyclerAdapter(getContext(), 0);
        createDialog();
        Button reqBtn = v.findViewById(R.id.requesrBtn);
        reqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRequestBtn();
            }
        });
        Button clearBtn = v.findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClearBtn();}
        });
        Button sendBtn = v.findViewById(R.id.sendToSelected);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showPopup();
                allPatientsPresenter.getBloodPressureLimitValue();
                alertDialog.show();
                onSendBtn(v);
            }
        });
        setHasOptionsMenu(true);
        return v;

    }
    public void createDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View promptsView = LayoutInflater.from(getActivity()).inflate(R.layout.monitor_value_setting,null);
        systolicMonitorValue = (EditText)promptsView.findViewById(R.id.systolic_set_id);
        diastolicMonitorValue = promptsView.findViewById(R.id.diastolic_set_id);
        // set prompts.xml to alert dialog builder
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Set",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                allPatientsPresenter.setBloodPressureLimitValue(systolicMonitorValue.getText().toString(),diastolicMonitorValue.getText().toString());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        alertDialog = alertDialogBuilder.create();

    }


//    public void showPopup(){
//
//
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//
//        View promptsView = LayoutInflater.from(getActivity()).inflate(R.layout.monitor_value_setting,null);
//        // set prompts.xml to alert dialog builder
//        alertDialogBuilder.setView(promptsView);
//
//        final EditText systolicMonitorValue = (EditText)promptsView.findViewById(R.id.systolic_set_id);
//        final EditText diastolicMonitorValue = promptsView.findViewById(R.id.diastolic_set_id);
//        int sys_value = LocalStorage.getSystolicLimitValue(getContext());
//        int dia_value = LocalStorage.getDiastolicLimitValue(getContext());
//        systolicMonitorValue.setText(sys_value+"");
//        diastolicMonitorValue.setText(dia_value+"");
//
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton("Set",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                // get user input and set it to result
//                                // edit text
//                                final String systolicValue = systolicMonitorValue.getText().toString();
//                                final String diastolicValue = diastolicMonitorValue.getText().toString();
//                                LocalStorage.saveSystolicLimitValue(getContext(),Integer.parseInt(systolicValue));
//                                LocalStorage.saveDiastolicLimitValue(getContext(),Integer.parseInt(diastolicValue));
//
//                            }
//                        })
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        alertDialog.show();
//
//    }


    /**
     * resume to reload all patients
     */
    @Override
    public void onResume() {
        super.onResume();

        allPatientsPresenter.initAllPatients();
        Log.d("Test","onResume");

    }

    /**
     *  the function is belongs to request button, which will retrieve the data from web service and
     *     clear up all the selected patient and existed patient before the data loading.
     */
    private void onRequestBtn(){
        allPatientsPresenter.clearSelectPatients();
        allPatientsPresenter.clearAllPatients();
        allPatientsPresenter.updateAllPatient();
        ViewModelProviders.of(getActivity()).get(ShareViewModel.class).sendMessageToSelected("change");
    }



    /**
     *   this function would send the selected patients data from the fragment which contains all patients to
     *   the other fragment which show the selected patients to monitor the TC value
     * @param v: the fragment view
     */
    private void onSendBtn(View v){

        allPatientsPresenter.updateSelectPatients(adapter.selectPatientCards);
        ViewModelProviders.of(getActivity()).get(ShareViewModel.class).sendMessageToSelected("change");


    }


    /**
     * this function would clear all the selected patients
     */
    private void onClearBtn(){
        allPatientsPresenter.clearSelectPatients();
        ViewModelProviders.of(getActivity()).get(ShareViewModel.class).sendMessageToSelected("change");
    }


    /**
     * this function would send all patients card details to recycler view adapter and it would list all
     * patients one by one.
     * @param allPatientCards: patient cards for all patients under current practitioner
     */
    @Override
    public void initAllPatientsCardView(ArrayList<PatientCard> allPatientCards) {
        this.adapter.setData(allPatientCards);
        recyclerView.setAdapter(adapter);

    }



    /**
     *this function would send a list of selected patient card which would be showed in the second fragment one by one.
     * @param selectedPatientCards: selected patient cards for all patients under current practitioner
     */
    @Override
    public void initSelectedPatientsCardView(ArrayList<PatientCard> selectedPatientCards) {
        this.adapter.setSelected_patient(selectedPatientCards);
        recyclerView.setAdapter(adapter);

    }

    /**
     * this function would hide the loading symbol
     */
    @Override
    public void hideLoadingPanel() {
        v.findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
    }

    /**
     * display the systolic and diastolic blood pressure limit in dialog edit text
     * @param systolicValue: systolic limit value
     * @param diastolicValue:  diastolic limit value
     */
    @Override
    public void displayBloodPressureLimitValue(int systolicValue, int diastolicValue) {
        systolicMonitorValue.setText(systolicValue+"");
        diastolicMonitorValue.setText(diastolicValue+"");
    }

    /**
     *  this function would show the loading symbol
     */
    @Override
    public void showLoadingPanel(){
        v.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
    }

}

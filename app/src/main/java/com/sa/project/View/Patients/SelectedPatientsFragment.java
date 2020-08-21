package com.sa.project.View.Patients;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sa.project.Model.Local.LocalStorage;
import com.sa.project.Presenter.Contract.SelectedPatientsContract;
import com.sa.project.R;
import com.sa.project.Presenter.SelectedPatientsPresenter;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class SelectedPatientsFragment extends ParentFragment implements SelectedPatientsContract.View {


    private View v;
    private SelectedPatientsContract.Presenter selectedPatientsPresenter;

    final int CARD_VIEW = 0;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_selected, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view_selected);
        layoutManager = new LinearLayoutManager(getActivity());  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
        adapter = new PatientRecyclerAdapter(getContext(), 1);
        ///
        selectedPatientsPresenter = new SelectedPatientsPresenter(this,getContext(),CARD_VIEW);
        selectedPatientsPresenter.initSelectedPatients();
        selectedPatientsPresenter.initSelectedPatientsBloodPressureMonitorIds();
        selectedPatientsPresenter.initSelectedPatientsCholesterolMonitorIds();
        ///
        Button sendBtn = (Button) v.findViewById(R.id.recvBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onRecvBtn();}
        });
        Button stopBtn = (Button) v.findViewById(R.id.onStopBtn_id);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onStopBtn();}
        });

        Button startBtn = (Button) v.findViewById(R.id.checkbox_change_id);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onBarBtn();}
        });
        ToggleButton tcToggle = (ToggleButton)v.findViewById(R.id.sys_toggle_id);
        tcToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedPatientsPresenter.initSelectedPatientsCholesterolMonitorIds();
                if(isChecked){

                    adapter.tc_id_list = setMonitorIds(adapter.tc_id_list,adapter.allPatientCards);
                    onBarBtn();
                }else{
                    adapter.tc_id_list.clear();
                    onBarBtn();
                }
            }
        });

        ToggleButton bpToggle = (ToggleButton)v.findViewById(R.id.dia_toggle_id);
        bpToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedPatientsPresenter.initSelectedPatientsBloodPressureMonitorIds();
                if(isChecked){

                    adapter.bp_id_list = setMonitorIds(adapter.bp_id_list,adapter.allPatientCards);
                    onBarBtn();
                }else{

                    adapter.bp_id_list.clear();
                    onBarBtn();

                }
            }
        });



        ToggleButton allToggle = (ToggleButton)v.findViewById(R.id.boold_pressure_toggle_id);
        allToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedPatientsPresenter.initSelectedPatientsCholesterolMonitorIds();
                selectedPatientsPresenter.initSelectedPatientsBloodPressureMonitorIds();
                if(isChecked){
                    adapter.tc_id_list = setMonitorIds(adapter.tc_id_list,adapter.allPatientCards);
                    adapter.bp_id_list = setMonitorIds(adapter.bp_id_list,adapter.allPatientCards);
                    onBarBtn();
                }else{
                    adapter.tc_id_list.clear();
                    adapter.bp_id_list.clear();
                    onBarBtn();

                }
            }
        });




        return v;


    }
    //this function would listen all fragment, when all fragment save the new selected patients, it would observe the MutableLive data changed in the
    //shareViewModel so that it would request the data from sharePreference and update the new selected patients.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setup the listener for the fragment A
        ViewModelProviders.of(getActivity()).get(ShareViewModel.class).getContainerSelected().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                onRecvBtn();
            }
        });

    }

    private ArrayList<String> setMonitorIds(ArrayList<String> monitorIds, ArrayList<PatientCard> selectedPatients){
        monitorIds.clear();
        for(int i = 0; i<selectedPatients.size();i++){
            monitorIds.add(selectedPatients.get(i).getPatientID());
        }
        return monitorIds;
    }




    //this function would stop the update for the selected patients.
    private void onStopBtn(){
        if(selectedPatientsPresenter.checkUpdateTimeExist() != null){
            selectedPatientsPresenter.cancelUpdateFrequency();
        }
        Toast.makeText(getContext(),"Monitor stopped",Toast.LENGTH_LONG).show();


    }
    private void onBarBtn(){

        selectedPatientsPresenter.updateSelectedPatientsCholesterolMonitorIds(adapter.tc_id_list);
        selectedPatientsPresenter.updateSelectedPatientsBloodPressureMonitorIds(adapter.bp_id_list);

        if(selectedPatientsPresenter.checkUpdateTimeExist() != null){
            selectedPatientsPresenter.cancelUpdateFrequency();
        }
        selectedPatientsPresenter.initSelectedPatients();

    }



    //the function would set the update time as well as it would receive the new data from web service for the selected patients
    //And if there is no update time , the update time would set to default for 20 seconds.
    private void onRecvBtn(){
        EditText updateTimeTv = v.findViewById(R.id.update_time_id);
        String frequency = updateTimeTv.getText().toString();

        if(selectedPatientsPresenter.checkUpdateTimeExist() != null){
            selectedPatientsPresenter.cancelUpdateFrequency();
        }

        if(!frequency.equals("")){
            int frequencyInt = Integer.parseInt(frequency);
            selectedPatientsPresenter.informFrequency(frequencyInt);
            Toast.makeText(getContext(),"Set Frequency to "+frequency+" seconds.",Toast.LENGTH_LONG).show();
        }
        selectedPatientsPresenter.initSelectedPatients();


    }



    //this function would send a list of selected patient cards to the adapter,
    //so the adapter in recyclerView can list them one by one.
    @Override
    public void initSelectedPatientsCardView(ArrayList<PatientCard> patientCard) {
        adapter.setData(patientCard);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initSelectedPatientsBloodPressureMonitorCheckbox(ArrayList<String> monitoredBloodPressurePatientsIDs) {
        adapter.setDataBpList(monitoredBloodPressurePatientsIDs);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initSelectedPatientsCholesterolMonitorCheckbox(ArrayList<String> monitoredCholesterolPatientsIDs) {
        adapter.setDataTcList(monitoredCholesterolPatientsIDs);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }




}






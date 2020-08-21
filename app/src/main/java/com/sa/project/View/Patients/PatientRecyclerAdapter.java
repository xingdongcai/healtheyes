package com.sa.project.View.Patients;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.sa.project.R;
import com.sa.project.View.PatientDetail.PatientDetailActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * the adapter which handle all patient card in both all patient fragment and selected patient fragment
 */
public class PatientRecyclerAdapter extends RecyclerView.Adapter<PatientRecyclerAdapter.ViewHolder> {
    ArrayList<PatientCard> selectPatientCards = new ArrayList<PatientCard>();
    ArrayList<PatientCard> allPatientCards = new ArrayList<PatientCard>();
    ArrayList<Integer> selected_pos = new ArrayList<>();
    ArrayList<String> tc_id_list = new ArrayList<>();
    ArrayList<String> bp_id_list = new ArrayList<>();
    ArrayList<PatientCard> highSystolicBloodPressurePatientCards = new ArrayList<>();


    Context mContext;
    private int section;

    /**
     *
     * @param mContext: the fragment context
     * @param section: section is to identify the fragment
     */
    public PatientRecyclerAdapter(Context mContext, int section){
        this.mContext = mContext;
        this.section = section;
    }

    /**
     * set up the patient ids which need to monitor cholesterol value
     * @param monitoredCholesterolPatientsIDs: list of patient ids
     */
    public void setDataTcList(ArrayList<String> monitoredCholesterolPatientsIDs){
        this.tc_id_list = monitoredCholesterolPatientsIDs;
    }

    /**
     * set up the patient ids which need to monitor blood pressure value
     * @param monitoredBloodPressurePatientsIDs:list of patient ids
     */
    public void setDataBpList(ArrayList<String> monitoredBloodPressurePatientsIDs){
        this.bp_id_list = monitoredBloodPressurePatientsIDs;

    }

    /**
     * this function would create the view holder based the xml file card view
     * @param parent: nested view group
     * @param viewType: type of view
     * @return: the view holder of card view
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_card_layout, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        Log.d("adapter","onCreateViewHolder");
        return viewHolder;
    }




    /**
     * this function would bind the each patient card view, and there are two section, section 0 is for all patients and section 1 is for selected patients.
     * In the all patients section, the card view would change color between write and black when the user clicked.
     * In the selected patients section, the card view would show the TC value and highlight the guys whose TC values are higher than average.
     * @param holder: view holder of current card
     * @param position: current card position
     */
    @Override
    public void onBindViewHolder(@NonNull PatientRecyclerAdapter.ViewHolder holder, int position) {
        String systolicBloodPressure,diastolicBloodPressure,totalCholesterol,bpEffectedTime,tcEffectedTime;
        PatientCard currentPatient = allPatientCards.get(position);
        int currentColor = holder.bpEffectedTv.getCurrentTextColor();
        if(section == 1 ){
            //highlight the total Cholesterol
            if(currentPatient.isCholesterolHighlighted()){
                holder.tcTv.setTextColor(Color.RED);


                //System.out.println("highlight:"+position+"  "+allPatientCards.get(position).getPatientFullName()+allPatientCards.get(position).isHighlighted());
            }else{
                holder.tcTv.setTextColor(currentColor);

            }
            //highlight the blood pressure
            if(currentPatient.isSystolicBloodPressureHighlighted()){
                holder.systolicBpTv.setTextColor(Color.parseColor("purple"));
            }else {
                holder.systolicBpTv.setTextColor(currentColor);
            }

            if(currentPatient.isDiastolicBloodPressureHighlighted()){
                holder.diastolicBpTv.setTextColor(Color.parseColor("purple"));
            }else {
                holder.diastolicBpTv.setTextColor(currentColor);

            }
            //get the Cholesterol value
            if(currentPatient.getTotalCholesterol() == 0.0){
                totalCholesterol = "N/A";
                tcEffectedTime = "N/A";
            }else{
                totalCholesterol =currentPatient.getTotalCholesterol()+" mg/dL";
                tcEffectedTime = currentPatient.getCholesterolEffectiveDateTime();
            }
            holder.tcTv.setText(totalCholesterol);
            holder.tcEffectedTv.setText(tcEffectedTime);

            //get the blood pressure value
            if(currentPatient.getDiastolicBloodPressure() == 0){
                diastolicBloodPressure = "N/A";
                systolicBloodPressure = "N/A";
                bpEffectedTime = "N/A";
            }else{
                diastolicBloodPressure = currentPatient.getDiastolicBloodPressure() +" mmHg";
                systolicBloodPressure = currentPatient.getSystolicBloodPressure() +" mmHg";
                bpEffectedTime = currentPatient.getBloodPressureEffectiveDateTime();
            }
            //set name and card view black ground color
            holder.systolicBpTv.setText(systolicBloodPressure);
            holder.diastolicBpTv.setText(diastolicBloodPressure);
            holder.bpEffectedTv.setText(bpEffectedTime);
            holder.nameTv.setText(allPatientCards.get(position).getPatientFullName());
            holder.cardView.setBackgroundResource(R.color.colorBackWhite);


            //resume the check box status
            if(tc_id_list != null){
                if(tc_id_list.contains(allPatientCards.get(position).getPatientID())){
                    holder.checkboxTcTv.setChecked(true);
                }else{
                    holder.checkboxTcTv.setChecked(false);
                }
            }
            if(bp_id_list != null){
                if(bp_id_list.contains(allPatientCards.get(position).getPatientID())){
                    holder.checkBoxBdTv.setChecked(true);
                }else{
                    holder.checkBoxBdTv.setChecked(false);
                }
            }

            return;

        }

        if(section == 0){
            if (selected_pos.contains(position)) {

                holder.cardView.setBackgroundResource(R.color.colorBackDark);
            } else {
                holder.cardView.setBackgroundResource(R.color.colorBackWhite);
            }
            holder.nameTv.setText(allPatientCards.get(position).getPatientFullName());
            holder.tcTv.setVisibility(View.GONE);
          //  holder.monitorTv.setVisibility(View.INVISIBLE);
            holder.checkBoxBdTv.setVisibility(View.GONE);
            holder.checkboxTcTv.setVisibility(View.GONE);
            holder.diastolicBpTv.setVisibility(View.GONE);
            holder.systolicBpTv.setVisibility(View.GONE);
            holder.bpEffectedTv.setVisibility(View.GONE);
            holder.tcEffectedTv.setVisibility(View.GONE);
            holder.text_tc.setVisibility(View.GONE);
            holder.text_sys.setVisibility(View.GONE);
            holder.text_dia.setVisibility(View.GONE);

        }

        Log.d("adapter","onBindViewHolder");
    }

    /**
     *  This function is for the fragment to set the selected patient, therefore when the app resume,
     *  all selected patient would show in black color.
     * @param selectedPatientCards: patient card of selected patient
     */
    public void setSelected_patient(ArrayList<PatientCard> selectedPatientCards){
        this.selectPatientCards = selectedPatientCards;
        this.selected_pos.clear();
        this.allPatientCards.forEach(p ->{
            this.selectPatientCards.forEach(s ->{
                if(p.getPatientID().equals(s.getPatientID())){
                    this.selected_pos.add(this.allPatientCards.indexOf(p));
                }
            });
        });
        this.notifyDataSetChanged();


    }

    /**
     * get the number of cards
     * @return: the number of cards
     */
    @Override
    public int getItemCount() {
        return allPatientCards.size();
    }

    /**
     * set the patient cards data
     * @param allPatientCards: the patient card for current fragment
     */
    public void setData(ArrayList<PatientCard> allPatientCards) {
        this.allPatientCards = allPatientCards;
    }

    /**
     * this function contains onclick function for each card view.
     * for the all patients section, the relevant patient for the card view would send to selected patient and the onBindView function would change it color
     * for the selected patients section, the click would open a new activity which would request the more details for current patients and show all details..
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTv;

        public Context nConetx;
        public CardView cardView;
        public TextView tcTv;
        public TextView diastolicBpTv;
        public TextView systolicBpTv;
        public TextView tcEffectedTv;
        public TextView bpEffectedTv;
        public CheckBox checkboxTcTv;
        public CheckBox checkBoxBdTv;
        public TextView text_sys;
        public TextView text_dia;
        public TextView text_tc;

        private Integer index = 0;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nConetx = mContext;
            nameTv = itemView.findViewById(R.id.name_id);
            tcTv = itemView.findViewById(R.id.patient_tc_id);
            tcEffectedTv  = itemView.findViewById(R.id.tc_effected_time_id);
            systolicBpTv = itemView.findViewById(R.id.sys_blood_id);
            diastolicBpTv = itemView.findViewById(R.id.dia_blood_id);
            bpEffectedTv = itemView.findViewById(R.id.bp_effiected_tiem_id);
           // monitorTv = itemView.findViewById(R.id.monitior_switch_id);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            checkboxTcTv = itemView.findViewById(R.id.tc_checkBox_id);
            checkBoxBdTv = itemView.findViewById(R.id.bd_checkBox_id);
            text_dia = itemView.findViewById(R.id.text_dia_id);
            text_sys = itemView.findViewById(R.id.text_sys_id);
            text_tc = itemView.findViewById(R.id.text_tc_id);


            //listen the check box status changed
            checkBoxBdTv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String bd_id = allPatientCards.get(getAdapterPosition()).getPatientID();
                    if(isChecked){
                        if(!bp_id_list.contains(bd_id)){
                            bp_id_list.add(bd_id);
                        }
                    }else{
                        if(bp_id_list.contains(bd_id)){
                            bp_id_list.remove(bd_id);
                        }
                    }
                    //notifyDataSetChanged();
                }

            });
            checkboxTcTv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String tc_id = allPatientCards.get(getAdapterPosition()).getPatientID();
                    if(isChecked){
                        if(!tc_id_list.contains(tc_id)){
                            tc_id_list.add(tc_id);
                        }
                    }else{
                        if(tc_id_list.contains(tc_id)){
                            tc_id_list.remove(tc_id);
                        }
                    }

                }

            });
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                if(section == 0) {
                    if (selected_pos.contains(getAdapterPosition())) {
                        selectPatientCards.removeIf(p ->(p.getPatientID().equals( allPatientCards.get(getAdapterPosition()).getPatientID())));
                        selected_pos.removeAll(Arrays.asList(getAdapterPosition()));
                    } else {
                        selectPatientCards.add(allPatientCards.get(getAdapterPosition()));
                        selected_pos.add(getAdapterPosition());
                    }
                    notifyDataSetChanged();
                }else if(section == 1) {
                    int selectedPos = getAdapterPosition();
                    String patientID = allPatientCards.get(selectedPos).getPatientID();
                    Intent intentToMainActivity = new Intent(nConetx, PatientDetailActivity.class);
                    intentToMainActivity.putExtra("patientID",patientID);
                    nConetx.startActivity(intentToMainActivity);
                 //   Intent intentToBar = new Intent(nConetx, BarChartMonitor.class);
          //          ArrayList<PatientCard> myList = allPatientCards;
              //      intentToBar.putExtra("patients",myList);
                  //  nConetx.startActivity(intentToBar);
                }
                }
            });

        }
    }
}


package com.sa.project.Presenter;

import com.sa.project.Model.DataHolder.BloodPressureMonitor;
import com.sa.project.Model.DataHolder.CholesterolMonitor;
import com.sa.project.Model.DataHolder.Patient;
import com.sa.project.View.Patients.PatientCard;

import java.util.ArrayList;

/**
 * Process the monitors data for presenter before send to view
 * Use PatientCard to store the data needed for the user interface(View)
 */
class MonitorDataProcessor {
    /**
     * @method put the cholesterol values and patient information into the PatientCard
     * @param cholesterolMonitors : cholesterol values from CholesterolMonitorContainer
     * @param selected_patients : selected patients by the practitioner
     * @return PatientCard that hold the patient basic information and cholesterol values
     */
    public ArrayList<PatientCard> mergeCholesterolToPatientCard(ArrayList<CholesterolMonitor> cholesterolMonitors, ArrayList<Patient> selected_patients){

        ArrayList<PatientCard> patientCards = new ArrayList<>();



        double avgOfTotalCholesterolValues = getCholesterolAvg(cholesterolMonitors);
        for(Patient selectedPatient:selected_patients){
            PatientCard newPatientCard = new PatientCard(selectedPatient.getId(),selectedPatient.getFullName());
            for(CholesterolMonitor cholesterolMonitor:cholesterolMonitors){
                if(cholesterolMonitor.getPatientID().equals(selectedPatient.getId())){
                    /*
                    Calculating average total cholesterol values of selected patients which have observation result
                    if above the average, highlight the value
                     */
                    if(cholesterolMonitor.getLatestTotalCholesterol()>avgOfTotalCholesterolValues){
                        newPatientCard.setCholesterolHighlighted(true);
                    }

                    double totalCholesterol = cholesterolMonitor.getLatestTotalCholesterol();
                    String effectiveDateTime = cholesterolMonitor.getEffectiveDateTime();
                    newPatientCard.setCholesterolEffectiveDateTime(effectiveDateTime);
                    newPatientCard.setTotalCholesterol(totalCholesterol);
                    break;
                }
            }
            patientCards.add(newPatientCard);
        }

        return patientCards;
    }

    /**
     * @method put the Blood pressure values and patient information into the PatientCard
     * @param bloodPressureMonitors : blood pressure values from BloodPressureMonitorContainer
     * @param selected_patients : selected patients by the practitioner
     * @param systolicLimit : Systolic limit entered by practitioner
     * @param diastolicLimit : Diastolic limit entered by practitioner
     * @return PatientCard that hold the patient basic information and cholesterol values
     */
    public ArrayList<PatientCard> mergeBloodPressureToPatientCard(ArrayList<BloodPressureMonitor> bloodPressureMonitors, ArrayList<Patient> selected_patients,int systolicLimit, int diastolicLimit){
        ArrayList<PatientCard> patientCards = new ArrayList<>();

        for(Patient selectedPatient:selected_patients){
            PatientCard newPatientCard = new PatientCard(selectedPatient.getId(),selectedPatient.getFullName());
            for(BloodPressureMonitor bloodPressureMonitor: bloodPressureMonitors){
                if(bloodPressureMonitor.getPatientID().equals(selectedPatient.getId())){
                    int systolicBloodPressure = bloodPressureMonitor.getSystolicBloodPressure();
                    int diastolicBloodPressure = bloodPressureMonitor.getDiastolicBloodPressure();
                    String effectiveDateTime = bloodPressureMonitor.getEffectiveDateTime();
                    newPatientCard.setBloodPressureEffectiveDateTime(effectiveDateTime);
                    newPatientCard.setDiastolicBloodPressure(diastolicBloodPressure);
                    newPatientCard.setSystolicBloodPressure(systolicBloodPressure);

                    if(systolicBloodPressure>systolicLimit){
                        newPatientCard.setSystolicBloodPressureHighlighted(true);
                    }

                    if(diastolicBloodPressure>diastolicLimit){
                        newPatientCard.setDiastolicBloodPressureHighlighted(true);
                    }
                    break;
                }
            }
            patientCards.add(newPatientCard);
        }


        return patientCards;
    }

    /**
     * @method put both Blood pressure values and Cholesterol values and patient information into the PatientCard
     * @param bloodPressureMonitors : blood pressure values from BloodPressureMonitorContainer
     * @param cholesterolMonitors : cholesterol values from CholesterolMonitorContainer
     * @param selected_patients : selected patients by the practitioner
     * @param systolicLimit : Systolic limit entered by practitioner
     * @param diastolicLimit : Diastolic limit entered by practitioner
     * @return PatientCard that hold the patient basic information and cholesterol values
     */
    public ArrayList<PatientCard> mergeBothMonitorsToPatientCard(ArrayList<BloodPressureMonitor> bloodPressureMonitors,ArrayList<CholesterolMonitor> cholesterolMonitors, ArrayList<Patient> selected_patients,int systolicLimit, int diastolicLimit){
        ArrayList<PatientCard> patientCards = new ArrayList<>();
        double avgOfTotalCholesterolValues = getCholesterolAvg(cholesterolMonitors);

        for(Patient selectedPatient:selected_patients){
            PatientCard newPatientCard = new PatientCard(selectedPatient.getId(),selectedPatient.getFullName());
            for(BloodPressureMonitor bloodPressureMonitor:bloodPressureMonitors){
                if(bloodPressureMonitor.getPatientID().equals(selectedPatient.getId())){
                    int systolicBloodPressure = bloodPressureMonitor.getSystolicBloodPressure();
                    int diastolicBloodPressure = bloodPressureMonitor.getDiastolicBloodPressure();
                    String effectiveDateTime = bloodPressureMonitor.getEffectiveDateTime();
                    newPatientCard.setBloodPressureEffectiveDateTime(effectiveDateTime);
                    newPatientCard.setDiastolicBloodPressure(diastolicBloodPressure);
                    newPatientCard.setSystolicBloodPressure(systolicBloodPressure);

                    if(systolicBloodPressure>systolicLimit){
                        newPatientCard.setSystolicBloodPressureHighlighted(true);
                    }

                    if(diastolicBloodPressure>diastolicLimit){
                        newPatientCard.setDiastolicBloodPressureHighlighted(true);
                    }
                    break;
                }
            }

            for(CholesterolMonitor cholesterolMonitor:cholesterolMonitors){
                if(cholesterolMonitor.getPatientID().equals(selectedPatient.getId())){
                    if(cholesterolMonitor.getLatestTotalCholesterol()>avgOfTotalCholesterolValues){
                        newPatientCard.setCholesterolHighlighted(true);
                    }
                    double totalCholesterol = cholesterolMonitor.getLatestTotalCholesterol();
                    String effectiveDateTime = cholesterolMonitor.getEffectiveDateTime();
                    newPatientCard.setCholesterolEffectiveDateTime(effectiveDateTime);
                    newPatientCard.setTotalCholesterol(totalCholesterol);
                    break;
                }
            }
            patientCards.add(newPatientCard);
        }



        return patientCards;
    }




    /*
    -------------------------------------------------------------------------------------------------
     */


    /**
     * @method used to calculate the average number of requesting cholesterol values
     * @param cholesterolMonitors: cholesterol values from CholesterolMonitorContainer
     * @return the average number that used to highlight the cholesterol value
     */
    private double getCholesterolAvg(ArrayList<CholesterolMonitor> cholesterolMonitors){
        double sumOfTotalCholesterolValues = 0.0;
        for(CholesterolMonitor cholesterolMonitor:cholesterolMonitors){
            sumOfTotalCholesterolValues += cholesterolMonitor.getLatestTotalCholesterol();
        }
        return sumOfTotalCholesterolValues/cholesterolMonitors.size();
    }

}

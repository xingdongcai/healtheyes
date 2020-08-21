package com.sa.project.Model.Local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class is responsible for saving/getting data from local by SharedPreferences
 * Format: JSON string
 * All these method need a context provided by operating Activity or Fragment
 */
public abstract class LocalStorage {

    /**
     * @method Save practitioner id after the practitioner has entered id and login
     * @param practitionerID:
     * @param context:
     * @return true if saved successfully
     */
    public static boolean savePractitionerID(String practitionerID,Context context){
        boolean flag = false;

        SharedPreferences practitionerPreferences = context.getSharedPreferences("Practitioner",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = practitionerPreferences.edit();
        editor.putString("practitionerID", practitionerID);
        if(editor.commit()){
            flag = true;
        }
        return flag;
    }

    /**
     * @method Get current login practitioner's id
     * @param context: provided by current running Activity
     * @return string of practitioner's id
     */
    public static String getCurrentPractitionerID(Context context){
        SharedPreferences practitionerPreferences = context.getSharedPreferences("Practitioner", Context.MODE_PRIVATE);
        return practitionerPreferences.getString("practitionerID","");
    }

    /*
    ------------------------------------------------------------------------------------------------
     */

    /**
     * The methods in this block are used to save/get all/selected patients of the current practitioner
     * Before or After using these methods, transfer the ArrayList data to json string by Gson is needed
     */

    public static String getAllPatientsOfCurrentPractitioner(Context context){
        String practitionerID = getCurrentPractitionerID(context);
        SharedPreferences patientsPreferences = context.getSharedPreferences("AllPatients", Context.MODE_PRIVATE);
        return patientsPreferences.getString(practitionerID,"");
    }

    public static boolean saveAllPatientsOfCurrentPractitioner(Context context,String allPatientsString){
        String practitionerID = getCurrentPractitionerID(context);
        boolean flag = false;

        SharedPreferences patientsPreferences = context.getSharedPreferences("AllPatients",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = patientsPreferences.edit();
        editor.putString(practitionerID, allPatientsString);
        if(editor.commit()){
            flag = true;
        }
        return flag;
    }

    public static String getSelectedPatientsOfCurrentPractitioner(Context context){
        String practitionerID = getCurrentPractitionerID(context);
        SharedPreferences patientsPreferences = context.getSharedPreferences("toSelected", Context.MODE_PRIVATE);
        return patientsPreferences.getString(practitionerID,"");
    }

    public static boolean saveSelectedPatientsOfCurrentPractitioner(Context context,String selectedPatientsString){
        String practitionerID = getCurrentPractitionerID(context);
        boolean flag = false;

        SharedPreferences patientsPreferences = context.getSharedPreferences("toSelected",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = patientsPreferences.edit();
        editor.putString(practitionerID, selectedPatientsString);
        if(editor.commit()){
            flag = true;
        }
        return flag;
    }

    /*
    ------------------------------------------------------------------------------------------------
     */

    /**
     * Store/Retrieve the Frequency N to update the monitor
     *
     */
    public static boolean saveMonitorFrequency(Context context, int N){
        String practitionerID = getCurrentPractitionerID(context);
        boolean flag = false;

        SharedPreferences practitionerPreferences = context.getSharedPreferences("Frequency",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = practitionerPreferences.edit();
        editor.putInt(practitionerID, N);
        if(editor.commit()){
            flag = true;
        }
        return flag;

    }

    public static int getMonitorFrequency(Context context){
        String practitionerID = getCurrentPractitionerID(context);
        SharedPreferences practitionerPreferences = context.getSharedPreferences("Frequency", Context.MODE_PRIVATE);
        return practitionerPreferences.getInt(practitionerID,20);
    }

    /*
    ------------------------------------------------------------------------------------------------
     */

    /**
     * The methods in this block are used to save/get monitored patients of the current practitioner
     * Before or After using these methods, transfer the ArrayList data to json string by Gson is needed
     */

    public static boolean saveCholesterolMonitoredPatients(Context context,String cholesterolMonitoredPatientsString ){
        String practitionerID = getCurrentPractitionerID(context);
        boolean flag = false;
        SharedPreferences patientsPreferences = context.getSharedPreferences("CholesterolMonitored",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = patientsPreferences.edit();
        editor.putString(practitionerID, cholesterolMonitoredPatientsString);
        if(editor.commit()){
            flag = true;
        }
        return flag;
    }

    public static String getCholesterolMonitoredPatients(Context context){
        String practitionerID = getCurrentPractitionerID(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("CholesterolMonitored", Context.MODE_PRIVATE);
        return sharedPreferences.getString(practitionerID,"");
    }

    public static boolean saveBloodPressureMonitoredPatients(Context context,String bloodPressureMonitoredPatientsString ){
        clearHighSystolicPatients(context);

        String practitionerID = getCurrentPractitionerID(context);
        boolean flag = false;
        SharedPreferences patientsPreferences = context.getSharedPreferences("BloodPressureMonitored",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = patientsPreferences.edit();
        editor.putString(practitionerID, bloodPressureMonitoredPatientsString);
        if(editor.commit()){
            flag = true;
        }
        return flag;
    }

    public static String getBloodPressureMonitoredPatients(Context context){
        String practitionerID = getCurrentPractitionerID(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("BloodPressureMonitored", Context.MODE_PRIVATE);
        return sharedPreferences.getString(practitionerID,"");
    }

    public static boolean clearAllMonitoredPatients(Context context){
        String practitionerID = getCurrentPractitionerID(context);
        boolean flag = false;
        SharedPreferences bloodPressureMonitored = context.getSharedPreferences("BloodPressureMonitored",Context.MODE_PRIVATE);
        SharedPreferences cholesterolMonitored = context.getSharedPreferences("CholesterolMonitored",Context.MODE_PRIVATE);

        if(bloodPressureMonitored.edit().remove(practitionerID).commit()){
            if(cholesterolMonitored.edit().remove(practitionerID).commit()){
                flag = true;
            }
        }
        return flag;
    }

    /*
    ------------------------------------------------------------------------------------------------
     */

    /**
     * The methods in this block are used to save/get limit values of the systolic and diastolic blood pressure
     * These values(X and Y) are entered by practitioner
     */

    public static boolean saveSystolicLimitValue(Context context, int systolicValue){
        String practitionerID = getCurrentPractitionerID(context);
        boolean flag = false;

        SharedPreferences systolicSharedPreferences = context.getSharedPreferences("SystolicLimit",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = systolicSharedPreferences.edit();
        editor.putInt(practitionerID,systolicValue);
        if(editor.commit()){
            flag = true;
        }
        return flag;
    }

    public static boolean saveDiastolicLimitValue(Context context, int diastolicValue){
        String practitionerID = getCurrentPractitionerID(context);
        boolean flag = false;

        SharedPreferences diastolicSharedPreferences = context.getSharedPreferences("DiastolicLimit",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = diastolicSharedPreferences.edit();
        editor.putInt(practitionerID,diastolicValue);
        if(editor.commit()){
            flag = true;
        }
        return flag;
    }

    public static int getSystolicLimitValue(Context context){
        String practitionerID = getCurrentPractitionerID(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("SystolicLimit", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(practitionerID,120);
    }

    public static int getDiastolicLimitValue(Context context){
        String practitionerID = getCurrentPractitionerID(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("DiastolicLimit", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(practitionerID,80);
    }

    /*
    ------------------------------------------------------------------------------------------------
     */

    /**
     * The methods in this block are used to save/get the highlighted patients of the current practitioner
     * Which will be used to get more detailed systolic values display in the graphical line chart
     */
    public static boolean saveHighSystolicPatients(Context context,String highSystolicPatientsString){
        String practitionerID = getCurrentPractitionerID(context);
        boolean flag = false;

        SharedPreferences highSystolicSharedPreferences = context.getSharedPreferences("HighSystolic",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = highSystolicSharedPreferences.edit();
        editor.putString(practitionerID,highSystolicPatientsString);
        if(editor.commit()){
            flag = true;
        }
        return flag;
    }

    public static String getHighSystolicPatients(Context context){
        String practitionerID = getCurrentPractitionerID(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("HighSystolic", Context.MODE_PRIVATE);
        return sharedPreferences.getString(practitionerID,"");
    }

    public static boolean clearHighSystolicPatients(Context context){
        String practitionerID = getCurrentPractitionerID(context);
        boolean flag = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences("HighSystolic",Context.MODE_PRIVATE);

        if(sharedPreferences.edit().remove(practitionerID).commit()){
            flag = true;
        }

        return flag;
    }
}

package com.sa.project.Model.ServerSources;

import android.content.Context;

import java.util.ArrayList;

/**
 * Parent class of specific Monitor Container
 */
public abstract class MonitorContainer extends DataSource{

    protected String requestUrl;
    protected int iterator;
    protected int size;
    protected ArrayList<String> patientsIDs;

    protected Context context;

    public MonitorContainer(Context context) {
        this.context = context;
    }

}

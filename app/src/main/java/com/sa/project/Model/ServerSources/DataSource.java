package com.sa.project.Model.ServerSources;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sa.project.Model.Callbacks.ServerCallback;

import org.json.JSONObject;

/**
 * This class is only responsible to request the server and return the JSON object
 */
public abstract class DataSource {
    private String serverUrl = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/";

    protected void requestServer(Context context, String requestUrl, final ServerCallback serverCallback){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest stringRequest =
                new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //send the result to inherited class that using this method to get JSON data
                                    serverCallback.onSuccess(response);
                                } catch (Exception e) {
                                    Log.d("Server Request:", e.getMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Server Request:", error.getMessage());
                    }

                });

        // due to long response time, we need to add a long delay time
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    protected String getServerUrl() {
        return serverUrl;
    }
}

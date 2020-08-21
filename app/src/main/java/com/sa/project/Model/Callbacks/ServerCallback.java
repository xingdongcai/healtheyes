package com.sa.project.Model.Callbacks;

import org.json.JSONObject;

/**
 * return the result from server
 */
public interface ServerCallback {
    void onSuccess(JSONObject jsonResult);
}

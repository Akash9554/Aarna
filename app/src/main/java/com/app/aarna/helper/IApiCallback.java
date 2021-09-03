package com.app.aarna.helper;


public interface IApiCallback {

    /**
     * Method for getting the type and data.
     *
     * @param data Actual data
     */
    void onSuccess(Object type, Object data, Object extraData);

    /**
     * Failure Reason
     * @param data
     */
    void onFailure(Object data);
}

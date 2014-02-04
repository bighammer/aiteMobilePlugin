package com.chariotsolutions.toast.plugin;

import android.util.Log;
import android.widget.Toast;
import org.apache.cordova.*;
//import org.apache.cordova.api.CallbackContext;
//import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

// daoyan
public class ToastPlugin extends CordovaPlugin  {

    private static final String TAG = "ToastPlugin";
    private static final String LONG_TOAST_ACTION = "show_long";
    private static final String CANCEL_ACTION = "cancel";
    
    private static final String REBOOT_ACTION = "reboot";
    
    private static final int TOAST_MESSAGE_INDEX = 0;

    private Toast toast = null;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, action);

        if (action.equals(CANCEL_ACTION)) {

            cancelToast();

        } else if( action.equals( REBOOT_ACTION ) ) {
          rebootDevice();
        
        } else {

            String message;
            try {
                message = args.getString(TOAST_MESSAGE_INDEX);
            } catch (JSONException e) {
                Log.e(TAG, "Required parameter 'Toast Message' missing");
                //return new PluginResult(Status.JSON_EXCEPTION);
                LOG.e("PingPlugin", "Error : " + e.getMessage());
                return false;
            }

            if (action.equals(LONG_TOAST_ACTION)) {
                showToast(message, Toast.LENGTH_LONG);
            } else {
                showToast(message, Toast.LENGTH_SHORT);
            }
        }

        //return new PluginResult(Status.OK);
        callbackContext.success();
        return true;
    }

    private void cancelToast() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast != null) toast.cancel();
            }
        });
    }

    private void rebootDevice() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process proc = Runtime.getRuntime().exec(new String[]{
                    "su","-c", "reboot"});
                    proc.waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showToast(final String message, final int length) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast = Toast.makeText(cordova.getActivity(), message, length);
                toast.show();
            }
        });
    }

}
package com.chariotsolutions.toast.plugin;

import android.util.Log;
import android.widget.Toast;
import org.apache.cordova.*;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
//import org.apache.cordova.api.CallbackContext;
//import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import com.aitemobile.atpadv1.ATPadv1;
import com.aitemobile.atpadv1.UpdateManager;

// daoyan
public class ToastPlugin extends CordovaPlugin  {

    private static final String TAG = "ToastPlugin";
    private static final String LONG_TOAST_ACTION = "show_long";
    private static final String CANCEL_ACTION = "cancel";
    
    // daoyan
    // 重启和关机
    private static final String REBOOT_ACTION = "reboot";
    private static final String SHUTDOWN_ACTION = "shutdown";
    
    //liuhao
    //获取app的版本信息
    private static final String ACTION_GET_VERSION_NAME = "GetVersionName";
    private static final String ACTION_GET_VERSION_CODE = "GetVersionCode";

    private static final String ACTION_UPDATE_APK = "UpdateAPK";
    
    private static final int TOAST_MESSAGE_INDEX = 0;

    private Toast toast = null;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, action);

        if (action.equals(CANCEL_ACTION)) {

            cancelToast();

        } else if( action.equals( REBOOT_ACTION ) ) {
          rebootDevice();
        
        } else if( action.equals( SHUTDOWN_ACTION ) ) {
          shutdownDevice();
        
        } else if( action.equals( ACTION_GET_VERSION_NAME ) ){
          getVersionName(callbackContext);
        } else if( action.equals( ACTION_GET_VERSION_CODE ) ){
          getVersionCode(callbackContext);
        } else if( action.equals( ACTION_UPDATE_APK ) ){
          updateAPK();
        }{

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
    
    private void updateAPK(){
    	ATPadv1 atp = (ATPadv1)this.cordova.getActivity();
    	UpdateManager manager = new UpdateManager(atp.getATPadContext());
		//
		manager.checkUpdate();
    }

    private void getVersionCode(CallbackContext callbackContext) {
		// TODO Auto-generated method stub
        PackageManager packageManager = this.cordova.getActivity().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0);
            callbackContext.success(packageInfo.versionCode);
        }
        catch (NameNotFoundException nnfe) {
            callbackContext.success(nnfe.getMessage());
        }
	}

	private void getVersionName(CallbackContext callbackContext) {
		// TODO Auto-generated method stub
        PackageManager packageManager = this.cordova.getActivity().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0);
            callbackContext.success(packageInfo.versionName);
        }
        catch (NameNotFoundException nnfe) {
            callbackContext.success(nnfe.getMessage());
        }
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
    
    private void shutdownDevice() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process proc = Runtime.getRuntime().exec(new String[]{
                    "su","-c", "reboot -p"});
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
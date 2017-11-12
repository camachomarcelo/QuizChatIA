package com.dranser.quizchatia.BroadcastReceiver;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Dranser on 12/11/2017.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer (refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.d("TOKEN",refreshedToken);

    }
}

package net.openvpn.openvpn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isNetworkAvailable(context)) {
            // Refresh promos
            Intent promoIntent = new Intent(context, OpenVPNService.class);
            promoIntent.setAction(OpenVPNService.ACTION_REFRESH_PROMOS);
            context.startService(promoIntent);

            // Refresh profiles
            Intent profileIntent = new Intent(context, OpenVPNService.class);
            profileIntent.setAction(OpenVPNService.ACTION_REFRESH_PROFILES);
            context.startService(profileIntent);
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}

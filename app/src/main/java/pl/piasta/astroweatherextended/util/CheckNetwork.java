package pl.piasta.astroweatherextended.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;

public final class CheckNetwork {

    private final Context context;

    public CheckNetwork(Context context) {
        this.context = context;
    }

    public void registerNetworkCallback() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerNetworkCallback(
                new NetworkRequest.Builder().build(),
                new NetworkCallback());
    }

    private static class NetworkCallback extends ConnectivityManager.NetworkCallback {

        @Override
        public void onAvailable(Network network) {
            GlobalVariables.sIsNetworkConnected = true;
        }

        @Override
        public void onLost(Network network) {
            GlobalVariables.sIsNetworkConnected = false;
        }
    }
}

package pl.piasta.astroweatherextended.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

import androidx.annotation.NonNull;

public class ConnectivityLiveData extends SingleLiveEvent<Boolean> {

    private static final String TAG = ConnectivityLiveData.class.getSimpleName();

    public ConnectivityLiveData(@NonNull final Context context) {
        postValue(false);
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerNetworkCallback(
                new NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                        .build(),
                new NetworkCallback());
    }

    private final class NetworkCallback extends ConnectivityManager.NetworkCallback {

        @Override
        public void onAvailable(Network network) {
            Log.i(TAG, "Network connection available");
            GlobalVariables.sIsNetworkConnected = true;
            postValue(true);
        }

        @Override
        public void onUnavailable() {
            Log.i(TAG, "Network connection unavailable");
            GlobalVariables.sIsNetworkConnected = false;
            postValue(false);
        }

        @Override
        public void onLost(Network network) {
            Log.i(TAG, "Network connection lost");
            GlobalVariables.sIsNetworkConnected = false;
            postValue(false);
        }
    }
}

package com.ads.admodule;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.AsyncTask;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtils {


    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            Network network = cm.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
                return capabilities != null &&
                        (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
            }
        }
        return false;
    }


    public static String getInternetSpeed(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            Network network = cm.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                        return "High Speed (WiFi)";
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                        return "Moderate Speed (Mobile Data)";
                    }
                }
            }
        }
        return "No Internet";
    }


    public static void checkInternetStability(InternetCheckCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    InetAddress address = InetAddress.getByName("google.com");
                    return !address.equals("");
                } catch (UnknownHostException e) {
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean isStable) {
                callback.onResult(isStable);
            }
        }.execute();
    }

    public interface InternetCheckCallback {
        void onResult(boolean isStable);
    }
}

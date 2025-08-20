package com.example.talkdjpro;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

public class NsdHelper {

    private NsdManager nsdManager;

    public NsdHelper(Context context) {
        nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }

    public void registerService(int port) {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName("TalkDJProService");
        serviceInfo.setServiceType("_udp._local.");
        serviceInfo.setPort(port);

        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD,
                new NsdManager.RegistrationListener() {
                    @Override public void onServiceRegistered(NsdServiceInfo serviceInfo) { Log.d("NSD", "Service registered"); }
                    @Override public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) { }
                    @Override public void onServiceUnregistered(NsdServiceInfo serviceInfo) { }
                    @Override public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) { }
                });
    }
}
package com.xmz.handson10.data.source;

import com.xmz.handson10.data.DeviceSocket;

import java.util.List;

/**
 * Created by xmz on 2016/5/26.
 */
public interface DeviceSocketSource {

    interface LoadSocketsCallback {

        void onSocketsLoaded(List<DeviceSocket> deviceSockets);

        void onDataNotAvailable();
    }

    interface GetSocketCallback {

        void onSocketLoaded(DeviceSocket deviceSocket);

        void onDataNotAvailable();
    }

    void getDeviceSockets(LoadSocketsCallback callback);

    void getDeviceSocket(int socketId, GetSocketCallback callback);

    void saveDeviceSocket(DeviceSocket deviceSocket);

    void updateDeviceSocket(DeviceSocket deviceSocket);

    void deleteAllSockets();

    void deleteDeviceSocket(int deviceSocketId);


}

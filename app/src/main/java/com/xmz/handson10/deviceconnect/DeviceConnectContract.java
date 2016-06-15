package com.xmz.handson10.deviceconnect;

import android.widget.ImageView;

import com.xmz.handson10.BasePresenter;
import com.xmz.handson10.BaseView;
import com.xmz.handson10.data.DeviceAvailable;
import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.DeviceSocket;

import java.util.List;

/**
 * Created by xmz on 2016/5/30.
 */
public interface DeviceConnectContract {

    interface View extends BaseView<Presenter> {

        void showDeviceSockets(List<DeviceSocket> deviceSockets);

        void showAvailableDevices(List<DeviceAvailable> availableDevices);

        void showAvailableSocketsAnimation(List<DeviceSocket> deviceSockets);

        void showDeviceDescriptions(List<DeviceDescription> deviceDescriptions);

        void showEditControl();

        void showDeviceDescriptionsDrawer();

        void closeDeviceDescriptionsDrawer();

        void flash(ImageView imageView);

        void toastMsg(String message);
    }

    interface Presenter extends BasePresenter {

        void initData();

        void loadDeviceSockets();

        void loadDeviceSocketsAnimation();

        void loadDeviceSocket(int socketId);

        void moveDeviceSocket(int socketId, int x, int y);

        void updateDeviceSocket(DeviceSocket deviceSocket);

        void createDeviceSocket(DeviceSocket deviceSocket);

        void loadAvailableDevices();

        void loadAvailableDevice(int deviceId);

        void loadDeviceDescription();

        void saveDeviceSocket(DeviceSocket deviceSocket);

        void connectDevice(int deviceSocketId, int deviceTypeId, String deviceTypeFeature);

        void disConnectDevice(int socketId);
    }
}

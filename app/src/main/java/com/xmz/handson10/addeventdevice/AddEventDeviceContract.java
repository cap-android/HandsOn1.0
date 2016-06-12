package com.xmz.handson10.addeventdevice;

import com.xmz.handson10.BasePresenter;
import com.xmz.handson10.BaseView;
import com.xmz.handson10.data.DeviceAvailable;
import com.xmz.handson10.data.DeviceDescription;

import java.util.List;

/**
 * Created by xmz on 2016/6/8.
 */
public interface AddEventDeviceContract {

    interface View extends BaseView<Presenter> {

        void showEventDevices(List<DeviceDescription> deviceDescriptions);

        void showAvailableEventDevices(List<DeviceAvailable> availableEventDevices);

        void showEventDevicesEditor(int eventDeviceId);

        void showInstructionEditor(int eventDeviceId);

        void showEventDevicesDrawer();

        void closeEventDevicesDrawer();
    }

    interface Presenter extends BasePresenter {

        void loadEventDevices();

        void loadEventDevice(int eventDeviceId);

        void loadAvailableEventDevices();

        void loadDeviceDescription(int deviceId);

        int addAvailableEventDevice(int deviceTypeId);

        void deleteAvailableEventDevice(int eventDeviceId);

        void updateAvailableEventDevice(DeviceDescription deviceDescription);
    }
}

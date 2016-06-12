package com.xmz.handson10.addeventdevice;

import com.xmz.handson10.data.DeviceDescription;

/**
 * Created by xmz on 2016/6/8.
 */
public class AddEventDevicePresenter implements AddEventDeviceContract.Presenter {

    private final AddEventDeviceContract.View mView;

    public AddEventDevicePresenter(AddEventDeviceContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadEventDevices() {

    }

    @Override
    public void loadEventDevice(int eventDeviceId) {

    }

    @Override
    public void loadAvailableEventDevices() {

    }

    @Override
    public void loadDeviceDescription(int deviceId) {

    }

    @Override
    public int addAvailableEventDevice(int deviceTypeId) {
        return 0;
    }

    @Override
    public void deleteAvailableEventDevice(int eventDeviceId) {

    }

    @Override
    public void updateAvailableEventDevice(DeviceDescription deviceDescription) {

    }

}

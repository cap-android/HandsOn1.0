package com.xmz.handson10.data;

/**
 * Created by xmz on 2016/5/26.
 */

/*
* 当前可用的设备
*/
public class DeviceAvailable {

    private int mDeviceId;              // 该设备的唯一编号

    private int mDeviceTypeId;                // 该设备的类型编号，如：灯，马达，按钮，

    private String mTypeFeatureId;         // 设备的特征编号，如：事件类型设备（按钮、感应器）、动作类型设备（灯、马达）

    public DeviceAvailable(int deviceTypeId, String typeFeatureId) {
        mDeviceTypeId = deviceTypeId;
        mTypeFeatureId = typeFeatureId;
    }

    public DeviceAvailable(int deviceId, int deviceTypeName, String typeFeatureId) {
        mDeviceId = deviceId;
        mDeviceTypeId = deviceTypeName;
        mTypeFeatureId = typeFeatureId;
    }

    public int getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(int deviceId) {
        mDeviceId = deviceId;
    }

    public int getDeviceTypeId() {
        return mDeviceTypeId;
    }

    public void setDeviceTypeId(int deviceTypeId) {
        mDeviceTypeId = deviceTypeId;
    }

    public String getTypeFeatureId() {
        return mTypeFeatureId;
    }

    public void setTypeFeatureId(String typeFeatureId) {
        mTypeFeatureId = typeFeatureId;
    }
}

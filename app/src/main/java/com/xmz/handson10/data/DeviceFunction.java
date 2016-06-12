package com.xmz.handson10.data;

/**
 * Created by xmz on 2016/5/26.
 */
public class DeviceFunction  {

    private int mDeviceTypeId;          // 设备类型编号

    private int mFunctionId;            // 设备功能编号

    private String mFunctionName;       // 对用户显示的功能名称，如“灯亮”

    public DeviceFunction(int deviceTypeId, int functionId, String functionName) {
        mDeviceTypeId = deviceTypeId;
        mFunctionId = functionId;
        mFunctionName = functionName;
    }

    public int getDeviceTypeId() {
        return mDeviceTypeId;
    }

    public void setDeviceTypeId(int deviceTypeId) {
        mDeviceTypeId = deviceTypeId;
    }

    public int getFunctionId() {
        return mFunctionId;
    }

    public void setFunctionId(int functionId) {
        mFunctionId = functionId;
    }

    public String getFunctionName() {
        return mFunctionName;
    }

    public void setFunctionName(String functionName) {
        mFunctionName = functionName;
    }
}

package com.xmz.handson10.data;

import android.util.Log;

/**
 * Created by xmz on 2016/5/26.
 */
public class DeviceDescription {

    private int mTypeId;                // 设备类型编号

    private String mTypeName;           // 设备类型名

    private String mDeviceName;         // 设备类型名 用于显示在用户界面

    private int mFuncCount;          // 该设备有几种功能

    private String[] mFuncName;         // 每种功能对用户显示的名称

    private int mDevicePicSrcId;        // 设备的图片ID

    private String mTypeFeatureId;         // 设备的特征编号，如事件类型设备(按钮、感应器）、动作类型设备（灯、马达）

    public DeviceDescription(String typeName, String deviceName,
                             int funcCount, String[] funcName, int devicePicSrcId, String typeFeatureId) {
        mTypeName = typeName;
        mDeviceName = deviceName;
        mFuncCount = funcCount;
        mFuncName = funcName;
        mDevicePicSrcId = devicePicSrcId;
        mTypeFeatureId = typeFeatureId;
    }

    public DeviceDescription(int typeId, String typeName, String deviceName, int funcCount, String[] funcName, int devicePicSrcId, String typeFeatureId) {
        mTypeId = typeId;
        mTypeName = typeName;
        mDeviceName = deviceName;
        mFuncCount = funcCount;
        mFuncName = funcName;
        mDevicePicSrcId = devicePicSrcId;
        mTypeFeatureId = typeFeatureId;
    }

    public void play(int funcIndex) {
        Log.d("play", "funcIndex");
    }

    public int getTypeId() {
        return mTypeId;
    }

    public void setTypeId(int typeId) {
        mTypeId = typeId;
    }

    public String getTypeName() {
        return mTypeName;
    }

    public void setTypeName(String typeName) {
        mTypeName = typeName;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public void setDeviceName(String deviceName) {
        mDeviceName = deviceName;
    }

    public int getFuncCount() {
        return mFuncCount;
    }

    public void setFuncCount(int funcCount) {
        mFuncCount = funcCount;
    }

    public String[] getFuncName() {
        return mFuncName;
    }

    public void setFuncName(String[] funcName) {
        mFuncName = funcName;
    }

    public int getDevicePicSrcId() {
        return mDevicePicSrcId;
    }

    public void setDevicePicSrcId(int devicePicSrcId) {
        mDevicePicSrcId = devicePicSrcId;
    }

    public String getTypeFeatureId() {
        return mTypeFeatureId;
    }

    public void setTypeFeatureId(String typeFeatureId) {
        mTypeFeatureId = typeFeatureId;
    }
}

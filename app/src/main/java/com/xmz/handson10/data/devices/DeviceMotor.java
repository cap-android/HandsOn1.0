package com.xmz.handson10.data.devices;

import android.util.Log;

import com.xmz.handson10.data.DeviceDescription;

/**
 * Created by xmz on 2016/5/26.
 */
public class DeviceMotor extends DeviceDescription {

    public static final int CLOCKWISE = 1;          // 马达 顺时针转

    public static final int ANTI_CLOCKWISE = 2;     // 马达 逆时针转

    public DeviceMotor(int typeId, String typeName, String deviceName, int funcCount, String[] funcName, int devicePicSrcId, String typeFeatureId) {
        super(typeId, typeName, deviceName, funcCount, funcName, devicePicSrcId, typeFeatureId);
    }

    public DeviceMotor(String typeName, String deviceName, int funcCount, String[] funcName, int devicePicSrcId, String typeFeatureId) {
        super(typeName, deviceName, funcCount, funcName, devicePicSrcId, typeFeatureId);
    }

    @Override
    public void play(int funcIndex) {
        super.play(funcIndex);

        switch (funcIndex) {
            case CLOCKWISE:
                clockwise();
                break;
            case ANTI_CLOCKWISE:
                antiClockwise();
                break;
            default:
                break;
        }

    }

    private void clockwise() {
        Log.d("clockwise", "顺时针转");
    }

    private void antiClockwise() {
        Log.d("antiClockwise", "逆时针转");
    }
}

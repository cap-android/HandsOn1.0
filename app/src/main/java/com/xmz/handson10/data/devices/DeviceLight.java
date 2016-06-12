package com.xmz.handson10.data.devices;

import android.util.Log;

import com.xmz.handson10.data.DeviceDescription;

/**
 * Created by xmz on 2016/5/26.
 */
public class DeviceLight extends DeviceDescription {

    public static final int TURN_ON = 1;        // 灯 点亮

    public static final int TURN_OFF = 2;       // 灯 熄灭

    public static final int FLASH = 3;          // 灯 闪烁

    public DeviceLight(int typeId, String typeName, String deviceName,
                       int funcCount, String[] funcName, int devicePicSrcId, String typeFeatureId) {
        super(typeId, typeName, deviceName, funcCount, funcName, devicePicSrcId, typeFeatureId);
    }

    public DeviceLight(String typeName, String deviceName,
                       int funcCount, String[] funcName, int devicePicSrcId, String typeFeatureId) {
        super(typeName, deviceName, funcCount, funcName, devicePicSrcId, typeFeatureId);
    }

    @Override
    public void play(int funcIndex) {
        super.play(funcIndex);

        switch (funcIndex) {
            case TURN_ON:
                turnOn();
                break;
            case TURN_OFF:
                turnOff();
                break;
            case FLASH:
                flash();
                break;
            default:
                break;
        }
    }

    private void turnOn() {
        Log.d("turnOn", "点亮");
    }

    private void turnOff() {
        Log.d("turnOff", "熄灭");
    }

    private void flash() {
        Log.d("flash", "闪烁");
    }
}

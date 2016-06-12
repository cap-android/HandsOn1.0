package com.xmz.handson10.data.devices;

import android.util.Log;

import com.xmz.handson10.data.DeviceDescription;

/**
 * Created by xmz on 2016/6/2.
 */
public class EventButton extends DeviceDescription {

    public static final int CLICK = 1;

    public static final int LONG_CLICK = 2;

    public static final int CLICK_HOLD = 3;

    public EventButton(int typeId, String typeName, String deviceName,
                       int funcCount, String[] funcName, int devicePicId, String typeFeatureId) {
        super(typeId, typeName, deviceName, funcCount, funcName, devicePicId, typeFeatureId);
    }

    public EventButton(String typeName, String deviceName,
                       int funcCount, String[] funcName, int devicePicId, String typeFeatureId) {
        super(typeName, deviceName, funcCount, funcName, devicePicId, typeFeatureId);
    }

    @Override
    public void play(int funcIndex) {
        super.play(funcIndex);

        switch (funcIndex) {
            case CLICK:
                buttonClick();
                break;
            case LONG_CLICK:
                buttonLongClick();
                break;
            case CLICK_HOLD:
                buttonClickHold();
                break;
            default:
                break;
        }
    }

    private void buttonClick() {
        Log.d("buttonClick", "点击了按钮");
    }

    private void buttonLongClick() {
        Log.d("buttonLongClick", "长按按钮");
    }

    private void buttonClickHold() {
        Log.d("buttonClickHold", "点击按钮，开启持续模式");
    }
}

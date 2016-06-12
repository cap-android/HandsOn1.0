package com.xmz.handson10.data;

/**
 * Created by xmz on 2016/5/26.
 */
public class DeviceSocket {

    private int mSocketId;                  // 设备接口编号

    private int mPicSrcId;                  // 该接口的图片

    private String mType;                   // 该接口的类型

    private int mCoordinate_x;            // 用于用户显示，x坐标

    private int mCoordinate_y;            // 用于用户显示，y坐标

    private int mConnectedDeviceId;         // 该接口连接的设备编号，无连接则为 -1

    public DeviceSocket(int picSrcId, String type) {
        mPicSrcId = picSrcId;
        mType = type;
        mConnectedDeviceId = -1;

        mCoordinate_x = -1;
        mCoordinate_y = -1;
    }

    public DeviceSocket(int socketId, int picSrcId, String type, int coordinate_x, int coordinate_y, int connectedDeviceId) {
        mSocketId = socketId;
        mPicSrcId = picSrcId;
        mType = type;
        mCoordinate_x = coordinate_x;
        mCoordinate_y = coordinate_y;
        mConnectedDeviceId = connectedDeviceId;
    }

    public DeviceSocket(int picSrcId, String type, int connectedDeviceId) {
        mPicSrcId = picSrcId;
        mType = type;
        mConnectedDeviceId = connectedDeviceId;
    }

    public DeviceSocket(int picSrcId, String type, int coordinate_x, int coordinate_y, int connectedDeviceId) {
        mPicSrcId = picSrcId;
        mType = type;
        mCoordinate_x = coordinate_x;
        mCoordinate_y = coordinate_y;
        mConnectedDeviceId = connectedDeviceId;
    }

    public DeviceSocket(int picSrcId, String type, int coordinate_x, int coordinate_y) {
        mPicSrcId = picSrcId;
        mType = type;
        mCoordinate_x = coordinate_x;
        mCoordinate_y = coordinate_y;
        mConnectedDeviceId = -1;
    }

    public int getSocketId() {
        return mSocketId;
    }

    public void setSocketId(int socketId) {
        mSocketId = socketId;
    }

    public int getPicSrcId() {
        return mPicSrcId;
    }

    public void setPicSrcId(int picSrcId) {
        mPicSrcId = picSrcId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public int getCoordinate_x() {
        return mCoordinate_x;
    }

    public void setCoordinate_x(int coordinate_x) {
        mCoordinate_x = coordinate_x;
    }

    public int getCoordinate_y() {
        return mCoordinate_y;
    }

    public void setCoordinate_y(int coordinate_y) {
        mCoordinate_y = coordinate_y;
    }

    public int getConnectedDeviceId() {
        return mConnectedDeviceId;
    }

    public void setConnectedDeviceId(int connectedDeviceId) {
        mConnectedDeviceId = connectedDeviceId;
    }
}

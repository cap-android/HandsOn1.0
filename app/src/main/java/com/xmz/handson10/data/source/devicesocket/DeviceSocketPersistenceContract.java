package com.xmz.handson10.data.source.devicesocket;

/**
 * Created by xmz on 2016/5/27.
 *
 *  用于保存DeviceSocket数据的格式契约
 */
public final class DeviceSocketPersistenceContract {

    public DeviceSocketPersistenceContract() {}

    public static abstract class DeviceSocketEntry {
        public static final String TABLE_NAME = "device_socket";
        public static final String COLUMN_NAME_SOCKET_ID = "id";
        public static final String COLUMN_NAME_PIC_ID = "pic_id";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_X = "x";
        public static final String COLUMN_NAME_Y = "y";
        public static final String COLUMN_NAME_CONNECTED_ID = "connected_device_id";
    }
}

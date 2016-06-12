package com.xmz.handson10.data.source.devicedescription;

/**
 * Created by xmz on 2016/5/27.
 */
public final class DeviceDescriptionPersistenceContract {

    public DeviceDescriptionPersistenceContract() {
    }

    public static abstract class DeviceDescriptionEntry {
        public static final String TABLE_NAME = "device_description";
        public static final String COLUMN_NAME_TYPE_ID = "type_id";
        public static final String COLUMN_NAME_TYPE_NAME = "type_name";
        public static final String COLUMN_NAME_DEVICE_NAME = "device_name";
        public static final String COLUMN_NAME_FUNCTION_COUNT = "func_count";
        public static final String COLUMN_NAME_PIC_ID = "pic_id";
        public static final String COLUMN_NAME_FEATURE_ID = "feature_id";
    }

    public static abstract class DeviceAvailableEntry {
        public static final String TABLE_NAME = "device_available";
        public static final String COLUMN_NAME_DEVICE_ID = "device_id";
        public static final String COLUMN_NAME_TYPE_ID = "device_type_id";
        public static final String COLUMN_NAME_TYPE_FEATURE_ID = "feature_id";
    }

    public static abstract class DeviceFunctionEntry {
        public static final String TABLE_NAME = "device_function";
        public static final String COLUMN_NAME_TYPE_NAME = "type_name";
        public static final String COLUMN_NAME_FUNCTION_ID = "func_id";
        public static final String COLUMN_NAME_FUNCTION_NAME = "func_name";

    }
}

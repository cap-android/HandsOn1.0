package com.xmz.handson10.data.source.devicesocket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xmz.handson10.data.DeviceSocket;
import com.xmz.handson10.data.source.DbHelper;
import com.xmz.handson10.data.source.DeviceSocketSource;
import com.xmz.handson10.data.source.devicesocket.DeviceSocketPersistenceContract.DeviceSocketEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmz on 2016/5/27.
 */
public class DeviceSocketLocalSource implements DeviceSocketSource {

    private static DeviceSocketLocalSource INSTANCE;

    private DbHelper mDbHelper;

    private DeviceSocketLocalSource(Context context, String databaseIndex) {
        mDbHelper = new DbHelper(context, databaseIndex);
    }

    public static DeviceSocketLocalSource getInstance(Context context, String databaseIndex) {
        if (INSTANCE == null) {
            INSTANCE = new DeviceSocketLocalSource(context, databaseIndex);
        }
        return INSTANCE;
    }

    @Override
    public void getDeviceSockets(LoadSocketsCallback callback) {
        List<DeviceSocket> deviceSockets = new ArrayList<DeviceSocket>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DeviceSocketEntry.COLUMN_NAME_SOCKET_ID,
                DeviceSocketEntry.COLUMN_NAME_PIC_ID,
                DeviceSocketEntry.COLUMN_NAME_TYPE,
                DeviceSocketEntry.COLUMN_NAME_X,
                DeviceSocketEntry.COLUMN_NAME_Y,
                DeviceSocketEntry.COLUMN_NAME_CONNECTED_ID
        };

        Cursor c = db.query(
                DeviceSocketEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount()>0) {
            while (c.moveToNext()) {
                int socketId = c.getInt(c.getColumnIndexOrThrow(DeviceSocketEntry.COLUMN_NAME_SOCKET_ID));
                int picId = c.getInt(c.getColumnIndexOrThrow(DeviceSocketEntry.COLUMN_NAME_PIC_ID));
                String socketType = c.getString(c.getColumnIndexOrThrow(DeviceSocketEntry.COLUMN_NAME_TYPE));
                int co_x = c.getInt(c.getColumnIndexOrThrow(DeviceSocketEntry.COLUMN_NAME_X));
                int co_y = c.getInt(c.getColumnIndexOrThrow(DeviceSocketEntry.COLUMN_NAME_Y));
                int connectedId = c.getInt(c.getColumnIndexOrThrow(DeviceSocketEntry.COLUMN_NAME_CONNECTED_ID));
                DeviceSocket deviceSocket = new DeviceSocket(socketId, picId, socketType,
                        co_x, co_y, connectedId);
                deviceSockets.add(deviceSocket);
            }
        }

        if (c != null) {
            c.close();
        }
        db.close();
        if (deviceSockets.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onSocketsLoaded(deviceSockets);
        }
    }

    @Override
    public void getDeviceSocket(int socketId, GetSocketCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selectionArg = String.valueOf(socketId);

        String[] projection = {
                DeviceSocketEntry.COLUMN_NAME_SOCKET_ID,
                DeviceSocketEntry.COLUMN_NAME_PIC_ID,
                DeviceSocketEntry.COLUMN_NAME_TYPE,
                DeviceSocketEntry.COLUMN_NAME_X,
                DeviceSocketEntry.COLUMN_NAME_Y,
                DeviceSocketEntry.COLUMN_NAME_CONNECTED_ID
        };

        String selection = DeviceSocketEntry.COLUMN_NAME_SOCKET_ID + " LIKE ?";
        String[] selectionArgs = { selectionArg };

        Cursor c = db.query(DeviceSocketEntry.TABLE_NAME, projection, selection, selectionArgs,
                null, null, null);
        DeviceSocket deviceSocket = null;

        if (c != null && c.getCount() > 0 ) {
            c.moveToLast();
            int mSocketId = c.getInt(c.getColumnIndexOrThrow(DeviceSocketEntry.COLUMN_NAME_SOCKET_ID));
            int picId = c.getInt(c.getColumnIndexOrThrow(DeviceSocketEntry.COLUMN_NAME_PIC_ID));
            String socketType = c.getString(c.getColumnIndexOrThrow(DeviceSocketEntry.COLUMN_NAME_TYPE));
            int co_x = c.getInt(c.getColumnIndexOrThrow(DeviceSocketEntry.COLUMN_NAME_X));
            int co_y = c.getInt(c.getColumnIndexOrThrow(DeviceSocketEntry.COLUMN_NAME_Y));
            int connectedId = c.getInt(c.getColumnIndexOrThrow(DeviceSocketEntry.COLUMN_NAME_CONNECTED_ID));
            deviceSocket = new DeviceSocket(mSocketId, picId, socketType,
                    co_x, co_y, connectedId);
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (deviceSocket != null) {
            callback.onSocketLoaded(deviceSocket);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveDeviceSocket(DeviceSocket deviceSocket) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DeviceSocketEntry.COLUMN_NAME_PIC_ID, deviceSocket.getPicSrcId());
        values.put(DeviceSocketEntry.COLUMN_NAME_TYPE, deviceSocket.getType());
        values.put(DeviceSocketEntry.COLUMN_NAME_X, deviceSocket.getCoordinate_x());
        values.put(DeviceSocketEntry.COLUMN_NAME_Y, deviceSocket.getCoordinate_y());
        values.put(DeviceSocketEntry.COLUMN_NAME_CONNECTED_ID, deviceSocket.getConnectedDeviceId());

        db.insert(DeviceSocketEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void updateDeviceSocket(DeviceSocket deviceSocket) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selectionArg = String.valueOf(deviceSocket.getSocketId());

        String selection = DeviceSocketEntry.COLUMN_NAME_SOCKET_ID + " LIKE ?";
        String[] selectionArgs = { selectionArg };

        ContentValues values = new ContentValues();
        values.put(DeviceSocketEntry.COLUMN_NAME_SOCKET_ID, deviceSocket.getSocketId());
        values.put(DeviceSocketEntry.COLUMN_NAME_PIC_ID, deviceSocket.getPicSrcId());
        values.put(DeviceSocketEntry.COLUMN_NAME_TYPE, deviceSocket.getType());
        Log.d("update", String.valueOf(deviceSocket.getCoordinate_x()) + "   " + String.valueOf(deviceSocket.getCoordinate_y()));
        values.put(DeviceSocketEntry.COLUMN_NAME_X, deviceSocket.getCoordinate_x());
        values.put(DeviceSocketEntry.COLUMN_NAME_Y, deviceSocket.getCoordinate_y());
        values.put(DeviceSocketEntry.COLUMN_NAME_CONNECTED_ID, deviceSocket.getConnectedDeviceId());

        db.update(DeviceSocketEntry.TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    @Override
    public void deleteAllSockets() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DeviceSocketEntry.TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void deleteDeviceSocket(int deviceSocketId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selectionArg = String.valueOf(deviceSocketId);

        String selection = DeviceSocketEntry.COLUMN_NAME_SOCKET_ID + " LIKE ?";
        String[] selectionArgs = { selectionArg };

        db.delete(DeviceSocketEntry.TABLE_NAME, selection, selectionArgs);
    }
}

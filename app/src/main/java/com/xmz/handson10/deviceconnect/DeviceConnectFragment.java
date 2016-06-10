package com.xmz.handson10.deviceconnect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xmz.handson10.R;
import com.xmz.handson10.data.DeviceAvailable;
import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.DeviceSocket;

import java.util.List;

/**
 * Created by xmz on 2016/5/30.
 */
public class DeviceConnectFragment extends Fragment implements DeviceConnectContract.View {

    private DeviceConnectContract.Presenter mPresenter;

    private DrawerLayout mDrawerLayout;

    private NestedScrollView mNestedScrollView;

    private LinearLayout mDeviceLL;

    private FrameLayout mFrameLayout;

    private FrameLayout mContentFL;

    private FloatingActionButton fabOpenDrawer;

    private int mSocketX;

    private int mSocketY;

    private int mConnectedTypeId;

    private String mConnectedTypeFeature;

    private DeviceSocketTouchMoveListener mDeviceSocketTouchMoveListener;

    private DeviceConnectOnDragListener mDeviceConnectOnDragListener;



    public DeviceConnectFragment() {

    }

    public static DeviceConnectFragment newInstance() { return new DeviceConnectFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(DeviceConnectContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.connect_frag, container, false);

        mDrawerLayout = (DrawerLayout) root.findViewById(R.id.drawer_layout_frag);

        mNestedScrollView = (NestedScrollView) root.findViewById(R.id.devices_scroll_view);
        mDeviceLL = (LinearLayout) root.findViewById(R.id.devices_linear_layout);
//        mFrameLayout = (FrameLayout) root.findViewById(R.id.contentFrame);
        mContentFL = (FrameLayout) root.findViewById(R.id.contentFL);

        fabOpenDrawer =
                (FloatingActionButton) root.findViewById(R.id.fab_open_drawer);
        fabOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeviceDescriptionsDrawer();
            }
        });

        mDeviceSocketTouchMoveListener = new DeviceSocketTouchMoveListener();
        mDeviceConnectOnDragListener = new DeviceConnectOnDragListener();

        return root;
    }

    @Override
    public void showDeviceDescriptions(List<DeviceDescription> deviceDescriptions) {

        mDeviceLL.removeAllViews();
        for (DeviceDescription deviceDescription : deviceDescriptions) {
            LinearLayout compLL = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.device_description_comp, null);
            TextView deviceName = (TextView) compLL.getChildAt(0);
            ImageView devicePic = (ImageView) compLL.getChildAt(1);
            TextView deviceFeature = (TextView) compLL.getChildAt(2);

            deviceName.setText(deviceDescription.getDeviceName());
            devicePic.setImageResource(deviceDescription.getDevicePicSrcId());
            deviceFeature.setText(deviceDescription.getTypeFeatureId());

            int newId = View.generateViewId();
            compLL.setId(newId);
            compLL.setTag(deviceDescription);

            compLL.setOnTouchListener(new DevicesTouchListener());
//            compLL.setOnClickListener(new DevicesClickListener());
            mDeviceLL.addView(compLL);
        }
    }

    @Override
    public void showDeviceSockets(List<DeviceSocket> deviceSockets) {
        mContentFL.removeAllViews();
        for (final DeviceSocket deviceSocket : deviceSockets) {
            LinearLayout compLL = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.device_socket_comp, null);
            ImageView socketPic = (ImageView) compLL.getChildAt(0);
            TextView socketTypeName = (TextView) compLL.getChildAt(1);
            if (deviceSocket.getConnectedDeviceId() == -1) {
                Log.d(String.valueOf(deviceSocket.getSocketId()), "no connected");
                socketPic.setImageResource(R.drawable.socket);
            } else {
                Log.d(String.valueOf(deviceSocket.getSocketId()), "Picture ID");
                socketPic.setImageResource(deviceSocket.getPicSrcId());
            }
            socketTypeName.setText(String.valueOf(deviceSocket.getSocketId()));
            int x = deviceSocket.getCoordinate_x();
            int y = deviceSocket.getCoordinate_y();

            FrameLayout.LayoutParams layoutParam =
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParam.leftMargin = x;
            layoutParam.topMargin = y;

            int newId = View.generateViewId();
            compLL.setId(newId);
            compLL.setTag(deviceSocket);

            compLL.setOnTouchListener(mDeviceSocketTouchMoveListener);
            compLL.setOnDragListener(mDeviceConnectOnDragListener);

            mContentFL.addView(compLL, layoutParam);
        }
    }

    private class DevicesTouchListener implements  View.OnTouchListener {
        DeviceDescription mDeviceDescription;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mDeviceDescription = (DeviceDescription) v.getTag();
            mConnectedTypeId = mDeviceDescription.getTypeId();
            mConnectedTypeFeature = mDeviceDescription.getTypeFeatureId();
            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
            v.startDrag(null, myShadow, null, 0);

//            v.startDrag(null, myShadow, null, 0 );
//            v.setOnDragListener(mDeviceConnectOnDragListener);
            return true;
        }
    }

    private class DevicesClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class DeviceSocketTouchMoveListener implements View.OnTouchListener {
        FrameLayout.LayoutParams layoutParams;
        int socketId;
        boolean isMove = false;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            socketId = ((DeviceSocket) v.getTag()).getSocketId();
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch ( event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isMove = false;
                    layoutParams =
                            (FrameLayout.LayoutParams) v.getLayoutParams();
                    mSocketX = X - layoutParams.leftMargin;
                    mSocketY = Y - layoutParams.topMargin;
                    return true;
                case MotionEvent.ACTION_UP:
                    mDeviceSocketListener.onSocketMove(socketId, X - mSocketX, Y - mSocketY);
                    if (!isMove) {
                        Log.d("up", "is not move");
                        Log.d("socketId Click", String.valueOf(socketId));
                        mPresenter.disConnectDevice(socketId);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    isMove = true;
                    layoutParams.leftMargin = X - mSocketX;
                    layoutParams.topMargin = Y - mSocketY;
                    layoutParams.rightMargin = -200;
                    layoutParams.bottomMargin = -200;
                    v.setLayoutParams(layoutParams);
                    break;
            }
            mContentFL.invalidate();
            return true;
        }
    }

    public class DeviceConnectOnDragListener implements View.OnDragListener {
        DeviceSocket mDeviceSocket;
        DeviceDescription mDeviceDescription;
        @Override
        public boolean onDrag(View v, DragEvent event) {
            mDeviceSocket = (DeviceSocket) v.getTag();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    mPresenter.loadDeviceSocketsAnimation();
                    Log.d("onDrag","start");
                    closeDeviceDescriptionsDrawer();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    mPresenter.loadDeviceSockets();
                    Log.d("ended", String.valueOf(mDeviceSocket.getSocketId()));
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.d("location", String.valueOf(mDeviceSocket.getSocketId()));
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("exited", String.valueOf(mDeviceSocket.getSocketId()));
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("entered", String.valueOf(mDeviceSocket.getSocketId()));
                    break;
                case DragEvent.ACTION_DROP:
                    mPresenter.connectDevice(mDeviceSocket.getSocketId(), mConnectedTypeId, mConnectedTypeFeature);
                    break;

            }
            return true;
        }
    }

    @Override
    public void showAvailableDevices(List<DeviceAvailable> availableDevices) {

    }

    @Override
    public void showAvailableSocketsAnimation(List<DeviceSocket> deviceSockets) {
        Log.d("childCount", String.valueOf(mContentFL.getChildCount()));

        for (int i=0; i<mContentFL.getChildCount(); i++) {

            LinearLayout compLL = (LinearLayout) mContentFL.getChildAt(i);
            DeviceSocket deviceSocket = (DeviceSocket) compLL.getTag();
            ImageView socketPic = (ImageView) compLL.getChildAt(0);
            if (deviceSocket.getConnectedDeviceId() == -1) {
                socketPic.setImageResource(R.drawable.socket_black);
            }
        }

    }

    @Override
    public void showEditControl() {

    }

    @Override
    public void showDeviceDescriptionsDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeDeviceDescriptionsDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public interface DeviceSocketListener {

        void onSocketMove(int socketId, int x, int y);

        void onSocketClick(int socketId);

        void onSocketShowAvailable(int socketId);
    }

    DeviceSocketListener mDeviceSocketListener = new DeviceSocketListener() {
        @Override
        public void onSocketMove(int socketId, int x, int y) {
            mPresenter.moveDeviceSocket(socketId, x, y);
        }

        @Override
        public void onSocketClick(int socketId) {

        }

        @Override
        public void onSocketShowAvailable(int socketId) {

        }
    };
}

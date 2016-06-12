package com.xmz.handson10.addeventdevice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
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

import java.util.List;

/**
 * Created by xmz on 2016/6/8.
 */
public class AddEventDeviceFragment extends Fragment implements AddEventDeviceContract.View {

    private AddEventDeviceContract.Presenter mPresenter;

    private DrawerLayout mDrawerLayout;

    private LinearLayout mEventDeviceLL;

    private FrameLayout mContentFL;

    private FloatingActionButton fabStartPlayActivity;

    private FloatingActionButton fabOpenDrawer;

    private AvailableEventDeviceTouchMoveListener mAvailableEventDeviceTouchMoveListener;

    public AddEventDeviceFragment() {}

    public static AddEventDeviceFragment newInstance() {
        return new AddEventDeviceFragment();
    }

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
    public void setPresenter(AddEventDeviceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_event_frag, container, false);
        mDrawerLayout = (DrawerLayout) root.findViewById(R.id.drawer_layout_frag);
        mEventDeviceLL = (LinearLayout) root.findViewById(R.id.event_devices_linear_layout);
        mContentFL = (FrameLayout) root.findViewById(R.id.contentFL);

        fabOpenDrawer =
                (FloatingActionButton) root.findViewById(R.id.fab_open_drawer);
        fabOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEventDevicesDrawer();
            }
        });

        fabStartPlayActivity =
                (FloatingActionButton) root.findViewById(R.id.fab_play);
        fabStartPlayActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }

    @Override
    public void showEventDevices(List<DeviceDescription> deviceDescriptions) {
        mEventDeviceLL.removeAllViews();
        for (DeviceDescription deviceDescription : deviceDescriptions) {
            LinearLayout compLL = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.event_device_comp, null);
            TextView deviceName = (TextView) compLL.getChildAt(0);
            ImageView devicePic = (ImageView) compLL.getChildAt(1);

            deviceName.setText(deviceDescription.getDeviceName());
            devicePic.setImageResource(deviceDescription.getDevicePicSrcId());

            int newId = View.generateViewId();
            compLL.setId(newId);
            compLL.setTag(deviceDescription);

            compLL.setOnTouchListener(new EventDeviceTouchListener());
            mEventDeviceLL.addView(compLL);
        }
    }

    private class EventDeviceTouchListener implements View.OnTouchListener {
        DeviceDescription mDeviceDescription;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mDeviceDescription = (DeviceDescription) v.getTag();

            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
            v.startDrag(null, myShadow, null, 0);

            return true;
        }
    }

    @Override
    public void showAvailableEventDevices(List<DeviceAvailable> availableEventDevices) {

    }

    private class AvailableEventDeviceTouchMoveListener implements View.OnTouchListener {
        FrameLayout.LayoutParams layoutParams;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    }

    @Override
    public void showEventDevicesEditor(int eventDeviceId) {

    }

    @Override
    public void showInstructionEditor(int eventDeviceId) {

    }

    @Override
    public void showEventDevicesDrawer() {

    }

    @Override
    public void closeEventDevicesDrawer() {

    }
}

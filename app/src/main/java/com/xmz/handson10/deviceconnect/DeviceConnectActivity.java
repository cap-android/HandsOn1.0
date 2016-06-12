package com.xmz.handson10.deviceconnect;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.xmz.handson10.R;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionLocalSource;
import com.xmz.handson10.data.source.devicesocket.DeviceSocketLocalSource;

/**
 * Created by xmz on 2016/5/30.
 */
public class DeviceConnectActivity extends AppCompatActivity {

    private DeviceConnectPresenter mPresenter;

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFrameLayout = (FrameLayout) findViewById(R.id.frame_act);
        DeviceConnectFragment deviceConnectFragment =
                (DeviceConnectFragment) getSupportFragmentManager().findFragmentById(R.id.frame_act);
        if (deviceConnectFragment == null) {
            deviceConnectFragment = DeviceConnectFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frame_act, deviceConnectFragment);
            transaction.commit();
        }

        mPresenter = new DeviceConnectPresenter(
                DeviceDescriptionLocalSource.getInstance(this, "00"),
                DeviceSocketLocalSource.getInstance(this, "00"),
                deviceConnectFragment);
    }

}

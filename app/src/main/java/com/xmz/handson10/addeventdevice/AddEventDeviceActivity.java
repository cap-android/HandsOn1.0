package com.xmz.handson10.addeventdevice;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.xmz.handson10.R;

/**
 * Created by xmz on 2016/6/8.
 */
public class AddEventDeviceActivity extends AppCompatActivity {

    private AddEventDevicePresenter mPresenter;

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFrameLayout = (FrameLayout) findViewById(R.id.frame_act);
        AddEventDeviceFragment addEventDeviceFragment =
                (AddEventDeviceFragment) getSupportFragmentManager().findFragmentById(R.id.frame_act);
        if (addEventDeviceFragment == null) {
            addEventDeviceFragment = AddEventDeviceFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frame_act, addEventDeviceFragment);
            transaction.commit();
        }

        mPresenter = new AddEventDevicePresenter(addEventDeviceFragment);
    }

}

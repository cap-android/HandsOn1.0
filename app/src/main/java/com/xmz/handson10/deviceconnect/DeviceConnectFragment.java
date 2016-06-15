package com.xmz.handson10.deviceconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.media.TransportPerformer;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xmz.handson10.R;
import com.xmz.handson10.addeventdevice.AddEventDeviceActivity;
import com.xmz.handson10.data.DeviceAvailable;
import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.DeviceSocket;

import java.util.List;

/**
 * Created by xmz on 2016/5/30.
 */
public class DeviceConnectFragment extends Fragment implements DeviceConnectContract.View {

//    private LinearLayout connect_linearLayout;

    private boolean flash_state;

    private boolean lock_state = false;

    private DeviceConnectContract.Presenter mPresenter;

    private DrawerLayout mDrawerLayout;

    private NestedScrollView mNestedScrollView;

    private LinearLayout mDeviceLL;

//    private FrameLayout mFrameLayout;

    private android.support.design.widget.CoordinatorLayout coordinatorLayout;

    private FrameLayout mContentFL;

    private FloatingActionButton fabOpenDrawer;

    private FloatingActionButton fabDone;
    private int mSocketX;

    private int mSocketY;

    private int mConnectedTypeId;

    private String mConnectedTypeFeature;

    private DeviceSocketTouchMoveListener mDeviceSocketTouchMoveListener;

    private DeviceConnectOnDragListener mDeviceConnectOnDragListener;


    public DeviceConnectFragment() {

    }

    public static DeviceConnectFragment newInstance() {
        return new DeviceConnectFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toggle, menu);
        Log.d("LOCK_STATE", String.valueOf(lock_state));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_lock:
                lock_state = true;
                toastMsg("界面已锁定");
                break;
            case R.id.menu_unlock:
                lock_state = false;
                toastMsg("界面已解锁");
                break;
        }
        return super.onOptionsItemSelected(item);
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

        coordinatorLayout = (CoordinatorLayout) root.findViewById(R.id.coordinatorLayout);
//        connect_linearLayout = (LinearLayout) root.findViewById(R.id.connect_linner_layout);

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

        fabDone =
                (FloatingActionButton) root.findViewById(R.id.fab_done);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddEventDeviceActivity.class);
                startActivity(intent);
            }
        });


        mDeviceSocketTouchMoveListener = new DeviceSocketTouchMoveListener();
        mDeviceConnectOnDragListener = new DeviceConnectOnDragListener();

        setHasOptionsMenu(true);

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
                Log.d(String.valueOf(deviceSocket.getSocketId()), "Picture ID" + " " + deviceSocket.getSocketId());
                socketPic.setImageResource(deviceSocket.getPicSrcId());
                Log.i("deviceSocket.getConnectedDeviceId", String.valueOf(deviceSocket.getConnectedDeviceId()));
                if (flash_state){
                    flash(socketPic);
                }
            }

            socketTypeName.setText("端口" + String.valueOf(deviceSocket.getSocketId()));
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

    private class DevicesTouchListener implements View.OnTouchListener {
        DeviceDescription mDeviceDescription;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mDeviceDescription = (DeviceDescription) v.getTag();
            mConnectedTypeId = mDeviceDescription.getTypeId();
            mConnectedTypeFeature = mDeviceDescription.getTypeFeatureId();
            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
            v.startDrag(null, myShadow, null, 0);

            return true;
        }
    }

    private class DeviceSocketTouchMoveListener implements View.OnTouchListener {
        FrameLayout.LayoutParams layoutParams;
        int socketId;
        boolean isMove = false;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (!lock_state){
                socketId = ((DeviceSocket) v.getTag()).getSocketId();
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flash_state = false;
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
            }
            return true;
        }
    }

    public class DeviceConnectOnDragListener implements View.OnDragListener {
        DeviceSocket mDeviceSocket;
        DeviceDescription mDeviceDescription;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            if (!lock_state){
                mDeviceSocket = (DeviceSocket) v.getTag();
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        mPresenter.loadDeviceSocketsAnimation();
                        Log.d("onDrag", "start");
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
                        flash_state = true;
                        break;
                }
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

        for (int i = 0; i < mContentFL.getChildCount(); i++) {

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

    /*为控件添加动画效果*/
    @Override
    public void flash(ImageView imageView) {
        /*旋转*/
//            AnimationSet animationSet = new AnimationSet(true);
//            RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
//                    Animation.RELATIVE_TO_SELF,0.5f,
//                    Animation.RELATIVE_TO_SELF,0.5f);
//            rotateAnimation.setDuration(1000);
//            animationSet.addAnimation(rotateAnimation);
//            compLL.getChildAt(0).startAnimation(animationSet);

            /*淡入淡出*/
        //创建一个AnimationSet对象，参数为Boolean型，
        //true表示使用Animation的interpolator，false则是使用自己的
//            AnimationSet animationSet = new AnimationSet(true);
//            //创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明
//            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
//            //设置动画执行的时间
//            alphaAnimation.setDuration(300);
//            //将alphaAnimation对象添加到AnimationSet当中
//            animationSet.addAnimation(alphaAnimation);
//            compLL.getChildAt(0).startAnimation(animationSet);

        AnimationSet animationSet = new AnimationSet(true);
        //参数1：x轴的初始值
        //参数2：x轴收缩后的值
        //参数3：y轴的初始值
        //参数4：y轴收缩后的值
        //参数5：确定x轴坐标的类型
        //参数6：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
        //参数7：确定y轴坐标的类型
        //参数8：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
            /*由远及近*/
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 0.5f, 0, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(300);
        animationSet.addAnimation(scaleAnimation);
        imageView.startAnimation(animationSet);

    }

    @Override
    public void toastMsg(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.invalidate();
            }
        }).show();
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

//    class ImageEffect extends View {
//
//        Paint paint;
//
//        public ImageEffect(Context context) {
//            super(context);
//            paint = new Paint();//初始化画笔
//            paint.setAntiAlias(true);//去锯齿
//            paint.setShadowLayer(5f, 5.0f, 5.0F, Color.BLACK);
//            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        }
//
//        public void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
//            int posX = 20;
//            int posY = 50;
//            int PicWidth, PicHeight;
//
//            Drawable drawable = getResources().getDrawable(R.drawable.light);
//            Drawable dbe = getResources().getDrawable(R.drawable.light).mutate();
//            Drawable drawTest = getResources().getDrawable(R.drawable.light);
//            Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.light);
//            PicWidth = drawable.getIntrinsicWidth();
//            PicHeight = drawable.getIntrinsicHeight();
//
//            drawTest.setBounds(posX,(2*posY) + PicHeight,posX + PicWidth,(2*posY) + 2*PicHeight);
//            drawable.setBounds(posX,posY,posX+PicWidth,posY+PicHeight);
//            dbe.setBounds(0,0,PicWidth,PicHeight);
//
//            canvas.drawColor(Color.WHITE);
//            canvas.save(Canvas.MATRIX_SAVE_FLAG);
//            dbe.setColorFilter(0x7f000000,PorterDuff.Mode.SRC_IN);
//            canvas.translate( (posX+(int) (0.9 * (PicWidth/2))), posY + PicHeight/2);
//
//            canvas.skew(-0.9F, 0.0F);//图像倾斜效果。
//            canvas.scale(1.0f, 0.5f);//图像（其实是画布）缩放，Y方向缩小为1/2。
//            dbe.draw(canvas);//此处为画原图像影子效果图，比原图先画，则会在下层。
//            drawable.clearColorFilter();
//            canvas.restore();
//
//            canvas.save(Canvas.MATRIX_SAVE_FLAG);
//            drawable.draw(canvas);//此处为画原图像，由于canvas有层次效果，因此会盖在影子之上。
//            canvas.restore();
//
//            //默认无效果原图
//            canvas.save(Canvas.MATRIX_SAVE_FLAG);
//            drawTest.draw(canvas);
//            canvas.restore();
//
//            //图片阴影效果
//            Rect rect = new Rect(2*posX + PicWidth + 3, 2*posY + PicHeight + 3, 2*posX + 2*PicWidth - 2, 2*posY + 2*PicHeight - 2);
//            //由于图片的实际尺寸比显示出来的图像要大一些，因此需要适当更改下大小，以达到较好的效果
//
//            RectF rectF = new RectF(rect);
//            canvas.drawRoundRect(rectF, 10f, 10f, paint);//在原有矩形基础上，画成圆角矩形，同时带有阴影层。
//            canvas.drawBitmap(bmp, 2*posX + PicWidth, 2*posY + PicHeight, null);//画上原图。
//            canvas.restore();
//        }
//    }
}

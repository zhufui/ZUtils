package com.zf.zutils.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zf.library.DeviceUtil;
import com.zf.zutils.R;
import com.zf.zutils.adapter.MainAdapter;
import com.zf.zutils.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 展示设备信息
 */
public class DeviceAct extends BaseActivity {
    @BindView(R.id.rl)
    RecyclerView rl;

    private MainAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rl.setLayoutManager(linearLayoutManager);
        adapter = new MainAdapter(getDeviceList());
        rl.setAdapter(adapter);
    }

    private List<String> getDeviceList() {
        List<String> list = new ArrayList<>();
        int[] m = DeviceUtil.getRealMetrics(this);
        list.add("屏幕分辨率:" + m[0] + "*" + m[1]);
        list.add("系统SDK版本号:" + DeviceUtil.getSDKVersion());
        list.add("设备AndroidID:" + DeviceUtil.getAndroidID(mContext));
        list.add("设备厂商:" + DeviceUtil.getManufacturer());
        list.add("IMEI:" + DeviceUtil.getIMEI(mContext));
        DeviceUtil.Device device = DeviceUtil.getDevice();
        list.add("手机品牌:" + device.phoneBrand);
        list.add("手机型号:" + device.phoneModel);
        list.add("手机Android API等级:" + device.buildLevel);
        list.add("手机Android 版本:" + device.buildVersion);
        return list;
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, DeviceAct.class);
        context.startActivity(intent);
    }
}

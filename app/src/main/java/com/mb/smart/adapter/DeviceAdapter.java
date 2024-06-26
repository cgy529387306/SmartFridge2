package com.mb.smart.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.mb.smart.R;

import java.util.List;

public class DeviceAdapter extends BaseQuickAdapter<BleDevice, BaseViewHolder> {


    public DeviceAdapter(@LayoutRes int layoutResId, @Nullable List<BleDevice> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BleDevice bleDevice) {
        boolean isConnected = BleManager.getInstance().isConnected(bleDevice);
        helper.setText(R.id.tv_name, TextUtils.isEmpty(bleDevice.getName()) ? mContext.getString(R.string.title_tawa) : bleDevice.getName());
        helper.setText(R.id.tv_model, bleDevice.getMac());
        helper.setText(R.id.tv_bound_state,isConnected?mContext.getString(R.string.connected):mContext.getString(R.string.disconnet));
        helper.setTextColor(R.id.tv_bound_state,isConnected?mContext.getResources().getColor(R.color.colorBlue):mContext.getResources().getColor(R.color.colorHint));
        helper.setImageResource(R.id.iv_bound_state,isConnected?R.mipmap.ic_arrow_right:R.mipmap.ic_warn_info);
    }

}

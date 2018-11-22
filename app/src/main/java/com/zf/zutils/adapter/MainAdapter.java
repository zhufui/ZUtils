package com.zf.zutils.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zf.zutils.R;

import java.util.List;

public class MainAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MainAdapter(@Nullable List<String> data) {
        super(R.layout.item_main, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv, item);
    }
}

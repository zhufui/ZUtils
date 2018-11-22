package com.zf.zutils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zf.zutils.adapter.MainAdapter;
import com.zf.zutils.base.BaseActivity;
import com.zf.zutils.ui.KeyboardAct;
import com.zf.zutils.vo.DataManager;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.rl)
    RecyclerView rl;

    private MainAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rl.setLayoutManager(linearLayoutManager);
        adapter = new MainAdapter(DataManager.getList());
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            switch (position) {
                case 0:
                    KeyboardAct.launch(mContext);
                    break;
            }
        });
        rl.setAdapter(adapter);
    }
}

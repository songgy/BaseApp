package com.ly.baseapp.ui.InfoList;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ly.baseapp.R;
import com.ly.baseapp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 信息页
 * Created by admin on 2017/3/14.
 */
public class InfoActivity extends BaseActivity {
    @Bind(R.id.tl_main)
    TabLayout tlMain;
    @Bind(R.id.vp_main)
    ViewPager vpMain;
    private InfoAdapter adapter;

    @Override
    protected int getView() {
        return R.layout.activity_info;
    }

    @Override
    protected void initView() {
        tlMain.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置标题
        for (int i = 0; i < InfoUtils.getTitles().size(); i++) {
            tlMain.addTab(tlMain.newTab().setText(InfoUtils.getTitles().get(i)));
        }
        final List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            fragments.add(new InfoFragment());
        }
        adapter = new InfoAdapter(getSupportFragmentManager(), fragments);
        vpMain.setAdapter(adapter);
        tlMain.setupWithViewPager(vpMain);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

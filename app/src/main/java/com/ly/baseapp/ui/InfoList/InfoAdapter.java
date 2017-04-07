package com.ly.baseapp.ui.InfoList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/14.
 */
public class InfoAdapter extends FragmentPagerAdapter {
    private List<Fragment> datas = new ArrayList<>();
    private List<String> titles = InfoUtils.getTitles();

    public InfoAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        if (fragments != null && fragments.size() > 0) {
            datas = fragments;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

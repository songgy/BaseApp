package com.ly.baseapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.baseapp.App;

/**
 * Fragment基类
 * Created by admin on 2016/11/24.
 */

public abstract class BaseFragment extends Fragment implements ExtrasContacts {
    public Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater, container, savedInstanceState);
        return view;
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
        initData();
    }


    protected abstract void initData();

    protected <T> T createApi(final Class<T> api) {
        return App.createApi(getActivity(), api);
    }

}

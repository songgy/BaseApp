package com.ly.baseapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.ly.baseapp.App;

import butterknife.ButterKnife;

/**
 * activity基类
 * Created by admin on 2017/2/4.
 */
public abstract class BaseActivity extends AppCompatActivity implements ExtrasContacts {
    public Context mContext;//上下文

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        ButterKnife.bind(this);
        App.addActivityForList(this);
        mContext = this;
        initView();
        initData();
    }

    protected abstract int getView();

    protected abstract void initView();

    protected abstract void initData();

    protected <T> T createApi(final Class<T> api) {

        return App.createApi(getApplicationContext(), api);
    }


    @Override
    protected void onDestroy() {
        App.removeActivityFromList(this);
        super.onDestroy();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}

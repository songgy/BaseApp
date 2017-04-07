package com.ly.baseapp.ui.InfoList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.baseapp.R;
import com.ly.baseapp.base.BaseFragment;
import com.ly.baseapp.base.recyclerview.RVBaseAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/14.
 */
public class InfoFragment extends BaseFragment {

    @Bind(R.id.rv_info)
    RecyclerView rvInfo;
    private List<String> datas;
    private RVBaseAdapter adapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    protected void initData() {
        rvInfo.setLayoutManager(new GridLayoutManager(mContext,4));
        datas = InfoUtils.getItems();
        adapter = new RVBaseAdapter(datas) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_rv_message;
            }

            @Override
            public void convert(VH holder, Object data, final int position) {
                holder.setText(R.id.tv_title, (String) data);
                holder.setOnClickListener(R.id.tv_title, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog dialog = new AlertDialog.Builder(mContext)
                                .setTitle("提示")
                                .setMessage("确定删除当前？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        datas.remove(position);
                                        adapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .create();
                        dialog.show();
                    }
                });
            }
        };
        rvInfo.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

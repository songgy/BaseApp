package com.ly.baseapp.ui.recycleView;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ly.baseapp.R;
import com.ly.baseapp.base.BaseActivity;
import com.ly.baseapp.base.recyclerview.RVBaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/14.
 */
public class RecycleViewActivity extends BaseActivity {
    @Bind(R.id.rv_message)
    RecyclerView rvMessage;
    private LinearLayoutManager manager;
    private int lastVisibleItemPosition;//最后一个可见
    private RVBaseAdapter adapter;

    private Handler handler = new Handler();

    @Override
    protected int getView() {
        return R.layout.activity_recycleview;
    }

    @Override
    protected void initView() {
        final List<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("iii" + i);
        }
        final List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("item2-----" + i);
        }
        rvMessage.setItemAnimator(new DefaultItemAnimator());
        manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvMessage.setLayoutManager(manager);
        rvMessage.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 > manager.getChildCount()) {
                    Toast.makeText(mContext, "加载更多", Toast.LENGTH_LONG).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            datas.addAll(data);
                            adapter.notifyDataSetChanged();
                        }
                    }, 5000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = manager.findLastVisibleItemPosition();
            }
        });
        rvMessage.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
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
        rvMessage.setAdapter(adapter);
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

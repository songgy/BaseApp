package com.ly.baseapp.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.baseapp.R;


/**
 * 自定义提示Dialog  -- 完工
 * Created by sgy on 2016/11/23.
 */

public class PromptDialog extends Dialog {

    private TextView tvTitle;//标题
    private TextView tvMessage;//信息
    private TextView tvNegative;//第一个按钮
    private TextView tvPositive;//第二个按钮
    private View line;//分割线
    private String title;//标题文本
    private String message;//信息文本
    private String negative;//第一个按钮文本
    private String positive;//第二个按钮文本
    private OnClickListener listener;//事件监听者
    private Context context;//上下文
    private int style;//文本显示样式
    public static int GRAVITY = 0;
    public static int LEFT = 1;
    private static String CANCEL = "取 消";
    private static String CONFIRM = "确 定";

    private PromptDialog(Context context, String title, String message, int style, String negative, String positive) {
        super(context, R.style.dialog);
        this.context = context;
        this.title = title;
        this.message = message;
        this.negative = negative;
        this.positive = positive;
        this.style = style;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setPadding(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics()),
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics()),
                0);
        setContentView(R.layout.dialog_prompt);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        initEvent();
    }

    /**
     * 显示对话框
     *
     * @param context  上下文
     * @param title    标题
     * @param message  信息
     * @param sytle    样式
     * @param negative 第一个按钮
     * @param positive 第二个按钮
     * @param listener 监听
     */
    public static PromptDialog show(Context context, String title, String message, int sytle, String negative, String positive, OnClickListener listener) {
        PromptDialog dialog = new PromptDialog(context, title, message, sytle, negative, positive);
        dialog.setListener(listener);
        dialog.show();
        return dialog;
    }

    /**
     * 显示对话框
     *
     * @param context  上下文
     * @param title    标题
     * @param message  信息
     * @param negative 第一个按钮
     * @param positive 第二个按钮
     * @param listener 监听
     */
    public static PromptDialog show(Context context, String title, String message, String negative, String positive, OnClickListener listener) {
        return show(context, title, message, GRAVITY, negative, positive, listener);
    }

    /**
     * 显示对话框
     *
     * @param context  上下文
     * @param message  信息
     * @param negative 第一个按钮
     * @param positive 第二个按钮
     * @param listener 监听
     */
    public static PromptDialog show(Context context, String message, String negative, String positive, OnClickListener listener) {
        return show(context, "", message, GRAVITY, negative, positive, listener);
    }




    /**
     * 设置事件监听
     */
    private void initEvent() {
        tvNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.negative();
                }
                dismiss();
            }
        });
        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.positive();
                }
                dismiss();
            }
        });
    }

    /**
     * 加载数据
     */
    private void initData() {
        //标题为空的时候
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
            //设置信息布局
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics()),
                    0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics()));
            tvMessage.setLayoutParams(params);
            tvMessage.setPadding(
                    0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics()),
                    0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics())
            );
        } else {
            tvTitle.setText(title);
        }
        //信息为空的时候
        if (TextUtils.isEmpty(message)) {
            tvMessage.setVisibility(View.GONE);
            //设置标题布局
            tvTitle.setPadding(
                    0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics()),
                    0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics())
            );
        } else {
            //当信息布局为左对齐的时候
            if (style == LEFT) {
                tvMessage.setGravity(Gravity.LEFT);
                //如果标题为空
                if (TextUtils.isEmpty(title)) {
                    tvMessage.setPadding(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics()),
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics()),
                            0,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics())
                    );
                }
            }
            tvMessage.setText(message);
        }
        //如果第一个按钮为空，隐藏布局
        if (TextUtils.isEmpty(negative)) {
            tvNegative.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            tvNegative.setText(negative);
        }
        //第二个按钮为空的时候
        if (TextUtils.isEmpty(positive)) {
            tvPositive.setVisibility(View.GONE);
        } else {
            tvPositive.setText(positive);
        }
    }


    /**
     * 设置事件监听
     */
    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * 加载布局
     */
    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvNegative = (TextView) findViewById(R.id.tv_negative);
        tvPositive = (TextView) findViewById(R.id.tv_positive);
        line = findViewById(R.id.view_line);
    }

    /**
     * 事件监听
     */
    public interface OnClickListener {
        /**
         * 第一个按钮
         */
        void negative();

        /**
         * 第二个按钮
         */
        void positive();
    }
}

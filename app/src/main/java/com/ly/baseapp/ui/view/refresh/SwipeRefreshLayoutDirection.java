package com.ly.baseapp.ui.view.refresh;

/**
 * @author xutao
 */
public enum SwipeRefreshLayoutDirection {

    TOP(0), // 只有下拉刷新
    BOTTOM(1), // 只有加载更多
    BOTH(2);// 全都有

    private int mValue;

    SwipeRefreshLayoutDirection(int value) {
        this.mValue = value;
    }

    public static SwipeRefreshLayoutDirection getFromInt(int value) {
        for (SwipeRefreshLayoutDirection direction : SwipeRefreshLayoutDirection
                .values()) {
            if (direction.mValue == value) {
                return direction;
            }
        }
        return BOTH;
    }

}

package com.ly.baseapp.ui.InfoList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/14.
 */
public class InfoUtils {
    public static List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("今日1");
        titles.add("今日2");
        titles.add("今日3");
        titles.add("今日4");
        titles.add("今日5");
        titles.add("今日6");
        titles.add("今日7");
        titles.add("今日8");
        titles.add("今日9");
        return titles;
    }

    public static List<String> getItems() {
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            titles.add("item----" + i);
        }
        return titles;
    }
}

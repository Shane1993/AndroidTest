package com.lzs.androidtest.utils;

import java.util.List;

/**
 * Created by LEE on 2018/3/8.
 */

public class ListUtil {

    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() <= 0;
    }

    public static int getSize(List<?> list) {
        if (isEmpty(list)) {
            return 0;
        }
        return list.size();
    }
}

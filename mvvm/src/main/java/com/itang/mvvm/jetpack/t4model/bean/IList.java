package com.itang.mvvm.jetpack.t4model.bean;

import java.util.List;

/**
 * Created by tangfh on 2017/12/27.
 */

public interface IList<T> {
    List<T> getDataList();
    int getTotalPages();
}

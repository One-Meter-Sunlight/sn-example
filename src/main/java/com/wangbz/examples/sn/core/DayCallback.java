package com.wangbz.examples.sn.core;

public interface DayCallback {

    DayFetchResult fetch(int size);

    String format(String date, long sequence);
}

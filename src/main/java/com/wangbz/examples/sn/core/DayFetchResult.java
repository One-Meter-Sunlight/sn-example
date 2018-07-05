package com.wangbz.examples.sn.core;

import lombok.Data;

import java.util.Date;

@Data
public class DayFetchResult {
    private long start;
    private int size;
    private String date;
    private Date expiry;

    public DayFetchResult(long start, int size, String date, Date expiry) {
        this.start = start;
        this.size = size;
        this.date = date;
        this.expiry = expiry;
    }
}

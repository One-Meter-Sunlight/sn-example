package com.wangbz.examples.sn.core;

import java.util.Date;

public class DayGenerator implements Generator {
    private static final int DEFAULT_SIZE = 1000;
    private DayCallback callback;
    private int size;
    private DayFetchResult day;
    private long currentIndex;
    private long endIndex;

    public DayGenerator(DayCallback callback) {
        this(callback, DEFAULT_SIZE);
    }

    public DayGenerator(DayCallback callback, int size) {
        this.callback = callback;
        this.size = size;

        synchronized (this) {
            fetch();
        }
    }

    private void fetch() {
        day = this.callback.fetch(size);
        this.currentIndex = day.getStart();
        this.endIndex = this.currentIndex + day.getSize();
    }

    @Override
    public String next() {
        Date now = new Date();
        String date;
        long sequence;
        synchronized (this) {
            if (now.before(this.day.getExpiry()) && ++this.currentIndex > endIndex) {
                fetch();
                ++this.currentIndex;
            }
            date = this.day.getDate();
            sequence = this.currentIndex;
        }
        return callback.format(date, sequence);
    }
}

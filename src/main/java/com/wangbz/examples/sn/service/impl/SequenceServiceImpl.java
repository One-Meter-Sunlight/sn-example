package com.wangbz.examples.sn.service.impl;

import com.wangbz.examples.sn.core.*;
import com.wangbz.examples.sn.dao.CompanySequenceDao;
import com.wangbz.examples.sn.model.CompanySequence;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class SequenceServiceImpl implements SequenceService {
    @Resource
    private CompanySequenceDao companySequenceDao;
    private Map<Key, Generator> generators = new ConcurrentHashMap<>();
    private Lock lock = new ReentrantLock();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    @Override
    public String generateOrderNo(String type, String company) {
        return getGenerator(type, company).next();
    }

    private Generator getGenerator(String type, String company) {
        Key key = new Key(type, company);
        if (!generators.containsKey(key)) {
            try {
                lock.lock();
                if (!generators.containsKey(key)) {
                    generators.put(key, buildGenerator(type, company));
                }
            } finally {
                lock.unlock();
            }
        }
        return generators.get(key);
    }

    private Generator buildGenerator(String type, String company) {
        return new DayGenerator(new DayCallback() {
            @Override
            public DayFetchResult fetch(int size) {
                Date now = new Date();
                String strDate = DATE_FORMAT.format(now);
                CompanySequence sequence = companySequenceDao.findByTypeAndCompany(type, company);
                if (sequence == null) {
                    sequence = new CompanySequence();
                    sequence.setId(UUID.randomUUID().toString());
                    sequence.setType(type);
                    sequence.setCompany(company);
                    sequence.setCurrentSequence(size + 1L);
                    sequence.setCurrentDate(strDate);
                    companySequenceDao.save(sequence);
                } else {
                    if (strDate.equals(sequence.getCurrentDate())) {
                        sequence.setCurrentSequence(sequence.getCurrentSequence() + size);
                    } else {
                        sequence.setCurrentDate(strDate);
                        sequence.setCurrentSequence(size + 1L);
                    }
                    sequence.setUpdateDate(now);
                    companySequenceDao.save(sequence);
                }

                Calendar c = Calendar.getInstance();
                c.setTime(now);
                c.add(Calendar.DAY_OF_YEAR, 1);

                return new DayFetchResult(sequence.getCurrentSequence() - size, size, strDate, c.getTime());
            }

            @Override
            public String format(String date, long sequence) {
                return String.format("%s%s%06d", type, date, sequence);
            }
        });
    }

    private class Key {
        String type;
        String company;

        public Key(String type, String company) {
            this.type = type;
            this.company = company;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Key key = (Key) o;
            return Objects.equals(type, key.type) &&
                    Objects.equals(company, key.company);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, company);
        }
    }
}

/*
 * Copyright (c) 2017. 深圳市万连软件有限公司版权所有.
 * 注意:本内容仅限于深圳市万连软件有限公司内部传阅,禁止外泄以及用于其他的商业目的.
 *
 * Document    : SequenceServiceTest.java
 * Created on  : 18-6-23 上午1:09
 * Last Update : 18-6-23 上午1:09
 * Author      : wangbz
 */

package com.wangbz.examples.sn;

import com.wangbz.examples.sn.core.SequenceService;
import com.wangbz.examples.sn.dao.CompanySequenceDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class SequenceServiceTest {
    @Resource
    private SequenceService sequenceService;
//
//    @Test
//    public void test() {
//        for (int i = 0; i < 20; i++) {
//            System.out.println(sequenceService.generateOrderNo("DZ", "WL1001"));
//        }
//    }


    @Resource
    private CompanySequenceDao companySequenceDao;

    @Test
    public void test() {
        System.out.println("companySequenceDao:" + companySequenceDao);
    }
}

package com.wangbz.examples.sn.core;

/**
 * 序列号服务
 */
public interface SequenceService {
    /**
     * 生成新的订单号
     *
     * @param type    订单类型
     * @param company 公司ID
     * @return
     */
    String generateOrderNo(String type, String company);
}

package com.pf.plat.service;

import java.util.List;

/**
 * @ClassName : IPlatformGeneralService
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/10/16-11:07
 */
public interface IPlatformGeneralService {
    void receiptNotice(List<String> extendNo, String matchingRule, String data);
}

package com.course.controller;

import com.course.service.TestDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//测试案例
public class TestDesignController {

    @Autowired
    private TestDesignService testDesignService;

    // 积分行为示例
    public void testDesign(int userId) {
        testDesignService.testDesign(userId);
        System.out.println("======testDesign方法执行并处理积分======");
    }

    // 可兑换积分有效期清零
    public void clearExpiredExchangeScore(int userId) {
        testDesignService.clearExpiredExchangeScore(userId);
        System.out.println("======清零过期可兑换积分======");
    }

    // 月度成长积分评定
    public String evaluateLevel(int userId) {
        String level = testDesignService.evaluateLevel(userId);
        System.out.println("======本月成长积分等级：" + level + "======");
        return level;
    }
}

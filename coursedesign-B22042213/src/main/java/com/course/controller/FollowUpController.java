package com.course.controller;

import com.course.service.FollowUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//完成门诊随访
public class FollowUpController {

    @Autowired
    private FollowUpService followUpService;

    public void followUp(int userId) {
        followUpService.followUp(userId);
        System.out.println("======followUp方法执行并处理积分======");
    }
}

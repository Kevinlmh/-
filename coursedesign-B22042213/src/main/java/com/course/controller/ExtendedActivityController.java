package com.course.controller;

import com.course.service.ExtendedActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//参加扩展活动
public class ExtendedActivityController {

    @Autowired
    private ExtendedActivityService extendedActivityService;

    public void extendedActivity(int userId) {
        extendedActivityService.extendedActivity(userId);
        System.out.println("======extendedActivity方法执行并处理积分======");
    }

}

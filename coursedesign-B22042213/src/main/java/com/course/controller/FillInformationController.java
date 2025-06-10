package com.course.controller;

import com.course.service.FillInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//填写个人资料
public class FillInformationController {

    @Autowired
    private FillInformationService fillInformationService;

    public void fillInformation(int userId) {
        fillInformationService.fillInformation(userId);
        System.out.println("======fillInformation方法执行并处理积分======");
    }
}
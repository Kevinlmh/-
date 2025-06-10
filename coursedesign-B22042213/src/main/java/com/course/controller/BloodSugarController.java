package com.course.controller;

import com.course.service.BloodSugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//记录血糖
public class BloodSugarController {

    @Autowired
    private BloodSugarService bloodSugarService;

    public void bloodSugar(int userId) {
        bloodSugarService.bloodSugar(userId);
        System.out.println("======bloodSugar方法执行并处理积分======");
    }
}

package com.course.controller;

import com.course.service.EvaluateReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//生成评估报告
public class EvaluateReportController {

    @Autowired
    private EvaluateReportService evaluateReportService;

    public void evaluateReport(int userId) {
        evaluateReportService.evaluateReport(userId);
        System.out.println("======evaluateReport方法执行并处理积分======");
    }
}
package com.course.controller;

import com.course.service.ResearchRecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//参加科研招募
public class ResearchRecruitmentController {

    @Autowired
    private ResearchRecruitmentService researchRecruitmentService;

    public void researchRecruitment(int userId) {
        researchRecruitmentService.researchRecruitment(userId);
        System.out.println("======researchRecruitment方法执行并处理积分======");
    }
}

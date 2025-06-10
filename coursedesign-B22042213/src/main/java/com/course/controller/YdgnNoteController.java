package com.course.controller;

import com.course.service.YdgnNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//监测胰岛功能
public class YdgnNoteController {

    @Autowired
    private YdgnNoteService ydgnNoteService;

    public void ydgnNote(int userId) {
        ydgnNoteService.ydgnNote(userId);
        System.out.println("======ydgnNote方法执行并处理积分======");
    }

}

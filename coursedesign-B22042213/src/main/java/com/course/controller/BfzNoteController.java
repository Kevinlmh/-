package com.course.controller;

import com.course.service.BfzNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//填写并发症记录
public class BfzNoteController {

    @Autowired
    private BfzNoteService bfzNoteService;

    public void bfzNote(int userId) {
        bfzNoteService.bfzNote(userId);
        System.out.println("======bfzNote方法执行并处理积分======");
    }
}

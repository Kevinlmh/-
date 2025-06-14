package com.course.service;

import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class BloodSugarService {

    public void bloodSugar(int userId, double bloodSugarValue) {
        try {
            String file = FileUtils.readFile("score");
            PointObject point = JsonUtils.jsonToPojo(file, PointObject.class);
            if (point == null) {
                point = new PointObject();
                point.setId(userId);
                point.setGrowScore(0);
                point.setExchangeScore(0);
                point.setScoreTotal(0);
                point.setValidBloodSugarCount(0);
            }
            // 只有当血糖值大于3时才积分
            if (bloodSugarValue > 3) {
                point.setGrowScore(point.getGrowScore() + 1);
                point.setScoreTotal(point.getGrowScore() + point.getExchangeScore());
                point.incrementValidBloodSugarCount();
                FileUtils.writeFile("score", JsonUtils.objectToJson(point));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

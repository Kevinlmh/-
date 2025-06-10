package com.course.service;

import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import org.springframework.stereotype.Service;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class FillInformationService {

    public void fillInformation(int userId) {
        try {
            String file = FileUtils.readFile("score");
            PointObject point = JsonUtils.jsonToPojo(file, PointObject.class);
            if (point == null) {
                point = new PointObject();
                point.setId(userId);
                point.setGrowScore(0);
                point.setExchangeScore(0);
                point.setScoreTotal(0);
            }
            if (!point.isFilledInformation()) {
                point.setGrowScore(point.getGrowScore() + 2);
                point.setFilledInformation(true);
                point.setScoreTotal(point.getGrowScore() + point.getExchangeScore());
                FileUtils.writeFile("score", JsonUtils.objectToJson(point));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

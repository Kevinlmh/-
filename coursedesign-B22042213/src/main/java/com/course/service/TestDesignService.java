package com.course.service;

import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import com.course.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class TestDesignService {

    public void testDesign(int userId) {
        try {
            String file = FileUtils.readFile("score");
            PointObject pointObject = JsonUtils.jsonToPojo(file, PointObject.class);

            pointObject.setGrowScore(pointObject.getGrowScore() + 1);
            pointObject.setScoreTotal(pointObject.getScoreTotal() + 1);

            FileUtils.writeFile("score", JsonUtils.objectToJson(pointObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearExpiredExchangeScore(int userId) {
        try {
            String file = FileUtils.readFile("score");
            PointObject pointObject = JsonUtils.jsonToPojo(file, PointObject.class);

            Date startDate = pointObject.getExchangeScoreStartDate();
            if (startDate != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                cal.add(Calendar.YEAR, 1);

                if (new Date().after(cal.getTime())) {
                    pointObject.setScoreTotal(pointObject.getScoreTotal() - pointObject.getExchangeScore());
                    pointObject.setExchangeScore(0);
                    pointObject.setExchangeScoreStartDate(null);
                }

                FileUtils.writeFile("score", JsonUtils.objectToJson(pointObject));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String evaluateLevel(int userId) {
        try {
            String file = FileUtils.readFile("score");
            PointObject pointObject = JsonUtils.jsonToPojo(file, PointObject.class);
            int growScore = pointObject.getGrowScore();

            if (growScore >= 30) {
                return "A";
            } else if (growScore >= 10) {
                return "B";
            } else {
                return "C";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "C";
        }
    }
}

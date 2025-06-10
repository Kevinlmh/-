package com.course.service;

import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import com.course.utils.PointUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class TestDesignService {

    // 示例：每次调用加1成长积分
    public void testDesign(int userId) {
        try {
            String file = FileUtils.readFile("score");
            PointObject pointObject = JsonUtils.jsonToPojo(file, PointObject.class);
            if (pointObject == null) {
                pointObject = new PointObject();
                pointObject.setId(userId);
                pointObject.setGrowScore(0);
                pointObject.setExchangeScore(0);
                pointObject.setScoreTotal(0);
            }
            Integer grow = pointObject.getGrowScore() == null ? 0 : pointObject.getGrowScore();
            Integer total = pointObject.getScoreTotal() == null ? 0 : pointObject.getScoreTotal();
            pointObject.setGrowScore(grow + 1);
            pointObject.setScoreTotal(total + 1);
            String content = JsonUtils.objectToJson(pointObject);
            FileUtils.writeFile("score", content);
            System.out.println("+++++积分计算方法+++++");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 可兑换积分有效期清零
    public void clearExpiredExchangeScore(int userId) {
        try {
            String file = FileUtils.readFile("score");
            PointObject point = JsonUtils.jsonToPojo(file, PointObject.class);
            if (point != null && point.getExchangeScoreStartDate() != null) {
                if (PointUtils.isOverOneYear(point.getExchangeScoreStartDate(), new Date())) {
                    point.setExchangeScore(0);
                    point.setExchangeScoreStartDate(null);
                    point.setScoreTotal(point.getGrowScore());
                    FileUtils.writeFile("score", JsonUtils.objectToJson(point));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 月度成长积分评定
    public String evaluateLevel(int userId) {
        try {
            String file = FileUtils.readFile("score");
            PointObject point = JsonUtils.jsonToPojo(file, PointObject.class);
            int grow = point != null && point.getGrowScore() != null ? point.getGrowScore() : 0;
            if (grow <= 10) return "C";
            else if (grow <= 25) return "B";
            else return "A";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "C";
    }
}

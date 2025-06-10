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
public class YdgnNoteService {

    public void ydgnNote(int userId) {
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
            Date now = new Date();
            Date last = point.getLastActionDate().get("YDGN");
            if (last == null || !PointUtils.isWithinMonths(last, now, 3)) {
                point.setExchangeScore(point.getExchangeScore() + 2);
                if (point.getExchangeScoreStartDate() == null) {
                    point.setExchangeScoreStartDate(now);
                }
                point.setScoreTotal(point.getGrowScore() + point.getExchangeScore());
                point.getLastActionDate().put("YDGN", now);
                FileUtils.writeFile("score", JsonUtils.objectToJson(point));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

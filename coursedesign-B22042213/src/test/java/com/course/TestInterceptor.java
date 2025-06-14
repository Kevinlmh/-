package com.course;

import com.course.controller.TestDesignController;
import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import com.course.utils.DateUtils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class TestInterceptor {

    @Autowired
    TestDesignController testDesign;

    private static final String SCORE_FILE = "score";
    private int userId = 1;

    private PointObject getCurrentScore() {
        try {
            String file = FileUtils.readFile(SCORE_FILE);
            return JsonUtils.jsonToPojo(file, PointObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void printScore(PointObject pointObject) {
        if (pointObject != null) {
            System.out.println("成长积分：" + pointObject.getGrowScore());
            System.out.println("可交换积分：" + pointObject.getExchangeScore());
            System.out.println("总积分：" + pointObject.getScoreTotal());
        }
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("测试开始...");
        PointObject pointObject = new PointObject();
        pointObject.setId(userId);
        pointObject.setGrowScore(0);
        pointObject.setExchangeScore(0);
        pointObject.setScoreTotal(0);
        FileUtils.writeFile(SCORE_FILE, JsonUtils.objectToJson(pointObject));
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("测试结束...");
        new File(SCORE_FILE).delete();
    }

    @Test
    public void testDesign() {
        try {
            PointObject beforeScore = getCurrentScore();
            int score1 = beforeScore != null ? beforeScore.getScoreTotal() : 0;

            testDesign.testDesign(userId);

            PointObject afterScore = getCurrentScore();
            int score2 = afterScore != null ? afterScore.getScoreTotal() : 0;

            assertEquals("积分应该增加1分", 1, score2 - score1);
            assertEquals("成长积分应该增加1分", Integer.valueOf(1), afterScore.getGrowScore());
        } catch (Exception e) {
            fail("测试过程中发生异常: " + e.getMessage());
        }
    }

    @Test
    public void testClearExpiredExchangeScore() {
        try {
            // 设置初始积分
            PointObject pointObject = new PointObject();
            pointObject.setId(userId);
            pointObject.setGrowScore(10);
            pointObject.setExchangeScore(5);
            pointObject.setScoreTotal(15);

            // 设置一年前的日期
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -1);
            calendar.add(Calendar.DAY_OF_MONTH, -1); // 确保超过一年
            pointObject.setExchangeScoreStartDate(calendar.getTime());

            FileUtils.writeFile(SCORE_FILE, JsonUtils.objectToJson(pointObject));

            // 执行清零操作
            testDesign.clearExpiredExchangeScore(userId);

            // 验证结果
            PointObject afterScore = getCurrentScore();
            assertNotNull("积分对象不应为空", afterScore);
            assertEquals("可交换积分应该被清零", Integer.valueOf(0), afterScore.getExchangeScore());
            assertEquals("总积分应该等于成长积分", afterScore.getGrowScore(), afterScore.getScoreTotal());
            assertNull("可交换积分开始日期应该被清空", afterScore.getExchangeScoreStartDate());
        } catch (Exception e) {
            fail("测试过程中发生异常: " + e.getMessage());
        }
    }

    @Test
    public void testEvaluateLevel() {
        try {
            PointObject pointObject = new PointObject();
            pointObject.setId(userId);

            // Test C level
            pointObject.setGrowScore(5);
            pointObject.setExchangeScore(0);
            pointObject.setScoreTotal(5);
            FileUtils.writeFile(SCORE_FILE, JsonUtils.objectToJson(pointObject));
            assertEquals("5分应该评为C等级", "C", testDesign.evaluateLevel(userId));

            // Test B level
            pointObject.setGrowScore(15);
            pointObject.setScoreTotal(15);
            FileUtils.writeFile(SCORE_FILE, JsonUtils.objectToJson(pointObject));
            assertEquals("15分应该评为B等级", "B", testDesign.evaluateLevel(userId));

            // Test A level
            pointObject.setGrowScore(30);
            pointObject.setScoreTotal(30);
            FileUtils.writeFile(SCORE_FILE, JsonUtils.objectToJson(pointObject));
            assertEquals("30分应该评为A等级", "A", testDesign.evaluateLevel(userId));
        } catch (Exception e) {
            fail("测试过程中发生异常: " + e.getMessage());
        }
    }
}
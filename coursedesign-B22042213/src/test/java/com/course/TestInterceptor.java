package com.course;

import com.course.controller.TestDesignController;
import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Calendar;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class TestInterceptor {

    @Autowired
    TestDesignController testDesign;

    private static final String SCORE_FILE = "score";
    private int userId = 1;

    private boolean testPassed = false;

    @Rule
    public TestName testName = new TestName();

    private PointObject getCurrentScore() {
        try {
            String file = FileUtils.readFile(SCORE_FILE);
            return JsonUtils.jsonToPojo(file, PointObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void printScore(String label, PointObject pointObject) {
        if (pointObject != null) {
            System.out.println("【" + label + "】成长积分：" + pointObject.getGrowScore()
                    + "，可交换积分：" + pointObject.getExchangeScore()
                    + "，总积分：" + pointObject.getScoreTotal());
        } else {
            System.out.println("【" + label + "】积分对象为空");
        }
    }

    @Before
    public void setUp() {
        System.out.println("测试开始: " + testName.getMethodName());
        PointObject pointObject = new PointObject();
        pointObject.setId(userId);
        pointObject.setGrowScore(0);
        pointObject.setExchangeScore(0);
        pointObject.setScoreTotal(0);
        FileUtils.writeFile(SCORE_FILE, JsonUtils.objectToJson(pointObject));
    }

    @After
    public void tearDown() {
        new File(SCORE_FILE).delete();
        System.out.println("测试结束: " + testName.getMethodName() +
                (testPassed ? "测试通过" : "测试失败"));
        System.out.println("--------------------------------------------------");
    }

    @Test
    public void testDesign() {
        try {
            PointObject beforeScore = getCurrentScore();
            printScore("测试前", beforeScore);

            int score1 = beforeScore != null ? beforeScore.getScoreTotal() : 0;

            testDesign.testDesign(userId);

            PointObject afterScore = getCurrentScore();
            printScore("测试后", afterScore);

            int score2 = afterScore != null ? afterScore.getScoreTotal() : 0;

            assertEquals("积分应该增加1分", 1, score2 - score1);
            assertEquals("成长积分应该增加1分", Integer.valueOf(1), afterScore.getGrowScore());

            testPassed = true;
        } catch (Exception e) {
            fail("测试过程中发生异常: " + e.getMessage());
        }
    }

    @Test
    public void testClearExpiredExchangeScore() {
        try {
            PointObject pointObject = new PointObject();
            pointObject.setId(userId);
            pointObject.setGrowScore(10);
            pointObject.setExchangeScore(5);
            pointObject.setScoreTotal(15);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -1);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            pointObject.setExchangeScoreStartDate(calendar.getTime());

            FileUtils.writeFile(SCORE_FILE, JsonUtils.objectToJson(pointObject));

            printScore("清零前", getCurrentScore());

            testDesign.clearExpiredExchangeScore(userId);

            PointObject afterScore = getCurrentScore();
            printScore("清零后", afterScore);

            assertNotNull("积分对象不应为空", afterScore);
            assertEquals("可交换积分应该被清零", Integer.valueOf(0), afterScore.getExchangeScore());
            assertEquals("总积分应该等于成长积分", afterScore.getGrowScore(), afterScore.getScoreTotal());
            assertNull("可交换积分开始日期应该被清空", afterScore.getExchangeScoreStartDate());

            testPassed = true;
        } catch (Exception e) {
            fail("测试过程中发生异常: " + e.getMessage());
        }
    }

    @Test
    public void testEvaluateLevel() {
        try {
            PointObject pointObject = new PointObject();
            pointObject.setId(userId);

            pointObject.setGrowScore(5);
            pointObject.setExchangeScore(0);
            pointObject.setScoreTotal(5);
            FileUtils.writeFile(SCORE_FILE, JsonUtils.objectToJson(pointObject));
            printScore("5分等级评估", getCurrentScore());
            assertEquals("5分应该评为C等级", "C", testDesign.evaluateLevel(userId));

            pointObject.setGrowScore(15);
            pointObject.setScoreTotal(15);
            FileUtils.writeFile(SCORE_FILE, JsonUtils.objectToJson(pointObject));
            printScore("15分等级评估", getCurrentScore());
            assertEquals("15分应该评为B等级", "B", testDesign.evaluateLevel(userId));

            pointObject.setGrowScore(30);
            pointObject.setScoreTotal(30);
            FileUtils.writeFile(SCORE_FILE, JsonUtils.objectToJson(pointObject));
            printScore("30分等级评估", getCurrentScore());
            assertEquals("30分应该评为A等级", "A", testDesign.evaluateLevel(userId));

            testPassed = true;
        } catch (Exception e) {
            fail("测试过程中发生异常: " + e.getMessage());
        }
    }
}

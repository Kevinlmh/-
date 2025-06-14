package com.course;

import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @author lixuy
 * Created on 2019-04-10
 */
public class TestFileIo {

    private static final String TEST_FILE = "test_score";
    private static final String SPECIAL_CHARS = "测试特殊字符!@#$%^&*()_+{}|:\"<>?[]\\;',./~`";

    @Rule
    public TestName testName = new TestName();

    private boolean testPassed = false;

    @After
    public void afterTest() {
        if (testPassed) {
            System.out.println(testName.getMethodName() + " 测试通过");
        } else {
            System.out.println(testName.getMethodName() + " 测试失败");
        }
        testPassed = false; // 重置标志
    }

    @Test
    public void testWriteAndRead() {
        try {
            PointObject pointObject = new PointObject();
            pointObject.setId(1);
            pointObject.setGrowScore(10);
            pointObject.setExchangeScore(5);
            pointObject.setScoreTotal(15);

            String json = JsonUtils.objectToJson(pointObject);
            FileUtils.writeFile(TEST_FILE, json);

            String readContent = FileUtils.readFile(TEST_FILE);
            PointObject readObject = JsonUtils.jsonToPojo(readContent, PointObject.class);

            assertNotNull("读取的对象不应为空", readObject);
            assertEquals("ID应该匹配", Integer.valueOf(1), Integer.valueOf(readObject.getId()));
            assertEquals("成长积分应该匹配", Integer.valueOf(10), readObject.getGrowScore());
            assertEquals("可交换积分应该匹配", Integer.valueOf(5), readObject.getExchangeScore());
            assertEquals("总积分应该匹配", Integer.valueOf(15), readObject.getScoreTotal());

            testPassed = true;
        } catch (Exception e) {
            fail("测试过程中发生异常: " + e.getMessage());
        } finally {
            new File(TEST_FILE).delete();
        }
    }

    @Test
    public void testEmptyContent() {
        try {
            FileUtils.writeFile(TEST_FILE, "");
            String content = FileUtils.readFile(TEST_FILE);
            assertEquals("空内容应该被正确写入和读取", "", content);

            testPassed = true;
        } catch (Exception e) {
            fail("测试过程中发生异常: " + e.getMessage());
        } finally {
            new File(TEST_FILE).delete();
        }
    }

    @Test
    public void testSpecialCharacters() {
        try {
            FileUtils.writeFile(TEST_FILE, SPECIAL_CHARS);
            String content = FileUtils.readFile(TEST_FILE);
            assertEquals("特殊字符应该被正确写入和读取", SPECIAL_CHARS, content);

            testPassed = true;
        } catch (Exception e) {
            fail("测试过程中发生异常: " + e.getMessage());
        } finally {
            new File(TEST_FILE).delete();
        }
    }

    @Test
    public void testReadNonExistentFile() {
        String content = FileUtils.readFile("non_existent_file_" + System.currentTimeMillis());
        assertEquals("读取不存在的文件应该返回空字符串", "", content);

        testPassed = true;
    }
}

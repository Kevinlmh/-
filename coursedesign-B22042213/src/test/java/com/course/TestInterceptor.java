package com.course;

import com.course.controller.TestDesignController;
import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

import java.io.File;

/**
 * @author lixuy
 * Created on 2019-04-10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class TestInterceptor {
    
    private static final String SCORE_FILE = "score";
    private static final int TEST_USER_ID = 1;
    
    @Autowired
    private TestDesignController testDesign;
    
    private PointObject initialPointState;
    
    @Before
    public void setUp() {
        // Initialize test environment
        initializeScoreFile();
        initialPointState = getCurrentPointState();
    }
    
    @After
    public void tearDown() {
        // Clean up after tests
        File file = new File(SCORE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
    
    private void initializeScoreFile() {
        try {
            PointObject pointObject = new PointObject();
            pointObject.setId(TEST_USER_ID);
            pointObject.setGrowScore(0);
            pointObject.setExchangeScore(0);
            pointObject.setScoreTotal(0);
            String json = JsonUtils.objectToJson(pointObject);
            FileUtils.writeFile(SCORE_FILE, json);
        } catch (Exception e) {
            fail("Failed to initialize score file: " + e.getMessage());
        }
    }
    
    private PointObject getCurrentPointState() {
        try {
            String file = FileUtils.readFile(SCORE_FILE);
            PointObject pointObject = JsonUtils.jsonToPojo(file, PointObject.class);
            assertNotNull("Point object should not be null", pointObject);
            return pointObject;
        } catch (Exception e) {
            fail("Failed to get current point state: " + e.getMessage());
            return null;
        }
    }

    private void assertPointChange(int expectedGrowScoreChange, int expectedExchangeScoreChange) {
        PointObject currentState = getCurrentPointState();
        assertNotNull("Current point state should not be null", currentState);

        int expectedGrow = initialPointState.getGrowScore() + expectedGrowScoreChange;
        int expectedExchange = initialPointState.getExchangeScore() + expectedExchangeScoreChange;
        int expectedTotal = initialPointState.getScoreTotal() + expectedGrowScoreChange + expectedExchangeScoreChange;

        assertEquals("Grow score should change by " + expectedGrowScoreChange, expectedGrow, currentState.getGrowScore().intValue());
        assertEquals("Exchange score should change by " + expectedExchangeScoreChange, expectedExchange, currentState.getExchangeScore().intValue());
        assertEquals("Total score should change by " + (expectedGrowScoreChange + expectedExchangeScoreChange),
                expectedTotal, currentState.getScoreTotal().intValue());
    }


    @Test
    public void testDesignBasicFunctionality() {
        try {
            // Test basic point addition
            testDesign.testDesign(TEST_USER_ID);
            assertPointChange(1, 0); // Assuming testDesign adds 1 grow score
            
            // Verify file integrity
            String fileContent = FileUtils.readFile(SCORE_FILE);
            assertNotNull("Score file content should not be null", fileContent);
            PointObject pointObject = JsonUtils.jsonToPojo(fileContent, PointObject.class);
            assertNotNull("Deserialized point object should not be null", pointObject);
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testDesignMultipleCalls() {
        try {
            // Test multiple consecutive calls
            for (int i = 0; i < 3; i++) {
                testDesign.testDesign(TEST_USER_ID);
            }
            assertPointChange(3, 0); // Assuming each call adds 1 grow score
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testDesignWithInitialPoints() {
        try {
            // Set up initial points
            PointObject pointObject = new PointObject();
            pointObject.setId(TEST_USER_ID);
            pointObject.setGrowScore(50);
            pointObject.setExchangeScore(30);
            pointObject.setScoreTotal(80);
            String json = JsonUtils.objectToJson(pointObject);
            FileUtils.writeFile(SCORE_FILE, json);
            
            // Update initial state
            initialPointState = getCurrentPointState();
            
            // Test point addition with existing points
            testDesign.testDesign(TEST_USER_ID);
            assertPointChange(1, 0);
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        }
    }
}

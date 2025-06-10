package com.course;

import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

/**
 * @author lixuy
 * Created on 2019-04-10
 */
public class TestFileIo {
    private static final String TEST_FILE = "score";
    private PointObject testPointObject;

    @Before
    public void setUp() {
        // Initialize test data
        testPointObject = new PointObject();
        testPointObject.setId(1);
        testPointObject.setGrowScore(100);
        testPointObject.setExchangeScore(50);
        testPointObject.setScoreTotal(150);
    }

    @After
    public void tearDown() {
        // Clean up test file after each test
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testWriteAndRead() {
        try {
            // Test writing
            String json = JsonUtils.objectToJson(testPointObject);
            assertNotNull("JSON string should not be null", json);
            FileUtils.writeFile(TEST_FILE, json);
            
            // Verify file exists
            File file = new File(TEST_FILE);
            assertTrue("File should exist after writing", file.exists());
            
            // Test reading
            String readContent = FileUtils.readFile(TEST_FILE);
            assertNotNull("Read content should not be null", readContent);
            assertFalse("Read content should not be empty", readContent.isEmpty());
            
            // Verify content
            PointObject readPointObject = JsonUtils.jsonToPojo(readContent, PointObject.class);
            assertNotNull("Deserialized object should not be null", readPointObject);
            assertEquals("ID should match", testPointObject.getId(), readPointObject.getId());
            assertEquals("Grow score should match", testPointObject.getGrowScore(), readPointObject.getGrowScore());
            assertEquals("Exchange score should match", testPointObject.getExchangeScore(), readPointObject.getExchangeScore());
            assertEquals("Total score should match", testPointObject.getScoreTotal(), readPointObject.getScoreTotal());
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test
    public void testReadNonExistentFile() {
        // Delete file if it exists
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
        
        // Attempt to read non-existent file
        String content = FileUtils.readFile(TEST_FILE);
        assertTrue("Content should be empty for non-existent file", content.isEmpty());
    }

    @Test
    public void testWriteWithNullObject() {
        try {
            // Test writing null object
            String json = JsonUtils.objectToJson(null);
            FileUtils.writeFile(TEST_FILE, json);
            
            // Verify file exists
            File file = new File(TEST_FILE);
            assertTrue("File should exist after writing", file.exists());
            
            // Read and verify content
            String readContent = FileUtils.readFile(TEST_FILE);
            assertNotNull("Read content should not be null", readContent);
            assertEquals("Read content should be 'null'", "null", readContent);
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        }
    }
}

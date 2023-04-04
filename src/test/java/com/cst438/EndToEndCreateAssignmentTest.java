package com.cst438;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import com.cst438.domain.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EndToEndCreateAssignmentTest {

    public static final String FIREFOX_DRIVER_FILE_LOCATION = "geckodriver";
    public static final String URL = "http://localhost:3000";
    public static final String TEST_INSTRUCTOR_EMAIL = "dwisneski@csumb.edu";
    public static final int SLEEP_DURATION = 1000; // 1 second.
    public static final String TEST_ASSIGNMENT_NAME = "Test Assignment";
    public static final String TEST_ASSIGNMENT_DUE_DATE = "2999-12-31";
    public static final String TEST_COURSE_TITLE = "Test Course";
    public static final int TEST_COURSE_ID = 9999;
    public static final String CREATE_ASSIGNMENT_BUTTON_ID = "CreateAssignmentButton";
    public static final String ASSIGNMENT_NAME_TEXTBOX_ID = "assignmentName";
    public static final String ASSIGNMENT_DATE_PICKER_ID = "dueDate";
    public static final String ASSIGNMENT_COURSE_SELECT_ID = "courseSelect";
    public static final String ASSIGNMENT_SUBMIT_BUTTON_ID = "submit";

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Test
    public void createAssignmentTest() throws Exception {

        Course c = new Course();
        c.setCourse_id(TEST_COURSE_ID);
        c.setInstructor(TEST_INSTRUCTOR_EMAIL);
        c.setSemester("Fall");
        c.setYear(2021);
        c.setTitle(TEST_COURSE_TITLE);

        courseRepository.save(c);

        System.setProperty("webdriver.firefox.driver", FIREFOX_DRIVER_FILE_LOCATION);
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(URL);
        Thread.sleep(SLEEP_DURATION);

        try {
            WebElement createAssignmentButton = driver.findElement(By.id(CREATE_ASSIGNMENT_BUTTON_ID));
            assertNotEquals(createAssignmentButton, null, "'"+ CREATE_ASSIGNMENT_BUTTON_ID +"'button element not found.");
            createAssignmentButton.click();


            Thread.sleep(SLEEP_DURATION);


            //detect elements
            WebElement assignmentNameTextbox = driver.findElement(By.id(ASSIGNMENT_NAME_TEXTBOX_ID));
            assertNotEquals(createAssignmentButton, null, "'"+ ASSIGNMENT_NAME_TEXTBOX_ID +"' textbox element not found.");

            WebElement assignmentDueDatePicker = driver.findElement(By.id(ASSIGNMENT_DATE_PICKER_ID));
            assertNotEquals(assignmentDueDatePicker, null, "'"+ ASSIGNMENT_DATE_PICKER_ID +"' datepicker element not found.");

            Select assignmentCourseSelect = new Select(driver.findElement(By.id(ASSIGNMENT_COURSE_SELECT_ID)));
            assertNotEquals(assignmentCourseSelect, null, "'"+ ASSIGNMENT_COURSE_SELECT_ID +"' datepicker element not found.");

            WebElement submitAssignmentButton = driver.findElement(By.id(ASSIGNMENT_SUBMIT_BUTTON_ID));
            assertNotEquals(submitAssignmentButton, null, "'"+ ASSIGNMENT_SUBMIT_BUTTON_ID +"'button element not found.");

            //input test values
            assignmentNameTextbox.sendKeys(TEST_ASSIGNMENT_NAME+Keys.ENTER);
            assertEquals(assignmentNameTextbox.getAttribute("value"), TEST_ASSIGNMENT_NAME, "Value Mismatch: " + ASSIGNMENT_NAME_TEXTBOX_ID);

            assignmentDueDatePicker.clear();
            assignmentDueDatePicker.sendKeys(TEST_ASSIGNMENT_DUE_DATE+Keys.ENTER);
            assertEquals(assignmentDueDatePicker.getAttribute("value"), TEST_ASSIGNMENT_DUE_DATE, "Value Mismatch: " + ASSIGNMENT_DATE_PICKER_ID);

            assignmentCourseSelect.selectByVisibleText(TEST_COURSE_TITLE);
            assertEquals(assignmentCourseSelect.getFirstSelectedOption().getText(), TEST_COURSE_TITLE, "Value Mismatch: " + ASSIGNMENT_COURSE_SELECT_ID + "/text");
            assertEquals(assignmentCourseSelect.getFirstSelectedOption().getAttribute("value"), Integer.toString(TEST_COURSE_ID), "Value Mismatch: " + ASSIGNMENT_COURSE_SELECT_ID + "/value");

            Thread.sleep(SLEEP_DURATION);

            //submit
            submitAssignmentButton.click();

            Thread.sleep(SLEEP_DURATION);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Assignment a = assignmentRepository.findByName(TEST_ASSIGNMENT_NAME).get(0);
            assertEquals(a.getCourse().getCourse_id(), c.getCourse_id());
            assertEquals(a.getDueDate(), df.parse(TEST_ASSIGNMENT_DUE_DATE));


        } catch (Exception ex) {
            throw ex;
        } finally {

            Assignment a = assignmentRepository.findByName(TEST_ASSIGNMENT_NAME).get(0);
            if (a != null) {
                assignmentRepository.delete(a);
            }
            courseRepository.delete(c);

            driver.quit();

        }


    }











}

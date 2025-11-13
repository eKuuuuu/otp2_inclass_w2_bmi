package org.example.otp2_inclass_w2_bmi;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {

    private HelloController controller;

    @BeforeEach
    void setUp() {
        // Initialize JavaFX Toolkit
        try {
            javafx.application.Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // JavaFX runtime already initialized
        }

        controller = new HelloController();

        // Use real instances of JavaFX components
        controller.lblWeight = new Label();
        controller.lblHeight = new Label();
        controller.lblResult = new Label();
        controller.txtWeight = new TextField();
        controller.txtHeight = new TextField();
        controller.btnCalculate = new Button();
        controller.button1 = new Button();
        controller.button2 = new Button();
        controller.button3 = new Button();
        controller.button4 = new Button();

        controller.localizedStrings = new HashMap<>();
        controller.localizedStrings.put("invalid", "Invalid input");
        controller.localizedStrings.put("result", "BMI:");
        controller.localizedStrings.put("weight", "Weight");
        controller.localizedStrings.put("height", "Height");
        controller.localizedStrings.put("calculate", "Calculate");
    }

    @Test
    void onCalculateBmi() {
        controller.txtWeight.setText("70");
        controller.txtHeight.setText("170");

        controller.onCalculateBmi();

        assertEquals("BMI: 24,22", controller.lblResult.getText());
    }

    @Test
    void onCalculateBmi_InvalidInput() {
        controller.txtWeight.setText("abc");
        controller.txtHeight.setText("170");

        controller.onCalculateBmi();

        assertEquals("Invalid input", controller.lblResult.getText());
    }

    @Test
    void initialize() {
        controller.initialize();

        assertEquals("en", controller.currentLanguage);
        assertEquals("Weight (kg)", controller.localizedStrings.get("weight"));
        assertEquals("Weight (kg)", controller.lblWeight.getText());
        assertEquals("Height (cm)", controller.lblHeight.getText());
        assertEquals("Calculate BMI", controller.btnCalculate.getText());
    }

    @Test
    void onButton1() {
        controller.onButton1();

        assertEquals("en", controller.currentLanguage);
        assertEquals("Weight (kg)", controller.lblWeight.getText());
    }

    @Test
    void onButton2() {
        controller.onButton2();

        assertEquals("fr", controller.currentLanguage);
        assertEquals("Poids (kg)", controller.lblWeight.getText());
    }

    @Test
    void onButton3() {
        controller.onButton3();

        assertEquals("ur", controller.currentLanguage);
        assertEquals("وزن (کلوگرام)", controller.lblWeight.getText());
    }

    @Test
    void onButton4() {
        controller.onButton4();

        assertEquals("vi", controller.currentLanguage);
        assertEquals("Cân nặng (kg)", controller.lblWeight.getText());
    }
}
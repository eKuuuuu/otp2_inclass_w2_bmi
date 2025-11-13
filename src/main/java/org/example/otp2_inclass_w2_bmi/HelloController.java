package org.example.otp2_inclass_w2_bmi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class HelloController {
    @FXML
    public Label lblWeight;
    @FXML
    public Label lblHeight;
    @FXML
    public Label lblResult;
    @FXML
    public TextField txtWeight;
    @FXML
    public TextField txtHeight;
    @FXML
    public Button btnCalculate;
    @FXML
    public Button button1;
    @FXML
    public Button button2;
    @FXML
    public Button button3;
    @FXML
    public Button button4;

    private double bmi = -1;
    public Map<String, String> localizedStrings = new HashMap<>();
    public String currentLanguage = "en";

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bmi_localization";
    private static final String DB_USER = "eku";
    private static final String DB_PASSWORD = "password";

    public void onCalculateBmi() {
        try {
            double weight = Double.parseDouble(txtWeight.getText());
            double height = Double.parseDouble(txtHeight.getText());
            double heightInMeters = height / 100.0;
            bmi = weight / (heightInMeters * heightInMeters);
            updateLabels();
            saveBmiResult(weight, height, bmi, currentLanguage);
        } catch (NumberFormatException e) {
            lblResult.setText(localizedStrings.get("invalid"));
        }
    }

    private void saveBmiResult(double weight, double height, double bmi, String language) {
        String insertQuery = "INSERT INTO bmi_results (weight, height, bmi, language) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setDouble(1, weight);
            preparedStatement.setDouble(2, height);
            preparedStatement.setDouble(3, bmi);
            preparedStatement.setString(4, language);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML public void initialize() {
        loadLocalizedStrings("en");
        updateLabels();
    }

    private void updateLabels() {
        lblWeight.setText(localizedStrings.get("weight"));
        lblHeight.setText(localizedStrings.get("height"));
        btnCalculate.setText(localizedStrings.get("calculate"));
        button1.setText("EN");
        button2.setText("FR");
        button3.setText("UR");
        button4.setText("VI");

        if (bmi >= 0) {
            lblResult.setText(String.format("%s %.2f", localizedStrings.get("result"), bmi));
        } else {
            lblResult.setText("");
        }
    }

    private void loadLocalizedStrings(String language) {
        localizedStrings.clear();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT `key`, value FROM localization_strings WHERE language = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, language);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                localizedStrings.put(resultSet.getString("key"), resultSet.getString("value"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onButton1() {
        currentLanguage = "en";
        loadLocalizedStrings(currentLanguage);
        updateLabels();
    }

    public void onButton2() {
        currentLanguage = "fr";
        loadLocalizedStrings(currentLanguage);
        updateLabels();
    }

    public void onButton3() {
        currentLanguage = "ur";
        loadLocalizedStrings(currentLanguage);
        updateLabels();
    }

    public void onButton4() {
        currentLanguage = "vi";
        loadLocalizedStrings(currentLanguage);
        updateLabels();
    }
}
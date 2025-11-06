module org.example.otp2_inclass_w2_bmi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.otp2_inclass_w2_bmi to javafx.fxml;
    exports org.example.otp2_inclass_w2_bmi;
}
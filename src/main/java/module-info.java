module lk.ijse.gdse.footwear {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires lombok;
    requires com.ctc.wstx;
    requires java.mail;
    requires net.sf.jasperreports.core;

    opens lk.ijse.gdse.footwear.controller to javafx.fxml;
    opens lk.ijse.gdse.footwear.dto.tm to javafx.base;
    exports lk.ijse.gdse.footwear;
}
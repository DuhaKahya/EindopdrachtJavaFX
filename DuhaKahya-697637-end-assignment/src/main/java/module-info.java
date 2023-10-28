module com.example.duhakahya697637endassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.duhakahya697637endassignment to javafx.fxml;
    exports com.example.duhakahya697637endassignment;
    exports com.example.duhakahya697637endassignment.Controller;
    opens com.example.duhakahya697637endassignment.Controller to javafx.fxml;
    exports com.example.duhakahya697637endassignment.Model;
    opens com.example.duhakahya697637endassignment.Model to javafx.fxml;
    exports com.example.duhakahya697637endassignment.Data;
    opens com.example.duhakahya697637endassignment.Data to javafx.fxml;
    exports com.example.duhakahya697637endassignment.exceptions;
    opens com.example.duhakahya697637endassignment.exceptions to javafx.fxml;
}
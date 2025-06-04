module ap.restaurant.restaurant {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;






//    requires org.postgresql.jdbc;   // Add this for PostgreSQL driver if you use it

    opens ap.restaurant.restaurant to javafx.fxml;  // Allows FXML to reflectively access your package
    exports ap.restaurant.restaurant;               // Makes your package visible outside
}


package it2c.larosa.dcas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Config {
    
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:rode.db"); // Establish connection
            System.out.println("\nConnection Successful");
        } catch (Exception e) {
            System.out.println("\nConnection Failed: " + e);
        }
        return con;
    }
    
     public void addRecord(String sql, String... values) {
        try (Connection conn = this.connectDB(); // Use the connectDB method
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Loop through the values and set them in the prepared statement
            for (int i = 0; i < values.length; i++) {
                pstmt.setString(i + 1, values[i]); // PreparedStatement index starts at 1
            }

            pstmt.executeUpdate();
            System.out.println("\nRecord added successfully!");
        } catch (SQLException e) {
            System.out.println("\nError adding record: " + e.getMessage());
        }
    }
    
}

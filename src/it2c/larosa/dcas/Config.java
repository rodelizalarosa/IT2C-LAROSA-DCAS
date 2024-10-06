
package it2c.larosa.dcas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class Config {
    
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:rode.db"); // Establish connection
            System.out.println("\t\nConnection Successful!");
        } catch (Exception e) {
            System.out.println("\t\nConnection Failed: " + e);
        }
        return con;
    }
    
//    public void addRecord(String sql, String... values) {
//        try (Connection conn = this.connectDB(); // Use the connectDB method
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            // Loop through the values and set them in the prepared statement
//            for (int i = 0; i < values.length; i++) {
//                pstmt.setString(i + 1, values[i]); // PreparedStatement index starts at 1
//            }
//
//            pstmt.executeUpdate();
//            System.out.println("\nRecord added successfully!");
//        } catch (SQLException e) {
//            System.out.println("\nError adding record: " + e.getMessage());
//        }
    
        
    public void addRecord(String sql, Object... values) {
        try (Connection conn = this.connectDB(); // Use the connectDB method
             PreparedStatement prstmt = conn.prepareStatement(sql)) {

            // Loop through the values and set them in the prepared statement dynamically
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    prstmt.setInt(i + 1, (Integer) values[i]); // If the value is Integer
                } else if (values[i] instanceof Double) {
                    prstmt.setDouble(i + 1, (Double) values[i]); // If the value is Double
                } else if (values[i] instanceof Float) {
                    prstmt.setFloat(i + 1, (Float) values[i]); // If the value is Float
                } else if (values[i] instanceof Long) {
                    prstmt.setLong(i + 1, (Long) values[i]); // If the value is Long
                } else if (values[i] instanceof Boolean) {
                    prstmt.setBoolean(i + 1, (Boolean) values[i]); // If the value is Boolean
                } else if (values[i] instanceof java.util.Date) {
                    prstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime())); // If the value is Date
                } else if (values[i] instanceof java.sql.Date) {
                    prstmt.setDate(i + 1, (java.sql.Date) values[i]); // If it's already a SQL Date
                } else if (values[i] instanceof java.sql.Timestamp) {
                    prstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]); // If the value is Timestamp
                } else {
                    prstmt.setString(i + 1, values[i].toString()); // Default to String for other types
                }
            }

            prstmt.executeUpdate();
            System.out.println("\t\nRecord added successfully!");
        } catch (SQLException e) {
            System.out.println("\t\nError adding record: " + e.getMessage());
        }
    }    
    
    public void viewRecord(String sqlQuery, String[] columnHeaders, String[] columnNames) {
    if (columnHeaders.length != columnNames.length) {
        System.out.println("Error: Mismatch between column headers and column names.");
        return;
    }

    try (Connection conn = this.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
         ResultSet rs = pstmt.executeQuery()) {

        // Print the headers dynamically
        StringBuilder headerLine = new StringBuilder();
        headerLine.append("--------------------------------------------------------------------------------\n| ");
        for (String header : columnHeaders) {
            headerLine.append(String.format("%-20s | ", header));
        }
        headerLine.append("\n--------------------------------------------------------------------------------");

        System.out.println(headerLine.toString());

        // Get metadata to check column types
        ResultSetMetaData metaData = rs.getMetaData();

        // Print the rows dynamically based on the provided column names
        while (rs.next()) {
            StringBuilder row = new StringBuilder("| ");
            for (int i = 0; i < columnNames.length; i++) {
                String colName = columnNames[i];
                int colType = metaData.getColumnType(i + 1);

                // Retrieve the appropriate value based on the column type
                String value;
                if (colType == Types.INTEGER) {
                    value = String.valueOf(rs.getInt(colName));
                } else if (colType == Types.FLOAT || colType == Types.DOUBLE || colType == Types.DECIMAL) {
                    value = String.valueOf(rs.getDouble(colName));
                } else if (colType == Types.BOOLEAN) {
                    value = String.valueOf(rs.getBoolean(colName));
                } else {
                    value = rs.getString(colName);  // Default to handling as String
                }

                row.append(String.format("%-20s | ", value != null ? value : ""));
            }
            System.out.println(row.toString());
        }
        System.out.println("--------------------------------------------------------------------------------");

    } catch (SQLException e) {
        System.out.println("Error retrieving records: " + e.getMessage());
    }
}


}


package it2c.larosa.dcas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Config {
    
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:rode.db"); // Establish connection
        } catch (Exception e) {
            System.out.println("\t\nConnection Failed: " + e);
        }
        return con;
    }
    
    //VALIDATION FOR MAIN MENU
    public int validateChoiceMain() {
        Scanner sc = new Scanner(System.in);
        int getNum;
        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            try {
                getNum = sc.nextInt();

                if (getNum < 1 || getNum > 6) { 
                    System.out.print("\tInvalid Input: Please enter a number between 1 and 6. Try again: ");
                    attempts++; 
                    continue; 
                }
                return getNum; 

            } catch (InputMismatchException e) {
                System.out.print("\tInvalid Input: Must only be a number, try again: ");
                sc.next(); 
                attempts++; 
            }

            if (attempts >= maxAttempts) {
                System.out.println("\tMaximum attempts reached. Exiting...");
                return -1; 
            }
        }
        return -1; 
    }

    
    //VALIDATION FOR INDEPENDENT DATA
    public int validateChoice() {
        Scanner sc = new Scanner(System.in);
        int getNum;
        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            try {
                getNum = sc.nextInt();

                if (getNum < 1 || getNum > 5) {
                    System.out.print("\tInvalid Input: Please enter a number between 1 and 5. Try again: ");
                    attempts++; 
                    continue; 
                }
                return getNum; 

            } catch (InputMismatchException e) {
                System.out.print("\tInvalid Input: Must only be a number, try again: ");
                sc.next(); 
                attempts++; 
            }

            if (attempts >= maxAttempts) {
                System.out.println("\tMaximum attempts reached. Exiting...");
                return -1; 
            }
        }
        return -1; 
    }

    
    public void addRecords(String sql, Object... values) {
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
            System.out.print("\n");
        } catch (SQLException e) {
            System.out.println("\t\nError adding record: " + e.getMessage());
        }
    }    
    
    public void viewRecords(String sqlQuery, String[] columnHeaders, String[] columnNames) {
        // Check that columnHeaders and columnNames arrays are the same length
        if (columnHeaders.length != columnNames.length) {
            System.out.println("Error: Mismatch between column headers and column names.");
            return;
        }

        try (Connection conn = this.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
             ResultSet rs = pstmt.executeQuery()) {

            // Print the headers dynamically
            StringBuilder headerLine = new StringBuilder();
            headerLine.append("-----------------------------------------------------------------------------------------------------------\n| ");
            for (String header : columnHeaders) {
                headerLine.append(String.format("%-20s | ", header)); // Adjust formatting as needed
            }
            headerLine.append("\n---------------------------------------------------------------------------------------------------------");

            System.out.println(headerLine.toString());

            // Print the rows dynamically based on the provided column names
            while (rs.next()) {
                StringBuilder row = new StringBuilder("| ");
                for (String colName : columnNames) {
                    String value = rs.getString(colName);
                    row.append(String.format("%-20s | ", value != null ? value : "")); // Adjust formatting
                }
                System.out.println(row.toString());
            }
            System.out.println("----------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
    }
    
    public void updateRecords(String sql, Object... values) {
        try (Connection conn = this.connectDB(); // Use the connectDB method
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Loop through the values and set them in the prepared statement dynamically
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]); // If the value is Integer
                } else if (values[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) values[i]); // If the value is Double
                } else if (values[i] instanceof Float) {
                    pstmt.setFloat(i + 1, (Float) values[i]); // If the value is Float
                } else if (values[i] instanceof Long) {
                    pstmt.setLong(i + 1, (Long) values[i]); // If the value is Long
                } else if (values[i] instanceof Boolean) {
                    pstmt.setBoolean(i + 1, (Boolean) values[i]); // If the value is Boolean
                } else if (values[i] instanceof java.util.Date) {
                    pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime())); // If the value is Date
                } else if (values[i] instanceof java.sql.Date) {
                    pstmt.setDate(i + 1, (java.sql.Date) values[i]); // If it's already a SQL Date
                } else if (values[i] instanceof java.sql.Timestamp) {
                    pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]); // If the value is Timestamp
                } else {
                    pstmt.setString(i + 1, values[i].toString()); // Default to String for other types
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }
    
    public boolean deleteRecords(String sql, Object... values) {
        try (Connection conn = this.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]); 
                } else {
                    pstmt.setString(i + 1, values[i].toString()); 
                }
            }

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0; //return kapag successful ang deletion

        } catch (SQLException e) {
            System.out.println("Error deleting record: " + e.getMessage());
        }
        return false; // return kapag false or no rows affected
    }

    
    public boolean pIDExists(String patientID) {
        String sql = "SELECT COUNT(*) FROM tbl_patients WHERE pID = ?";
        try (Connection conn = connectDB();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error checking Patient ID: " + e.getMessage());
        }
            return false; 
    }
    
    public boolean hasPatientApp(String patientID) {
        String query = "SELECT COUNT(*) FROM tbl_appointments WHERE patientID = ?";
        try (Connection conn = connectDB();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, patientID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error checking Patient Appointment: " + e.getMessage());
        }
        return false; 
    }
    
    public boolean authenticateStaff(String username, String password) {
        String query = "SELECT * FROM tbl_staff WHERE sUSERNAME = ? AND sPASS = ?";
        try (Connection conn = connectDB(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { 
                return true; 
            } else {
                return false; 
            }
        } catch (SQLException e) {
            System.out.println("Error during authentication: " + e.getMessage());
            return false;
        }
    }

    
    public boolean dIDExists(String doctorID) {
        String sql = "SELECT COUNT(*) FROM tbl_doctors WHERE dID = ?";
        try (Connection conn = connectDB();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctorID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error checking Doctor ID: " + e.getMessage());
        }
            return false; 
    }
    
    public boolean hasDoctorApp(String doctorID) {
        String query = "SELECT COUNT(*) FROM tbl_appointments WHERE doctorID = ?";
        try (Connection conn = connectDB();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, doctorID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error checking Doctor Appointment: " + e.getMessage());
        }
        return false; 
    }
    
    public boolean sIDExists(String staffID) {
        String sql = "SELECT COUNT(*) FROM tbl_staff WHERE sID = ?";
        try (Connection conn = connectDB();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, staffID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error checking Staff ID: " + e.getMessage());
        }
        
        return false; 
    }
    
    public String getStaffID(String username) {
        String sql = "SELECT sID FROM tbl_staff WHERE sUSERNAME = ?";
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("sID"); 
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Staff ID: " + e.getMessage());
        }
        return null; 
    }

    
    public boolean hasStaffApp(String staffID) {
        String query = "SELECT COUNT(*) FROM tbl_appointments WHERE staffID = ?";
        try (Connection conn = connectDB();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, staffID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error checking Staff Appointment: " + e.getMessage());
        }
        return false; 
    }
    
    public boolean appIDExists(String appID) {
        String sql = "SELECT COUNT(*) FROM tbl_appointments WHERE appID = ?";
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, appID.trim()); 
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error checking Appointment ID: " + e.getMessage());
        }
        return false; 
    }

    
     public String getStatus(String appID) {
        String status = null;
        String query = "SELECT status FROM tbl_appointments WHERE appID = ?";
        
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, appID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching appointment status: " + e.getMessage());
        }

        return status;
    }
     
    public boolean recordExists(String query, String id) {
        boolean exists = false;

        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    exists = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking if record exists: " + e.getMessage());
        }

        return exists;
    }



}

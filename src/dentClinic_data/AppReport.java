package dentClinic_data;

import dentClinic_data.PatientInfo;
import dentClinic_data.DoctorInfo;
import dentClinic_data.StaffInfo;
import dentClinic_data.Appointment;
import it2c.larosa.dcas.Config;
import it2c.larosa.dcas.viewConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AppReport {
    
        Config conf = new Config();
        viewConfig vcnf = new viewConfig();
    
    private void displayPatientOpt() {
        System.out.println("\n");
        System.out.println("----------------------------------");
        System.out.println("|         PATIENT RECORDS        |");
        System.out.println("|--------------------------------|");
        System.out.println("|      1. View All               |");
        System.out.println("|      2. View Specific Record   |");
        System.out.println("----------------------------------");
    }   
    
    private void displayDoctorOpt() {
        System.out.println("\n");
        System.out.println("----------------------------------");
        System.out.println("|         DOCTOR RECORDS         |");
        System.out.println("|--------------------------------|");
        System.out.println("|      1. View All               |");
        System.out.println("|      2. View Specific Record   |");
        System.out.println("----------------------------------");
    }
    
    private void displayStaffOpt() {
        System.out.println("\n");
        System.out.println("----------------------------------");
        System.out.println("|         STAFF RECORDS          |");
        System.out.println("|--------------------------------|");
        System.out.println("|      1. View All               |");
        System.out.println("|      2. View Specific Record   |");
        System.out.println("----------------------------------");
    }
    
    private void displayAppointmentOpt() {
        System.out.println("\n");
        System.out.println("----------------------------------");
        System.out.println("|      APPOINTMENT RECORDS       |");
        System.out.println("|--------------------------------|");
        System.out.println("|      1. View All               |");
        System.out.println("|      2. View Specific Record   |");
        System.out.println("----------------------------------");
    }
        
        
    public static void viewRecords(){
        
        Scanner sc = new Scanner (System.in);
        boolean response = true;
        
        do {
                System.out.print("\n");
                System.out.println("===================================");
                System.out.println("           VIEW RECORDS            ");
                System.out.println("-----------------------------------");
                System.out.println("      1. PATIENT RECORDS           ");
                System.out.println("      2. DOCTOR RECORDS            ");
                System.out.println("      3. STAFF RECORDS             ");
                System.out.println("      4. APPOINTMENT RECORDS       ");
                System.out.println("      5. EXIT                      ");
                System.out.println("===================================");
                
                System.out.print ("\nEnter Option: ");
                int opt = sc.nextInt();
                    while (opt < 1 ||  opt > 5){
                       System.out.print("\tInvalid Input, Try Again: ");
                          opt = sc.nextInt();
                    }   
                    
                    AppReport appR = new AppReport ();
                switch (opt){
                    case 1:
                        appR.patientRecord();
                        break;
                    case 2: 
                        appR.doctorRecord();
                        break;
                    case 3: 
                        appR.staffRecord();
                        break;
                    case 4:
                        appR.appointmentRecord();
                        break;
                    case 5:
                        response = false;
                        System.out.println("\nExiting View Records...");
                        break;
                }
                           
        } while(response);
}
    // FOR VIEW ALL OPTION
    private void viewPatient() {
                
        String rodeQuery = "SELECT * FROM tbl_patients";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Age", "Gender", "Contact Number", "Email", "Address"};
        String[] rodeColumns = {"pID", "pFNAME", "pLNAME", "pAGE", "pGENDER", "pCONTNUM", "pEMAIL", "pADDRESS"};

        vcnf.viewPatient(rodeQuery, rodeHeaders, rodeColumns);
    }
    
    private void viewDoctors() {
        
        String rodeQuery = "SELECT * FROM tbl_doctors";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Specialization", "Contact Number", "Availability Start", "Availability End"};
        String[] rodeColumns = {"dID", "dFNAME", "dLNAME", "dSPECIALIZATION", "dCONTNUM", "dAVAILABILITY_START", "dAVAILABILITY_END"};
        
        vcnf.viewDoctor(rodeQuery, rodeHeaders, rodeColumns);
    }
    
    private void viewStaff() { 
        
        String rodeQuery = "SELECT * FROM tbl_staff";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Role", "Contact Number"};
        String[] rodeColumns = {"sID", "sFNAME", "sLNAME", "sROLE", "sCONTNUM"};
        
        vcnf.viewStaff(rodeQuery, rodeHeaders, rodeColumns);
    }
    
    public void viewAppointment() {
        String rodequery = "SELECT a.appID, a.doctorID, p.pFNAME, p.pLNAME, a.staffID, a.appDATE, a.appTIME, a.appService, a.status " +
                       "FROM tbl_appointments a " +
                       "INNER JOIN tbl_patients p ON a.patientID = p.pID";
        String[] rodeheaders = {"Appointment ID", "Doctor ID", "Patient First Name", "Patient Last Name", "Staff ID", "Date", "Time", "Service", "Status"};
        String[] rodecolumns = {"appID", "doctorID", "pFNAME", "pLNAME", "staffID", "appDATE", "appTIME", "appService", "status"};
        viewConfig cnf = new viewConfig ();
        cnf.viewAppointment(rodequery, rodeheaders, rodecolumns);
    }
    
    //FOR VIEWING SPECIFIC DATA
    private void viewPatientOnly() {
                
        String rodeQuery = "SELECT pID, pFNAME, pLNAME FROM tbl_patients";
        String[] rodeHeaders = {"ID", "First Name", "Last Name"};
        String[] rodeColumns = {"pID", "pFNAME", "pLNAME"};

        vcnf.viewOnlyPatient(rodeQuery, rodeHeaders, rodeColumns);
    }
    
    private void viewDoctorOnly() {
        
        String rodeQuery = "SELECT dID, dFNAME, dLNAME FROM tbl_doctors";
        String[] rodeHeaders = {"ID", "First Name", "Last Name"};
        String[] rodeColumns = {"dID", "dFNAME", "dLNAME"};
        
        vcnf.viewOnlyDoctor(rodeQuery, rodeHeaders, rodeColumns);
    }
    
    private void viewStaffOnly() { 
        
        String rodeQuery = "SELECT sID, sFNAME, sLNAME FROM tbl_staff";
        String[] rodeHeaders = {"ID", "First Name", "Last Name"};
        String[] rodeColumns = {"sID", "sFNAME", "sLNAME"};
        
        vcnf.viewOnlyStaff(rodeQuery, rodeHeaders, rodeColumns);
    }
    
    public void viewAppointmentOnly() {
        String rodequery = "SELECT a.appID, p.pFNAME, p.pLNAME" +
                       "FROM tbl_appointments a " +
                       "INNER JOIN tbl_patients p ON a.patientID = p.pID";
        String[] rodeheaders = {"Appointment ID", "Patient First Name", "Patient Last Name"};
        String[] rodecolumns = {"appID", "pFNAME", "pLNAME"};
        
        vcnf.viewOnlyAppointment(rodequery, rodeheaders, rodecolumns);
    }

    
    
    public void patientRecord() {
        Scanner sc = new Scanner(System.in);

        displayPatientOpt();
        System.out.print("Select option: ");
        int patientOpt = sc.nextInt();

        switch (patientOpt) {
            case 1:
                viewPatient();
                break;

            case 2:
                boolean continueViewing;
                do {
                    String patientID = "";
                    boolean idExist = false;
                    int attempts = 0;
                    int maxAttempts = 3;

                    while (!idExist && attempts < maxAttempts) {
                        viewPatientOnly();
                        System.out.print("\nEnter Patient ID to view (3 max attempts): ");
                        patientID = sc.next();

                        if (conf.pIDExists(patientID)) {
                            idExist = true;
                            System.out.println("Patient ID found.");
                        } else {
                            attempts++;
                            System.out.println("Invalid ID or ID not found.");

                            if (attempts >= maxAttempts) {
                                System.out.println("Maximum attempts reached. Exiting...");
                                return;
                            }
                        }
                    }

                    viewSpecificPatient(patientID);
   
                    System.out.print("\nDo you want to view another patient? (yes/no): ");
                    String response = sc.next().trim().toLowerCase();
                    continueViewing = response.equals("yes");

                } while (continueViewing);

                break;

            default:
                System.out.println("Invalid option. Returning to View Records...");
                break;
        }
    }

    
    public void doctorRecord() {
        Scanner sc = new Scanner(System.in);

        displayDoctorOpt();
        System.out.print("Select option: ");
        int doctorOpt = sc.nextInt();

        switch (doctorOpt) {
            case 1:
                viewDoctors();
                break;

            case 2:
                boolean continueViewing;
                do {
                    String doctorID = "";
                    boolean idExist = false;
                    int attempts = 0;
                    int maxAttempts = 3;

                    while (!idExist && attempts < maxAttempts) {
                        viewDoctorOnly();
                        System.out.print("\nEnter Doctor ID to view (3 max attempts): ");
                        doctorID = sc.next();

                        if (conf.dIDExists(doctorID)) {
                            idExist = true;
                            System.out.println("Doctor ID found.");
                        } else {
                            attempts++;
                            System.out.println("Invalid ID or ID not found.");

                            if (attempts >= maxAttempts) {
                                System.out.println("Maximum attempts reached. Exiting...");
                                return;
                            }
                        }
                    }

                    viewSpecificDoctor(doctorID);

                    System.out.print("\nDo you want to view another doctor? (yes/no): ");
                    String response = sc.next().trim().toLowerCase();
                    continueViewing = response.equals("yes");

                } while (continueViewing);

                break;

            default:
                System.out.println("Invalid option. Returning to View Records...");
                break;
        }
    }
    
    public void staffRecord() {
        Scanner sc = new Scanner(System.in);

        displayStaffOpt();
        System.out.print("Select option: ");
        int staffOpt = sc.nextInt();

        switch (staffOpt) {
            case 1:
                viewStaff();
                break;

            case 2:
                boolean continueViewing;
                do {
                    String staffID = "";
                    boolean idExist = false;
                    int attempts = 0;
                    int maxAttempts = 3;

                    while (!idExist && attempts < maxAttempts) {
                        viewStaffOnly();
                        System.out.print("\nEnter Staff ID to view (3 max attempts): ");
                        staffID = sc.next();

                        if (conf.sIDExists(staffID)) {
                            idExist = true;
                            System.out.println("Staff ID found.");
                        } else {
                            attempts++;
                            System.out.println("Invalid ID or ID not found.");

                            if (attempts >= maxAttempts) {
                                System.out.println("Maximum attempts reached. Exiting...");
                                return;
                            }
                        }
                    }

                    viewSpecificStaff(staffID);

                    System.out.print("\nDo you want to view another staff? (yes/no): ");
                    String response = sc.next().trim().toLowerCase();
                    continueViewing = response.equals("yes");

                } while (continueViewing);

                break;

            default:
                System.out.println("Invalid option. Returning to View Records...");
                break;
        }
    }
    
    public void appointmentRecord() {
        Scanner sc = new Scanner(System.in);

        displayAppointmentOpt();
        System.out.print("Select option: ");
        int appointmentOpt = sc.nextInt();

        switch (appointmentOpt) {
            case 1:
                viewAppointment();
                break;

            case 2:
                boolean continueViewing;
                do {
                    String appID = "";
                    int attempts = 0;

                    while (attempts < 3) {
                        viewAppointmentOnly();
                        System.out.print("\nEnter Appointment ID to view (3 max attempts): ");
                        appID = sc.next();

                        if (conf.appIDExists(appID)) {
                            System.out.println("Appointment ID found.");
                            break;
                        } else {
                            attempts++;
                            System.out.println("Invalid ID or ID not found.");
                        }

                        if (attempts >= 3) {
                            System.out.println("Maximum attempts reached. Exiting...");
                            return;
                        }
                    }

                    viewSpecificAppointment(appID);

                    System.out.print("\nDo you want to view another appointment? (yes/no): ");
                    String response = sc.next().trim().toLowerCase();
                    continueViewing = response.equals("yes");

                } while (continueViewing);

                break;

            default:
                System.out.println("Invalid option. Returning to main menu.");
                break;
        }
    }
   

    
    private void viewSpecificPatient(String patientID) {
    
    String query = "SELECT * FROM tbl_patients WHERE pID = " + patientID;
    
        try {

            ResultSet rs = vcnf.executeQuery(query);

            if (rs.next()) {
                System.out.println("\n");
                System.out.println("==============================================");
                System.out.println("            DENTAL PATIENT RECORD             ");
                System.out.println("==============================================");
                System.out.println("    Patient ID: " + rs.getInt("pID"));
                System.out.println("       First Name: " + rs.getString("pFNAME"));
                System.out.println("       Last Name: " + rs.getString("pLNAME"));
                System.out.println("       Age: " + rs.getInt("pAGE"));
                System.out.println("       Gender: " + rs.getString("pGENDER"));
                System.out.println("       Contact Number: " + rs.getString("pCONTNUM"));
                System.out.println("       Email: " + rs.getString("pEMAIL"));
                System.out.println("       Address: " + rs.getString("pADDRESS"));
                System.out.println("==============================================");
            } else {
                System.out.println("No patient found with ID: " + patientID);
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Error fetching patient record: " + e.getMessage());
        }
    }

    private void viewSpecificDoctor(String doctorID) {
    
        String query = "SELECT * FROM tbl_doctors WHERE dID = " + doctorID;

        try {
            ResultSet rs = vcnf.executeQuery(query);

            if (rs.next()) {
                System.out.println("\n");
                System.out.println("==============================================");
                System.out.println("             DOCTOR RECORD                    ");
                System.out.println("==============================================");
                System.out.println("    Doctor ID: " + rs.getInt("dID"));
                System.out.println("       First Name: " + rs.getString("dFNAME"));
                System.out.println("       Last Name: " + rs.getString("dLNAME"));
                System.out.println("       Specialization: " + rs.getString("dSPECIALIZATION"));
                System.out.println("       Contact Number: " + rs.getString("dCONTNUM"));
                System.out.println("       Availability Start: " + rs.getString("dAVAILABILITY_START"));
                System.out.println("       Availability End: " + rs.getString("dAVAILABILITY_END"));
                System.out.println("==============================================");
            } else {
                System.out.println("No doctor found with ID: " + doctorID);
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Error fetching doctor record: " + e.getMessage());
        }
    }
    
    private void viewSpecificStaff(String staffID) {
        
        String query = "SELECT * FROM tbl_staff WHERE sID = " + staffID;

        try {
            ResultSet rs = vcnf.executeQuery(query);

            if (rs.next()) {
                System.out.println("\n");
                System.out.println("==============================================");
                System.out.println("               STAFF RECORD                   ");
                System.out.println("==============================================");
                System.out.println("    Staff ID: " + rs.getInt("sID"));
                System.out.println("       First Name: " + rs.getString("sFNAME"));
                System.out.println("       Last Name: " + rs.getString("sLNAME"));
                System.out.println("       Role: " + rs.getString("sROLE"));
                System.out.println("       Contact Number: " + rs.getString("sCONTNUM"));
                System.out.println("==============================================");
            } else {
                System.out.println("No staff member found with ID: " + staffID);
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Error fetching staff record: " + e.getMessage());
        }
    }
    
   private void viewSpecificAppointment(String appID) {
    String sqlQuery = "SELECT a.appID, a.appDATE, a.appTIME, a.appService, a.status, " +
                      "d.dID AS doctorID, (d.dFNAME || ' ' || d.dLNAME) AS doctorName, " +
                      "s.sID AS staffID, (s.sFNAME || ' ' || s.sLNAME) AS staffName, " +
                      "p.pID AS patientID, p.pFNAME, p.pLNAME " +
                      "FROM tbl_appointments a " +
                      "LEFT JOIN tbl_doctors d ON a.doctorID = d.dID " +
                      "LEFT JOIN tbl_staff s ON a.staffID = s.sID " +
                      "LEFT JOIN tbl_patients p ON a.patientID = p.pID " +
                      "WHERE a.appID = ?";

    try (Connection conn = conf.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {

        pstmt.setString(1, appID);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String doctorID = rs.getString("doctorID");
            String doctorName = rs.getString("doctorName") != null ? rs.getString("doctorName") : "N/A";
            String staffID = rs.getString("staffID");
            String staffName = rs.getString("staffName") != null ? rs.getString("staffName") : "N/A";
            String patientID = rs.getString("patientID");
            String patientFirstName = rs.getString("pFNAME");
            String patientLastName = rs.getString("pLNAME");
            String appointmentID = rs.getString("appID");
            String services = rs.getString("appService");
            String date = rs.getString("appDATE");
            String time = rs.getString("appTIME");
            String status = rs.getString("status");

            System.out.println("\n");
            System.out.println("*********************************************************************************");
            System.out.println("*                               INDIVIDUAL APPOINTMENT                          *");
            System.out.println("*********************************************************************************");
            System.out.printf("%-20s: %-30s %-20s: %-30s%n", "Doctor ID", doctorID, "Attending Doctor", doctorName);
            System.out.printf("%-20s: %-30s %-20s: %-30s%n", "Staff ID", staffID, "Assigned Staff", staffName);
            System.out.println("=================================================================================");
            System.out.printf("%-20s: %-30s %-20s: %-30s%n", "Patient ID", patientID, "First Name", patientFirstName);
            System.out.printf("%-20s: %-30s%n", "Last Name", patientLastName);
            System.out.println("---------------------------------------------------------------------------------");
            System.out.printf("%-20s: %-30s%n", "Appointment ID", appointmentID);
            System.out.printf("%-20s: %-30s%n", "Dental Services", services);
            System.out.printf("%-20s: %-30s %-20s: %-30s%n", "Date", date, "Time", time);
            System.out.printf("%-20s: %-30s%n", "Status", status);
            System.out.println("*********************************************************************************");
        } else {
            System.out.println("No record found for Appointment ID: " + appID);
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving appointment data: " + e.getMessage());
    }
}



}

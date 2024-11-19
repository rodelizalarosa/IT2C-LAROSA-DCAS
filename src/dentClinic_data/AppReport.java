package dentClinic_data;

import dentClinic_data.PatientInfo;
import dentClinic_data.DoctorInfo;
import dentClinic_data.StaffInfo;
import dentClinic_data.Appointment;
import it2c.larosa.dcas.Config;
import it2c.larosa.dcas.viewConfig;
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
    
    public void patientRecord(){
        
        Scanner sc = new Scanner (System.in);
        
        displayPatientOpt();
        System.out.print("Select option: ");
        int patientOpt = sc.nextInt();

            switch (patientOpt) {
                case 1:
                    viewPatient(); 
                    break;
                case 2:
                    String patientID = "";
                    boolean idexist = false;
                    int attempts = 0;
                    int maxAttempts = 3;

                    while (!idexist && attempts < maxAttempts) {
                        System.out.print("\n");
                        System.out.print("Enter Patient ID to view (3 max attempts): ");
                        patientID = sc.next();

                        if (conf.pIDExists(patientID)) {
                            idexist = true;
                            System.out.println("Patient ID found.");
                        } else {
                            attempts++;
                            System.out.println("Invalid ID or ID not Existed.");

                            if (attempts >= maxAttempts) {
                                System.out.println("Maximum attempts reached. Exiting...");
                                return;
                            }
                        }
                    }    
                                   
                    viewSpecificPatient(patientID);
                    break;
                default:
                    System.out.println("Invalid option. Returning to View Records...");
                    break;
            }
        
    }
    
    public void doctorRecord(){
        
        Scanner sc = new Scanner (System.in);
        
        displayDoctorOpt();
        System.out.print("Select option: ");
        int doctorOpt = sc.nextInt();
        
            switch (doctorOpt) {
                case 1:
                    viewDoctors(); 
                    break;
                case 2:
                    String doctorID = "";
                    boolean idexist = false;
                    int attempts = 0;
                    int maxAttempts = 3;

                    while (!idexist && attempts < maxAttempts) {
                        System.out.print("\nEnter Doctor ID to view (3 max attempts): ");
                        doctorID = sc.next();

                        if (conf.dIDExists(doctorID)) {
                            idexist = true;
                            System.out.println("Doctor ID found.");
                        } else {
                            attempts++;
                            System.out.println("\tInvalid ID or ID not Existed.");

                            if (attempts >= maxAttempts) {
                                System.out.println("Maximum attempts reached. Exiting...");
                                return;
                            }
                        }
                    }            
                                      
                    viewSpecificDoctor(doctorID); 
                    break;
                default:
                    System.out.println("Invalid option. Returning to View Records...");
                    break;
            }
    }
    
    public void staffRecord(){
        
        Scanner sc = new Scanner (System.in);
        
        displayStaffOpt();
        System.out.print("Select option: ");
        int staffOpt = sc.nextInt();
        
            switch (staffOpt) {
                case 1:
                    viewStaff();
                    break;
                case 2:
                    String staffID = "";
                    boolean idexist = false;
                    int attempts = 0;
                    int maxAttempts = 3;

                    while (!idexist && attempts < maxAttempts) {
                        System.out.print("\nEnter Staff ID to view (3 max attempts): ");
                        staffID = sc.next();

                        if (conf.sIDExists(staffID)) {
                            idexist = true;
                            System.out.println("Staff ID found.");
                        } else {
                            attempts++;
                            System.out.println("\tInvalid ID or ID not Existed.");

                            if (attempts >= maxAttempts) {
                                System.out.println("Maximum attempts reached. Exiting...");
                                return;
                            }
                        }
                    }   
           
                    
                    viewSpecificStaff(staffID); 
                    break;
                default:
                    System.out.println("Invalid option. Returning to View Records...");
                    break;
            }
        
    }
    
    public void appointmentRecord(){
        
        Scanner sc = new Scanner (System.in);
        
        displayAppointmentOpt();
        System.out.print("Select option: ");
        int appointmentOpt = sc.nextInt();
        
            switch (appointmentOpt) {
                case 1:
                    viewAppointment(); 
                    break;
                case 2:
                    String appID = "";
                    int attempts = 0;

                    while (attempts < 3) {
                        System.out.print("\nEnter Appointment ID to view (3 max attempts): ");
                        appID = sc.next();
                        if (conf.appIDExists(appID)) {
                            System.out.println("Appointment ID found.");
                            break;
                        } else {
                            attempts++;
                            System.out.println("\tInvalid ID or ID does not exist.");
                        }
                    }
                    if (attempts == 3) {
                        System.out.println("Maximum attempts reached. Exiting...");
                        return;
                    }
                    
                    
                    viewSpecificAppointment(appID); // Calls the method to view a specific appointment record
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

        
//    public static void viewIndvAppointment() {
//        Scanner sc = new Scanner(System.in);
//       
//        System.out.print("Enter Appointment ID to view details: ");
//        String appID = sc.next();
//
//        // SQL query to retrieve appointment details and join related tables
//        String query = "SELECT a.appID, a.doctorID, d.dFNAME AS doctorFirstName, d.dLNAME AS doctorLastName, " +
//                       "a.staffID, s.sFNAME AS staffFirstName, s.sLNAME AS staffLastName, " +
//                       "a.patientID, p.pFNAME AS patientFirstName, p.pLNAME AS patientLastName, " +
//                       "a.appService, a.appDATE, a.appTIME, a.status " +
//                       "FROM tbl_appointments a " +
//                       "LEFT JOIN tbl_doctors d ON a.doctorID = d.dID " +
//                       "LEFT JOIN tbl_staff s ON a.staffID = s.sID " +
//                       "LEFT JOIN tbl_patients p ON a.patientID = p.pID " +
//                       "WHERE a.appID = ?";
//
//        // Fetch the record
//        Map<String, String> result = conf.getRecordMap(query, appID);
//
//        // Check if record is found
//        if (result.isEmpty()) {
//            System.out.println("No appointment found with the provided Appointment ID.");
//            return;
//        }
//
//        // Display the formatted individual appointment details
//        System.out.println("\nIndividual Appointment\n");
//
//        System.out.printf("Doctor ID: %-20s Attending Doctor: %s %s\n", 
//                          result.get("doctorID"), result.get("doctorFirstName"), result.get("doctorLastName"));
//
//        System.out.printf("Staff ID: %-21s Assigned Staff: %s %s\n",
//                          result.get("staffID"), result.get("staffFirstName"), result.get("staffLastName"));
//
//        System.out.println("--------------------------------------------------------------------------------------------------");
//
//        System.out.printf("Patient ID: %-19s First Name: %-20s Last Name: %s\n",
//                          result.get("patientID"), result.get("patientFirstName"), result.get("patientLastName"));
//
//        System.out.println("\n| Appointment ID      | Dental Service         | Date          | Time     | Status         |");
//        System.out.println("|---------------------|------------------------|---------------|----------|----------------|");
//
//        System.out.printf("| %-19s | %-22s | %-13s | %-8s | %-14s |\n", 
//                          result.get("appID"), result.get("appService"), result.get("appDATE"), result.get("appTIME"), result.get("status"));
//        }

}

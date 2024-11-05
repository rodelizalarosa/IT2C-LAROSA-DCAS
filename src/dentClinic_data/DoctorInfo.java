package dentClinic_data;

import static it2c.larosa.dcas.Config.connectDB;
import it2c.larosa.dcas.Config;
import java.util.Scanner;

public class DoctorInfo {
    
        Config conf = new Config();
    public static void manageDoctors() {
        Scanner sc = new Scanner(System.in);
        boolean response = true;
        
        do { 
            System.out.print("\n");
            System.out.println("      MANAGE DOCTOR INFORMATION    ");
            System.out.println("-----------------------------------");
            System.out.println("      1. REGISTER A DOCTOR         ");
            System.out.println("      2. VIEW DOCTOR RECORD        ");
            System.out.println("      3. EDIT DOCTOR RECORD        ");
            System.out.println("      4. DELETE DOCTOR RECORD      ");
            System.out.println("      5. EXIT                      ");
            System.out.println("-----------------------------------");

            System.out.print("\nEnter Option: ");
            int opt = sc.nextInt();

            while (opt < 1 || opt > 5) {
                System.out.print("\tInvalid Input, Try Again: ");
                opt = sc.nextInt();
            }
        
                DoctorInfo dINFO = new DoctorInfo();
        switch (opt) {
            case 1:          
                dINFO.addDoctor();
                break;
            case 2: 
                dINFO.viewDoctors();
                break;
            case 3:
                dINFO.viewDoctors();
                dINFO.updateDoctor();
                break;
            case 4:
                dINFO.viewDoctors();
                dINFO.deleteDoctor();
                dINFO.viewDoctors();
                break;
            case 5:
                response = false;
                System.out.println("Exiting Manage Doctor Information...");
                break;
        }
          
        } while(response);
            System.out.println("\n\tThank you, See you! ");
    }
    
    public void addDoctor() {
        Scanner sc = new Scanner(System.in);        
        
        System.out.print("\n");
        System.out.print("Doctor First Name: ");
        String fname = sc.next();
        System.out.print("Doctor Last Name: ");
        String lname = sc.next();
        System.out.print("Doctor Specialization: ");
        String specialization = sc.nextLine();
  
        String contnum;
            while (true) {
                System.out.print("Doctor Contact Number (11 digits): ");
                contnum = sc.next();
                if (contnum.matches("\\d{11}")) {
                    break;
                } else {
                    System.out.println("Invalid contact number. Must be 11 digits and numeric.");
                }
            }
        
        System.out.print("Doctor Availability Start: ");
        String availStart = sc.next();
        System.out.print("Doctor Availability End: ");
        String availEnd = sc.next();

        String sql = "INSERT INTO tbl_doctors (dFNAME, dLNAME, dSPECIALIZATION, dCONTACTNUM, dAVAILABILITY_START, dAVAILABILITY_END) VALUES (?, ?, ?, ?, ?, ?)";

        conf.addRecords(sql, fname, lname, specialization, contnum, availStart, availEnd);
    }
    
    private void viewDoctors() {
        String rodeQuery = "SELECT * FROM tbl_doctors";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Specialization", "Contact Number", "Availability Start", "Availability End"};
        String[] rodeColumns = {"dID", "dFNAME", "dLNAME", "dSPECIALIZATION", "dCONTACTNUM", "dAVAILABILITY_START", "dAVAILABILITY_END"};

        conf.viewRecords(rodeQuery, rodeHeaders, rodeColumns);
    }

    private void updateDoctor() {
        Scanner sc = new Scanner(System.in);
        
        String doctorID = "";
        boolean idexist = false;
        
        while(!idexist){
            System.out.print("\nEnter Doctor ID to update: ");
            doctorID = sc.next();
            
            if(conf.dIDExists(doctorID)){
                idexist = true;
            }else{
                System.out.println("Invalid ID or ID not Existed");
            }
        }
        
        System.out.print("\n");
        System.out.print("Enter new First Name: ");
        String updfname = sc.next();
        
        System.out.print("Enter new Last Name: ");
        String updlname = sc.next();
        
        System.out.print("Enter new Specialization: ");
        String updspecialization = sc.next();
        
        String updcontnum;
            while (true) {
                    System.out.print("Enter new Contact Number (11 digits): ");
                    updcontnum = sc.next();
                    if (updcontnum.matches("\\d{11}")) {
                        break;
                    } else {
                        System.out.println("Invalid contact number. Must be 11 digits and numeric.");
                    }
            }
        
        System.out.print("Enter new Availability Start: ");
        String updavailStart = sc.next();
        
        System.out.print("Enter new Availability End: ");
        String updavailEnd = sc.next();
        
        String update = "UPDATE tbl_doctors SET dFNAME = ?, dLNAME = ?, dSPECIALIZATION = ?, dCONTACTNUM = ?, dAVAILABILITY_START = ?, dAVAILABILITY_END = ? WHERE dID = ?";
      
        conf.updateRecords(update, updfname, updlname, updspecialization, updcontnum, updavailStart, updavailEnd, doctorID);
    }
    
    private void deleteDoctor() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Doctor ID to delete: ");
        int id = sc.nextInt();
        
        String delete = "DELETE FROM tbl_doctors WHERE dID = ?";
        
        conf.deleteRecords(delete, id);
    }
}

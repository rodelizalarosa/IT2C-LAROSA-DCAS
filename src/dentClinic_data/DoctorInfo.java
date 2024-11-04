package dentClinic_data;

import static it2c.larosa.dcas.Config.connectDB;
import it2c.larosa.dcas.Config;
import java.util.Scanner;

public class DoctorInfo {
    
    public static void manageDoctors() {
        Scanner sc = new Scanner(System.in);
        int opt;
        String response;
        
        do { 
        System.out.println("MANAGE DOCTOR INFORMATION");
        System.out.println("-----------------------------------");
        System.out.println("|    1. REGISTER A DOCTOR        |");
        System.out.println("|    2. VIEW DOCTOR RECORD       |");
        System.out.println("|    3. EDIT DOCTOR RECORD       |");
        System.out.println("|    4. DELETE DOCTOR RECORD     |");
        System.out.println("|    5. EXIT                     |");
        System.out.println("-----------------------------------");
        
        System.out.print("\nEnter Option: ");
        opt = sc.nextInt();
        
        while (opt < 1 || opt > 5) {
            System.out.print("\tInvalid Input, Try Again: ");
            opt = sc.nextInt();
        }
        
        switch (opt) {
            case 1:
                DoctorInfo add = new DoctorInfo();
                add.addDoctor();
                break;
            case 2: 
                DoctorInfo view = new DoctorInfo();
                view.viewDoctors();
                break;
            case 3:
                DoctorInfo update = new DoctorInfo();
                update.updateDoctor();
                break;
            case 4:
                DoctorInfo delete = new DoctorInfo();
                delete.deleteDoctor();
                break;
            case 5:
                System.out.println("Exiting...");
                return;
        }
        
        System.out.print("\nDo you want to continue? (yes/no): ");
          response = sc.next();
                
        } while(response.equalsIgnoreCase("yes"));
        System.out.println("\n\tThank you, See you! ");
    }
    
    public void addDoctor() {
        Scanner sc = new Scanner(System.in);
        Config conf = new Config();
        
        System.out.print("\n");
        System.out.print("Doctor First Name: ");
        String fname = sc.next();
        System.out.print("Doctor Last Name: ");
        String lname = sc.next();
        System.out.print("Doctor Specialization: ");
        String specialization = sc.next();
        System.out.print("Doctor Contact Number: ");
        String contnum = sc.next();
        System.out.print("Doctor Availability Start: ");
        String availStart = sc.next();
        System.out.print("Doctor Availability End: ");
        String availEnd = sc.next();

        String sql = "INSERT INTO tbl_doctors (dFNAME, dLNAME, dSPECIALIZATION, dCONTACTNUM, dAVAILABILITY_START, dAVAILABILITY_END) VALUES (?, ?, ?, ?, ?, ?)";

        conf.addRecords(sql, fname, lname, specialization, contnum, availStart, availEnd);
    }
    
    private void viewDoctors() {
        Config cnf = new Config();
        
        String rodeQuery = "SELECT * FROM tbl_doctors";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Specialization", "Contact Number", "Availability Start", "Availability End"};
        String[] rodeColumns = {"dID", "dFNAME", "dLNAME", "dSPECIALIZATION", "dCONTACTNUM", "dAVAILABILITY_START", "dAVAILABILITY_END"};

        cnf.viewRecords(rodeQuery, rodeHeaders, rodeColumns);
    }

    private void updateDoctor() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Doctor ID to update: ");
        int id = sc.nextInt();
        
        System.out.print("\n");
        System.out.print("Enter new First Name: ");
        String updfname = sc.next();
        
        System.out.print("Enter new Last Name: ");
        String updlname = sc.next();
        
        System.out.print("Enter new Specialization: ");
        String updspecialization = sc.next();
        
        System.out.print("Enter new Contact Number: ");
        String updcontnum = sc.next();
        
        System.out.print("Enter new Availability Start: ");
        String updavailStart = sc.next();
        
        System.out.print("Enter new Availability End: ");
        String updavailEnd = sc.next();
        
        String update = "UPDATE tbl_doctors SET dFNAME = ?, dLNAME = ?, dSPECIALIZATION = ?, dCONTACTNUM = ?, dAVAILABILITY_START = ?, dAVAILABILITY_END = ? WHERE dID = ?";
        
        Config cnf = new Config();
        cnf.updateRecords(update, updfname, updlname, updspecialization, updcontnum, updavailStart, updavailEnd, id);
    }
    
    private void deleteDoctor() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Doctor ID to delete: ");
        int id = sc.nextInt();
        
        String delete = "DELETE FROM tbl_doctors WHERE dID = ?";
        
        Config cnf = new Config();
        cnf.deleteRecords(delete, id);
    }
}

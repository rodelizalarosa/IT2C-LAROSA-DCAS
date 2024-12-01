package dentClinic_data;

import static it2c.larosa.dcas.Config.connectDB;
import it2c.larosa.dcas.Config;
import it2c.larosa.dcas.viewConfig;
import java.util.Scanner;

public class DoctorInfo {
    
        Config conf = new Config();
    public static void manageDoctors() {
        
        Config conf = new Config();
        Scanner sc = new Scanner(System.in);
        boolean response = true;
        
        do { 
            System.out.print("\n");
            System.out.println("===================================");
            System.out.println("      MANAGE DOCTOR INFORMATION    ");
            System.out.println("-----------------------------------");
            System.out.println("      1. REGISTER A DOCTOR         ");
            System.out.println("      2. VIEW DOCTOR RECORD        ");
            System.out.println("      3. EDIT DOCTOR RECORD        ");
            System.out.println("      4. DELETE DOCTOR RECORD      ");
            System.out.println("      5. EXIT                      ");
            System.out.println("===================================");

            System.out.print("\nEnter Option: ");
            int opt = conf.validateChoice();
        
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
                System.out.println("\nExiting Manage Doctor Information...");
                break;
        }
          
        } while(response);
    }
    
    public void addDoctor() {
        Scanner sc = new Scanner(System.in);     
        int attempts = 0;
        
        System.out.print("\n");
        System.out.print("Doctor's First Name: ");
        String fname = sc.nextLine();
        
        System.out.print("Doctor's Last Name: ");
        String lname = sc.nextLine();
        
        System.out.print("Doctor's Specialization: ");
        String specialization = sc.nextLine();
  
        String contnum = "";
        while (attempts < 3) {
            System.out.print("Doctor's Contact Number (must be 11 digits): ");
            contnum = sc.nextLine().trim();
            if (contnum.matches("\\d{11}")) {
                break;
            } else {
                System.out.println("\tInvalid contact number. Must be 11 digits and numeric.");
                attempts++;
            }
        }

        if (attempts >= 3) {
            System.out.println("\tToo many invalid attempts. Exiting Register a Doctor . . . ");
            return; 
        }
   
        String addDOCTOR = "INSERT INTO tbl_doctors (dFNAME, dLNAME, dSPECIALIZATION, dCONTNUM) VALUES (?, ?, ?, ?)";

        conf.addRecords(addDOCTOR, fname, lname, specialization, contnum);
    }
    
    private void viewDoctors() {
        String rodeQuery = "SELECT * FROM tbl_doctors";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Specialization", "Contact Number"};
        String[] rodeColumns = {"dID", "dFNAME", "dLNAME", "dSPECIALIZATION", "dCONTNUM"};
        
        viewConfig cnf = new viewConfig();
        cnf.viewDoctor(rodeQuery, rodeHeaders, rodeColumns);
    }
    
    private void updateDoctor() {
        Scanner sc = new Scanner(System.in);

        String doctorID = "";
        boolean idExist = false;
        int attempts = 0;
        int maxAttempts = 3;

        while (!idExist && attempts < maxAttempts) {
            System.out.print("\nEnter Doctor ID to update (3 max attempts): ");
            doctorID = sc.nextLine().trim();

            if (conf.dIDExists(doctorID)) {
                idExist = true;
                System.out.println("Doctor ID found.");
            } else {
                attempts++;
                System.out.println("\tInvalid ID or ID does not exist.");

                if (attempts >= maxAttempts) {
                    System.out.println("Maximum attempts reached. Exiting...");
                    return;
                }
            }
        }

        System.out.print("\nDoctor's New First Name: ");
        String fname = sc.nextLine().trim();

        System.out.print("Doctor's New Last Name: ");
        String lname = sc.nextLine().trim();

        System.out.print("Doctor's New Specialization: ");
        String specialization = sc.nextLine().trim();

        String contnum = "";
        attempts = 0;
        while (attempts < 3) {
            System.out.print("Doctor's New Contact Number (must be 11 digits): ");
            contnum = sc.nextLine().trim();
            if (contnum.matches("\\d{11}")) {
                break;
            } else {
                System.out.println("\tInvalid contact number. Must be 11 digits and numeric.");
                attempts++;
            }
        }

        if (attempts >= 3) {
            System.out.println("\tToo many invalid attempts. Exiting update process...");
            return;
        }

        String updateDOCTOR = "UPDATE tbl_doctors SET dFNAME = ?, dLNAME = ?, dSPECIALIZATION = ?, dCONTNUM = ? WHERE dID = ?";
      
        conf.updateRecords(updateDOCTOR, fname, lname, specialization, contnum, doctorID);
    }
    
    
    private void deleteDoctor() {
        Scanner sc = new Scanner(System.in);

        System.out.print("\n");
        System.out.print("=========================================");
        System.out.print("       STAFF AUTHENTICATION ACCESS       ");
        System.out.print("=========================================");
        System.out.print("Staff's Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Staff's Password: ");
        String password = sc.nextLine().trim();

        if (!conf.authenticateStaff(username, password)) { 
            System.out.println("Authentication failed. Access denied.");
            return;
        }

        System.out.println("Authentication successful. Proceeding with doctor deletion.");

        boolean continueDeleting = true;
        
        while (continueDeleting){
        String doctorID = "";
        boolean idExist = false;
        int attempts = 0;
        int maxAttempts = 3;

        while (!idExist && attempts < maxAttempts) {
            System.out.print("\nEnter Doctor ID to delete (3 max attempts): ");
            doctorID = sc.next();

            if (conf.dIDExists(doctorID)) { 
                idExist = true;
                System.out.println("Doctor ID found.");
            } else {
                attempts++;
                System.out.println("\tInvalid ID or ID does not exist.");

                if (attempts >= maxAttempts) {
                    System.out.println("Maximum attempts reached. Exiting...");
                    return;
                }
            }
        }

        if (conf.hasDoctorApp(doctorID)) { 
            System.out.println("Cannot delete doctor. They have associated appointments.");
            return;
        }

        String delete = "DELETE FROM tbl_doctors WHERE dID = ?";
        if (!conf.deleteRecords(delete, doctorID)) {
            System.out.println("Failed to delete doctor record. Please try again.");
        } else {
            System.out.println("Doctor record deleted successfully.");
        }
        
            System.out.print("\nDo you want to delete another doctor? (yes/no): ");
            String response = sc.next().trim().toLowerCase();

            if (!response.equals("yes")) {
                continueDeleting = false;
                System.out.println("Exiting doctor deletion process . . .");
            }
        
        }
    }

}

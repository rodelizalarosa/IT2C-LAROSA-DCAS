
package dentClinic_data;

import it2c.larosa.dcas.Config;
import it2c.larosa.dcas.viewConfig;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StaffInfo {
        
        Config conf = new Config();
        
    public static void manageStaffs (){
        
        Config conf = new Config();
        Scanner sc = new Scanner (System.in);
        boolean response = true;
        
        do {
            System.out.print("\n");
            System.out.println("===================================");
            System.out.println("     MANAGE STAFF INFORMATION     ");
            System.out.println("-----------------------------------");
            System.out.println("     1. REGISTER A STAFF           ");
            System.out.println("     2. VIEW STAFF RECORD          ");
            System.out.println("     3. EDIT STAFF RECORD          ");
            System.out.println("     4. DELETE STAFF RECORD        ");
            System.out.println("     5. EXIT                       ");
            System.out.println("===================================");

            System.out.print("\nEnter Option: ");
            int opt = conf.validateChoice();
            
            StaffInfo sINFO = new StaffInfo();
        switch (opt) {
            case 1:
                sINFO.addStaff();
                break;
            case 2: 
                sINFO.viewStaff();
                break;
            case 3:
                sINFO.viewStaff();
                sINFO.updateStaff();
                break;
            case 4:
                sINFO.viewStaff();
                sINFO.deleteStaff();
                sINFO.viewStaff();
                break;
            case 5:
                response = false;
                System.out.println("\nExiting Manage Staff Information...");
                break;
        }
                
        } while(response);
  
    }

   public void addStaff() {
        Scanner sc = new Scanner(System.in);
        int attempts = 0;

        System.out.print("\n");
        System.out.print("Staff's First Name: ");
        String fname = sc.nextLine();
        System.out.print("Staff's Last Name: ");
        String lname = sc.nextLine();
        System.out.print("Staff's Role: ");
        String role = sc.nextLine();

        String contnum = "";
        while (attempts < 3) {
            System.out.print("Staff's Contact Number (must be 11 digits): ");
            contnum = sc.nextLine().trim();
            if (contnum.matches("\\d{11}")) {
                break;
            } else {
                System.out.println("\tInvalid contact number. Must be 11 digits and numeric.");
                attempts++;
            }
        }

        if (attempts >= 3) {
            System.out.println("\tToo many invalid attempts. Exiting Register a Patient . . . ");
            return; 
        }

        String username;
        while (true) {
            System.out.print("Staff Username (min. 5 characters): ");
            username = sc.nextLine();
            if (username.length() >= 5) {
                break;
            } else {
                System.out.println("Invalid username. It must have at least 5 characters.");
            }
        }

        String password;
        while (true) {
            System.out.print("Staff Password (min. 8 chars, 1 capital letter, 1 number): ");
            password = sc.nextLine();
            if (password.length() >= 8 && 
                Pattern.compile("[A-Z]").matcher(password).find() && 
                Pattern.compile("[0-9]").matcher(password).find() && 
                !Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
                break;
            } else {
                System.out.println("Invalid password. It must be at least 8 characters long, include at least one capital letter and one number, and contain no special characters.");
            }
        }

        String hashedPassword = hashPassword(password);

        String addSTAFF = "INSERT INTO tbl_staff (sFNAME, sLNAME, sROLE, sCONTNUM, sUSERNAME, sPASS) VALUES (?, ?, ?, ?, ?, ?)";

        conf.addRecords(addSTAFF, fname, lname, role, contnum, username, hashedPassword); 
    }
   
   private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
   
    private void viewStaff() { 
        
        String rodeQuery = "SELECT sID, sFNAME, sLNAME, sROLE, sCONTNUM, sUSERNAME FROM tbl_staff";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Role", "Contact Number", "Username"};
        String[] rodeColumns = {"sID", "sFNAME", "sLNAME", "sROLE", "sCONTNUM", "sUSERNAME"};
        
        viewConfig cnf = new viewConfig();
        cnf.viewStaff(rodeQuery, rodeHeaders, rodeColumns);
    }

    private void updateStaff() {
        Scanner sc = new Scanner(System.in);

        System.out.print("\n");
        System.out.println("=========================================");
        System.out.println("       STAFF AUTHENTICATION ACCESS       ");
        System.out.println("=========================================");
        System.out.print("\n\tStaff's Username: ");
        String username = sc.nextLine();
        System.out.print("\tStaff's Password: ");
        String password = sc.nextLine();

        String hashedPassword = hashPassword(password);

        if (!conf.authenticateStaff(username, hashedPassword)) {
            System.out.println("\nAuthentication failed. Access denied.");
            return;
        }

        int attempts = 0;
        boolean validChoice = false;

        
        System.out.print("\n");
        System.out.println("=============================================================");
        System.out.println("    Authentication successful! What would you like to do?    ");
        System.out.println("-------------------------------------------------------------");
        System.out.println("                   UPDATE STAFF INFORMATION                  ");
        System.out.println("-------------------------------------------------------------");
        System.out.println("                1. Update Username and Password              ");
        System.out.println("                2. Edit Staff Information                    ");
        System.out.println("=============================================================");

        while (attempts < 3 && !validChoice) {
            System.out.print("Enter your choice (1 or 2): ");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine(); 

                switch (choice) {
                    case 1:
                        validChoice = true;
                        updateCredentials(username);
                        break;

                    case 2:
                        viewStaff();
                        validChoice = true;
                        editStaffInfo();
                        break;

                    default:
                        attempts++;
                        System.out.println("\tInvalid choice. Please enter 1 or 2. You have " + (3 - attempts) + " attempt(s) remaining.");
                        break;
                }
            } else {
                sc.next(); 
                attempts++;
                System.out.println("\tInvalid input. Please enter a number. You have " + (3 - attempts) + " attempt(s) remaining.");
            }

            if (attempts >= 3) {
                System.out.println("\tMaximum attempts reached. Exiting...");
                return;
            }
        }
    }

    private void updateCredentials(String oldUsername) {
        Scanner sc = new Scanner(System.in);

        String newUsername;
        while (true) {
            System.out.print("Enter new Username (min. 5 characters): ");
            newUsername = sc.nextLine();
            if (newUsername.length() >= 5) {
                break;
            } else {
                System.out.println("Invalid username. It must have at least 5 characters.");
            }
        }

        String newPassword;
        while (true) {
            System.out.print("Enter new Password (min. 8 chars, 1 capital letter, 1 number): ");
            newPassword = sc.nextLine();
            if (newPassword.length() >= 8 &&
                newPassword.matches(".*[A-Z].*") && 
                newPassword.matches(".*[0-9].*") && 
                !newPassword.matches(".*[^a-zA-Z0-9].*")) { 
                break;
            } else {
                System.out.println("Invalid password. It must meet the criteria.");
            }
        }

        String hashedNewPassword = hashPassword(newPassword);

        String updateSQL = "UPDATE tbl_staff SET sUSERNAME = ?, sPASS = ? WHERE sUSERNAME = ?";
        conf.updateRecords(updateSQL, newUsername, hashedNewPassword, oldUsername);

        System.out.println("Username and password successfully updated.");
    }

    private void editStaffInfo() {
        Scanner sc = new Scanner(System.in);

        String staffID = "";
        boolean idExists = false;
        int attempts = 0;
        int maxAttempts = 3;

        while (!idExists && attempts < maxAttempts) {
            System.out.print("\nEnter Staff ID to update (3 max attempts): ");
            staffID = sc.next();

            if (conf.sIDExists(staffID)) {
                idExists = true;
                System.out.println("Staff ID found.");
            } else {
                attempts++;
                System.out.println("\tInvalid ID or ID does not exist.");

                if (attempts >= maxAttempts) {
                    System.out.println("Maximum attempts reached. Exiting...");
                    return;
                }
            }
        }

        System.out.print("\nStaff's New First Name: ");
        String updfname = sc.next();

        System.out.print("Staff's New Last Name: ");
        sc.nextLine();
        String updlname = sc.nextLine();

        System.out.print("Staff's New Role: ");
        String updrole = sc.nextLine();

        String updcontnum = "";
        while (attempts < 3) {
            System.out.print("Staff's New Contact Number (must be 11 digits): ");
            updcontnum = sc.nextLine().trim();
            if (updcontnum.matches("\\d{11}")) {
                break;
            } else {
                System.out.println("\tInvalid contact number. Must be 11 digits and numeric.");
                attempts++;
            }
        }

        if (attempts >= 3) {
            System.out.println("\tToo many invalid attempts. Exiting Register a Patient . . . ");
            return; 
        }

        String updateSTAFF = "UPDATE tbl_staff SET sFNAME = ?, sLNAME = ?, sROLE = ?, sCONTNUM = ? WHERE sID = ?";

        conf.updateRecords(updateSTAFF, updfname, updlname, updrole, updcontnum, staffID);
        System.out.println("Staff information successfully updated.");
    }

    
    private void deleteStaff() {
        Scanner sc = new Scanner(System.in);

        System.out.print("\n");
        System.out.println("=========================================");
        System.out.println("       STAFF AUTHENTICATION ACCESS       ");
        System.out.println("=========================================");
        System.out.print("\n\tStaff's Username: ");
        String username = sc.nextLine();
        System.out.print("\tStaff's Password: ");
        String password = sc.nextLine();

        String hashedPassword = hashPassword(password);

        if (!conf.authenticateStaff(username, hashedPassword)) {
            System.out.println("\nAuthentication failed. Access denied.");
            return;
        }

        int attempts = 0;
        int maxAttempts = 3;
        boolean idExist = false;

        while (!idExist && attempts < maxAttempts) {
            System.out.print("\nEnter Staff ID to delete (3 max attempts): ");
            String staffID = sc.nextLine();

            if (conf.sIDExists(staffID)) {
                idExist = true;
                System.out.println("Staff ID found.");
            } else {
                attempts++;
                System.out.println("Invalid ID or ID does not exist.");

                if (attempts >= maxAttempts) {
                    System.out.println("Maximum attempts reached. Exiting...");
                    return;
                }
            }
        }

        String staffID = sc.nextLine(); 
        if (conf.hasStaffApp(staffID)) {
            System.out.println("Cannot delete staff. They have associated appointments.");
            return;
        }

        String delete = "DELETE FROM tbl_staff WHERE sID = ?";
        if (!conf.deleteRecords(delete, staffID)) {
            System.out.println("Failed to delete staff record. Please try again.");
        } else {
            System.out.println("Staff record deleted successfully.");
        }

        System.out.print("\nWould you like to delete another staff member? (Y/N): ");
        String choice = sc.nextLine().toUpperCase();

        if (choice.equals("Y")) {
            deleteStaff(); 
        } else {
            System.out.println("Exiting staff deletion process . . .");
        }
    }

}
    
   

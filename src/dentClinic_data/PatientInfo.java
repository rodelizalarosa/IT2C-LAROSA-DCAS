package dentClinic_data;

import static it2c.larosa.dcas.Config.connectDB;
import it2c.larosa.dcas.Config;
import it2c.larosa.dcas.viewConfig;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class PatientInfo {
        
        Config conf = new Config();
        
    public static void managePatients(){
        
        Config conf = new Config();
        Scanner sc = new Scanner (System.in);
        boolean response = true;
        
        do {
                System.out.print("\n");
                System.out.println("===================================");
                System.out.println("     MANAGE PATIENT INFORMATION    ");
                System.out.println("-----------------------------------");
                System.out.println("      1. REGISTER A PATIENT        ");
                System.out.println("      2. VIEW PATIENT RECORD       ");
                System.out.println("      3. EDIT PATIENT RECORD       ");
                System.out.println("      4. DELETE PATIENT RECORD     ");
                System.out.println("      5. EXIT                      ");
                System.out.println("===================================");
                
                System.out.print ("\nEnter Option: ");
                int opt = conf.validateChoice();  
                    
                    PatientInfo pINFO = new PatientInfo();
                switch (opt){
                    case 1:
                        pINFO.addPatients();
                        break;
                    case 2: 
                        pINFO.viewPatient();
                        break;
                    case 3:
                        pINFO.viewPatient();
                        pINFO.updatePatient();
                        break;
                    case 4:
                        pINFO.viewPatient();
                        pINFO.deletePatient();
                        pINFO.viewPatient();
                        break;
                    case 5:
                        response = false;
                        System.out.println("\nExiting Manage Patient Information...");
                        break;
                }
                           
        } while(response);
}
    
    public void addPatients() {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int attempts = 0;

        System.out.print("\nPatient's First Name: ");
        String fname = sc.nextLine().trim();

        System.out.print("Patient's Last Name: ");
        String lname = sc.nextLine().trim();

        String contnum = "";
        while (attempts < 3) {
            System.out.print("Patient's Contact Number (must be 11 digits): ");
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
        
        attempts = 0;
        
        String gender = "";
        attempts = 0; 
        while (attempts < 3) {
            System.out.print("Patient's Gender (M/F): ");
            gender = sc.nextLine().trim().toUpperCase();
            if (gender.equals("M") || gender.equals("F")) {
                break;
            } else {
                System.out.println("\tInvalid input. Please enter 'M' for Male or 'F' for Female.");
                attempts++;
            }
        }
        
        if (attempts >= 3) {
            System.out.println("\tToo many invalid attempts. Exiting Register a Patient . . . ");
            return; 
        }
        
        attempts = 0;
        
        String dob = "";
        while (attempts < 3) {
            System.out.print("Enter Patient's Date of Birth (YYYY-MM-DD): ");
            dob = sc.nextLine().trim();

            if (dob.isEmpty()) {
                System.out.println("\tInvalid Input: Date of birth cannot be empty.");
                attempts++;
                continue;
            }

            try {
                LocalDate parsedDate = LocalDate.parse(dob, formatter);

                if (parsedDate.isAfter(LocalDate.now())) {
                    System.out.println("\tInvalid Input: Date of birth cannot be in the future.");
                    attempts++;
                    continue;
                }

                if (parsedDate.isAfter(LocalDate.now().minusYears(1))) {
                    System.out.println("\tInvalid Input: Patient must be at least 1 year old.");
                    attempts++;
                    continue;
                }

                break; 
            } catch (DateTimeParseException e) {
                System.out.println("\tInvalid Input: Please enter a valid date in the format (YYYY-MM-DD).");
                attempts++;
            }
        }

        if (attempts >= 3) {
            System.out.println("\tToo many invalid attempts. Exiting Register a Patient . . . ");
            return;
        }

        attempts = 0;
        
        String email = "";
        while (attempts < 3) {
            System.out.print("Enter Patient's Email: ");
            email = sc.nextLine().trim();

                if (email.isEmpty()) {
                    System.out.println("\tEmail cannot be empty. Please try again.");
                    attempts++;
                    continue;
                }

                if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                    System.out.println("\tInvalid email format. Please try again.");
                    attempts++;
                    continue;
                }

                String[] validDomains = {"@gmail.com", "@yahoo.com", "@outlook.com"};
                boolean validDomain = false;

                for (String domain : validDomains) {
                    if (email.endsWith(domain)) {
                        validDomain = true;
                        break;
                    }
                }

                if (!validDomain) {
                    System.out.println("\tEmail must have one of the supported domain names (@gmail.com, @yahoo.com, @outlook.com). Please try again.");
                    attempts++;
                    continue;
                }

                try {
                    PreparedStatement findEmail = connectDB().prepareStatement("SELECT pEMAIL FROM tbl_patients WHERE pEMAIL = ?");
                    findEmail.setString(1, email);
                    ResultSet result = findEmail.executeQuery();

                    if (result.next()) {
                        System.out.println("\tError: Email is already registered. Please try again.");
                        attempts++;
                        continue;
                    }

                    break;
                } catch (SQLException e) {
                    System.out.println("\tError: " + e.getMessage());
                    attempts++;
                    continue;
                }
            }

            if (attempts >= 3) {
                System.out.println("\tToo many invalid attempts. Process terminated.");
                return;
            }

            System.out.println("Patient registration completed successfully!");

        System.out.print("Patient Address: ");
        String address = sc.nextLine().trim();

        String addPATIENT = "INSERT INTO tbl_patients (pFNAME, pLNAME, pCONTNUM, pGENDER, pDOB, pEMAIL, pADDRESS) VALUES (?, ?, ?, ?, ?, ?, ?)";

        conf.addRecords(addPATIENT, fname, lname, contnum, gender, dob, email, address);
        System.out.println("\tPatient record added successfully!");
    }

    
    private void viewPatient() {
        viewConfig cnf = new viewConfig();
        
        String rodeQuery = "SELECT * FROM tbl_patients";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Age", "Gender", "Contact Number", "Email", "Address"};
        String[] rodeColumns = {"pID", "pFNAME", "pLNAME", "pAGE", "pGENDER", "pCONTNUM", "pEMAIL", "pADDRESS"};

        cnf.viewPatient(rodeQuery, rodeHeaders, rodeColumns);
    }

    
    private void updatePatient() {
        Scanner sc = new Scanner(System.in);

        System.out.print("\n");
        System.out.print("=========================================");
        System.out.print("       STAFF AUTHENTICATION ACCESS       ");
        System.out.print("=========================================");
        System.out.print("Staff's username: ");
        String username = sc.nextLine().trim();

        System.out.print("Staff's password: ");
        String password = sc.nextLine().trim();

        if (!conf.authenticateStaff(username, password)) { 
            System.out.println("Authentication failed. Access denied.");
            return;
        }

        System.out.println("Authentication successful. Proceeding with patient update.");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int attempts = 0;
        int maxAttempts = 3;

        String patientID = "";
        boolean idExists = false;

        while (!idExists && attempts < maxAttempts) {
            System.out.print("\nEnter Patient ID to update (3 max attempts): ");
            patientID = sc.nextLine().trim();

            if (conf.pIDExists(patientID)) {
                idExists = true;
                System.out.println("Patient ID found.");
            } else {
                attempts++;
                System.out.println("Invalid ID or ID does not exist.");

                if (attempts >= maxAttempts) {
                    System.out.println("Maximum attempts reached. Exiting...");
                    return;
                }
            }
        }

        System.out.print("\nEnter new First Name: ");
        String updfname = sc.nextLine().trim();

        System.out.print("Enter new Last Name: ");
        String updlname = sc.nextLine().trim();

        String updgen = "";
        attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter new Gender (M/F): ");
            updgen = sc.nextLine().trim().toUpperCase();
            if (updgen.equals("M") || updgen.equals("F")) {
                break;
            } else {
                System.out.println("\tInvalid input. Please enter 'M' for Male or 'F' for Female.");
                attempts++;
            }
        }

        if (attempts >= maxAttempts) {
            System.out.println("\tToo many invalid attempts. Exiting update process...");
            return;
        }

        String upddob = "";
        attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter new Date of Birth (YYYY-MM-DD): ");
            upddob = sc.nextLine().trim();

            if (upddob.isEmpty()) {
                System.out.println("\tInvalid input: Date of birth cannot be empty.");
                attempts++;
                continue;
            }

            try {
                LocalDate parsedDate = LocalDate.parse(upddob, formatter);

                if (parsedDate.isAfter(LocalDate.now())) {
                    System.out.println("\tInvalid input: Date of birth cannot be in the future.");
                    attempts++;
                    continue;
                }

                if (parsedDate.isAfter(LocalDate.now().minusYears(1))) {
                    System.out.println("\tInvalid input: Patient must be at least 1 year old.");
                    attempts++;
                    continue;
                }

                break;
            } catch (DateTimeParseException e) {
                System.out.println("\tInvalid input: Please enter a valid date in the format (YYYY-MM-DD).");
                attempts++;
            }
        }

        if (attempts >= maxAttempts) {
            System.out.println("\tToo many invalid attempts. Exiting update process...");
            return;
        }

        String updcontnum = "";
        attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter new Contact Number (11 digits): ");
            updcontnum = sc.nextLine().trim();
            if (updcontnum.matches("\\d{11}")) {
                break;
            } else {
                System.out.println("\tInvalid contact number. Must be 11 digits and numeric.");
                attempts++;
            }
        }

        if (attempts >= maxAttempts) {
            System.out.println("\tToo many invalid attempts. Exiting update process...");
            return;
        }

        String updemail = "";
        attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter new Email: ");
            updemail = sc.nextLine().trim();

            if (updemail.isEmpty()) {
                System.out.println("\tEmail cannot be empty. Please try again.");
                attempts++;
                continue;
            }

            if (!updemail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                System.out.println("\tInvalid email format. Please try again.");
                attempts++;
                continue;
            }

            String[] validDomains = {"@gmail.com", "@yahoo.com", "@outlook.com"};
            boolean validDomain = false;

            for (String domain : validDomains) {
                if (updemail.endsWith(domain)) {
                    validDomain = true;
                    break;
                }
            }

            if (!validDomain) {
                System.out.println("\tEmail must have one of the supported domain names (@gmail.com, @yahoo.com, @outlook.com). Please try again.");
                attempts++;
                continue;
            }

            break;
        }

        if (attempts >= maxAttempts) {
            System.out.println("\tToo many invalid attempts. Exiting update process...");
            return;
        }


        System.out.print("Enter new Address: ");
        String upadd = sc.nextLine().trim();

        
        String updatePATIENT = "UPDATE tbl_patients SET pFNAME = ?, pLNAME = ?, pGENDER = ?, pDOB = ?, pCONTNUM = ?, pEMAIL = ?, pADDRESS = ?  WHERE pID = ?";
        
        conf.updateRecords(updatePATIENT, updfname, updlname, updgen, upddob, updcontnum, updemail, upadd, patientID);
    }
    
    private void deletePatient() {
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

        System.out.println("Authentication successful. Proceeding with patient deletion.");

        boolean continueDeleting = true;

        while (continueDeleting) {
            String patientID = "";
            boolean idExist = false;
            int attempts = 0;
            int maxAttempts = 3;

            while (!idExist && attempts < maxAttempts) {
                System.out.print("\n");
                System.out.print("Enter Patient ID to delete (3 max attempts): ");
                patientID = sc.next();

                if (conf.pIDExists(patientID)) { 
                    idExist = true;
                    System.out.println("Patient ID found.");
                } else {
                    attempts++;
                    System.out.println("Invalid ID or ID does not exist.");

                    if (attempts >= maxAttempts) {
                        System.out.println("Maximum attempts reached. Exiting...");
                        return;
                    }
                }
            }

            if (conf.hasPatientApp(patientID)) { 
                System.out.println("Cannot delete patient record. They have existing appointments.");
                return;
            }

            String delete = "DELETE FROM tbl_patients WHERE pID = ?";
            if (!conf.deleteRecords(delete, patientID)) { 
                System.out.println("Failed to delete patient record. Please try again.");
            } else {
                System.out.println("Patient record deleted successfully.");
            }

            System.out.print("\nDo you want to delete another patient? (yes/no): ");
            String response = sc.next().trim().toLowerCase();

            if (!response.equals("yes")) {
                continueDeleting = false;
                System.out.println("Exiting patient deletion process.");
            }
        }
    }

 

}



 
package dentClinic_data;

import static it2c.larosa.dcas.Config.connectDB;
import it2c.larosa.dcas.Config;
import it2c.larosa.dcas.viewConfig;
import java.util.Scanner;


public class PatientInfo {
        
        Config conf = new Config();
        
    public static void managePatients(){
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
                int opt = sc.nextInt();
                    while (opt < 1 ||  opt > 5){
                       System.out.print("\tInvalid Input, Try Again: ");
                          opt = sc.nextInt();
                    }   
                    
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
    
    public void addPatients(){
        Scanner sc = new Scanner(System.in);
//        Config conf = new Config();
        
        System.out.print("\n");
        System.out.print("Patient First Name: ");
        String fname = sc.nextLine();
        System.out.print("Patient Last Name: ");
        String lname = sc.nextLine();
        System.out.print("Patient Age: ");
        int age = sc.nextInt();
        System.out.print("Patient Gender: ");
        String gender = sc.next();
        
        String contnum;
            while (true) {
                System.out.print("Patient Contact Number (11 digits): ");
                contnum = sc.next();
                if (contnum.matches("\\d{11}")) {
                    break;
                } else {
                    System.out.println("Invalid contact number. Must be 11 digits and numeric.");
                }
            }
        
        System.out.print("Patient Email: ");
        String email = sc.next();
        System.out.print("Patient Address: ");
        String address = sc.nextLine();

        String sql = "INSERT INTO tbl_patients (pFNAME, pLNAME, pAGE, pGENDER, pCONTNUM, pEMAIL, pADDRESS) VALUES (?, ?, ?, ?, ?, ?, ?)";

        conf.addRecords(sql, fname, lname, age, gender, contnum, email, address);
    }
    
    private void viewPatient() {
        viewConfig cnf = new viewConfig();
        
        String rodeQuery = "SELECT * FROM tbl_patients";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Age", "Gender", "Contact Number", "Email", "Address"};
        String[] rodeColumns = {"pID", "pFNAME", "pLNAME", "pAGE", "pGENDER", "pCONTNUM", "pEMAIL", "pADDRESS"};

        cnf.viewPatient(rodeQuery, rodeHeaders, rodeColumns);
    }

    
    private void updatePatient(){
        Scanner sc = new Scanner (System.in);
        
        String patientID = "";
        boolean idexist = false;
        int attempts = 0;
        int maxAttempts = 3;

        while (!idexist && attempts < maxAttempts) {
            System.out.print("\n");
            System.out.print("Enter Patient ID to update (3 max attempts): ");
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
        
        System.out.print("\n");
        System.out.print("Enter new First Name: ");
        String updfname = sc.next();
        
        System.out.print("Enter new Last Name: ");
        sc.nextLine();
        String updlname = sc.nextLine();
        
        System.out.print("Enter new Age: ");
        int updage = sc.nextInt();
        
        System.out.print("Enter new Gender: ");
        String updgen = sc.next();
        
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
        
        System.out.print("Enter new Email: ");
        String updemail = sc.next();
        
        System.out.print("Enter new Address: ");
        String upadd = sc.nextLine();
        
        String update = "UPDATE tbl_patients SET pFNAME = ?, pLNAME = ?, pAGE = ?, pGENDER = ?, pCONTNUM = ?, pEMAIL = ?, pADDRESS = ?  WHERE pID = ?";
        
        conf.updateRecords(update, updfname, updlname, updage, updgen, updcontnum, updemail, upadd, patientID);
    }
    
    private void deletePatient() {
        Scanner sc = new Scanner(System.in);

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
    }
 

}



 
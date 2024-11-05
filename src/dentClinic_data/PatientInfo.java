package dentClinic_data;

import static it2c.larosa.dcas.Config.connectDB;
import it2c.larosa.dcas.Config;
import java.util.Scanner;


public class PatientInfo {
        
        Config conf = new Config();
        
    public static void managePatients(){
        Scanner sc = new Scanner (System.in);
        boolean response = true;
        
        do {
                System.out.print("\n");
                System.out.println("MANAGE PATIENT INFORMATION");
                System.out.println("-----------------------------------");
                System.out.println("      1. REGISTER A PATIENT        ");
                System.out.println("      2. VIEW PATIENT RECORD       ");
                System.out.println("      3. EDIT PATIENT RECORD       ");
                System.out.println("      4. DELETE PATIENT RECORD     ");
                System.out.println("      5. EXIT                      ");
                System.out.println("-----------------------------------");
                
                System.out.print ("\nEnter Option: ");
                int opt = sc.nextInt();
                    while (opt==0 && opt>6){
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
                        System.out.println("Exiting...");
                        break;
                }
                           
            } while(response);
            System.out.println("\n\tThank you, See you! ");
    }
    
    public void addPatients(){
        Scanner sc = new Scanner(System.in);
//        Config conf = new Config();
        
        System.out.print("\n");
        System.out.print("Patient First Name: ");
        String fname = sc.next();
        System.out.print("Patient Last Name: ");
        String lname = sc.next();
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
        String address = sc.next();

        String sql = "INSERT INTO tbl_patients (pFNAME, pLNAME, pAGE, pGENDER, pCONTACTNUM, pEMAIL, pADDRESS) VALUES (?, ?, ?, ?, ?, ?, ?)";

        conf.addRecords(sql, fname, lname, age, gender, contnum, email, address);
    }
    
    private void viewPatient() {
//        Config cnf = new Config();
        
        String rodeQuery = "SELECT * FROM tbl_patients";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Age", "Gender", "Contact Number", "Email", "Address"};
        String[] rodeColumns = {"pID", "pFNAME", "pLNAME", "pAGE", "pGENDER", "pCONTACTNUM", "pEMAIL", "pADDRESS"};

        conf.viewRecords(rodeQuery, rodeHeaders, rodeColumns);
    }

    
    private void updatePatient(){
        Scanner sc = new Scanner (System.in);
        
        String patientID = "";
        boolean idexist = false;
        
        while(!idexist){
            System.out.print("\nEnter Patient ID to update: ");
            patientID = sc.next();
            
            if(conf.pIDExists(patientID)){
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
        String upadd = sc.next();
        
        String update = "UPDATE tbl_patients SET pFNAME = ?, pLNAME = ?, pAGE = ?, pGENDER = ?, pCONTNUM = ?, pEMAIL = ?, pADDRESS = ?  WHERE pID = ?";
        
//        Config cnf = new Config();
        conf.updateRecords(update, updfname, updlname, updage, updgen, updcontnum, updemail, upadd, patientID);
    }
    
    private void deletePatient(){
        Scanner sc = new Scanner (System.in);
        
        System.out.print("Enter Patient ID to delete: ");
        int id = sc.nextInt();
        
        String delete = "DELETE FROM tbl_patients WHERE pID = ?";
        
//        Config cnf = new Config();
        conf.deleteRecords(delete, id);
    }

}



 
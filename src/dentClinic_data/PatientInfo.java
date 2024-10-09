package dentClinic_data;

import static it2c.larosa.dcas.Config.connectDB;
import it2c.larosa.dcas.Config;
import java.util.Scanner;


public class PatientInfo {
    
    public static void Patients(){
        Scanner sc = new Scanner (System.in);
        int opt;
        
                System.out.println("MANAGE PATIENT INFORMATION");
                System.out.println("-----------------------------------");
                System.out.println("|    1. REGISTER A PATIENT        |");
                System.out.println("|    2. VIEW PATIENT RECORD       |");
                System.out.println("|    3. EDIT PATIENT RECORD       |");
                System.out.println("|    4. DELETE PATIENT RECORD     |");
                System.out.println("|    5. EXIT                      |");
                System.out.println("-----------------------------------");
                
                System.out.print ("\nEnter Option: ");
                opt = sc.nextInt();
                    while (opt==0 && opt>6){
                       System.out.print("\tInvalid Input, Try Again: ");
                          opt = sc.nextInt();
                    }   
              
                switch (opt){
                    case 1:
                        PatientInfo add = new PatientInfo();
                        add.addPatients();
                        break;
                    case 2: 
                        PatientInfo view = new PatientInfo();
                        view.viewPatient();
                        break;
                    case 3:
                        PatientInfo update = new PatientInfo();
                        update.updatePatient();
                        break;
                    case 4:
                        PatientInfo delete = new PatientInfo();
                        delete.deletePatient();
                        break;
                }
    }
    
    public void addPatients(){
        Scanner sc = new Scanner(System.in);
        Config conf = new Config();
        
        System.out.print("\n");
        System.out.print("Patient First Name: ");
        String fname = sc.next();
        System.out.print("Patient Last Name: ");
        String lname = sc.next();
        System.out.print("Patient Age: ");
        int age = sc.nextInt();
        System.out.print("Patient Gender: ");
        String gender = sc.next();
        System.out.print("Patient Contact Number: ");
        String contnum = sc.next();
        System.out.print("Patient Email: ");
        String email = sc.next();

        String sql = "INSERT INTO patients (pFNAME, pLNAME, pAGE, pGENDER, pCONTACTNUM, pEMAIL) VALUES (?, ?, ?, ?, ?, ?)";

        conf.addRecords(sql, fname, lname, age, gender, contnum, email);
    }
    
    private void viewPatient() {
        Config cnf = new Config();
        
        String rodeQuery = "SELECT * FROM patients";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Age", "Gender", "Contact Number", "Email"};
        String[] rodeColumns = {"pID", "pFNAME", "pLNAME", "pAGE", "pGENDER", "pCONTACTNUM", "pEMAIL"};

        cnf.viewRecords(rodeQuery, rodeHeaders, rodeColumns);
    }

    
    private void updatePatient(){
        Scanner sc = new Scanner (System.in);
        
        System.out.print("Enter Patient ID to update: ");
        int id = sc.nextInt();
        
        System.out.print("\n");
        System.out.print("Enter new First Name: ");
        String updfname = sc.next();
        
        System.out.print("Enter new Last Name: ");
        String updlname = sc.next();
        
        System.out.print("Enter new Age: ");
        int updage = sc.nextInt();
        
        System.out.print("Enter new Gender: ");
        String updgen = sc.next();
        
        System.out.print("Enter new Email: ");
        String updemail = sc.next();
        
        String update = "UPDATE patients SET pFNAME = ?, pLNAME = ?, pAGE = ?, pGENDER = ?, pEMAIL = ?  WHERE pID = ?";
        
        Config cnf = new Config();
        cnf.updateRecords(update, updfname, updlname, updage, updgen, updemail, id);
    }
    
    private void deletePatient(){
        Scanner sc = new Scanner (System.in);
        
        System.out.print("Enter Patient ID to delete: ");
        int id = sc.nextInt();
        
        String delete = "DELETE FROM patients WHERE pID = ?";
        
        Config cnf = new Config();
        cnf.deleteRecords(delete, id);
    }

}



 
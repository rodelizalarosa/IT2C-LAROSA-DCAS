package it2c.larosa.dcas;

import static it2c.larosa.dcas.Config.connectDB;
import java.util.Scanner;

public class PatientInfo {
    
    public void addPatients(){
        Scanner sc = new Scanner(System.in);
        Config conf = new Config();
        
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

        conf.addRecord(sql, fname, lname, age, gender, contnum, email);
    }
    
    public void viewPatients() {
        String rodeQuery = "SELECT * FROM patients";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Age", "Gender", "Contact Number", "Email"};
        String[] rodeColumns = {"id", "first_name", "last_name", "age", "gender", "contact_number", "email"};

        conf.viewRecord(rodeQuery, rodeHeaders, rodeColumns);
    }

    
}

package it2c.larosa.dcas;

import static it2c.larosa.dcas.Config.connectDB;
import java.util.Scanner;

public class IT2CLAROSADCAS {

        public void addPatients(){
        Scanner sc = new Scanner(System.in);
        Config conf = new Config();
        
        System.out.print("Patient First Name: ");
        String fname = sc.next();
        System.out.print("Patient Last Name: ");
        String lname = sc.next();
        System.out.print("Patient Email: ");
        String email = sc.next();
        System.out.print("Patient Gender: ");
        String gender = sc.next();

        String sql = "INSERT INTO patients (pFNAME, pLNAME, pEMAIL, pGENDER) VALUES (?, ?, ?, ?)";


        conf.addRecord(sql, fname, lname, email, gender);


    }
    
    
    public static void main(String[] args) {
       
        Scanner sc = new Scanner (System.in);
        
        String response;
        
    do{
        System.out.println("Welcome to Dental Clinic Appointment System");
        System.out.println("-------------------------------------");
        System.out.println("1. ADD");
        System.out.println("2. VIEW");
        System.out.println("3. UPDATE");
        System.out.println("4. DELETE");
        System.out.println("5. EXIT");
        System.out.println("-------------------------------------");
                
        System.out.print ("Enter Action: ");
        int act = sc.nextInt();
        
        switch (act){
            case 1:
                IT2CLAROSADCAS ap = new IT2CLAROSADCAS();
                ap.addPatients();
            break;
            case 2:
            
            break;
            case 3:
            
            break;    
            case 4:
                
            break;
        }
        
        System.out.println("Do you want to continue? (yes/no): ");
        response = sc.next();
                
    } while(response.equals("yes"));
        System.out.println("Thank you, See you! ");
    
    }

}

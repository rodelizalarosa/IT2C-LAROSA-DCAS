package it2c.larosa.dcas;

import java.util.Scanner;

public class IT2CLAROSADCAS {

//        public void addPatients(){
//        Scanner sc = new Scanner(System.in);
//        Config conf = new Config();
//        
//        System.out.print("Patient First Name: ");
//        String fname = sc.next();
//        System.out.print("Patient Last Name: ");
//        String lname = sc.next();
//        System.out.print("Patient Email: ");
//        String email = sc.next();
//        System.out.print("Patient Gender: ");
//        String gender = sc.next();
//
//        String sql = "INSERT INTO patients (pFNAME, pLNAME, pEMAIL, pGENDER) VALUES (?, ?, ?, ?)";
//
//
//        conf.addRecord(sql, fname, lname, email, gender);
//
//    }
    
    
    public static void main(String[] args) {
       
        Scanner sc = new Scanner (System.in);
        
        String response;
        
    do{
        System.out.println("Welcome to Dental Clinic Appointment System");
        System.out.println("---------------------------------------");
        System.out.println("|    1. MANAGE PATIENT INFORMATION    |");
        System.out.println("|    2. MANAGE DOCTOR INFORMATION     |");
        System.out.println("|    3. MANAGE STAFF INFORMATION      |");
        System.out.println("|    4. SCHEDULE AN APPOINTMENT       |");
        System.out.println("|    5. VIEW RECORDS                  |");
        System.out.println("|    6. EXIT                          |");
        System.out.println("---------------------------------------");
                
        System.out.print ("Enter Action: ");
        int act = sc.nextInt();
            while (act==0 && act>6){
                System.out.print("Invalid Input, Try Again: ");
                act = sc.nextInt();
            }
                                      
        switch (act){
            case 1:
                PatientInfo pi = new PatientInfo();
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
              
                if (opt == 1) {
                    pi.addPatients();
                } else if (opt == 2){
                    pi.viewPatients();
                }
            break;
            case 2:
            
            break;
            case 3:
            
            break;    
            case 4:
                
            break;
        }
        
        System.out.print("Do you want to continue? (yes/no): ");
        response = sc.next();
                
    } while(response.equals("yes"));
        System.out.println("Thank you, See you! ");
    
    }  

}

package it2c.larosa.dcas;

import dentClinic_data.PatientInfo;
import java.util.Scanner;

public class IT2CLAROSADCAS {
    
    public static void main(String[] args) {
       
        Scanner sc = new Scanner (System.in);
        String response;
        
    do{
        System.out.print("\n");
        System.out.println("Welcome to Dental Clinic Ap1pointment System");
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
            while (act==0 && act>=6){
                System.out.print("Invalid Input, Try Again: ");
                act = sc.nextInt();
            }
                                      
        switch (act){
            case 1:
                PatientInfo pi = new PatientInfo();
                pi.Patients();
            break;
            case 2:
            
            break;
            case 3:
            
            break;    
            case 4:
                
            break;
        }
        
        System.out.print("\nDo you want to continue? (yes/no): ");
        response = sc.next();
                
    } while(response.equals("yes"));
        System.out.println("\n\tThank you, See you! ");
    
    }  

}

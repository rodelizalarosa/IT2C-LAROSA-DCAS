
package dentClinic_data;

import it2c.larosa.dcas.Config;
import java.util.Scanner;

public class StaffInfo {
        
        Config conf = new Config();
    public static void manageStaffs (){
        
        Scanner sc = new Scanner (System.in);
        boolean response = true;
        
        do {
            System.out.print("\n");
            System.out.println("MANAGE STAFF INFORMATION");
            System.out.println("-----------------------------------");
            System.out.println("     1. REGISTER A STAFF           ");
            System.out.println("     2. VIEW STAFF RECORD          ");
            System.out.println("     3. EDIT STAFF RECORD          ");
            System.out.println("     4. DELETE STAFF RECORD        ");
            System.out.println("     5. EXIT                       ");
            System.out.println("-----------------------------------");

            System.out.print("\nEnter Option: ");
            int opt = sc.nextInt();

            while (opt < 1 || opt > 5) {
                System.out.print("\tInvalid Input, Try Again: ");
                opt = sc.nextInt();
            }
            
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
                System.out.println("Exiting...");
                break;
        }
//          System.out.print("\nDo you want to continue? (yes/no): ");
//          response = sc.next();
                
        } while(response);
            System.out.println("\n\tThank you, See you! ");
  
    }

   public void addStaff() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("\n");
        System.out.print("Staff First Name: ");
        String fname = sc.next();
        System.out.print("Staff Last Name: ");
        String lname = sc.next();
        System.out.print("Staff Role: ");
        String role = sc.next();

        String contnum;
            while (true) {
                System.out.print("Staff Contact Number (11 digits): ");
                contnum = sc.next();
                if (contnum.matches("\\d{11}")) {
                    break;
                } else {
                    System.out.println("Invalid contact number. Must be 11 digits and numeric.");
                }
            }

        String sql = "INSERT INTO tbl_staff (sFNAME, sLNAME, sROLE, sCONTACTNUM) VALUES (?, ?, ?, ?)";

        conf.addRecords(sql, fname, lname, role, contnum); 
    }

    private void viewStaff() { 
        
        String rodeQuery = "SELECT * FROM tbl_staff";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Role", "Contact Number"};
        String[] rodeColumns = {"sID", "sFNAME", "sLNAME", "sROLE", "sCONTACTNUM"};

        conf.viewRecords(rodeQuery, rodeHeaders, rodeColumns);
    }

    private void updateStaff() {
        Scanner sc = new Scanner(System.in);
        
        String stID = "";
        boolean idexist = false;
        
        while(!idexist){
            System.out.print("\nEnter Staff ID to update: ");
            stID = sc.next();
            
            if(conf.sIDExists(stID)){
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
        
        System.out.print("Enter new Role: ");
        String updrole = sc.next();
           
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
        
        
        String update = "UPDATE staff SET sFNAME = ?, sLNAME = ?, sROLE = ?, sCONTACTNUM = ? WHERE sID = ?";

        conf.updateRecords(update, updfname, updlname, updrole, updcontnum, stID);
    }
    
    private void deleteStaff() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Staff ID to delete: ");
        int id = sc.nextInt();
        
        String delete = "DELETE FROM staff WHERE sID = ?";
        
        conf.deleteRecords(delete, id);
    }
}
    
   

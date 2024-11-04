
package dentClinic_data;

import it2c.larosa.dcas.Config;
import java.util.Scanner;

public class StaffInfo {
    
    public static void manageStaffs (){
        
        Scanner sc = new Scanner (System.in);
        int opt;
        String response;
        
        do {
        System.out.println("MANAGE STAFF INFORMATION");
        System.out.println("-----------------------------------");
        System.out.println("|    1. REGISTER A STAFF          |");
        System.out.println("|    2. VIEW STAFF RECORD         |");
        System.out.println("|    3. EDIT STAFF RECORD         |");
        System.out.println("|    4. DELETE STAFF RECORD       |");
        System.out.println("|    5. EXIT                      |");
        System.out.println("-----------------------------------");
        
        System.out.print("\nEnter Option: ");
        opt = sc.nextInt();
        
        while (opt < 1 || opt > 5) {
            System.out.print("\tInvalid Input, Try Again: ");
            opt = sc.nextInt();
        }
        
        switch (opt) {
            case 1:
                StaffInfo add = new StaffInfo();
                add.addStaff();
                break;
            case 2: 
                StaffInfo view = new StaffInfo();
                view.viewStaff();
                break;
            case 3:
                StaffInfo update = new StaffInfo();
                update.updateStaff();
                break;
            case 4:
                StaffInfo delete = new StaffInfo();
                delete.deleteStaff();
                break;
            case 5:
                System.out.println("Exiting...");
                return;
        }
          System.out.print("\nDo you want to continue? (yes/no): ");
          response = sc.next();
                
        } while(response.equalsIgnoreCase("yes"));
        System.out.println("\n\tThank you, See you! ");
  
        
    }

   public void addStaff() {
        Scanner sc = new Scanner(System.in);
        Config conf = new Config();
        
        System.out.print("\n");
        System.out.print("Staff First Name: ");
        String fname = sc.next();
        System.out.print("Staff Last Name: ");
        String lname = sc.next();
        System.out.print("Staff Role: ");
        String role = sc.next();
        System.out.print("Staff Contact Number: ");
        String contnum = sc.next();

        String sql = "INSERT INTO tbl_staff (dFNAME, dLNAME, dROLE, dCONTACTNUM) VALUES (?, ?, ?, ?)";

        conf.addRecords(sql, fname, lname, role, contnum); 
    }

    private void viewStaff() {
        Config cnf = new Config();
        
        String rodeQuery = "SELECT * FROM tbl_staff";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Role", "Contact Number"};
        String[] rodeColumns = {"sID", "sFNAME", "sLNAME", "sROLE", "sCONTACTNUM"};

        cnf.viewRecords(rodeQuery, rodeHeaders, rodeColumns);
    }

    private void updateStaff() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Staff ID to update: ");
        int id = sc.nextInt();
        
        System.out.print("\n");
        System.out.print("Enter new First Name: ");
        String updfname = sc.next();
        
        System.out.print("Enter new Last Name: ");
        String updlname = sc.next();
        
        System.out.print("Enter new Role: ");
        String updrole = sc.next();
        
        System.out.print("Enter new Contact Number: ");
        String updcontnum = sc.next();
        
        String update = "UPDATE staff SET sFNAME = ?, sLNAME = ?, sROLE = ?, sCONTACTNUM = ? WHERE sID = ?";
        
        Config cnf = new Config();
        cnf.updateRecords(update, updfname, updlname, updrole, updcontnum, id);
    }
    
    private void deleteStaff() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Staff ID to delete: ");
        int id = sc.nextInt();
        
        String delete = "DELETE FROM staff WHERE sID = ?";
        
        Config cnf = new Config();
        cnf.deleteRecords(delete, id);
    }
}
    
   

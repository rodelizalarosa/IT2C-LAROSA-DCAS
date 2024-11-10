 package dentClinic_data;

import java.util.Scanner;
import it2c.larosa.dcas.Config;
import it2c.larosa.dcas.viewConfig;

public class Appointment {
    Config conf = new Config();

    private void displayServices() {
        System.out.println("\n");
        System.out.println("  Available Dental Services:");
        System.out.println("    1. Cleaning");
        System.out.println("    2. Filling");
        System.out.println("    3. Extraction");
        System.out.println("    4. Root Canal");
        System.out.println("    5. Orthodontic Treatment");
        System.out.println("    6. Whitening");
    }

    private void displayStatusOptions() {
        System.out.println("\n");
        System.out.println("Appointment Status Options:");
        System.out.println("    1. Complete");
        System.out.println("    2. Cancelled");
    }

    public static void manageAppointments() {
        Scanner sc = new Scanner(System.in);
        boolean response = true;

        do {
            System.out.print("\n");
            System.out.println("          APPOINTMENTS        ");
            System.out.println("------------------------------");
            System.out.println("     1. SCHEDULE APPOINTMENT  ");
            System.out.println("     2. VIEW APPOINTMENTS     ");
            System.out.println("     3. EDIT APPOINTMENT      ");
            System.out.println("     4. UPDATE STATUS         ");
            System.out.println("     5. CANCEL APPOINTMENT    ");
            System.out.println("     6. EXIT                  ");
            System.out.println("------------------------------");

            System.out.print("Enter Option: ");
            int opt = sc.nextInt();

            Appointment app = new Appointment();
            switch (opt) {
                case 1:
                    app.scheduleAppointment();
                    break;
                case 2:
                    app.viewAppointment();
                    break;
                case 3:
                    app.updateAppointment();
                    break;
                case 4:
                    app.updateStatus();
                    break;
                case 5:
                    app.cancelAppointment();
                    break;
                case 6: 
                    response = false;
                    System.out.println("Exiting Schedule an Appointment...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

        } while (response);
    }
    
    private boolean isIDValid(String tableName, String idColumn, String id) {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + idColumn + " = ?";
        return conf.recordExists(query, id);
    }
    
    private void viewDentist() {
        String rodeQuery = "SELECT dID, dFNAME, dLNAME, dSPECIALIZATION  FROM tbl_doctors";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Specialization"};
        String[] rodeColumns = {"dID", "dFNAME", "dLNAME", "dSPECIALIZATION"};
        
        viewConfig cnf = new viewConfig();
        cnf.viewDentist(rodeQuery, rodeHeaders, rodeColumns);
    }
    
     private void viewPatients() {
        viewConfig cnf = new viewConfig();
        
        String rodeQuery = "SELECT pID, pFNAME, pLNAME, pAGE, pGENDER FROM tbl_patients";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Age", "Gender"};
        String[] rodeColumns = {"pID", "pFNAME", "pLNAME", "pAGE", "pGENDER"};

        cnf.viewPatients(rodeQuery, rodeHeaders, rodeColumns);
    }
   
      private void viewStaff() { 
        
        String rodeQuery = "SELECT sID, sFNAME, sLNAME, sROLE FROM tbl_staff";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Role"};
        String[] rodeColumns = {"sID", "sFNAME", "sLNAME", "sROLE"};
        
        viewConfig cnf = new viewConfig();
        cnf.viewStaff(rodeQuery, rodeHeaders, rodeColumns);
    }
    
    public void scheduleAppointment() {
        Scanner sc = new Scanner(System.in);
        
        viewPatients();
        String patientID;
        do {
            System.out.print("Enter Patient ID: ");
            patientID = sc.next();
            if (!isIDValid("tbl_patients", "pID", patientID)) {
                System.out.println("Invalid Patient ID. Please try again.");
            }
        } while (!isIDValid("tbl_patients", "pID", patientID));

        viewDentist();
        String doctorID;
        do {
            System.out.print("Enter Doctor ID: ");
            doctorID = sc.next();
            if (!isIDValid("tbl_doctors", "dID", doctorID)) {
                System.out.println("Invalid Doctor ID. Please try again.");
            }
        } while (!isIDValid("tbl_doctors", "dID", doctorID));

        viewStaff();
        String staffID;
        do {
            System.out.print("Enter Staff ID: ");
            staffID = sc.next();
            if (!isIDValid("tbl_staff", "sID", staffID)) {
                System.out.println("Invalid Staff ID. Please try again.");
            }
        } while (!isIDValid("tbl_staff", "sID", staffID));
        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String date = sc.next();
        System.out.print("Enter Appointment Time (HH:MM): ");
        String time = sc.next();
        
        displayServices();
        System.out.print("Select Service (Enter number): ");
        int serviceOption = sc.nextInt();
        String[] services = {"Cleaning", "Filling", "Extraction", "Root Canal", "Orthodontic Treatment", "Whitening"};
        String service = services[serviceOption - 1];

        String status = "Pending";

        String sql = "INSERT INTO tbl_appointments (doctorID, patientID, staffID, appDATE, appTIME, appService, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        conf.addRecords(sql, doctorID, patientID, staffID.isEmpty() ? null : staffID, date, time, service, status);
        if (conf == null) {
            conf = new Config();
        }

    }

    public void viewAppointment() {
        String rodequery = "SELECT a.appID, a.doctorID, p.pFNAME, p.pLNAME, a.staffID, a.appDATE, a.appTIME, a.appService, a.status " +
                       "FROM tbl_appointments a " +
                       "INNER JOIN tbl_patients p ON a.patientID = p.pID";
        String[] rodeheaders = {"Appointment ID", "Doctor ID", "Patient First Name", "Patient Last Name", "Staff ID", "Date", "Time", "Service", "Status"};
        String[] rodecolumns = {"appID", "doctorID", "pFNAME", "pLNAME", "staffID", "appDATE", "appTIME", "appService", "status"};
        viewConfig cnf = new viewConfig ();
        cnf.viewAppointment(rodequery, rodeheaders, rodecolumns);
    }


    public void updateStatus() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Appointment ID to update status: ");
        String appID = sc.next();

        String currentStatus = conf.getStatus(appID); 
        
        if (!currentStatus.equals("Pending")) {
            System.out.println("Only appointments with 'Pending' status can be updated.");
            return;
        }

        displayStatusOptions();
        System.out.print("Select new Status (Enter number): ");
        int statusOption = sc.nextInt();
        String[] statuses = {"Complete", "Cancelled"};
        String status = statuses[statusOption - 1];

        String update = "UPDATE tbl_appointments SET status = ? WHERE appID = ?";
        conf.updateRecords(update, status, appID);

    }

    public void updateAppointment() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Appointment ID to update: ");
        String appID = sc.next();

        System.out.print("Enter new Doctor ID: ");
        String doctorID = sc.next();
        System.out.print("Enter new Patient ID: ");
        String patientID = sc.next();
        System.out.print("Enter new Staff ID: ");
        String staffID = sc.next();
        System.out.print("Enter new Date (YYYY-MM-DD): ");
        String date = sc.next();
        System.out.print("Enter new Time (HH:MM): ");
        String time = sc.next();
        
        System.out.print("\n");
        displayServices();
        System.out.print("Select new Service (Enter number): ");
        int serviceOption = sc.nextInt();
        String[] services = {"Cleaning", "Filling", "Extraction", "Root Canal", "Orthodontic Treatment", "Whitening"};
        String service = services[serviceOption - 1];

        String update = "UPDATE tbl_appointments SET doctorID = ?, patientID = ?, staffID = ?, appDATE = ?, appTIME = ?, appService = ? WHERE appID = ?";
        conf.updateRecords(update, doctorID, patientID, staffID, date, time, service, appID);
    }

    public void cancelAppointment() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Appointment ID to cancel: ");
        String appID = sc.next();

        String currentStatus = conf.getStatus(appID);

        if (currentStatus.equals("Complete")) {
            System.out.println("You cannot cancel an appointment once it is marked as 'Complete'.");
            return;
        }

        String delete = "DELETE FROM tbl_appointments WHERE appID = ?";
        conf.deleteRecords(delete, appID);
    }
}

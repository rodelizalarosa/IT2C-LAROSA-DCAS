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
            System.out.println("     5. DELETE APPOINTMENT    ");
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
                    app.deleteAppointment();
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
   
      private void viewStaffs() { 
        
        String rodeQuery = "SELECT sID, sFNAME, sLNAME, sROLE FROM tbl_staff";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Role"};
        String[] rodeColumns = {"sID", "sFNAME", "sLNAME", "sROLE"};
        
        viewConfig cnf = new viewConfig();
        cnf.viewStaffs(rodeQuery, rodeHeaders, rodeColumns);
    }
    
    public void scheduleAppointment() {
        Scanner sc = new Scanner(System.in);
        
        viewPatients();
        String patientID;
        int attempts = 0;
        do {
            System.out.print("Enter Patient ID: ");
            patientID = sc.next();
            attempts++;
            if (!conf.pIDExists(patientID)) {
                System.out.println("Invalid Patient ID. Please try again.");
            } else {
                break;
            }
        } while (attempts < 3);

        if (attempts == 3 && !conf.pIDExists(patientID)) {
            System.out.println("Maximum attempts reached. Returning...");
            return;
        }

        viewDentist();
        String doctorID;
        attempts = 0;
        do {
            System.out.print("Enter Doctor ID: ");
            doctorID = sc.next();
            attempts++;
            if (!conf.dIDExists(doctorID)) {
                System.out.println("Invalid Doctor ID. Please try again.");
            } else {
                break;
            }
        } while (attempts < 3);

        if (attempts == 3 && !conf.dIDExists(doctorID)) {
            System.out.println("Maximum attempts reached. Returning...");
            return;
        }
     
        viewStaffs();
        String staffID;
        attempts = 0;
        do {
            System.out.print("Enter Staff ID: ");
            staffID = sc.next();
            attempts++;
            if (!conf.sIDExists(staffID)) {
                System.out.println("Invalid Staff ID. Please try again.");
            } else {
                break;
            }
        } while (attempts < 3);

        if (attempts == 3 && !conf.sIDExists(staffID)) {
            System.out.println("Maximum attempts reached. Returning...");
            return;
        }

        
        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String date = sc.next();
        System.out.print("Enter Appointment Time (HH:MM): ");
        String time = sc.next();
        
        displayServices();
        System.out.print("Select Services (Enter numbers separated by commas): ");
        sc.nextLine();  
        String serviceInput = sc.nextLine();

        String[] services = {"Cleaning", "Filling", "Extraction", "Root Canal", "Orthodontic Treatment", "Whitening"};
        StringBuilder serviceChoice = new StringBuilder();

        String[] serviceNumbers = serviceInput.split(",");
        for (String num : serviceNumbers) {
            int serviceIndex = Integer.parseInt(num.trim()) - 1;
            if (serviceIndex >= 0 && serviceIndex < services.length) {
                if (serviceChoice.length() > 0) {
                    serviceChoice.append(", ");
                }
                serviceChoice.append(services[serviceIndex]);
            } else {
                System.out.println("Invalid service option: " + num);
            }
        }

        String status = "Pending";

        String sql = "INSERT INTO tbl_appointments (doctorID, patientID, staffID, appDATE, appTIME, appService, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        conf.addRecords(sql, doctorID, patientID, staffID.isEmpty() ? null : staffID, date, time, serviceChoice.toString(), status);
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

        viewAppointment(); 
        String appID = "";
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("Enter Appointment ID to update status: ");
            appID = sc.next();
            if (conf.appIDExists(appID)) {
                break; 
            } else {
                attempts++;
                System.out.println("Invalid Appointment ID. Please try again.");
            }
        }

        if (attempts == 3) {
            System.out.println("Maximum attempts reached. Returning...");
            return;
        }

        String currentStatus = conf.getStatus(appID);

        if ("Complete".equalsIgnoreCase(currentStatus) || "Cancelled".equalsIgnoreCase(currentStatus)) {
            System.out.println("Appointment with status '" + currentStatus + "' cannot be updated.");
            return;
        }

        displayStatusOptions();
        System.out.print("Select new Status (Enter number): ");
        int statusOption = sc.nextInt();

        String[] statuses = {"Complete", "Cancelled"};
        if (statusOption < 1 || statusOption > statuses.length) {
            System.out.println("Invalid status option. Returning...");
            return;
        }

        String newStatus = statuses[statusOption - 1];
        String updateSQL = "UPDATE tbl_appointments SET status = ? WHERE appID = ?";
        conf.updateRecords(updateSQL, newStatus, appID);

        System.out.println("Appointment status updated to: " + newStatus);
    }



    public void updateAppointment() {
        Scanner sc = new Scanner(System.in);

        viewAppointment(); 
        String appID = "";
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("\nEnter Appointment ID to update (3 max attempts): ");
            appID = sc.next();
            if (conf.appIDExists(appID)) {
                System.out.println("Appointment ID found.");
                break;
            } else {
                attempts++;
                System.out.println("\tInvalid ID or ID does not exist.");
            }
        }

        if (attempts == 3) {
            System.out.println("Maximum attempts reached. Exiting...");
            return;
        }

        String doctorID;
        do {
            System.out.print("Enter new Doctor ID: ");
            doctorID = sc.next();
            if (conf.dIDExists(doctorID)) {
                break;
            } else {
                System.out.println("Invalid Doctor ID. Please try again.");
            }
        } while (true);

        String patientID;
        do {
            System.out.print("Enter new Patient ID: ");
            patientID = sc.next();
            if (conf.pIDExists(patientID)) {
                break;
            } else {
                System.out.println("Invalid Patient ID. Please try again.");
            }
        } while (true);

        String staffID;
        do {
            System.out.print("Enter new Staff ID: ");
            staffID = sc.next();
            if (conf.sIDExists(staffID)) {
                break;
            } else {
                System.out.println("Invalid Staff ID. Please try again.");
            }
        } while (true);

        System.out.print("Enter new Date (YYYY-MM-DD): ");
        String date = sc.next();
        System.out.print("Enter new Time (HH:MM): ");
        String time = sc.next();

        displayServices();
        System.out.print("Select new Services (Enter numbers separated by commas): ");
        sc.nextLine(); 
        String serviceInput = sc.nextLine();

        String[] services = {"Cleaning", "Filling", "Extraction", "Root Canal", "Orthodontic Treatment", "Whitening"};
        StringBuilder selectedServices = new StringBuilder();

        for (String num : serviceInput.split(",")) {
            try {
                int serviceIndex = Integer.parseInt(num.trim()) - 1;
                if (serviceIndex >= 0 && serviceIndex < services.length) {
                    if (selectedServices.length() > 0) selectedServices.append(", ");
                    selectedServices.append(services[serviceIndex]);
                } else {
                    System.out.println("Invalid service option: " + num);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid service number: " + num);
            }
        }

        String updateSQL = "UPDATE tbl_appointments SET doctorID = ?, patientID = ?, staffID = ?, appDATE = ?, appTIME = ?, appService = ? WHERE appID = ?";
        conf.updateRecords(updateSQL, doctorID, patientID, staffID, date, time, selectedServices.toString(), appID);
        System.out.println("Appointment updated successfully.");
    }



    public void deleteAppointment() {
        Scanner sc = new Scanner(System.in);

        viewAppointment(); 
        String appID = "";
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("Enter Appointment ID to delete: ");
            appID = sc.next();
            if (conf.appIDExists(appID)) {
                break; // Valid ID found
            } else {
                attempts++;
                System.out.println("Invalid Appointment ID. Please try again.");
            }
        }

        if (attempts == 3) {
            System.out.println("Maximum attempts reached. Returning...");
            return;
        }

        String currentStatus = conf.getStatus(appID);

        if (!"Cancelled".equalsIgnoreCase(currentStatus)) {
            System.out.println("Only appointments with 'Cancelled' status can be deleted.");
            return;
        }

        String deleteSQL = "DELETE FROM tbl_appointments WHERE appID = ?";
        conf.deleteRecords(deleteSQL, appID);
        System.out.println("Appointment deleted successfully.");
    }


}

 package dentClinic_data;

import java.util.Scanner;
import it2c.larosa.dcas.Config;
import it2c.larosa.dcas.viewConfig;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Appointment {
    Config conf = new Config();

    private void displayServices() {
        System.out.println("\n");
        System.out.println("----------------------------------");
        System.out.println("|    Available Dental Services   |");
        System.out.println("|      1. Cleaning               |");
        System.out.println("|      2. Filling                |");
        System.out.println("|      3. Extraction             |");
        System.out.println("|      4. Root Canal             |");
        System.out.println("|      5. Orthodontic Treatment  |");
        System.out.println("|      6. Whitening              |");
        System.out.println("----------------------------------");
    }

    private void displayStatusOptions() {
        System.out.println("\n");
        System.out.println("------------------------------");
        System.out.println("| Appointment Status Options |");
        System.out.println("|      1. Complete           |");
        System.out.println("|      2. Cancelled          |");
        System.out.println("------------------------------");
    }

    public static void manageAppointments() {
        Config conf = new Config();
        Scanner sc = new Scanner(System.in);
        boolean response = true; // Controls the loop

        do {
            System.out.print("\n");
            System.out.println("===================================");
            System.out.println("            APPOINTMENTS           ");
            System.out.println("-----------------------------------");
            System.out.println("       1. SCHEDULE APPOINTMENT     ");
            System.out.println("       2. VIEW APPOINTMENTS        ");
            System.out.println("       3. EDIT APPOINTMENT         ");
            System.out.println("       4. UPDATE STATUS            ");
            System.out.println("       5. DELETE APPOINTMENT       ");
            System.out.println("       6. EXIT                     ");
            System.out.println("===================================");

            System.out.print("Enter Option: ");
            int act = conf.validateChoiceMain(); 

            Appointment app = new Appointment();
            switch (act) {
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
                    System.out.println("Exiting Schedule an Appointment...");
                    response = false; 
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
        
        String rodeQuery = "SELECT pID, pFNAME, pLNAME, pGENDER FROM tbl_patients";
        String[] rodeHeaders = {"ID", "First Name", "Last Name", "Gender"};
        String[] rodeColumns = {"pID", "pFNAME", "pLNAME", "pGENDER"};

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

        System.out.print("\n");
        System.out.println("=========================================");
        System.out.println("       STAFF AUTHENTICATION ACCESS       ");
        System.out.println("=========================================");
        System.out.print("\n\tStaff's Username: ");
        String username = sc.nextLine();
        System.out.print("\tStaff's Password: ");
        String password = sc.nextLine();

        String hashedPassword = hashPassword(password);

        String staffID = "";
        if (!conf.authenticateStaff(username, hashedPassword)) {
            System.out.println("\nAuthentication failed. Access denied.");
            return;
        } else {
            staffID = conf.getStaffID(username); 
            if (staffID == null || staffID.isEmpty()) {
                System.out.println("\nStaff ID not found for the authenticated username.");
                return;
            }
        }

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

        String date = "";
        boolean validDate = false;
        int dateAttempts = 0;  
        while (!validDate && dateAttempts < 3) {
            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            date = sc.next();
            dateAttempts++;

            if (!isValidDate(date)) {
                System.out.println("Invalid date. Date must be current or in the future, and cannot be a Sunday.");
            } else {
                validDate = true;
            }

            if (dateAttempts >= 3 && !validDate) {
                System.out.println("Maximum attempts reached for date validation. Returning...");
                return;  
            }
        }

        String time = "";
        boolean validTime = false;
        int timeAttempts = 0;  
        while (!validTime && timeAttempts < 3) {
            System.out.print("Enter Appointment Time (HH:MM AM/PM): ");
            time = sc.next();
            timeAttempts++;

            if (!isValidTime(time)) {
                System.out.println("Invalid time. Time must be between 9:00 AM and 5:00 PM.");
            } else {
                validTime = true;
            }

            if (timeAttempts >= 3 && !validTime) {
                System.out.println("Maximum attempts reached for time validation. Returning...");
                return;  
            }
        }

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

        
        String sql = "INSERT INTO tbl_appointments (doctorID, patientID, appDATE, appTIME, appService, status, staffID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        conf.addRecords(sql, doctorID, patientID, date, time, serviceChoice.toString(), status, staffID);
        System.out.println("\tAppointment successfully scheduled!");
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date appointmentDate = (Date) sdf.parse(date);  

            Date currentDate = new Date();

            if (appointmentDate.before(currentDate)) {
                return false;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(appointmentDate);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            return dayOfWeek != Calendar.SUNDAY;
        } catch (Exception e) {
            return false;  
        }
    }

    private boolean isValidTime(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            sdf.setLenient(false); 

            Date appointmentTime = (Date) sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(appointmentTime);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            return hour >= 9 && hour < 17;
        } catch (ParseException e) {
            return false;
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
        
        System.out.print("\n");
        System.out.println("=========================================");
        System.out.println("       STAFF AUTHENTICATION ACCESS       ");
        System.out.println("=========================================");
        System.out.print("\n\tStaff's Username: ");
        String username = sc.nextLine();
        System.out.print("\tStaff's Password: ");
        String password = sc.nextLine();

        String hashedPassword = hashPassword(password);

        String staffID = "";
        if (!conf.authenticateStaff(username, hashedPassword)) {
            System.out.println("\nAuthentication failed. Access denied.");
            return;
        } else {
            staffID = conf.getStaffID(username); 
            if (staffID == null || staffID.isEmpty()) {
                System.out.println("\nStaff ID not found for the authenticated username.");
                return;
            }
        }
        
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
        
        viewDentist();
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
        
        viewPatients();
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

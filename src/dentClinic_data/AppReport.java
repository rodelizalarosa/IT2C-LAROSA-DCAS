package dentClinic_data;

import dentClinic_data.PatientInfo;
import dentClinic_data.DoctorInfo;
import dentClinic_data.StaffInfo;
import dentClinic_data.Appointment;
import it2c.larosa.dcas.Config;
import java.util.Map;
import java.util.Scanner;

public class AppReport {
    
    public static void viewIndvAppointment() {
        Scanner sc = new Scanner(System.in);
        Config conf = new Config();
        System.out.print("Enter Appointment ID to view details: ");
        String appID = sc.next();

        // SQL query to retrieve appointment details and join related tables
        String query = "SELECT a.appID, a.doctorID, d.dFNAME AS doctorFirstName, d.dLNAME AS doctorLastName, " +
                       "a.staffID, s.sFNAME AS staffFirstName, s.sLNAME AS staffLastName, " +
                       "a.patientID, p.pFNAME AS patientFirstName, p.pLNAME AS patientLastName, " +
                       "a.appService, a.appDATE, a.appTIME, a.status " +
                       "FROM tbl_appointments a " +
                       "LEFT JOIN tbl_doctors d ON a.doctorID = d.dID " +
                       "LEFT JOIN tbl_staff s ON a.staffID = s.sID " +
                       "LEFT JOIN tbl_patients p ON a.patientID = p.pID " +
                       "WHERE a.appID = ?";

        // Fetch the record
        Map<String, String> result = conf.getRecordMap(query, appID);

        // Check if record is found
        if (result.isEmpty()) {
            System.out.println("No appointment found with the provided Appointment ID.");
            return;
        }

        // Display the formatted individual appointment details
        System.out.println("\nIndividual Appointment\n");

        System.out.printf("Doctor ID: %-20s Attending Doctor: %s %s\n", 
                          result.get("doctorID"), result.get("doctorFirstName"), result.get("doctorLastName"));

        System.out.printf("Staff ID: %-21s Assigned Staff: %s %s\n",
                          result.get("staffID"), result.get("staffFirstName"), result.get("staffLastName"));

        System.out.println("--------------------------------------------------------------------------------------------------");

        System.out.printf("Patient ID: %-19s First Name: %-20s Last Name: %s\n",
                          result.get("patientID"), result.get("patientFirstName"), result.get("patientLastName"));

        System.out.println("\n| Appointment ID      | Dental Service         | Date          | Time     | Status         |");
        System.out.println("|---------------------|------------------------|---------------|----------|----------------|");

        System.out.printf("| %-19s | %-22s | %-13s | %-8s | %-14s |\n", 
                          result.get("appID"), result.get("appService"), result.get("appDATE"), result.get("appTIME"), result.get("status"));
        }

}

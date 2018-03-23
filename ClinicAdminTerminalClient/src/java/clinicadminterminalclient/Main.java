/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadminterminalclient;

/**
 * Version 1.00
 * @author Yosafat
 */
public class Main {

    @EJB
    private static StaffEntityControllerRemote staffEntityControllerRemote;
    @EJB
    private static PatientEntityControllerRemote patientEntityControllerRemote;
    @EJB
    private static DoctorEntityControllerRemote doctorEntityControllerRemote;
    @EJB
    private static ConsultationEntityControllerRemote consultationEntityControllerRemote;
    @EJB
    private static AppointmentEntityControllerRemote appointmentEntityControllerRemote;

    public static void main(String[] args) {
        MainApp mainApp = new MainApp(staffEntityControllerRemote, patientEntityControllerRemote, doctorEntityControllerRemote, consultationEntityControllerRemote, appointmentEntityControllerRemote);
    	mainApp.runApp();
    }
}

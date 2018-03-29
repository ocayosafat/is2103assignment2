/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selfservicekioskclient;

import ejb.session.stateless.AppointmentEntityControllerRemote;
import ejb.session.stateless.ConsultationEntityControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import javax.ejb.EJB;
import util.exception.AppointmentNotFoundException;
import util.exception.ConsultationFullyBookedException;
import util.exception.DoctorNotFoundException;
import util.exception.PatientNotFoundException;

/**
 *
 * @author DELL
 */
public class Main {
    @EJB
    private static PatientEntityControllerRemote patientEntityControllerRemote;
    @EJB
    private static DoctorEntityControllerRemote doctorEntityControllerRemote;
    @EJB
    private static ConsultationEntityControllerRemote consultationEntityControllerRemote;
    @EJB
    private static AppointmentEntityControllerRemote appointmentEntityControllerRemote;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainApp mainApp = new MainApp(patientEntityControllerRemote, doctorEntityControllerRemote, consultationEntityControllerRemote, appointmentEntityControllerRemote);
        mainApp.runApp();
    }
    
}

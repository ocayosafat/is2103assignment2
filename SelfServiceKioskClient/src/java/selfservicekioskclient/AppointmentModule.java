/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selfservicekioskclient;

import ejb.session.stateless.AppointmentEntityControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import entity.PatientEntity;

/**
 *
 * @author DELL
 */
public class AppointmentModule {
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote;
    
    private PatientEntity currentPatientEntity;
    
    public AppointmentModule() {
    }
    
    public AppointmentModule(PatientEntityControllerRemote patientEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, AppointmentEntityControllerRemote appointmentEntityControllerRemote, PatientEntity currentPatientEntity) {
        this();
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.appointmentEntityControllerRemote = appointmentEntityControllerRemote;
        this.currentPatientEntity = currentPatientEntity;
    }
    
    public menuAppointment(int response) {
        if (response == 3) {
            doViewPatientAppointment();
        } else if (response == 4) {
            doCreateNewAppointment();
        } else if (response == 5) {
            doCancelAppointment();
        }
    }
    
    private void doViewPatientAppointment() {
    }
    
    private void doCreateNewAppointment() {
    }
    
    private void doCancelAppointment() {
    }
}

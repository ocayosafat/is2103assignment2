/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AppointmentEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import java.util.List;
import util.exception.AppointmentNotFoundException;

/**
 *
 * @author Aden
 */
public interface AppointmentEntityControllerRemote {
    AppointmentEntity createNewAppointment(AppointmentEntity newAppointmentEntity);
    List<AppointmentEntity> retrieveAppointment();
    AppointmentEntity retrieveAppointmentByAppointmentId(Long appointmentId) throws AppointmentNotFoundException;
    List<AppointmentEntity> retrieveAppointmentByPatient(PatientEntity patientEntity) throws AppointmentNotFoundException;
    void updateAppointment(AppointmentEntity appointmentEntity);
    void deleteAppointment(Long appointmentId);
    boolean isAvailableByDateTimeDoctor(String Date, String Time, DoctorEntity doctorEntity);
}

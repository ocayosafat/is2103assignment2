/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PatientEntity;
import java.util.List;
import util.exception.PatientNotFoundException;

/**
 *
 * @author Aden
 */
public interface DoctorEntityControllerRemote {
    
    PatientEntity createNewPatient(PatientEntity newPatientEntity);
    List<PatientEntity> retrieveAllPatients();
    PatientEntity retrievePatientByPatientId(Long PatientId) throws PatientNotFoundException;
    PatientEntity retrievePatientByPatientIdentityNumber(String identityNumber) throws PatientNotFoundException;
    void updatePatient(PatientEntity patientEntity);
    void deletePatient(String identityNumber) throws PatientNotFoundException;
    
}

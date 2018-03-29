/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DoctorEntity;
import java.util.List;
import util.exception.DoctorNotFoundException;

/**
 *
 * @author Aden
 */
public interface DoctorEntityControllerRemote {
    
    DoctorEntity createNewDoctor(DoctorEntity newDoctorEntity);
    List<DoctorEntity> retrieveAllDoctors();
    DoctorEntity retrieveDoctorByDoctorId(Long doctorId) throws DoctorNotFoundException;
    void updateDoctor(DoctorEntity doctorEntity);
    void deleteDoctor(Long doctorId) throws DoctorNotFoundException;
    
}

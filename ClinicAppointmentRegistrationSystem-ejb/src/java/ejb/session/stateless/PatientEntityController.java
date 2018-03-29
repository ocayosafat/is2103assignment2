/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PatientEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.PatientNotFoundException;

/**
 * Version 1.00
 * @author Aden
 */

@Stateless
@Local(PatientEntityControllerLocal.class)
@Remote(PatientEntityControllerRemote.class)

public class PatientEntityController implements PatientEntityControllerLocal, PatientEntityControllerRemote {
    
    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager em;
    
    public PatientEntityController() {
        
    }
    
    @Override
    public PatientEntity createNewPatient(PatientEntity newPatientEntity)
    {
        em.persist(newPatientEntity);
        em.flush();
//        em.refresh(newPatientEntity);
        
        return newPatientEntity;
    }
    
    @Override
    public List<PatientEntity> retrieveAllPatients()
    {
        Query query = em.createQuery("SELECT p FROM PatientEntity p");
        
        return query.getResultList();
    }
    
    @Override
    public PatientEntity retrievePatientByPatientId(Long patientId) throws PatientNotFoundException
    {
        PatientEntity patientEntity = em.find(PatientEntity.class, patientId);
        
        if(patientEntity != null)
        {
            return patientEntity;
        }
        else
        {
            throw new PatientNotFoundException("PatientID " + patientId + " does not exist!");
        }
    }
    
    @Override
    public PatientEntity retrievePatientByPatientIdentityNumber(String identityNumber) throws PatientNotFoundException
    {
        Query query = em.createQuery("SELECT p FROM PatientEntity p WHERE p.identityNumber LIKE :input")
        .setParameter("input", identityNumber);

        List<PatientEntity> target = query.getResultList();

        if ( target.isEmpty()) { //patient no found, invalid identity number
            throw new PatientNotFoundException("Patient Identity Number" + identityNumber + "does not exists!");
        }
        else {
            return target.get(0); //return PatientEntity to result
        }

    }
    
    @Override
    public void updatePatient(PatientEntity patientEntity)
    {
        em.merge(patientEntity);
//        em.refresh(patientEntity);
    }
    
    
    @Override
    public void deletePatient(String identityNumber) throws PatientNotFoundException
    {
        PatientEntity patientEntityToRemove = retrievePatientByPatientIdentityNumber(identityNumber);
        em.remove(patientEntityToRemove);
//        em.refresh(patientEntityToRemove);
    }
    
    
}

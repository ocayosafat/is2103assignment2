/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PatientEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Version 1.00
 * @author Aden
 */

@Stateless
@Local(PatientEntityControllerLocal.class)
@Remote(PatientEntityControllerRemote.class)

public class PatientEntityController implements PatientEntityControllerLocal, PatientEntityControllerRemote {
    
    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem")
    private EntityManager em;
    
    public PatientEntityController() {
        
    }
    
    @Override
    public PatientEntity createNewPatient(PatientEntity newPatientEntity)
    {
        em.persist(newPatientEntity);
        em.flush();
        
        return newPatientEntity;
    }
    
    @Override
    public List<PatientEntity> retrieveAllPatients()
    {
        Query query = em.createQuery("SELECT p FROM PatientEntity p");
        
        return query.getResultList();
    }
    
    @Override
    public PatientEntity retrievePatientByPatientId(Long PatientId) throws PatientNotFoundException
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
        PatientEntity patientEntity = em.find(PatientEntity.class, identityNumber);
        
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
    public void updatePatient(PatientEntity patientEntity)
    {
        em.merge(patientEntity);
    }
    
    
    @Override
    public void deletePatient(String identityNumber) throws PatientNotFoundException
    {
        PatientEntity patientEntityToRemove = retrievePatientByPatientIdentityNumber(identityNumber);
        em.remove(patientEntityToRemove);
    }
    
    
}

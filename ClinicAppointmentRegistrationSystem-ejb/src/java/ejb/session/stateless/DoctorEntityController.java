/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DoctorEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.DoctorNotFoundException;

/**
 * Version: 1.00
 * @author Aden
 */
@Stateless
@Local(DoctorEntityControllerLocal.class)
@Remote(DoctorEntityControllerRemote.class)

public class DoctorEntityController implements DoctorEntityControllerLocal, DoctorEntityControllerRemote {
    
    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager em;
    
    public DoctorEntityController() {
        
    }
    
    @Override
    public DoctorEntity createNewDoctor(DoctorEntity newDoctorEntity)
    {
        em.persist(newDoctorEntity);
        em.flush();
//        em.refresh(newDoctorEntity);
        
        return newDoctorEntity;
    }
    
    @Override
    public List<DoctorEntity> retrieveAllDoctors()
    {
        Query query = em.createQuery("SELECT d FROM DoctorEntity d");
        
        return query.getResultList();
    }
    
    @Override
    public DoctorEntity retrieveDoctorByDoctorId(Long doctorId) throws DoctorNotFoundException
    {
        DoctorEntity doctorEntity = em.find(DoctorEntity.class, doctorId);
        
        if(doctorEntity != null)
        {
            return doctorEntity;
        }
        else
        {
            throw new DoctorNotFoundException("DoctorID " + doctorId + " does not exist!");
        }
    }
        
    @Override
    public void updateDoctor(DoctorEntity doctorEntity)
    {
        em.merge(doctorEntity);
    }
    
    
    @Override
    public void deleteDoctor(Long doctorId) throws DoctorNotFoundException
    {
        DoctorEntity doctorEntityToRemove = retrieveDoctorByDoctorId(doctorId);
        em.remove(doctorEntityToRemove);
//        em.refresh(doctorEntityToRemove);
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.AppointmentEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import util.exception.AppointmentNotFoundException;

/**
 * Version 1.02
 * @author Yk
 * Implemented isAvaliableByDateTimeDoctor
 * Implemented retrieveAppointmentByPatient
 * Need to verify functionality
 * 
 * * Delete appointment does not affect doctor or patient
 */



@Stateless
@Local(AppointmentEntityControllerLocal.class)
@Remote(AppointmentEntityControllerRemote.class)

public class AppointmentEntityController implements AppointmentEntityControllerLocal, AppointmentEntityControllerRemote
{
    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSytem-ejbPU")
    private EntityManager em;

    
    
    
    public AppointmentEntityController()
    {

    }
    
    

    @Override
    public AppointmentEntity createNewAppointment(AppointmentEntity newAppointmentEntity) 
    {
        em.persist(newAppointmentEntity);
        em.flush();
        
        return newAppointmentEntity;
    }
    

    @Override
    public List<AppointmentEntity> retrieveAppointment()
    {
        Query query = em.createQuery("SELECT a FROM AppointmentEntity a");
        
        return query.getResultList();
    }
    
    
    @Override
    public AppointmentEntity retrieveAppointmentByAppointmentId(Long appointmentId) throws AppointmentNotFoundException
    {
        AppointmentEntity appointmentEntity = em.find(AppointmentEntity.class, appointmentId);
        
        if(appointmentEntity != null)
        {
            return appointmentEntity;
        }
        else
        {
            throw new AppointmentNotFoundException("AppointmentID " + appointmentId + " does not exist!");
        }
    }
    

    @Override
    public List<AppointmentEntity> retrieveAppointmentByPatient(PatientEntity patientEntity) throws AppointmentNotFoundException
    {
        Query query = em.createQuery("SELECT a FROM AppointmentEntity a WHERE a.patiententity = :inPatientenity");
        query.setParameter("inPatientenity", patientEntity);
      
            return query.getResultList();
       
    }
    
    
    @Override
    public void updateAppointment(AppointmentEntity appointmentEntity)
    {
        em.merge(appointmentEntity);
    }
    
    
    
    @Override
    public void deleteAppointment(Long appointmentId)
    {
        AppointmentEntity appointmentEntityToRemove;
        try {
            appointmentEntityToRemove = retrieveAppointmentByAppointmentId(appointmentId);
            em.remove(appointmentEntityToRemove);
        } catch (AppointmentNotFoundException ex) {
            Logger.getLogger(AppointmentEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Override
    public boolean isAvailableByDateTimeDoctor(String Date, String Time, DoctorEntity doctorEntity) {
      
        //query appointment
        
        
        Query query = em.createQuery("SELECT a FROM AppointmentEntity a WHERE a.doctorentity = :inDoctorentity AND a.date = :inDate AND a.time = :inTime");
        query.setParameter("inDoctorentity", doctorEntity);
        query.setParameter("inDate", Date);
        query.setParameter("inTime", Time);
        
        
        
        try {
            //Uncertain, need to confirm functionality***************************
            AppointmentEntity taken = (AppointmentEntity) query.getSingleResult();
            if(taken == null) {
                return true;
            }
            else {
                return false;
            }
       
        }
        catch(NoResultException | NonUniqueResultException ex) {
            return  true;
        }
        
        
        
  
    }

    
    

}

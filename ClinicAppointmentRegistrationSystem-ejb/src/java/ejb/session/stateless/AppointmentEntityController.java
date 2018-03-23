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

/**
 * Version 1.0
 * @author Yk
 * Delete appointment does not affect doctor or patient
 */



@Stateless
@Local(AppointmentEntityController.class)
@Remote(AppointmentEntityControllerRemote.class)

public class AppointmentEntityController implements AppointmentEntityControllerLocal, AppointmentEntityControllerRemote
{
    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSytem-ejbPU")
    private javax.persistence.EntityManager entityManager;
    @Resource
    private EJBContext eJBContext;
    
    @EJB
    private ConsultationEntityControllerLocal consultationEntityControllerLocal;
    
    
    
    public AppointmentEntityController()
    {
        entityManager = new EntityManager();
    }
    
    

    @Override
    public AppointmentEntity createNewAppointment(AppointmentEntity newAppointmentEntity) 
    {
        entityManager.persist(newAppointmentEntity);
        entityManager.flush();
        
        return newAppointmentEntity;
    }
    

    @Override
    public List<AppointmentEntity> retrieveAppointment()
    {
        Query query = entityManager.createQuery("SELECT a FROM AppointmentEntity a");
        
        return query.getResultList();
    }
    
    
    @Override
    public AppointmentEntity retrieveAppointmentByAppointmentId(Long appointmentId) throws AppointmentNotFoundException
    {
        AppointmentEntity appointmentEntity = entityManager.find(AppointmentEntity.class, appointmentId);
        
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
    public void updateAppointment(AppointmentEntity appointmentEntity)
    {
        entityManager.merge(appointmentEntity);
    }
    
    
    
    @Override
    public void deleteAppointment(Long appointmentId)
    {
        AppointmentEntity appointmentEntityToRemove =retrieveAppointmentByAppointmentId(appointmentId);
        entityManager.remove(appointmentEntityToRemove);
    }

    
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;


import javax.persistence.EntityManager;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.ConsultationEntity;
import entity.DoctorEntity;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import util.exception.ConsultationNotFoundException;


/**
 * Version 1.01
 *  
 * @author Yk
 */

@Stateless
@Local(ConsultationEntityControllerLocal.class)
@Remote(ConsultationEntityControllerRemote.class)

public class ConsultationEntityController implements ConsultationEntityControllerLocal, ConsultationEntityControllerRemote
{
    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager em;

    
    
    public ConsultationEntityController()
    {

    }
    
    
    

    @Override
    public ConsultationEntity createNewConsultation(ConsultationEntity newConsultationEntity) 
    {
        em.persist(newConsultationEntity);
        em.flush();
        
        return newConsultationEntity;
    }
    

    @Override
    public List<ConsultationEntity> retrieveAllConsultation()
    {
        Query query = em.createQuery("SELECT c FROM ConsultationEntity c");
        
        return query.getResultList();
    }
    
    
    @Override
    public ConsultationEntity retrieveConsultationByConsultationId(Long consultationId) throws ConsultationNotFoundException
    {
        ConsultationEntity consultationEntity = em.find(ConsultationEntity.class, consultationId);
        
        if(consultationEntity != null)
        {
            return consultationEntity;
        }
        else
        {
            throw new ConsultationNotFoundException("Consultation ID " + consultationId + " does not exist!");
        }
    }

    @Override
    public  List<ConsultationEntity> retrieveAllConsultationThisDateInDescOrder(String date) {
        return em.createQuery(
            "SELECT c FROM ConsultationEntity c WHERE c.date LIKE :curDate ORDER BY c.queueNumber DESC")
            .setParameter("curDate", date)
            .getResultList();
    }
    
    @Override
    public void updateConsultation(ConsultationEntity consultationEntity)
    {
        em.merge(consultationEntity);
    }
    
    
    
    @Override
    public void deleteConsultation(Long consultationId) throws ConsultationNotFoundException
    {
 
            ConsultationEntity consultationEntityToRemove =retrieveConsultationByConsultationId(consultationId);
            em.remove(consultationEntityToRemove);
 
    }
    
    
    
    @Override
    public boolean isAvailableByDateTimeDoctor(String Date, String Time, DoctorEntity doctorEntity) {
      
        //query appointment   
        Query query = em.createQuery("SELECT a FROM ConsultationEntity a WHERE a.doctorentity = :inDoctorentity AND a.date = :inDate AND a.time = :inTime");
        query.setParameter("inDoctorentity", doctorEntity);
        query.setParameter("inDate", Date);
        query.setParameter("inTime", Time);
        
        
        
        try {
            //Uncertain, need to confirm functionality***************************
            ConsultationEntity taken = (ConsultationEntity) query.getSingleResult();
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

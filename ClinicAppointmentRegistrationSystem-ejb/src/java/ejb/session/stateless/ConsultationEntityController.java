/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;


import javax.persistence.EntityManager;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.ConsultationEntity;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import util.exception.EntityManagerException;


/**
 * Verson 1.0
 *  
 * @author Yk
 */

@Stateless
@Local(ConsultationEntityController.class)
@Remote(ConsultationEntityControllerRemote.class)

public class ConsultationEntityController implements ConsultationEntityControllerLocal, ConsultationEntityControllerRemote
{
    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSytem-ejbPU")
    private javax.persistence.EntityManager entityManager;
    @Resource
    private EJBContext eJBContext;
    
    @EJB
    private ConsultationEntityControllerLocal consultationEntityControllerLocal;
    
    
    
    public ConsultationEntityController()
    {
        entityManager = new EntityManager();
    }
    
    
    @Override
    public ConsultationEntity createNewQueue(ConsultationEntity newConsultationEntity) throws EntityManagerException {
        return (ConsultationEntity)entityManager.create(newConsultationEntity); //need to revise this
    }    
    

    @Override
    public ConsultationEntity createNewConsultation(ConsultationEntity newConsultationEntity) 
    {
        entityManager.persist(newConsultationEntity);
        entityManager.flush();
        
        return newConsultationEntity;
    }
    

    @Override
    public List<ConsultationEntity> retrieveAllConsultation()
    {
        Query query = entityManager.createQuery("SELECT c FROM ConsultationEntity c");
        
        return query.getResultList();
    }
    
    
    @Override
    public ConsultationEntity retrieveConsultationByConsultationId(Long consultationId) throws ConsultationNotFoundException
    {
        ConsultationEntity consultationEntity = entityManager.find(ConsultationEntity.class, consultationId);
        
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
        return entityManager.createQuery(
            "SELECT c FROM ConsultationEntity c WHERE c.date LIKE :curDate ORDER BY c.queueNumber DESC")
            .setParameter("curDate", date)
            .getResultList();
    }
    
    @Override
    public void updateConsultation(ConsultationEntity consultationEntity)
    {
        entityManager.merge(consultationEntity);
    }
    
    
    
    @Override
    public void deleteConsultation(Long consultationId)
    {
        ConsultationEntity consultationEntityToRemove =retrieveConsultationByConsultationId(consultationId);
        entityManager.remove(consultationEntityToRemove);
    }
    
    
    

}

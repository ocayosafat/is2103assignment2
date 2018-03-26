/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ConsultationEntity;
import java.util.List;
import util.exception.ConsultationNotFoundException;

/**
 *
 * @author Aden
 */
public interface ConsultationEntityControllerRemote {
    
    ConsultationEntity createNewQueue(ConsultationEntity newConsultationEntity);
    ConsultationEntity createNewConsultation(ConsultationEntity newConsultationEntity);
    List<ConsultationEntity> retrieveAllConsultation();
    ConsultationEntity retrieveConsultationByConsultationId(Long consultationId) throws ConsultationNotFoundException;
    List<ConsultationEntity> retrieveAllConsultationThisDateInDescOrder(String date);
    void updateConsultation(ConsultationEntity consultationEntity);
    void deleteConsultation(Long consultationId);
    
}

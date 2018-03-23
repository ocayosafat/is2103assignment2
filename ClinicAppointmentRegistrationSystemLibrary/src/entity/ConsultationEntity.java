/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Version: 1.00
 * @author Aden
 */
public class ConsultationEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultationId;
    @Column(nullable = false)
    private Long patientId;
    @Column(nullable = false)
    private Long doctorId;
    @Column(nullable = false)
    private Long queueNumber;
    @Column(nullable = false,length = 10)
    private String date;
    @Column(nullable = false, length = 5)
    private String time;

    public ConsultationEntity() {
        
    }

    public ConsultationEntity(Long patientId, Long doctorId, Long queueNumber, String date, String time) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.queueNumber = queueNumber;
        this.date = date;
        this.time = time;
    }


    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (this.consultationId != null ? this.consultationId.hashCode() : 0);
        
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof DoctorEntity)) 
        {
            return false;
        }
        
        ConsultationEntity other = (ConsultationEntity) object;
        
        if ((this.consultationId == null && other.consultationId != null) || (this.consultationId != null && !this.consultationId.equals(other.consultationId))) 
        {
            return false;
        }
        
        return true;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }



    public Long getQueueNumber() {
        return queueNumber;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }


    public void setQueueNumber(Long queueNumber) {
        this.queueNumber = queueNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    
}

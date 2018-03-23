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
    @ManyToOne
    private DoctorEntity doctor;
    @ManyToOne
    private PatientEntity patient;
    @Column(nullable = false)
    private Long queueNumber;
    @Column(nullable = false,length = 10)
    private String date;
    @Column(nullable = false, length = 5)
    private String time;

    public ConsultationEntity() {
        
    }

    public ConsultationEntity(DoctorEntity doctor, PatientEntity patient, Long queueNumber, String date, String time) {
        this.doctor = doctor;
        this.patient = patient;
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

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public PatientEntity getPatient() {
        return patient;
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

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
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
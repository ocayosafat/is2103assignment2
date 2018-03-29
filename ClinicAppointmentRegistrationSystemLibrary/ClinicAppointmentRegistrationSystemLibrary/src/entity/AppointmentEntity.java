
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Version 1.00
 * @author Yosafat
 */
@Entity
public class AppointmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    @ManyToOne
    private PatientEntity patient;
    @ManyToOne
    private DoctorEntity doctor;
    @Column(nullable = false, length = 10)
    private String date;
    @Column(nullable = false, length = 5)
    private String time;

    public AppointmentEntity() {
    }

    public AppointmentEntity(PatientEntity patient, DoctorEntity doctor, String date, String time) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.appointmentId != null ? this.appointmentId.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AppointmentEntity)) {
            return false;
        }

        AppointmentEntity other = (AppointmentEntity) object;

        if ((this.appointmentId == null && other.appointmentId != null) || (this.appointmentId != null && !this.appointmentId.equals(other.appointmentId))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "entity.AppointmentEntity[ appointmentId=" + this.appointmentId + " ]";
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    

}

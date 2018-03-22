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

/**
 * Version: 1.00
 * @author Aden
 */
public class DoctorEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    @Column (nullable = false)
    private String firstName;
    @Column (nullable = false)
    private String lastName;
    @Column (nullable = false)
    private String registration;
    @Column (nullable = false)
    private String qualifications;

    public DoctorEntity() {
    }

    public DoctorEntity(String firstName, String lastName, String registration, String qualifications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registration = registration;
        this.qualifications = qualifications;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (this.doctorId != null ? this.doctorId.hashCode() : 0);
        
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof DoctorEntity)) 
        {
            return false;
        }
        
        DoctorEntity other = (DoctorEntity) object;
        
        if ((this.doctorId == null && other.doctorId != null) || (this.doctorId != null && !this.doctorId.equals(other.doctorId))) 
        {
            return false;
        }
        
        return true;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRegistration() {
        return registration;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }
    
}





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



@Entity

public class PatientEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    @Column(length = 9, nullable = false, unique = true)
    private String identityNumber;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private long age;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String securityCode;
    
    @OneToMany
    private ConsultationEntity consultationEntity;

    @OneToMany
    private AppointmentEntity appointmentEntity;
    
    public PatientEntity()
    {
    }

    public PatientEntity(String identityNumber, String firstName, String lastName, String gender, long age, String phone, String address, String securityCode) {
        this.identityNumber = identityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.securityCode = securityCode;
    }



    
 
    
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (this.patientId != null ? this.patientId.hashCode() : 0);
        
        return hash;
    }

    
    
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof PatientEntity)) 
        {
            return false;
        }
        
        PatientEntity other = (PatientEntity) object;
        
        if ((this.patientId == null && other.patientId != null) || (this.patientId != null && !this.patientId.equals(other.patientId))) 
        {
            return false;
        }
        
        return true;
    }

    
    
    @Override
    public String toString() 
    {
        return "entity.PatientEntity[ patientId=" + this.patientId + " ]";
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    
    
    public ConsultationEntity getConsultationEntity() {
        return consultationEntity;
    }

    public void setConsultationEntity(ConsultationEntity consultationEntity) {
        this.consultationEntity = consultationEntity;
    }

    public AppointmentEntity getAppointmentEntity() {
        return appointmentEntity;
    }

    public void setAppointmentEntity(AppointmentEntity appointmentEntity) {
        this.appointmentEntity = appointmentEntity;
    }

    

}
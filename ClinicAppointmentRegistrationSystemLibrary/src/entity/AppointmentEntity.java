/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 * Version 1.01
 * @author Yosafat
 */
public class AppointmentEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    @ManyToOne
    private Long patientId;
    @ManyToOne
    private Long doctorId;
    @Column(nullable = false, length = 10)
    private String date;
    @Column(nullable = false, length = 5)
    private String time;

    public AppointmentEntity() {
    }

    public AppointmentEntity(Long patientId, Long doctorId, String date, String time) {
        this.patientId = patientId;
        this.doctorId = doctorId;
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

    public Long getPatientId() {
        return patientId;
    }

    public Long getDoctorId() {
        return doctor;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    

}

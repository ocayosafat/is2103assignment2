/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selfservicekioskclient;

import ejb.session.stateless.AppointmentEntityControllerRemote;
import ejb.session.stateless.ConsultationEntityControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import entity.AppointmentEntity;
import entity.ConsultationEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import util.exception.AppointmentNotFoundException;
import util.exception.ConsultationFullyBookedException;
import util.exception.DoctorNotFoundException;

/**
 *
 * @author DELL
 */
public class RegistrationModule {
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private ConsultationEntityControllerRemote consultationEntityControllerRemote;
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote;
    
    private PatientEntity currentPatientEntity;
    
    
    private String[] workTime = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
        "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
        "15:00", "15:30", "16:00", "16:30"};
    
    public RegistrationModule() {
    }
    
    public RegistrationModule(PatientEntityControllerRemote patientEntityControllerRemote) {
        this();
        this.patientEntityControllerRemote = patientEntityControllerRemote;
    }
    
    public RegistrationModule(PatientEntityControllerRemote patientEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, ConsultationEntityControllerRemote consultationEntityControllerRemote, AppointmentEntityControllerRemote appointmentEntityControllerRemote, PatientEntity currentPatientEntity) {
        this();
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.consultationEntityControllerRemote = consultationEntityControllerRemote;
        this.appointmentEntityControllerRemote = appointmentEntityControllerRemote;
        this.currentPatientEntity = currentPatientEntity;
    }
    
    public void menuRegistration(int response) throws ConsultationFullyBookedException {
        
        if (response == 1) { // Register Walk-in Consultation
            doCreateNewWalkInConsultation();
        } else if (response == 2) { // Register Consultation By Appointment
            doCreateNewAppointmentConsultation();
        }
    }
    
    public void doCreateNewPatient() {
        Scanner scanner = new Scanner(System.in);
        PatientEntity newPatientEntity = new PatientEntity();
        
        System.out.println("*** Self-Service Kiosk :: Register ***\n");
        System.out.print("Enter Identity Number> ");
        newPatientEntity.setIdentityNumber(scanner.nextLine().trim());
        System.out.print("Enter Security Code> ");
        newPatientEntity.setSecurityCode(scanner.nextLong());
        scanner.nextLine();
        System.out.print("Enter First Name> ");
        newPatientEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newPatientEntity.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Gender> ");
        newPatientEntity.setGender(scanner.nextLine().trim());
        System.out.print("Enter Age> ");
        newPatientEntity.setAge(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Enter Phone> ");
        newPatientEntity.setPhone(scanner.nextLine().trim());
        System.out.print("Enter Address> ");
        newPatientEntity.setAddress(scanner.nextLine().trim());
        
        newPatientEntity = patientEntityControllerRemote.createNewPatient(newPatientEntity);
        System.out.println("You have been registered successfully!\n");        
    }
    
    private void doCreateNewWalkInConsultation() throws ConsultationFullyBookedException {
        Scanner scanner = new Scanner(System.in);
        ConsultationEntity newConsultationEntity = new ConsultationEntity();
        
        System.out.println("*** Self-Service Kiosk :: Register Walk-In Consultation ***\n");
        System.out.println("Doctor:");
        System.out.println("Id |Name");
        
        List<DoctorEntity> allDoctors = doctorEntityControllerRemote.retrieveAllDoctors();
        for (DoctorEntity doctor: allDoctors) {
            System.out.println(doctor.getDoctorId() + "  |" + doctor.getFirstName() + " " + doctor.getLastName());
        }
        System.out.println("\nAvailability:");
        System.out.print("Time  |");
        for (DoctorEntity doctor : allDoctors) {
            System.out.print(doctor.getDoctorId() + " |");
        }
        System.out.print("\n");
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String[] dateTime = dtf.format(now).split(" ");
        String roundedTime = roundOff(dateTime[1]);
        
        // if it is 13:00, then consultation is from 13:30 onwards
        int index = 0;
        while (!workTime[index].equals(roundedTime)) {
            index++;
        }
        index++;
        int startAppointmentIndex = index;
        
        StringBuilder[] doctorAvailability = new StringBuilder[allDoctors.size()];
        
        for (int i = 0; i < doctorAvailability.length; i++) {
            doctorAvailability[i] = new StringBuilder("");
        }
        
        for (int j = 0; j < 6 && index < workTime.length; j++, index++) {
            System.out.print(workTime[index] + " |"); // error from here onwards.
            int doctorId = 1;
            for (DoctorEntity doctor : allDoctors) {
                boolean available = appointmentEntityControllerRemote.isAvailableByDateTimeDoctor(dateTime[0], workTime[index], doctor)
                        && consultationEntityControllerRemote.isAvailableByDateTimeDoctor(dateTime[0], workTime[index], doctor);

                if (available) {
                    System.out.print("O |");
                    doctorAvailability[doctorId - 1].append("O");
                } else {
                    System.out.print("X |");
                    doctorAvailability[doctorId - 1].append("X");
                }
                doctorId++;
            }
            System.out.print("\n");
        }
        System.out.print("\n");
        
        try {
            System.out.print("Enter Doctor Id> ");
            Long consultationDoctorId = scanner.nextLong();
            scanner.nextLine();
            DoctorEntity consultationDoctor = doctorEntityControllerRemote.retrieveDoctorByDoctorId(consultationDoctorId);
            newConsultationEntity.setDoctor(consultationDoctor);
            newConsultationEntity.setPatient(currentPatientEntity);
            newConsultationEntity.setDate(dateTime[0]);
            
            int availableIndex = doctorAvailability[consultationDoctorId.intValue() - 1].indexOf("O");
            if (availableIndex == -1) {
                throw new ConsultationFullyBookedException("There is no available consultation slot for this doctor currently.");
            }
            newConsultationEntity.setTime(workTime[availableIndex + startAppointmentIndex]);
            
            List<ConsultationEntity> allCurrConsultation = consultationEntityControllerRemote.retrieveAllConsultationThisDateInDescOrder(dateTime[0]);
            
            long queue = Long.valueOf(allCurrConsultation.size() + 1);
            newConsultationEntity.setQueueNumber(queue);
            
            newConsultationEntity = consultationEntityControllerRemote.createNewConsultation(newConsultationEntity);
            System.out.println(newConsultationEntity.getDoctor().getFirstName() + " " + newConsultationEntity.getDoctor().getLastName() + " is going to see " + newConsultationEntity.getPatient().getFirstName() + " " + newConsultationEntity.getPatient().getLastName() + " at " + newConsultationEntity.getTime() + ". Queue Number is: " + newConsultationEntity.getQueueNumber() + ".\n");
        }
        catch (DoctorNotFoundException ex) {
            System.out.println("An error has occured\n");
        }
    }
    
    private void doCreateNewAppointmentConsultation() {
        Scanner scanner = new Scanner(System.in);
        ConsultationEntity newConsultationEntity = new ConsultationEntity();

        try {
            System.out.println("*** Self-Service Kiosk :: Register Consultation By Appointment ***\n");

            System.out.print("\n");
            System.out.println("Appointments:");
            System.out.println("Id |Date       |Time  |Doctor");

            List<AppointmentEntity> appointments = appointmentEntityControllerRemote.retrieveAppointmentByPatient(currentPatientEntity);
            for (AppointmentEntity app : appointments) {
                System.out.println(app.getAppointmentId() + " |" + app.getDate() + " |" + app.getTime() + " |" + app.getDoctor().getFirstName() + " " + app.getDoctor().getLastName());
            }
            System.out.print("\n");

            System.out.print("Enter Appointment Id> ");
            Long appointmentId = scanner.nextLong();
            AppointmentEntity movedAppointment = appointmentEntityControllerRemote.retrieveAppointmentByAppointmentId(appointmentId);
            
            newConsultationEntity.setDoctor(movedAppointment.getDoctor());
            newConsultationEntity.setPatient(movedAppointment.getPatient());
            newConsultationEntity.setTime(movedAppointment.getTime());
            newConsultationEntity.setDate(movedAppointment.getDate());

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime now = LocalDateTime.now();
            String[] dateTime = dtf.format(now).split(" ");
        
            List<ConsultationEntity> allCurrConsultation = consultationEntityControllerRemote.retrieveAllConsultationThisDateInDescOrder(dateTime[0]);

            Long queue = Long.valueOf(allCurrConsultation.size() + 1);
            newConsultationEntity.setQueueNumber(queue);

            newConsultationEntity = consultationEntityControllerRemote.createNewConsultation(newConsultationEntity);
            appointmentEntityControllerRemote.deleteAppointment(appointmentId);
            System.out.println(newConsultationEntity.getPatient().getFirstName() + " " + newConsultationEntity.getPatient().getLastName()
                    + " is going to see " + newConsultationEntity.getDoctor().getFirstName() + " " + newConsultationEntity.getDoctor().getLastName()
                    + " at " + newConsultationEntity.getTime() + ". Queue Number is: " + newConsultationEntity.getQueueNumber() + ".\n");
        } catch (AppointmentNotFoundException ex) {
            System.out.println("An error has occured\n");
        }        
    }
    
    
    private String roundOff(String time) {
        String[] hourMin = time.split(":");
        int min = Integer.parseInt(hourMin[1]);
    	if (min < 30) {
            return hourMin[0] + ":" + "00";
    	}
    	return hourMin[0] + ":" + "30";
    }
}

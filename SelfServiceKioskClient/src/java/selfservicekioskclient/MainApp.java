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
import entity.PatientEntity;
import java.util.Scanner;
import util.exception.InvalidLoginException;

/**
 *
 * @author DELL
 */
public class MainApp {
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private ConsultationEntityControllerRemote consultationEntityControllerRemote;
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote;
    
    private AppointmentModule appointmentModule;
    private RegistrationModule registrationModule;
    
    private PatientEntity currentPatientEntity;
    
    public MainApp() {
    }
    
    public MainApp(PatientEntityControllerRemote patientEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, ConsultationEntityControllerRemote consultationEntityControllerRemote, AppointmentEntityControllerRemote appointmentEntityCotrollerRemote) {
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.consultationEntityControllerRemote = consultationEntityControllerRemote;
        this.appointmentEntityControllerRemote = appointmentEntityControllerRemote;
    }
    
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
 	Integer response = 0;
        
        while (true) {
            System.out.println("*** Welcome to Self-Service Kiosk ***\n");
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit\n");
            response = 0;
            
            while (response < 1 || response > 3) {
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    registrationModule = new RegistrationModule(patientEntityControllerRemote);
                    registrationModule.doCreateNewPatient();
                } else if (response == 2) {
                    try {
                        doLogin();
                        appointmentModule = new AppointmentModule(patientEntityControllerRemote, doctorEntityControllerRemote, appointmentEntityControllerRemote, currentPatientEntity);
 			registrationModule = new RegistrationModule(patientEntityControllerRemote, doctorEntityControllerRemote, consultationEntityControllerRemote, appointmentEntityControllerRemote, currentPatientEntity);
                        menuMain();
                    }
                    catch(InvalidLoginException ex) {
                    }
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 3) {
                break;
            }
        }
    }
    
    private void doLogin() throws InvalidLoginException {
        Scanner scanner = new Scanner(System.in);
        String identityNumber = "";
 	String securityCode = "";
        
        System.out.println("*** Self-Service Kiosk :: Login ***\n");
        System.out.print("Enter Identity Number> ");
        identityNumber = scanner.nextLine().trim();
        System.out.print("Enter Security Code> ");
        securityCode = scanner.nextLine().trim();
        
        if(identityNumber.length() > 0 && securityCode.length() > 0) {
            try {
                currentPatientEntity = patientEntityControllerRemote.patientLogin(identityNumber, securityCode);
                System.out.println("Login successful!\n");
            }
            catch (InvalidLoginException ex) {
                System.out.println("Invalid login: " + ex.getMessage() + "\n");
                throw new InvalidLoginException();
            }
        } else {
            System.out.println("Invalid login!");
        }
    }
    
    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
 	Integer response = 0;
        
        while(true) {
            System.out.println("*** Self-Service Kiosk :: Main ***\n");
            System.out.print("You are login as " + currentPatientEntity.getFirstname() + " " + currentPatientEntity.getLastName() + "\n");
            System.out.println("1: Register Walk-In Consultation");
            System.out.println("2: Register Consultation By Appointment");
            System.out.println("3: View Appointments");
            System.out.println("4: Add Appointments");
            System.out.println("5: Cancel Appointment");
            System.out.println("6: Logout\n");
            response = 0;
            
            while (response < 1 || response > 6) {
                System.out.print("> ");
                response = scanner.nextInt();
                
                if (response == 1) {
                    registrationModule.menuRegistration(response);
                } else if (response == 2) {
                    registrationModule.menuRegistration(response);
                } else if (response == 3) {
                    appointmentModule.menuAppointment(response);
                } else if (response == 4) {
                    appointmentModule.menuAppointment(response);
                } else if (response == 5) {
                    appointmentModule.menuAppointment(response);
                } else if (response == 6) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 6) {
                break;
            }
        }
    }
    
}

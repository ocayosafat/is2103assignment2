/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadminterminalclient;

import ejb.session.stateless.AppointmentEntityControllerRemote;
import ejb.session.stateless.ConsultationEntityControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import entity.StaffEntity;
import java.util.Scanner;
import util.exception.*;

/**
 * Version 1.00
 * @author Yosafat
 */
public class MainApp {
    private StaffEntityControllerRemote staffEntityControllerRemote;
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private ConsultationEntityControllerRemote consultationEntityControllerRemote;
    private AppointmentEntityControllerRemote appointmentEntityCotrollerRemote;

 	private AdministrationModule administrationModule;
 	private AppointmentModule appointmentModule;
 	private RegistrationModule registrationModule;

 	private StaffEntity currentStaffEntity;

 	public MainApp() {
 	}

 	public MainApp(StaffEntityControllerRemote staffEntityControllerRemote, PatientEntityControllerRemote patientEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, ConsultationEntityControllerRemote consultationEntityControllerRemote, AppointmentEntityControllerRemote appointmentEntityCotrollerRemote) {
 		this.staffEntityControllerRemote = staffEntityControllerRemote;
 		this.patientEntityControllerRemote = patientEntityControllerRemote;
 		this.doctorEntityControllerRemote = doctorEntityControllerRemote;
 		this.consultationEntityControllerRemote = consultationEntityControllerRemote;
 		this.appointmentEntityCotrollerRemote = appointmentEntityCotrollerRemote;
 	}

 	public void runApp() throws AppointmentNotFoundException, DoctorNotFoundException, StaffNotFoundException, PatientNotFoundException, ConsultationFullyBookedException {
 		Scanner scanner = new Scanner(System.in);
 		Integer response = 0;

 		while(true) {
 			System.out.println("*** Welcome to Clinic Appointment Registration System (CARS ***\n");
 			System.out.println("1: Login");
 			System.out.println("2: Exit\n");
 			response = 0;

 			while (response < 1 || response > 2) {
 				System.out.print("> ");
 				response = scanner.nextInt();
 				if (response == 1) {
 					try {
 						doLogin();
 						administrationModule = new AdministrationModule(patientEntityControllerRemote, doctorEntityControllerRemote, staffEntityControllerRemote);
 						appointmentModule = new AppointmentModule(patientEntityControllerRemote, doctorEntityControllerRemote, appointmentEntityCotrollerRemote); // parameters still need to be added
 						registrationModule = new RegistrationModule(patientEntityControllerRemote, doctorEntityControllerRemote, consultationEntityControllerRemote, appointmentEntityCotrollerRemote);
 						menuMain();
 					}
 					catch(InvalidLoginException ex) {
 					}
 				} else if (response == 2) {
 					break;
 				} else {
 					System.out.println("Invalid option, please try again!\n");
 				}
 			}
 			if (response == 2) {
 				break;
 			}
 		}
 	}

 	private void doLogin() throws InvalidLoginException {
 		Scanner scanner = new Scanner(System.in);
 		String username = "";
 		String password = "";

 		System.out.println("*** CARS :: Login ***\n");
 		System.out.println("Enter username> ");
 		username = scanner.nextLine().trim();
 		System.out.print("Enter password> ");
 		password = scanner.nextLine().trim();

 		if(username.length() > 0 && password.length() > 0) {
 			try {
 				currentStaffEntity = staffEntityControllerRemote.staffLogin(username, password);
 				System.out.println("Login successful!\n");
 			} catch (InvalidLoginException ex) {
 				System.out.println("Invalid login: " + ex.getMessage() + "\n");			
 				throw new InvalidLoginException();
 			}
 		} else {
 			System.out.println("Invalid login!");
 		}
 	}

 	private void menuMain() throws AppointmentNotFoundException, DoctorNotFoundException, StaffNotFoundException, PatientNotFoundException, ConsultationFullyBookedException{
 		Scanner scanner = new Scanner(System.in);
 		Integer response = 0;

 		while(true) {
 			System.out.println("*** CARS :: Main ***\n");
 			System.out.println("You are login as " + currentStaffEntity.getFirstName() + " " + currentStaffEntity.getLastName() + "\n");
 			System.out.println("1. Registration Operation");
 			System.out.println("2. Appointment Operation");
 			System.out.println("3. Administration Operation");
 			System.out.println("4. Logout\n");
 			response = 0;

 			while(response < 1 || response > 4) {
 				System.out.print("> ");
 				response = scanner.nextInt();

 				if(response == 1) {
 					registrationModule.menuRegistration();
 				} else if (response == 2) {
 					appointmentModule.menuAppointment();
 				} else if (response == 3) {
 					administrationModule.administrationOperation();
 				} else if (response == 4) {
 					break;
 				} else {
 					System.out.println("Invalid option, please try again!\n");
 				}
 			}
 			if(response == 4) {
 				break;
 			}
 		}
 	}
}

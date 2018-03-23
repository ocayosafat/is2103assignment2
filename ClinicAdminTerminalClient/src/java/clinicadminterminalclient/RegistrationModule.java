/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadminterminalclient;

/**
 * Version 1.00
 * @author Yosafat
 */
public class RegistrationModule {
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote;
    private ConsultationEntityControllerRemote consultationEntityControllerRemote;

    private String[] workTime = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
								"12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
								"15:00", "15:30", "16:00", "16:30"};

    public RegistrationModule() {
    }

    public RegistrationModule(PatientEntityControllerRemote patientEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, ConsultationEntityControllerRemote consultationEntityControllerRemote, AppointmentEntityControllerRemote appointmentEntityControllerRemote) {
        this();
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.consultationEntityControllerRemote = consultationEntityControllerRemote;
        this.appointmentEntityControllerRemote = appointmentEntityControllerRemote;
    }

    public void menuRegistration() {
    	Scanner scanner = new Scanner(System.in);
    	Integer response = 0;

    	while (true) {
    		System.out.println("*** CARS :: Registration Operation ***\n");
    		System.out.println("1: Register New Patient");
    		System.out.println("2: Register Walk-In Consultation");
    		System.out.println("3: Register Consultation By Consultation");
    		System.out.println("4: Back\n");
    		response = 0;

    		while(response < 1 || response > 4) {
    			System.out.print("> ");
    			response = scanner.nextInt();
    			if(response == 1) {
    				doCreateNewPatient();
    			} else if(response == 2) {
    				doCreateNewWalkInConsultation();
    			} else if(response == 3) {
    				doCreateNewConsultationConsultation();
    			} else if (response == 4) {
    				break;
    			} else {
    				System.out.println("Invalid option, please try again!\n");
    			}
    		}
    		if(response == 7) {
    			break;
    		}
    	}
    }

    private void doCreateNewPatient() {
    	Scanner scanner  = new Scanner(System.in);
    	PatientEntity newPatientEntity = new PatientEntity();

    	System.out.println("*** CARS :: Registration Operation :: Register New Patient ***\n");
    	System.out.print("Enter Identity Number> ");
    	newPatientEntity.setIdentityNumber(scanner.nextLine().trim());
    	System.out.print("Enter Security Code> ");
    	newPatientEntity.setSecurityCode(scanner.nextLine().trim());
    	System.out.print("Enter First Name> ");
    	newPatientEntity.setFirstName(scanner.nextLine().trim());
    	System.out.print("Enter Last Name> ");
    	newPatientEntity.setLastName(scanner.nextLine().trim());
    	System.out.print("Enter Gender> ");
    	newPatientEntity.setGender(scanner.nextLine().trim());
    	System.out.print("Enter Age> ");
    	newPatientEntity.setAge(scanner.nextInt().trim());
    	scanner.nextLine();
    	System.out.print("Enter Phone> ");
    	newPatientEntity.setPhone(scanner.nextLine().trim());
    	System.out.print("Enter Address> ");
    	newPatientEntity.setAddress(scanner.nextLine().trim());

    	newPatientEntity = patientEntityControllerRemote.createNewPatient(newPatientEntity);
    	System.out.println("Patient has been registered successfully!");
    }

    private void doCreateNewWalkInConsultation() throws ConsultationFullyBookedException, DoctorNotFoundException, PatientNotFoundException {
    	Scanner scanner = new Scanner(System.in);
    	ConsultationEntity newConsultationEntity = new ConsultationEntity();

		System.out.println("*** CARS :: Registration Operation :: Register Walk-In Consultation ***\n");
		System.out.println("Doctor:");
		System.out.println("Id |Name");

		List<DoctorEntity> allDoctors = doctorEntityControllerRemote.retrieveAllDoctors();
		for (DoctorEntity doctor : allDoctors) {
			System.out.println(doctor.getDoctorId() + "  |" + doctor.getFirstName() + " " + doctor.getLastName());
		}
		System.out.println("\nAvailability:");
		System.out.print("Time  |");
		for (DoctorEntity doctor: allDoctors) {
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
        // initialize stringbuilder
        for (int i = 0; i < doctorAvailability.length; i++) {
            doctorAvailability[i] = new StringBuilder("");
        }

		for (int j = 0; j < 6 && index < workTime.length; j++, index++) {
			System.out.print(workTime[i] + " |");
            int doctorId = 1;
			for (DoctorEntity doctor: allDoctors) {
				boolean available = ConsultationEntityControllerRemote.isAvailableByDateTimeDoctor(dateTime[0], workTime[i], doctor) &&
									consultationEntityControllerRemote.isAvailableByDateTimeDoctor(dateTime[0], workTime[i], doctor);
				if (available) {
					System.out.print("O |");
                    doctorAvailability[doctorId-1].append("O");
                } else {
                    System.out.print("X |");
                    doctorAvailability[doctorId-1].append("X");
				}
                doctorId++;
			}
			System.out.print("\n");
		}
		System.out.print("\n");

        try {
    		System.out.print("Enter Doctor Id> ");
            long consultationDoctorId = scanner.nextLong().trim();
            DoctorEntity consultationDoctor = doctorEntityControllerRemote.retrieveDoctorByDoctorId(consultationDoctorId);
            newConsultationEntity.setDoctor(consultationDoctor);
    		
            System.out.print("Enter Patient Identity Number> ");
            PatientEntity consultationPatient = patientEntityControllerRemote.retrievePatientByPatientId(scanner.nextLong().trim());
            scanner.nextLine();
            newConsultationEntity.setPatient(consultationPatient);
            
            newConsultationEntity.setDate(dateTime[0]);
            // check the stringbuilder index with doctor id-1
            // check with indexOf("O"), will return -1 if no "O"
            int availableIndex = doctorAvailability[consultationDoctorId-1].indexOf("O");
            if (availableIndex == -1) {
                throw new ConsultationFullyBookedException("There is no available consultation slot for this doctor currently.");
            }
            newConsultationEntity.setTime(workTime[availableIndex+startAppointmentIndex]);

            long queue = consultationEntityControllerRemote.retrieveLatestQueueNumber(dateTime[0]);
            if (queue == null) {
                queue = 1;
            } else {
                queue++;
            }
            newConsultationEntity.setQueueNumber(queue);

            newConsultationEntity = consultationEntityControllerRemote.createNewConsultation(newConsultationEntity);
            System.out.println(newConsultationEntity.getDoctor().getFirstName() + " " + newConsultationEntity.getDoctor().getLastName() +
                " is going to see " + newConsultationEntity.getPatient().getFirstName() + " " newConsultationEntity.getPatient().getLastName() + 
                " at " + newConsultationEntity.getTime() + ". Queue Number is: " + newConsultationEntity.getQueueNumber() + ".";
        }
        catch (DoctorNotFoundException | PatientNotFoundException ex) {
            System.out.println("An error has occured\n");
        }

    }

    private void doCreateNewAppoinmentConsultation() throws PatientNotFoundException, AppointmentNotFoundException {
        Scanner scanner = new Scanner(System.in);
        ConsultationEntity newConsultationEntity = new ConsultationEntity();

        try {
            System.out.println("*** CARS :: Registration Operation :: Register Consultation By Appointment ***\n");
            System.out.print("Enter Patient Identity Number> ");
            PatientEntity patient = patientEntityControllerRemote.retrievePatientByPatientIdentityNumber(scanner.nextLine().trim());

            System.out.print("\n");
            System.out.println("Appointments:");
            System.out.println("Id |Date       |Time  |Doctor");

            List<AppointmentEntity> appointments = appointmentEntityControllerRemote.retrieveAppointmentByPatient(patient);
            for (AppointmentEntity app : appointments) {
                System.out.println(app.getAppointmentId() + " |" + app.getDate() + " |" app.getTime() + " |" + app.getDoctor().getFirstName() + " " + app.getDoctor().getLastName());
            }
            System.out.print("\n");

            System.out.print("Enter Appointment Id> ");
            long appointmentId = scanner.nextLong().trim();
            AppointmentEntity movedAppointment = appointmentEntityControllerRemote.retrieveAppointmentByAppointmentId(appointmentId);
            appointmentEntityControllerRemote.deleteAppointment(appointmentId);

            newConsultationEntity.setDoctor(movedAppointment.getDoctor());
            newConsultationEntity.setPatient(movedAppointment.getPatient());
            newConsultationEntity.setTime(movedAppointment.getTime());
            newConsultationEntity.setDate(movedAppointment.getDate());

            long queue = consultationEntityControllerRemote.retrieveLatestQueueNumber(movedAppointment.getDate());
            if (queue == null) {
                queue = 1;
            } else {
                queue++;
            }
            newConsultationEntity.setQueueNumber(queue);

            newConsultationEntity = consultationEntityControllerRemote.createNewConsultation(newConsultationEntity);
            System.out.println(newConsultationEntity.getPatient().getFirstName() + " " + newConsultationEntity.getPatient().getLastName() +
                " is going to see " + newConsultationEntity.getDoctor().getFirstName() + " " + newConsultationEntity.getDoctor().getLastName() +
                " at " + newConsultationEntity.getTime() + ". Queue Number is: " + newConsultationEntity.getQueueNumber());
        }
        catch (PatientNotFoundException | AppointmentNotFoundException ex) {
            System.out.println("An error has occured\n");
        }
    }

    private String roundOff(String time) {
    	String[] hourMin = time.split(":");
    	if ((int min = Integer.parseInt(hourMin[1])) < 30) {
    		return new String(hourMin[0] + ":" + "00");
    	}
    	return new String(hourMin[0] + ":" + "30");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadminterminalclient;

import java.util.Scanner;
import ejb.session.stateless.AppointmentEntityControllerRemote;
import ejb.session.stateless.ConsultantionEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import entity.AppointmentEntity;
import entity.ConsultantionEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Version 1.00
 * @author Yk
 */

public class AppointmentModule {
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote;
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    
   
    private String[] workTime = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
								"12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
								"15:00", "15:30", "16:00", "16:30"};
    
    public void AppointmentModule() 
    {
    }
    
    
    public void AppointmentModule(PatientEntityControllerRemote patientEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, AppointmentEntityControllerRemote ApointmentEntityControllerRemote) 
    {
        this();
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.appointmentEntityControllerRemote = appointmentEntityControllerRemote;
    }

    public void menuAppointment()
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** CARS :: Appointment Operation ***\n");
            System.out.println("1: View Patient Appointment");
            System.out.println("2: Add Appointment");
            System.out.println("3: Cancel Appointment");
            System.out.println("4: Back\n");
            response = 0;
            
            while(response < 1 || response > 4)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    doViewPatientAppointment();
                }
                else if(response == 2)
                {
                    doCreateNewAppointment();
                }
                else if(response == 3)
                {
                    doCancelAppointment();
                }
                else if(response == 4)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 4)
            {
                break;
            }
        }
    }

    private void doViewPatientAppointment() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** CARS :: Appointment Operation :: View Patient Appointment ***\n");
        System.out.println("Enter Patient Identity Number> \n");
        
        String identityNumber = scanner.nextLine().trim();
        
        try 
        {
            PatientEntity patientEntity = patientEntityControllerRemote.retrievePatientByPatientIdentityNumber(identityNumber); 
            
            System.out.println("Appointments: \n");
            System.out.println("Id |Date       |Time   |Doctor \n");
            
            List<AppointmentEntity> appointmentEntities = appointmentEntityControllerRemote.retrieveAppointmentByPatientEntity(patientEntity);
            
            for(AppointmentEntity appointmentEntity:appointmentEntities) {
                System.out.println(appointmentEntity.getAppointmentId() + " |" + appointmentEntity.getDate() + " |" + appointmentEntity.getTime() + " |" +  appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName());
            }
        }
        catch(PatientNotFoundException ex)
        {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }
        
        
        System.out.print("\n");
        System.out.print("\n");
       
    }

    private void doCreateNewAppointment() {
        Scanner scanner = new Scanner(System.in);
        
        //create list of timeslots
        
        System.out.println("*** CARS :: Appointment Operation :: Add Appointment ***\n");
        
        System.out.println("Doctor:  \n");
        System.out.println("Id |Name \n");
        
        
        try 
        {
            List<DoctorEntity> doctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();
            
            for(DoctorEntity doctorEntity:doctorEntities) {
                System.out.println(doctorEntity.getDoctorId() + " |" + doctorEntity.getFirstName() + " " + doctorEntity.getLastName() );
            }
            System.out.println("\n");
            System.out.println("\n");
            
            System.out.println("Enter Doctor Id> ");
            Long doctorId = scanner.nextLong();
            DoctorEntity mydoctorEntity = doctorEntityControllerRemote.retrieveDoctorByDoctorId(doctorId);
            
            System.out.println("Enter Date> ");
            
            String inputdate = scanner.nextLine().trim();

            LocalDate date = LocalDate.now();
            DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String textDate = date.format(dateformatter);
            //convert string to date data type
            LocalDate parsedDate = LocalDate.parse(textDate, dateformatter);
            LocalDate parsedInputDate = LocalDate.parse(inputdate, dateformatter);
            
            //check valid date
            //Accept appointment where appointment date is same as currentdate or in later date
            if(parsedInputDate.isAfter(parsedDate) || parsedInputDate.isEqual(parsedDate) ) {
                System.out.println("\n");
                System.out.println("Availability for " + mydoctorEntity.getFirstName() + " " + mydoctorEntity.getLastName() + " on " + inputdate + ": \n" );
                
                ArrayList<String> availableTime = new ArrayList<String>();
                
                //isAvaliableByDateTimeDoctor(String Date, String Time, DoctorEntity doctorEntity)
                for(int i=0; i < workTime.length; i++) {
                    if(isAvaliableByDateTimeDoctor(textDate, workTime[i], mydoctorEntity)) {
                        System.out.print(workTime[i] + " ");
                        availableTime.add(workTime[i]);
                    }
                }
                
                
                System.out.println("\n");
                System.out.println("Enter time> ");
                String selectedTime = scanner.nextLine().trim();
                
                //implement check time
                if(availableTime.contains(selectedTime)) {
                
                
                    System.out.println("Enter Patient Identity Number> ");
                    String identityNumber = scanner.nextLine().trim();

                    try {
                        PatientEntity patientEntity = patientEntityControllerRemote.retrievePatientByPatientIdentityNumber(identityNumber); 

                        //AppointmentEntity(PatientEntity patient, DoctorEntity doctor, String date, String time) 
                        createNewAppointment(new AppointmentEntity(patientEntity,mydoctorEntity, inputdate,selectedTime));
                        System.out.println("Appointment:" + patientEntity.getFirstName() + " " + patientEntity.getLastName() + " and " + mydoctorEntity.getFirstName() + " " + mydoctorEntity.getLastName() + " at " + selectedTime + " on " + inputdate + " has been added." );
                    }
                    catch(PatientNotFoundException ex)
                    {
                        System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
                    }
                }
                //non-availableTime
                //possible exception thrown here********
                else {
                    System.out.println("Timing not found \n");
                }
            }
            //rejects inputdate before appointment date
            //possible exception thrown here*********
            else {
                System.out.println("Invalid date \n");
                    
            } 
         
        }
            
        catch(DoctorNotFoundException ex)
        {
            System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");            
        }
        
        System.out.println();
        
        
        System.out.print("\n");
        System.out.print("\n");
    }

    private void doCancelAppointment() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** CARS :: Appointment Operation :: Cancel Appointment ***\n");
        
        System.out.println("Enter Patient Identity Number> ");
        String identityNumber = scanner.nextLine().trim();
        
        try 
        {
            PatientEntity patientEntity = patientEntityControllerRemote.retrievePatientByPatientIdentityNumber(identityNumber); 
            
            System.out.println("Appointments: \n");
            System.out.println("Id |Date       |Time   |Doctor \n");
            
            List<AppointmentEntity> appointmentEntities = appointmentEntityControllerRemote.retrieveAppointmentByPatientEntity(patientEntity);
            
            for(AppointmentEntity appointmentEntity:appointmentEntities) {
                System.out.println(appointmentEntity.getAppointmentId() + " |" + appointmentEntity.getDate() + " |" +  appointmentEntity.getTime() + " |" + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName());
            }
            
            System.out.println("Enter Appointment Id> ");
            Long appointmentId = scanner.nextLong();
            
            AppointmentEntity appointEntity = appointmentEntityControllerRemote.retrieveAppointmentByAppointmentId(appointmentId);
            DoctorEntity mydoctorEntity = appointEntity.getDoctor();
           
            
            appointmentEntityControllerRemote.deleteAppointment(appointmentId);
            
            System.out.println("Appointment:" + patientEntity.getFirstName() + " " + patientEntity.getLastName() + " and " + mydoctorEntity.getFirstName() + " " + mydoctorEntity.getLastName() + " at " +  appointEntity.getTime() + " on " + appointEntity.getDate() + " has cancelled.");
        }
        catch(PatientNotFoundException ex)
        {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }
        
        System.out.print("\n");
        System.out.print("\n");
    }
}

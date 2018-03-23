/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadminterminalclient;

import entity.DoctorEntity;
import entity.PatientEntity;
import entity.StaffEntity;
import java.util.List;
import java.util.Scanner;
import util.exception.DoctorNotFoundException;
import util.exception.PatientNotFoundException;
import util.exception.StaffNotFoundException;
import ejb.session.stateless.PatientEntityControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;

/**
 * Version 1.01
 * @author Aden
 */
public class AdministrationModule {
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private StaffEntityControllerRemote staffEntityControllerRemote;
    
    public AdministrationModule() {
    }

    public AdministrationModule(PatientEntityControllerRemote patientEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, StaffEntityControllerRemote staffEntityControllerRemote) {
        this();
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.staffEntityControllerRemote = staffEntityControllerRemote;
    }
    
    public void administrationOperation() {
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** CARS :: Administration Operation ***\n");
            System.out.println("1: Patient Management");
            System.out.println("2: Doctor Management");
            System.out.println("3: Staff Management");
            System.out.println("4: Back\n");
            response = 0;
            
            while(response < 1 || response > 4)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    doPatientManagementOperation();
                }
                else if(response == 2)
                {
                    doDoctorManagementOperation();
                }
                else if(response == 3)
                {
                    doStaffManagementOperation();
                }
                else if (response == 4)
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
    
    public void doPatientManagementOperation() {
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** CARS :: Administration Operation :: Patient Management ***\n");
            System.out.println("1: Add Patient");
            System.out.println("2: View Patient Details");
            System.out.println("3: Update Patient");
            System.out.println("4: Delete Patient");
            System.out.println("5: View All Patients");
            System.out.println("6: Back\n");
            response = 0;
            
            while(response < 1 || response > 6)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    doAddPatient();
                }
                else if(response == 2)
                {
                    doViewPatientDetails();
                }
                else if(response == 3)
                {
                    doUpdatePatient();
                }
                else if(response == 4)
                {
                    doDeletePatient();
                }
                else if(response == 5)
                {
                    doViewAllPatients();
                }
                else if (response == 6)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 6)
            {
                break;
            }
        }
    }
    
    public void doAddPatient() {
        Scanner scanner = new Scanner(System.in);
        PatientEntity newPatientEntity = new PatientEntity();
        
        System.out.println("*** CARS :: Administration Operation :: Patient Management :: Add Patient ***\n");
        System.out.print("Enter First Name> ");
        newPatientEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newPatientEntity.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Gender> ");
        newPatientEntity.setGender(scanner.nextLine().trim());
        System.out.print("Enter Age> ");
        newPatientEntity.setAge(Integer.parseInt(scanner.nextLine().trim()));
        System.out.print("Enter Identity Number> ");
        newPatientEntity.setIdentityNumber(scanner.nextLine().trim());
        System.out.print("Enter Phone> ");
        newPatientEntity.setPhone(scanner.nextLine().trim());
        System.out.print("Enter Address> ");
        newPatientEntity.setAddress(scanner.nextLine().trim());
        System.out.print("Enter Security Code> ");
        newPatientEntity.setSecurityCode(Long.parseLong(scanner.nextLine().trim()));
                
        newPatientEntity = patientEntityControllerRemote.createNewPatient(newPatientEntity);
        System.out.println("Patient has been registered successfully!" + "\n");
        
        //missing try catch phrase, assuming creating of patient is always successful
    }
    
    public void doViewAllPatients() {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** CARS :: Administration Operation :: Patient Management :: View All Patients ***\n");
        
        List<PatientEntity> patientEntities = patientEntityControllerRemote.retrieveAllPatients();
        System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", "Patient ID", "Patient Identity Number", "First Name", "Last Name", "Gender", "Age", "Phone", "Address", "Security Code");

        for(PatientEntity patientEntity:patientEntities)
        {
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", patientEntity.getPatientId().toString(), patientEntity.getIdentityNumber(), patientEntity.getFirstName(), patientEntity.getLastName(), patientEntity.getGender(), patientEntity.getAge().toString(), patientEntity.getPhone(), patientEntity.getAddress().toString(), patientEntity.getSecurityCode().toString());
        }
        
        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }
    
    public void doUpdatePatient() {
        
        Scanner scanner = new Scanner(System.in);        
        String input;
        String patientIdentityNumberToUpdate;
        
        System.out.println("*** CARS :: Administration Operation :: Patient Management :: Update Patient ***\n");
        System.out.print("Enter Identity Number for patient to be updated: > ");
        patientIdentityNumberToUpdate = scanner.nextLine().trim();
        
        try {
            PatientEntity patientEntity = patientEntityControllerRemote.retrievePatientByPatientIdentityNumber(patientIdentityNumberToUpdate);
        
            System.out.print("Enter First Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                patientEntity.setFirstName(input);
            }
                
            System.out.print("Enter Last Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                patientEntity.setLastName(input);
            }
        
            scanner.nextLine();
            System.out.print("Enter Gender (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                patientEntity.setGender(input);
            }
        
            scanner.nextLine();
            System.out.print("Enter Age (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                patientEntity.setAge(Integer.parseInt(input));
            }
        
            scanner.nextLine();
            System.out.print("Enter Phone (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                patientEntity.setPhone(input); //assume phone = string
            }
        
            scanner.nextLine();
            System.out.print("Enter Address (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                patientEntity.setAddress(input); 
            
            }
        
            // Assume security code cant be changed once created
        
            patientEntityControllerRemote.updatePatient(patientEntity);
            System.out.println("Patient updated successfully!\n");
        } catch (PatientNotFoundException ex) {
            System.out.println("An error occured while trying to update patient, " + ex.getMessage() + "!");
        }
        
    }
    
    //This Method assumes all consultation/appointment entities are not changed
    public void doUpdatePatient(PatientEntity patientEntity) {
        
        Scanner scanner = new Scanner(System.in);        
        String input;
        
        System.out.println("*** CARS :: Administration Operation :: Patient Management :: Update Patient ***\n");
        System.out.print("Enter First Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            patientEntity.setFirstName(input);
        }
                
        System.out.print("Enter Last Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            patientEntity.setLastName(input);
        }
        
        scanner.nextLine();
        System.out.print("Enter Gender (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            patientEntity.setGender(input);
        }
        
        scanner.nextLine();
        System.out.print("Enter Age (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            patientEntity.setAge(Integer.parseInt(input));
        }
        
        scanner.nextLine();
        System.out.print("Enter Phone (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            patientEntity.setPhone(input); //assume phone = string
        }
        
        scanner.nextLine();
        System.out.print("Enter Address (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            patientEntity.setAddress(input); 
            
        }
        
        // Assume security code cant be changed once created
        
        patientEntityControllerRemote.updatePatient(patientEntity);
        System.out.println("Patient updated successfully!\n");
    }
    
    //This Method assumes all consultation/appointment entities are not changed
    public void doDeletePatient() {
        Scanner scanner = new Scanner(System.in);        
        String input;
        String identityNumberToBeDeleted;
        
        System.out.println("*** CARS :: Administration Operation :: Patient Management :: Delete Patient ***\n");
        System.out.print("Enter Patient ID for Patient to be deleted : > ");
        identityNumberToBeDeleted = scanner.nextLine().trim(); 
        PatientEntity patientEntity = patientEntityControllerRemote.retrievePatientByPatientIdentityNumber(identityNumberToBeDeleted);
        System.out.printf("Confirm Delete Patient %s %s (Patient Identity Number: %d) (Enter 'Y' to Delete)> ", patientEntity.getFirstName(), patientEntity.getLastName(), patientEntity.getIdentityNumber());
        input = scanner.nextLine().trim();
        
        if(input.equals("Y"))
        {
            try 
            {
                patientEntityControllerRemote.deletePatient(patientEntity.getIdentityNumber());
                System.out.println("Patient deleted successfully!\n");
            } 
            catch (PatientNotFoundException ex) 
            {
                System.out.println("An error has occurred while deleting patient: " + ex.getMessage() + "\n");
            }            
        }
        else
        {
            System.out.println("Patient NOT deleted!\n");
        }
    }
    //This Method assumes all consultation/appointment entities are not changed
    public void doDeletePatient(PatientEntity patientEntity) {
        Scanner scanner = new Scanner(System.in);        
        String input;
        
        System.out.println("*** CARS :: Administration Operation :: Patient Management :: Delete Patient ***\n");
        System.out.printf("Confirm Delete Patient %s %s (Patient Identity Number: %d) (Enter 'Y' to Delete)> ", patientEntity.getFirstName(), patientEntity.getLastName(), patientEntity.getIdentityNumber());
        input = scanner.nextLine().trim();
        
        if(input.equals("Y"))
        {
            try 
            {
                patientEntityControllerRemote.deletePatient(patientEntity.getIdentityNumber());
                System.out.println("Patient deleted successfully!\n");
            } 
            catch (PatientNotFoundException ex) 
            {
                System.out.println("An error has occurred while deleting patient: " + ex.getMessage() + "\n");
            }            
        }
        else
        {
            System.out.println("Patient NOT deleted!\n");
        }
    }
    
    public void doViewPatientDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        System.out.println("*** CARS :: Administration Operation :: Patient Management :: View Patient Details ***\n");
        System.out.print("Enter Patient Identity Number> ");
        String patientIdentityNumber = scanner.nextLine();
        
        try
        {
            PatientEntity patientEntity = patientEntityControllerRemote.retrievePatientByPatientIdentityNumber(patientIdentityNumber);
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", "Patient ID", "Patient Identity Number", "First Name", "Last Name", "Gender", "Age", "Phone", "Address", "Security Code");
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", patientEntity.getPatientId().toString(), patientEntity.getIdentityNumber(), patientEntity.getFirstName(), patientEntity.getLastName(), patientEntity.getGender(), patientEntity.getAge().toString(), patientEntity.getPhone(), patientEntity.getAddress().toString(), patientEntity.getSecurityCode().toString());
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("1: Update Patient");
            System.out.println("2: Delete Patient");
            System.out.println("3: Back\n");
            System.out.print("> ");
            response = scanner.nextInt();

            if(response == 1)
            {
                doUpdatePatient(patientEntity);
            }
            else if(response == 2)
            {
                doDeletePatient(patientEntity);
            }
        } catch (PatientNotFoundException ex)
        {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }
    }
    
    public void doStaffManagementOperation() {
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** CARS :: Administration Operation :: Staff Management ***\n");
            System.out.println("1: Add Staff");
            System.out.println("2: View Staff Details");
            System.out.println("3: Update Staff");
            System.out.println("4: Delete Staff");
            System.out.println("5: View All Staffs");
            System.out.println("6: Back\n");
            response = 0;
            
            while(response < 1 || response > 6)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    doAddStaff();
                }
                else if(response == 2)
                {
                    doViewStaffDetails();
                }
                else if(response == 3)
                {
                    doUpdateStaff();
                }
                else if(response == 4)
                {
                    doDeleteStaff();
                }
                else if(response == 5)
                {
                    doViewAllStaffs();
                }
                else if (response == 6)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 6)
            {
                break;
            }
        }
    }
    
    public void doAddStaff() {
        Scanner scanner = new Scanner(System.in);
        StaffEntity newStaffEntity = new StaffEntity();
        
        System.out.println("*** CARS :: Administration Operation :: Staff Management :: Add Staff ***\n");
        System.out.print("Enter First Name> ");
        newStaffEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newStaffEntity.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Username> ");
        newStaffEntity.setUsername(scanner.nextLine().trim()); //Note: username is all lower case
        System.out.print("Enter Password> ");
        newStaffEntity.setPassword(scanner.nextLine().trim());
                
        newStaffEntity = staffEntityControllerRemote.createNewStaff(newStaffEntity);
        System.out.println("Staff has been registered successfully!" + "\n");
        
        //missing try catch phrase, assuming creating of patient is always successful
    }
    
    public void doViewStaffDetails()
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        System.out.println("*** CARS :: Administration Operation :: Staff Management :: View Staff Details ***\n");
        System.out.print("Enter Staff ID> ");
        Long staffId = scanner.nextLong();
        
        try
        {
            StaffEntity staffEntity = staffEntityControllerRemote.retrieveStaffByStaffId(staffId);
            System.out.printf("%8s%20s%20s%20s%20s\n", "Staff ID", "First Name", "Last Name", "Username", "Password");
            System.out.printf("%8s%20s%20s%20s%20s\n", staffEntity.getStaffId().toString(), staffEntity.getFirstName(), staffEntity.getLastName(), staffEntity.getUsername(), staffEntity.getPassword());         
            System.out.println("------------------------");
            System.out.println("1: Update Staff");
            System.out.println("2: Delete Staff");
            System.out.println("3: Back\n");
            System.out.print("> ");
            response = scanner.nextInt();

            if(response == 1)
            {
                doUpdateStaff(staffEntity);
            }
            else if(response == 2)
            {
                doDeleteStaff(staffEntity);
            }
        }
        catch(StaffNotFoundException ex)
        {
            System.out.println("An error has occurred while retrieving staff: " + ex.getMessage() + "\n");
        }
    }
    
    public void doUpdateStaff() {
        Scanner scanner = new Scanner(System.in);        
        String input;
        Long staffIdToUpdate;
        
        System.out.println("*** CARS :: Administration Operation :: Staff Management :: Update Staff ***\n");
        System.out.print("Enter Staff ID for staff to be updated: > ");
        staffIdToUpdate = scanner.nextLong();
        scanner.nextLine(); //eats the \n for nextLong();
        
        try {
            StaffEntity staffEntity = staffEntityControllerRemote.retrieveStaffByStaffId(staffIdToUpdate);
        
            System.out.print("Enter First Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                staffEntity.setFirstName(input);
            }
                
            System.out.print("Enter Last Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                staffEntity.setLastName(input);
            }
        
            scanner.nextLine();
            System.out.print("Enter Username (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                staffEntity.setUsername(input);
            }
        
            scanner.nextLine();
            System.out.print("Enter Password (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                staffEntity.setPassword(input);
            }
        
            staffEntityControllerRemote.updateStaff(staffEntity);
            System.out.println("Staff updated successfully!\n");
        } catch(StaffNotFoundException ex) {
            System.out.println("An error occured while trying to update staff: "+ ex.getMessage() + "!");
        }
    }
    
    public void doUpdateStaff(StaffEntity staffEntity) {
        Scanner scanner = new Scanner(System.in);        
        String input;

        System.out.println("*** CARS :: Administration Operation :: Staff Management :: Update Staff ***\n");
        
        System.out.print("Enter First Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            staffEntity.setFirstName(input);
        }
                
        System.out.print("Enter Last Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            staffEntity.setLastName(input);
        }
        
        scanner.nextLine();
        System.out.print("Enter Username (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            staffEntity.setUsername(input);
        }
        
        scanner.nextLine();
        System.out.print("Enter Password (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            staffEntity.setPassword(input);
        }
        
        staffEntityControllerRemote.updateStaff(staffEntity);
        System.out.println("Staff updated successfully!\n");
    }
    
    
    public void doDeleteStaff() {
        Scanner scanner = new Scanner(System.in);        
        String input;
        Long staffIdToBeDeleted;
        
        System.out.println("*** CARS :: Administration Operation :: Staff Management :: Delete Staff ***\n");
        System.out.print("Enter Staff ID for Staff to be deleted : > ");
        staffIdToBeDeleted = scanner.nextLong(); 
        scanner.nextLine(); //eats \n
        StaffEntity tempStaffEntity = staffEntityControllerRemote.retrieveStaffByStaffId(staffIdToBeDeleted);
        System.out.printf("Confirm Delete Staff %s %s (Staff ID: %d) (Enter 'Y' to Delete)> ", tempStaffEntity.getFirstName(), tempStaffEntity.getLastName(), tempStaffEntity.getStaffId());
        input = scanner.nextLine().trim();
        
        if(input.equals("Y"))
        {
            try 
            {
                staffEntityControllerRemote.deleteStaff(tempStaffEntity.getStaffId());
                System.out.println("Staff deleted successfully!\n");
            } 
            catch (StaffNotFoundException ex) 
            {
                System.out.println("An error has occurred while deleting staff: " + ex.getMessage() + "\n");
            }            
        }
        else
        {
            System.out.println("Staff NOT deleted!\n");
        }
    }
    
    public void doDeleteStaff(StaffEntity staffEntity) {
        Scanner scanner = new Scanner(System.in);        
        String input;
        
        System.out.println("*** CARS :: Administration Operation :: Staff Management :: Delete Staff ***\n");
        System.out.printf("Confirm Delete Staff %s %s (Staff ID: %d) (Enter 'Y' to Delete)> ", staffEntity.getFirstName(), staffEntity.getLastName(), staffEntity.getStaffId());
        input = scanner.nextLine().trim();
        
        if(input.equals("Y"))
        {
            try 
            {
                staffEntityControllerRemote.deleteStaff(staffEntity.getStaffId());
                System.out.println("Staff deleted successfully!\n");
            } 
            catch (StaffNotFoundException ex) 
            {
                System.out.println("An error has occurred while deleting staff: " + ex.getMessage() + "\n");
            }            
        }
        else
        {
            System.out.println("Staff NOT deleted!\n");
        }
    }
    
    public void doViewAllStaffs() {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** CARS :: Administration Operation :: Staff Management :: View All Staffs ***\n");
        
        List<StaffEntity> staffEntities = staffEntityControllerRemote.retrieveAllStaffs();
        System.out.printf("%8s%20s%20s%20s%20s\n", "Staff ID", "First Name", "Last Name", "Username", "Password");

        for(StaffEntity staffEntity:staffEntities)
        {
            System.out.printf("%8s%20s%20s%20s%20s\n", staffEntity.getStaffId().toString(), staffEntity.getFirstName(), staffEntity.getLastName(), staffEntity.getUsername(), staffEntity.getPassword());
        }
        
        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }
    
    public void doDoctorManagementOperation() {
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** CARS :: Administration Operation :: Doctor Management ***\n");
            System.out.println("1: Add Doctor");
            System.out.println("2: View Doctor Details");
            System.out.println("3: Update Doctor");
            System.out.println("4: Delete Doctor");
            System.out.println("5: View All Doctors");
            System.out.println("6: Back\n");
            response = 0;
            
            while(response < 1 || response > 6)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    doAddDoctor();
                }
                else if(response == 2)
                {
                    doViewDoctorDetails();
                }
                else if(response == 3)
                {
                    doUpdateDoctor();
                }
                else if(response == 4)
                {
                    doDeleteDoctor();
                }
                else if(response == 5)
                {
                    doViewAllDoctors();
                }
                else if (response == 6)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 6)
            {
                break;
            }
        }
    }
    
    public void doAddDoctor() {
        Scanner scanner = new Scanner(System.in);
        DoctorEntity newDoctorEntity = new DoctorEntity();
        
        System.out.println("*** CARS :: Administration Operation :: Doctor Management :: Add Doctor ***\n");
        System.out.print("Enter First Name> ");
        newDoctorEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newDoctorEntity.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Registration> ");
        newDoctorEntity.setRegistration(scanner.nextLine().trim());
        System.out.print("Enter Qualifications> ");
        newDoctorEntity.setQualifications(scanner.nextLine().trim());
                
        newDoctorEntity = doctorEntityControllerRemote.createNewDoctor(newDoctorEntity);
        System.out.println("Doctor has been registered successfully!" + "\n");

    }
    
    public void doViewDoctorDetails()
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        System.out.println("*** CARS :: Administration Operation :: Doctor Management :: View Doctor Details ***\n");
        System.out.print("Enter Doctor ID> ");
        Long doctorId = scanner.nextLong();
        
        try
        {
            DoctorEntity doctorEntity = doctorEntityControllerRemote.retrieveDoctorByDoctorId(doctorId);
            System.out.printf("%8s%20s%20s%20s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualifications");
            System.out.printf("%8s%20s%20s%20s%20s\n", doctorEntity.getDoctorId().toString(), doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getRegistration(), doctorEntity.getQualifications());         
            System.out.println("------------------------");
            System.out.println("1: Update Doctor");
            System.out.println("2: Delete Doctor");
            System.out.println("3: Back\n");
            System.out.print("> ");
            response = scanner.nextInt();

            if(response == 1)
            {
                doUpdateDoctor(doctorEntity);
            }
            else if(response == 2)
            {
                doDeleteDoctor(doctorEntity);
            }
        }
        catch(DoctorNotFoundException ex)
        {
            System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");
        }
    }
    //This Method assumes all consultation/appointment entities are not changed
    public void doUpdateDoctor() {
        Scanner scanner = new Scanner(System.in);        
        String input;
        Long doctorIdToUpdate;
        
        System.out.println("*** CARS :: Administration Operation :: Staff Management :: Update Doctor ***\n");
        System.out.print("Enter Doctor ID for doctor to be updated: > ");
        doctorIdToUpdate = scanner.nextLong();
        scanner.nextLine(); //eats the \n for nextLong();
        
        try {
        
            DoctorEntity doctorEntity = doctorEntityControllerRemote.retrieveDoctorByDoctorId(doctorIdToUpdate);
        
            System.out.print("Enter First Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                doctorEntity.setFirstName(input);
            }
                
            System.out.print("Enter Last Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                doctorEntity.setLastName(input);
            }
        
            scanner.nextLine();
            System.out.print("Enter Username (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                doctorEntity.setRegistration(input);
            }
        
            scanner.nextLine();
            System.out.print("Enter Password (blank if no change)> ");
            input = scanner.nextLine().trim();
            if(input.length() > 0)
            {
                doctorEntity.setQualifications(input);
            }
        
            doctorEntityControllerRemote.updateDoctor(doctorEntity);
            System.out.println("Doctor updated successfully!\n");
        } catch (DoctorNotFoundException ex) {
            System.out.println("An error occured while trying to update doctor: " + ex.getMessage() + "!");
        }
    }
    //This Method assumes all consultation/appointment entities are not changed
    public void doUpdateDoctor(DoctorEntity doctorEntity) {
        Scanner scanner = new Scanner(System.in);        
        String input;
       
        System.out.println("*** CARS :: Administration Operation :: Staff Management :: Update Doctor ***\n");
        System.out.print("Enter First Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            doctorEntity.setFirstName(input);
        }
                
        System.out.print("Enter Last Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            doctorEntity.setLastName(input);
        }
        
        scanner.nextLine();
        System.out.print("Enter Username (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            doctorEntity.setRegistration(input);
        }
        
        scanner.nextLine();
        System.out.print("Enter Password (blank if no change)> ");
        input = scanner.nextLine().trim();
        if(input.length() > 0)
        {
            doctorEntity.setQualifications(input);
        }
        
        doctorEntityControllerRemote.updateDoctor(doctorEntity);
        System.out.println("Doctor updated successfully!\n");
    }
    //This Method assumes all consultation/appointment entities are not changed
    public void doDeleteDoctor() {
        Scanner scanner = new Scanner(System.in);        
        String input;
        Long doctorIdToBeDeleted;
        
        System.out.println("*** CARS :: Administration Operation :: Doctor Management :: Delete Doctor ***\n");
        System.out.print("Enter Doctor ID for Doctor to be deleted : > ");
        doctorIdToBeDeleted = scanner.nextLong(); 
        scanner.nextLine(); //eats \n
        DoctorEntity doctorEntity = doctorEntityControllerRemote.retrieveDoctorByDoctorId(doctorIdToBeDeleted);
        System.out.printf("Confirm Delete Doctor %s %s (Doctor ID: %d) (Enter 'Y' to Delete)> ", doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getDoctorId());
        input = scanner.nextLine().trim();
        
        if(input.equals("Y"))
        {
            try 
            {
                doctorEntityControllerRemote.deleteDoctor(doctorEntity.getDoctorId());
                System.out.println("Doctor deleted successfully!\n");
            } 
            catch (DoctorNotFoundException ex) 
            {
                System.out.println("An error has occurred while deleting doctor: " + ex.getMessage() + "\n");
            }            
        }
        else
        {
            System.out.println("Doctor NOT deleted!\n");
        }
    }
    //This Method assumes all consultation/appointment entities are not changed
    public void doDeleteDoctor(DoctorEntity doctorEntity) {
        Scanner scanner = new Scanner(System.in);        
        String input;
        
        System.out.println("*** CARS :: Administration Operation :: Doctor Management :: Delete Doctor ***\n");
        System.out.printf("Confirm Delete Doctor %s %s (Doctor ID: %d) (Enter 'Y' to Delete)> ", doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getDoctorId());
        input = scanner.nextLine().trim();
        
        if(input.equals("Y"))
        {
            try 
            {
                doctorEntityControllerRemote.deleteDoctor(doctorEntity.getDoctorId());
                System.out.println("Doctor deleted successfully!\n");
            } 
            catch (DoctorNotFoundException ex) 
            {
                System.out.println("An error has occurred while deleting doctor: " + ex.getMessage() + "\n");
            }            
        }
        else
        {
            System.out.println("Doctor NOT deleted!\n");
        }
    }
    
    public void doViewAllDoctors() {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** CARS :: Administration Operation :: Doctor Management :: View All Doctors ***\n");
        
        List<DoctorEntity> doctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();
        System.out.printf("%8s%20s%20s%20s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualifications");

        for(DoctorEntity doctorEntity:doctorEntities)
        {
            System.out.printf("%8s%20s%20s%20s%20s\n", doctorEntity.getDoctorId().toString(), doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getRegistration(), doctorEntity.getQualifications());
        }
        
        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }
    
    
}

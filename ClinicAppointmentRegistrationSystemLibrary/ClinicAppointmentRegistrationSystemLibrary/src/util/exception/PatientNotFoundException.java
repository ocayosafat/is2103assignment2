/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 * Version 1.0
 * @author Aden
 */
public class PatientNotFoundException extends Exception {
    
    public PatientNotFoundException() {
    }
    
    public PatientNotFoundException(String msg)
    {
        super(msg);
    }
    
}

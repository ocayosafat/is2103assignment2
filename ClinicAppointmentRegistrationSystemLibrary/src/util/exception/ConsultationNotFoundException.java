/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Aden
 */
public class ConsultationNotFoundException extends Exception {
    public ConsultationNotFoundException() {
    }
    
    public ConsultationNotFoundException(String msg)
    {
        super(msg);
    }
}

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
public class ConsultationFullyBookedException extends Exception {
    
    public ConsultationFullyBookedException() {
    }
    
    public ConsultationFullyBookedException(String msg)
    {
        super(msg);
    }
}

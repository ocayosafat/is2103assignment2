/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.StaffEntity;
import java.util.List;
import util.exception.InvalidLoginException;
import util.exception.StaffNotFoundException;

/**
 * Version 1.0
 * @author Aden
 */
public interface StaffEntityControllerRemote {
    
    StaffEntity createNewStaff(StaffEntity newStaffEntity);
    
    List<StaffEntity> retrieveAllStaffs();
    
    StaffEntity retrieveStaffByStaffId(Long staffId) throws StaffNotFoundException;
    
    StaffEntity retrieveStaffByUsername(String username) throws StaffNotFoundException;
    
    StaffEntity staffLogin(String username, String password) throws InvalidLoginException;
    
    void updateStaff(StaffEntity staffEntity);
    
    void deleteStaff(Long staffId) throws StaffNotFoundException;
}

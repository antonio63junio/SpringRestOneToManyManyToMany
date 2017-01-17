package com.antonio.servicios;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antonio.dao.PhoneDao;
import com.antonio.modelo.Employee;
import com.antonio.modelo.Phone;

//import com.antonio.servicios.EmployeeServiceImpl;

@Service("phoneService")
@Transactional
public class PhoneServiceImpl implements PhoneService {

	final static Logger logger = Logger.getLogger("PhoneServiceImpl");

	@Autowired
	private PhoneDao dao;
	
	@Autowired
	EmployeeService employeeService;
		
	@Override
	public Phone findById(Long id) {
		return dao.findById(id);
	}
	
	@Override
	public void savePhone (Phone phone){
		dao.savePhone(phone);
	}
	
	@Override 
	public Phone mergePhone (Phone phone, Long id){
		Phone currentPhone = findById(id);
		if (currentPhone == null) throw new RuntimeException("El telefono ya no existe");
		currentPhone.setContactnumber(phone.getContactnumber());
        currentPhone.setPhonetype(phone.getPhonetype());
        
        Phone phoneNew = dao.mergePhone(currentPhone); 
        employeeService.registrarMovimiento("MODIFICACION", currentPhone.getEmployee());
        return phoneNew;
	}
	
	
/*	
	@Override 
	public Phone mergePhone (Phone phone, Long id){
		Phone currentPhone = findById(id);
		Phone phon = null;
        
        if (currentPhone == null) {
            logger.info ("Phone with id " + id + " not found");
            return phon;
        }
 
        currentPhone.setContactnumber(phone.getContactnumber());
        //currentPhone.setEmployee(phone.getEmployee());
        currentPhone.setPhonetype(phone.getPhonetype());
        
        try {
            phon = dao.mergePhone(currentPhone);   
            logger.debug("Phone obtenido a partir del mergePhone :" + phon.toString());
            employeeService.registrarMovimiento("MODIFICACION", currentPhone.getEmployee());
        }
          catch (Exception e){
        	  logger.error("Error dao.mergePhone, phone id=" + id + ",message:" + e.getMessage());
        	  phon = null;
          }
		return phon;
	}
	*/
	
	@Override
	public boolean cratePhone (Phone phone, int EmployeeId){
            Employee e = employeeService.findById(EmployeeId);
            phone.setEmployee(e);
            dao.savePhone(phone);
            return true;               	  
	}
	
/*	@Override
	public boolean cratePhone (Phone phone, int EmployeeId){
    	try {

            Employee e = employeeService.findById(EmployeeId);
            if (e == null) {
          	  logger.error("El telefono debe pertenecer a un empleado registrado");
                return false;
            } else {
                phone.setEmployee(e);
                dao.savePhone(phone);
                return true;               	  
            }
          } catch(HibernateException he) {
  	    	 logger.error("Exception Creating " + phone.toString() + ": " +  he.getMessage());
       	    return false;
  	    	
  	    } catch(Exception he) {
  	    	logger.error("Exception Creating " + phone.toString() + ", getMessage():" + he.getMessage());
  	    	return false;
  	    }
	}*/
	
	@Override
	public void deletePhone (Phone phone){
		dao.deletePhone(phone.getId());
	}
}

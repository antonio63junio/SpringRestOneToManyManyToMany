package com.antonio.controladores;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.antonio.modelo.Employee;
import com.antonio.modelo.Phone;

import com.antonio.dao.PhoneDao;
import com.antonio.servicios.EmployeeService;
import com.antonio.servicios.PhoneService;

@RestController
public class PhoneController {

	final static Logger logger = Logger.getLogger(PhoneController.class);
	
    @Autowired
    PhoneDao phoneDao;
	
	@Autowired 
	PhoneService phoneService;

	@Autowired
	EmployeeService employeeService;
	
	 //------------------- Update a Phone --------------------------------------------------------
	
    @RequestMapping(value = "/phone/{id}", method = RequestMethod.PUT) 
	public ResponseEntity<Phone> updatePhone(@PathVariable("id") Long id, @Valid @RequestBody Phone phone){
    	Phone phoneActualizado = null;
		phoneActualizado = phoneService.mergePhone(phone, id);
		return new ResponseEntity<Phone>(phoneActualizado, HttpStatus.OK);
	}
    
/*	
    @RequestMapping(value = "/phone/{id}", method = RequestMethod.PUT) 
	public ResponseEntity<Phone> updatePhone(@PathVariable("id") Long id, @Valid @RequestBody Phone phone){
		Phone phoneActualizado = null;
		try { 
			phoneActualizado = phoneService.mergePhone(phone, id);
			if (phoneActualizado == null){
				logger.debug("** null ** employee del phoneActualizado :: ");
				return new ResponseEntity<Phone>(HttpStatus.CONFLICT);
			}
				else {
					logger.debug("** OK ** employee del phoneActualizado :: ");
					return new ResponseEntity<Phone>(phoneActualizado, HttpStatus.OK);
				}
		} catch (Exception e) {
			logger.error("** Exception ** , message:" + e.getMessage());
			// + ", cause:" + e.getCause().getMessage());
			return new ResponseEntity<Phone>(HttpStatus.CONFLICT);
		}
		
	}
*/
	
    //-------------------Create a Phone -------------------------------------------------------
    
    @RequestMapping(value = "/phone/{idEmployee}", method = RequestMethod.POST)
    public ResponseEntity<String> createPhone(@PathVariable("idEmployee") int id, @Valid @RequestBody Phone phone) {
        if (phoneService.cratePhone(phone, id)) {
        	return new ResponseEntity<String>(HttpStatus.OK);
        } else {
        	return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
    }
    
    //------------------- Delete a Phone --------------------------------------------------------
    
    @RequestMapping(value = "/phone/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Phone> deletePhone(@PathVariable("id") Long id) {
    	Phone phone = phoneService.findById(id);
        if (phone != null) phoneService.deletePhone (phone);
        return new ResponseEntity<Phone>(HttpStatus.NO_CONTENT);
    }
    
/*    @RequestMapping(value = "/phone/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Phone> deletePhone(@PathVariable("id") Long id) {
 
        Phone phone = phoneService.findById(id);
        if (phone == null) {      	
            logger.error ("Unable to delete. Phone was not found ");
            return new ResponseEntity<Phone>(HttpStatus.NOT_FOUND);
        }
 
        phoneService.deletePhone (phone);
        return new ResponseEntity<Phone>(HttpStatus.NO_CONTENT);
    }*/

}
package com.antonio.controladores;
 
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.bytecode.buildtime.spi.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.antonio.modelo.Employee;
import com.antonio.modelo.Phone;
import com.antonio.servicios.EmployeeService;



@RestController
public class EmployeeController {
	
	final static Logger logger = Logger.getLogger(EmployeeController.class);
 
    @Autowired
    EmployeeService employeeService;  //Service which will do all data retrieval/manipulation work
 
	@Autowired
	EmployeeService service;
	
    //-------------------Retrieve All Users--------------------------------------------------------
    
//    @RequestMapping(value = "/index/", method = RequestMethod.GET)
//    public ResponseEntity<List<Employee>> listAllEmployees2() {
//        List<Employee> employees = employeeService.findAllEmployees();
//        if(employees.isEmpty()){
//            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
//        }
//        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
//    }

    
    @RequestMapping(value = "/employee/", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> listAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();
        logger.debug("num. employees:"  + employees.size());

        if(employees.isEmpty()){
            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        // La escritura de los empleados falla aquí por ser LAZY, pero podemos hacerlo en el servicio 
        //  que está dentro de sesión
        //logger.debug("employees:"  + employees);
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }
 
    // Lista de telefonos de un empleado
    @RequestMapping(value = "/employee/phones/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Phone>> listEmployeePhones(@PathVariable("id") int id) {
        List<Phone> phones = employeeService.listEmployeePhones(id);    
        logger.debug("Telefonos del empleado con id = " + id + ":" + phones.toString());
        return new ResponseEntity<List<Phone>>(phones, HttpStatus.OK);
    }
  
    
    //-------------------Retrieve Single Employee --------------------------------------------------------
     
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> findById(@PathVariable("id") int id) {
        System.out.println("Fetching Employee with id " + id);
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            System.out.println("Employee with id " + id + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }
     
   
    //-------------------Create a Employee -------------------------------------------------------
     
    @RequestMapping(value = "/employee/", method = RequestMethod.POST)
    public ResponseEntity<String> createEmployee(@Valid @RequestBody Employee employee) {

       employeeService.saveEmployee(employee);
       return new ResponseEntity<String>(HttpStatus.CREATED);             
    }
 
    //------------------- Update a Employee --------------------------------------------------------
     
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int id, 
    		                                       @Valid @RequestBody Employee employee){
    		                                      // ,BindingResult bindingResults) {
        
    	Employee actEmployee = employeeService.mergeEmployee (employee, id);
    	return new ResponseEntity<Employee>(actEmployee, HttpStatus.OK);
    }	
    
    //------------------- Delete a Employee --------------------------------------------------------
     
    @RequestMapping(value = "/employee/{ssn}", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("ssn") String ssn) {
 
        Employee employee = employeeService.findEmployeeBySsn(ssn);
        if (employee == null) {      	
            System.out.println("Unable to delete. Employee with ssn " + ssn + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
 
        employeeService.deleteEmployeeBySsn(ssn);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }
 
     
    
    //------------------- Delete All Employees --------------------------------------------------------
     
//    @RequestMapping(value = "/employee/", method = RequestMethod.DELETE)
//    public ResponseEntity<Employee> deleteAllUsers() {
//        System.out.println("Deletingc All Employees");
// 
//        employeeService.deleteAllUsers();
//        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
//    }
    
 
}
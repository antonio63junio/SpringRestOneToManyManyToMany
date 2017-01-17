package com.antonio.servicios;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antonio.dao.EmployeeDao;
import com.antonio.dao.MovimientoDao;

import com.antonio.modelo.Employee;
import com.antonio.modelo.Movimiento;
import com.antonio.modelo.Phone;
import com.antonio.controladores.EmployeeController;
import com.antonio.dao.*;
import com.antonio.servicios.MovimientoService;


@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	
	final static Logger logger = Logger.getLogger("EmployeeServiceImpl");

	@Autowired
	private EmployeeDao dao;
	
    @Autowired
    MovimientoService movimientoService;  //Service which will do all data retrieval/manipulation work
 
    public void registrarMovimiento (String accion, Employee employee){
		java.util.Date jDate = new java.util.Date();
		java.sql.Date sqlDate= new java.sql.Date (jDate.getTime());
		
		Movimiento mov = new Movimiento ();
	    
		mov.setAccion(accion);
		mov.setDate(sqlDate);
		mov.setEmployeeId(employee.getId());
		mov.setEmployeeName(employee.getName());
		mov.setEmployeeJoiningDate(employee.getJoiningDate());
		mov.setEmployeeSalary(employee.getSalary());
		mov.setEmployeeSsn(employee.getSsn());
		
		movimientoService.saveMovimiento(mov);
    }
    
	public Employee findById(int id) {
		return dao.findById(id);
	}

	public void saveEmployee(Employee employee) {
		dao.saveEmployee(employee);
		registrarMovimiento("ALTA", employee);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public Employee mergeEmployee(Employee employee, int id) {		
        Employee currentEmployee = findById(id);
        Employee emp = null;
        
        if (currentEmployee==null) {
            logger.info ("Employee with id " + id + " not found");
            return emp;
        }
 
        currentEmployee.setName(employee.getName());
        currentEmployee.setJoiningDate(employee.getJoiningDate());
        currentEmployee.setSalary(employee.getSalary());
        currentEmployee.setSsn(employee.getSsn()); 
        currentEmployee.setPhoneNumbers(employee.getPhoneNumbers());
        
        try {
            emp = dao.mergeEmployee(currentEmployee);
            registrarMovimiento("MODIFICACION", emp);
        }
          catch (Exception e){
        	  logger.error("mergeEmployee, employee id=" + id + 
        			  ",\n e.toString(): " + e.toString() + 
        			  ",\n e.getMessage(): " + e.getMessage() + 
			          ",\n e.getCause().getMessage(): " + e.getCause().getMessage());
              emp = null;
          }

		return emp;
	}

	public void deleteEmployeeBySsn(String ssn) {
		Employee employee = findEmployeeBySsn(ssn);
		dao.deleteEmployeeBySsn(ssn);
		registrarMovimiento("BAJA", employee);
    }
	
	public List<Employee> findAllEmployees() {
		List<Employee> employees = dao.findAllEmployees();
		logger.debug("num. employees:"  + employees.size());
        if(!employees.isEmpty()){
            logger.debug("num. employees:"  + employees.size());
        }
		return employees;
	}

	public Employee findEmployeeBySsn(String ssn) {
		return dao.findEmployeeBySsn(ssn);
	}

	public boolean isEmployeeSsnUnique(Integer id, String ssn) {
		Employee employee = findEmployeeBySsn(ssn);
		return ( employee == null || ((id != null) && (employee.getId() == id)));
	}
	
	public List<Phone> listEmployeePhones(int id) {
		List<Phone> phones = dao.listEmployeePhones(id);
		return phones;
	}
}

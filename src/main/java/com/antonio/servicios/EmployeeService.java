package com.antonio.servicios;

import java.util.List;

import com.antonio.modelo.Employee;
import com.antonio.modelo.Phone;

public interface EmployeeService {

	Employee findById(int id);
	
	void saveEmployee(Employee employee);
	
	Employee mergeEmployee(Employee employee, int id);
	
	void deleteEmployeeBySsn(String ssn);

	List<Employee> findAllEmployees(); 
	
	Employee findEmployeeBySsn(String ssn);

	boolean isEmployeeSsnUnique(Integer id, String ssn);
	
	List<Phone> listEmployeePhones(int id);
	
	void registrarMovimiento (String accion, Employee employee);
	
}

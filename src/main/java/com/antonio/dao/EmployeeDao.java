package com.antonio.dao;

import java.util.List;
import com.antonio.modelo.Employee;
import com.antonio.modelo.Phone;

public interface EmployeeDao {

	Employee findById(int id);

	void saveEmployee(Employee employee);
	
	void updateEmployee(Employee employee);
	
	Employee mergeEmployee(Employee employee);
	
	void deleteEmployeeById(int id);
	
	void deleteEmployeeBySsn(String ssn);
	
	List<Employee> findAllEmployees();

	Employee findEmployeeBySsn(String ssn);
	
	List<Phone> listEmployeePhones(int id);

}

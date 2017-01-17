package com.antonio.servicios;


import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.joda.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//import com.antonio.servicios.EmployeeService;
import com.antonio.configuracion.HibernateTestConfiguration;
import com.antonio.modelo.Employee;
import com.antonio.modelo.Phone;
 

@ContextConfiguration(classes = { HibernateTestConfiguration.class })
@Transactional

public class EmployeeServiceImpl2Test extends EntityServiceImplTest{


	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	PhoneService phoneService;
	
	@Override
	protected IDataSet getDataSet() throws Exception{
		IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Employee.xml"));
		return dataSet;
	}
	

	public void altaEmpleado(){
		
		Employee employee = new Employee();
		employee.setName("Karen4");
		employee.setSsn("4");
		employee.setSalary(new BigDecimal(10980));
        LocalDate localDate = new LocalDate(); 
		employee.setJoiningDate(java.sql.Date.valueOf(localDate.toString()));
		
		employeeService.saveEmployee(employee);
	}

/*	@Test
	@Rollback(false)
	public void modificacionEmpleado(){
	  Employee employee = employeeService.findById(2);
	  setPhones(employee);
	  
	  employee.setSalary(new BigDecimal(333333));
	  Employee e = employeeService.mergeEmployee(employee);

	  System.out.println("employee after change:" +  e.toString());
	  
	}*/
	
	@Test
	public void getAllEmployees() {
	   List<Employee> employees = employeeService.findAllEmployees();
		  for (Employee e : employees){
	         System.out.println("employee :" +  e.toString());
		  }
	   
	}
	
	@Test
	public void getAllEmployeePhones() {
       List<Phone> phones = employeeService.listEmployeePhones(2);
	  for (Phone p : phones){
		         System.out.println("phones :" +  p.toString());
			  }  
	}
	
	@Test
	public void mergeEmployee (){
		
		BigDecimal salary =  new BigDecimal(4444.44);
		Employee e = employeeService.findById(2);
		e.setSalary(salary);
		Employee newe = employeeService.mergeEmployee(e, e.getId());
		System.out.println("newe: " + newe.toString());
		Assert.assertEquals(newe.getSalary(), salary);
		
	}
	
	/*@Test
	@Rollback(false)
	public void updateEmployeeById(){
		Employee empleado =getSampleEmployee();
		empleado.setId(1);

		Employee emp = employeeService.mergeEmployee(empleado);
		System.out.println("employee before:" +  empleado.toString());
		System.out.println("employee after:" +  emp.toString());
		Assert.assertEquals(empleado.getPhoneNumbers().size(), 2);
	}
*/
	
	public void setPhones (Employee e){
		Phone phone1 = new Phone();
		phone1.setContactnumber("5555551");
		phone1.setPhonetype("TRABAJO");
		phone1.setEmployee(e);
		
		Phone phone2 = new Phone();
		phone2.setContactnumber("5555552");
		phone2.setPhonetype("TRABAJO");
		phone2.setEmployee(e);
		
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone1);
		phones.add(phone2);
		
		e.setPhoneNumbers(phones);
	}
	
	public Employee getSampleEmployee(){
		Employee employee = new Employee();
		employee.setName("Karen");
		employee.setSsn("12346");
		employee.setSalary(new BigDecimal(10980));
        LocalDate localDate = new LocalDate(); 
		employee.setJoiningDate(java.sql.Date.valueOf(localDate.toString()));
		
		Phone phone1 = new Phone();
		phone1.setContactnumber("5555551");
		phone1.setPhonetype("TRABAJO");
		
		Phone phone2 = new Phone();
		phone2.setContactnumber("5555552");
		phone2.setPhonetype("TRABAJO");
		
		Phone phone3 = new Phone();
		phone2.setContactnumber("5555553");
		phone2.setPhonetype("MOVIL PERSONAL");
		
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone1);
		phones.add(phone2);
		phones.add(phone3);
		
		employee.setPhoneNumbers(phones);
		
		return employee;
	}
	
}

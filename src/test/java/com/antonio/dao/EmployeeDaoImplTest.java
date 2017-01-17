package com.antonio.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceContext;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.antonio.modelo.Employee;
import com.antonio.modelo.Phone;

@Transactional

public class EmployeeDaoImplTest extends EntityDaoImplTest{

    
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	PhoneDao phoneDao;

	@Override
	protected IDataSet getDataSet() throws Exception{
		IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Employee.xml"));
		return dataSet;
	}
	
	/* In case you need multiple datasets (mapping different tables) and you do prefer to keep them in separate XML's
	@Override
	protected IDataSet getDataSet() throws Exception {
	  IDataSet[] datasets = new IDataSet[] {
			  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Employee.xml")),
			  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Benefits.xml")),
			  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Departements.xml"))
	  };
	  return new CompositeDataSet(datasets);
	}
	*/

/*	@Test
	public void findById(){
		Assert.assertNotNull(employeeDao.findById(1));
		Assert.assertNull(employeeDao.findById(3));
	}
*/
/*	@Commit
	@Test
	public void saveEmployee(){
		employeeDao.saveEmployee(getSampleEmployee());
		System.out.println("num. employees:" +  employeeDao.findAllEmployees().toString());
		
		System.out.println("num. phones:" +  phoneDao.findAllPhones().toString());

		Assert.assertEquals(employeeDao.findAllEmployees().size(), 3);
	}*/
	
/*	@Commit
	@Test
	public void deleteEmployeeById(){
		employeeDao.deleteEmployeeById(6);
		System.out.println("num. employees:" +  employeeDao.findAllEmployees().toString());
		
		System.out.println("num. phones:" +  phoneDao.findAllPhones().toString());
		Assert.assertEquals(employeeDao.findAllEmployees().size(), 2);
	}
	*/


	
	/*	
	@Test
	public void deleteEmployeeByInvalidSsn(){
		employeeDao.deleteEmployeeBySsn("23423");
		Assert.assertEquals(employeeDao.findAllEmployees().size(), 2);
	}

	@Test
	public void findAllEmployees(){
		Assert.assertEquals(employeeDao.findAllEmployees().size(), 2);
	}
	
	@Test
	public void findEmployeeBySsn(){
		Assert.assertNotNull(employeeDao.findEmployeeBySsn("11111"));
		Assert.assertNull(employeeDao.findEmployeeBySsn("14545"));
	}*/
	
	@Test
	public void updateEmployeeById(){
		
		Employee emp =getSampleEmployee();
		Employee empleado = employeeDao.findById(1);
		if(empleado!=null){
			System.out.println("employee before:" +  empleado.toString());
			empleado.setName(emp.getName());
			empleado.setJoiningDate(emp.getJoiningDate());
			empleado.setSalary(emp.getSalary());
			empleado.setSsn(emp.getSsn());
			empleado.setPhoneNumbers(emp.getPhoneNumbers());
			employeeDao.updateEmployee(empleado);
			System.out.println("employee after:" +  empleado.toString());
			//employeeDao.registrarMovimiento("MODIFICACION", empleado);
			Assert.assertEquals(empleado.getPhoneNumbers().size(), 2);
		}
		
	}

	public Employee getSampleEmployee(){
		Employee employee = new Employee();
		employee.setName("Karen");
		employee.setSsn("12346");
		employee.setSalary(new BigDecimal(10980));
        LocalDate localDate = new LocalDate(); 
		employee.setJoiningDate(java.sql.Date.valueOf(localDate.toString()));
		
		Phone phone1 = new Phone();
		phone1.setContactnumber("5555556");
		phone1.setPhonetype("TRABAJO");
		
		Phone phone2 = new Phone();
		phone2.setContactnumber("5555556");
		phone2.setPhonetype("TRABAJO");
		
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone1);
		phones.add(phone2);
		
		employee.setPhoneNumbers(phones);
		
		return employee;
	}

}

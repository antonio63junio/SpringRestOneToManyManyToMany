package com.antonio.servicios;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.antonio.configuracion.HibernateTestConfiguration;
import com.antonio.dao.PhoneDao;
import com.antonio.modelo.Employee;
import com.antonio.modelo.Phone;

@ContextConfiguration(classes = { HibernateTestConfiguration.class })
@Transactional

public class PhoneServiceImplTest extends EntityServiceImplTest{
    
	@Autowired
	PhoneDao phoneDao;
	
	@Autowired
	PhoneService phoneService;
	
	@Autowired 
	EmployeeService employeeService;
	
	@Override
	protected IDataSet getDataSet() throws Exception{
		IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Phone.xml"));
		return dataSet;
	}
	
	
	@Test 
	public void mergePhone (){
		Phone phone = phoneService.findById(new Long(1));
		phone.setPhonetype("CASERO");
		Phone newPhone = phoneService.mergePhone(phone, new Long(1));
		Assert.assertEquals(newPhone.getPhonetype(),"CASERO");
	}
	
	@Test
	//@Rollback(false)
	public void savePhone (){
		Phone phone = new Phone();
		//phone.setId(new Long(2222));
		phone.setContactnumber("1234567891");
		phone.setPhonetype("CASA");
		
		Employee emp = new Employee();
		emp.setId(2);
		
		//Employee emp = employeeService.findById(2);
			
		phone.setEmployee(emp);
		//Phone newPhone = phoneDao.mergePhone(phone);
		
		phoneDao.persistPhone(phone);
		
		//Phone phone2 = phoneService.findById(new Long(2222));
		//System.out.println("phone2 :" + phone2.toString());
		
		
		
	}
	
}

package com.antonio.servicios;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;


import com.antonio.configuracion.HibernateTestConfiguration;
import com.antonio.dao.EmployeeDao;
import com.antonio.dao.PhoneDao;
import com.antonio.modelo.Phone;


@ContextConfiguration(classes = { HibernateTestConfiguration.class })
public abstract class EntityServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired 
	PhoneDao phoneDao;

/*	@BeforeMethod
	public void setUp() throws Exception {
		IDatabaseConnection dbConn = new DatabaseDataSourceConnection(
				dataSource);
		DatabaseOperation.CLEAN_INSERT.execute(dbConn, getDataSet());
	}
	*/
	protected abstract IDataSet getDataSet() throws Exception;

}
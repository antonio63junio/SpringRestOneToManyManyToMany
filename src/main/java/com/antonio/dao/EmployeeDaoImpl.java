package com.antonio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.antonio.modelo.Employee;
import com.antonio.modelo.Phone;

@Repository("employeeDao")
public class EmployeeDaoImpl extends AbstractDao<Integer, Employee> implements EmployeeDao {

	public Employee findById(int id) {
		return getByKey(id);
	}

	public void saveEmployee(Employee employee) {
		persist(employee);
	}

	//afl
	public void updateEmployee(Employee employee){
		update(employee);
	}
	
	//afl
	public Employee mergeEmployee(Employee employee){
		return merge(employee);
	}
	
	
	public void deleteEmployeeBySsn(String ssn) {
		Query query = getSession().createSQLQuery("delete from Employee where ssn = :ssn");
		query.setString("ssn", ssn);
		query.executeUpdate();
	}
	
	public void deleteEmployeeById(int id) {
		Query query = getSession().createQuery("delete Employee where id = :id");
		query.setParameter("id", id);
		int result = query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
/*	public List<Employee> findAllEmployees() {
		Criteria criteria = createEntityCriteria();
		return (List<Employee>) criteria.list();
	}*/
	
	public List<Employee> findAllEmployees() {
	return createEntityCriteria()  
            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)  
            .list();  
	}
	
	public Employee findEmployeeBySsn(String ssn) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("ssn", ssn));
		return (Employee) criteria.uniqueResult();
	}
	
	public List<Phone> listEmployeePhones(int idEmployee){
		Query q = getSession().createQuery("SELECT p FROM Phone p JOIN FETCH p.employee WHERE p.employee.id = :id");
		q.setParameter("id", idEmployee);
		List<Phone> phones = (List<Phone>) q.list();
		return phones;

	}
	
}

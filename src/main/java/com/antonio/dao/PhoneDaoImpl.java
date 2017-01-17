package com.antonio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.antonio.modelo.Employee;
import com.antonio.modelo.Phone;

@Repository("phoneDao")
public class PhoneDaoImpl extends AbstractDao<Long, Phone> implements PhoneDao {

	public Phone findById(Long id) {
		return getByKey(id);
	}

	public void savePhone(Phone phone) {
		persist(phone);
	}

	//afl
	public void updatePhone(Phone phone){
		update(phone);
	}
	
	//afl
	public void persistPhone(Phone phone){
		persist(phone);
	}
	
	public Phone mergePhone(Phone phone){
		return merge(phone);
	}
	
	public void deletePhone(Long id) {
		Query query = getSession().createSQLQuery("delete from Phone where PHONE_ID = :id");
		query.setLong("id", id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Phone> findAllPhones() {
		Criteria criteria = createEntityCriteria();
		return (List<Phone>) criteria.list();
	}
}

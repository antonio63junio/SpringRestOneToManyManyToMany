package com.antonio.dao;

import java.util.List;
import com.antonio.modelo.Phone;

public interface PhoneDao {
	Phone findById(Long id);
	void savePhone(Phone phone);
	void updatePhone(Phone phone);
	void persistPhone (Phone phone);
    void deletePhone(Long id);
	List<Phone> findAllPhones();
	Phone mergePhone(Phone phone);
}

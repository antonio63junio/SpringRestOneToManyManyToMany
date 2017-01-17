package com.antonio.servicios;

import com.antonio.modelo.Phone;


public interface PhoneService {

    Phone findById (Long id);
    
    void savePhone (Phone phone);
    
	Phone mergePhone (Phone phone, Long id);
	
	boolean cratePhone (Phone phone, int EmployeeId);
	
	void deletePhone (Phone phone);
}

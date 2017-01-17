package com.antonio.servicios;

import java.util.List;
import java.util.Map;

import com.antonio.modelo.Movimiento;

public interface MovimientoService {

	void saveMovimiento(Movimiento movimiento);
	
	List<Movimiento> findAllMovimientos(); 
	
	Map<String, Object> findPageMovimientos(int page, int size, String columnSort, String sentido);
	
	Long countMovimientos();
	
}

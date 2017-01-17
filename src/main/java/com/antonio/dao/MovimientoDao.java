package com.antonio.dao;

import java.util.List;
import java.util.Map;

import com.antonio.modelo.Movimiento;

public interface MovimientoDao {

	void saveMovimiento(Movimiento movimiento);
	List<Movimiento> findAllMovimientos();
	Map<String, Object> findPageMovimientos(int page, int size,String columnSort, String sentido);
	Long countMovimientos();
} 






















































































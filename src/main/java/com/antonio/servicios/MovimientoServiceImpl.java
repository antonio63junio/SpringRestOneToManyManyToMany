package com.antonio.servicios;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.antonio.dao.MovimientoDao;
import com.antonio.modelo.Movimiento;

@Service("movimientoService")
//@Transactional
public class MovimientoServiceImpl implements MovimientoService {

	@Autowired
	private MovimientoDao dao;
	
	public void saveMovimiento(Movimiento movimiento) {
		dao.saveMovimiento(movimiento);
	}

	@Transactional
	public List<Movimiento> findAllMovimientos() {
		return dao.findAllMovimientos();
	}
	
	@Transactional
	public Map<String, Object> findPageMovimientos(int page, int size, String columnSort, String sentido) {
		return dao.findPageMovimientos(page, size, columnSort, sentido);
	}
	
	@Transactional
	public Long countMovimientos(){
		return dao.countMovimientos();
	}
   
	
}

package com.antonio.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.antonio.modelo.Movimiento;

@Repository("movimientoDao")
public class MovimientoDaoImpl extends AbstractDao<Integer, Movimiento> implements MovimientoDao {

	public void saveMovimiento(Movimiento movimiento) {
		persist(movimiento);
	}

	@SuppressWarnings("unchecked")
	public List<Movimiento> findAllMovimientos() {
		Criteria criteria = createEntityCriteria();
		return (List<Movimiento>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> findPageMovimientos(int pageoffset, int size, String columnSort, String sentido) {
		Criteria criteria = createEntityCriteria();
		criteria.setFirstResult(pageoffset * size);
		criteria.setMaxResults(size);
		Order order;
		if ("asc".equals(sentido)){
			order = Order.asc(columnSort);
		} else {
			order = Order.desc(columnSort);
		}
		criteria.addOrder(order);
				
		List<Movimiento> listaMovimientos = criteria.list();

		HashMap<String, Object> page = new HashMap<>();
		page.put("rows", listaMovimientos.size());
		page.put("content", listaMovimientos);
		return (page);
	}
		
	public Long countMovimientos(){
		Criteria criteria = createEntityCriteria();
		criteria.setProjection(Projections.rowCount());
		return  (Long) criteria.uniqueResult();
	}

}

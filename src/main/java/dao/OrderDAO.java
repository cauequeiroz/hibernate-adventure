package dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import dto.SalesReportDTO;
import entity.Order;

public class OrderDAO {
	private EntityManager entityManager;

	public OrderDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Order> getAll() {
		return this.entityManager.createQuery("SELECT o FROM Order o", Order.class).getResultList();
	}
	
	public BigDecimal getTotalSalesPrice() {
		return this.entityManager.createQuery("SELECT SUM(o.totalPrice) FROM Order o", BigDecimal.class).getSingleResult();
	}
	
	public List<SalesReportDTO> getSalesReport() {
		return this.entityManager.createQuery("""
				SELECT new dto.SalesReportDTO(p.name, SUM(i.quantity), MAX(o.date)) FROM
				Order o JOIN o.items i JOIN i.product p
				GROUP BY p.name
				""", SalesReportDTO.class).getResultList();	
	}
}

package dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import dto.SalesReportDTO;
import entity.Order;

public class OrderDAO {
	private EntityManager entityManager;

	public OrderDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Order getById(Long id) {
		return this.entityManager
				.createQuery("SELECT o FROM Order o JOIN FETCH o.client WHERE o.id = :id", Order.class)
				.setParameter("id", id)
				.getSingleResult();
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
	
	public List<Order> getByParameters(Long id, LocalDate date, BigDecimal totalPrice) {
		boolean isIdAvailable = id != null;
		boolean isDateAvailable = date != null;
		boolean isTotalPriceAvailable = totalPrice != null;
		
		String jpql = "SELECT o FROM Order o WHERE 1=1 ";
		if (isIdAvailable) {
			jpql += "AND o.id = :id ";
		}
		if (isDateAvailable) {
			jpql += "AND o.date = :date ";
		}
		if (isTotalPriceAvailable) {
			jpql += "AND o.totalPrice = :totalPrice";
		}
		
		TypedQuery<Order> query = this.entityManager.createQuery(jpql, Order.class);
		if (isIdAvailable) {
			query.setParameter("id", id);
		}
		if (isDateAvailable) {
			query.setParameter("date", date);
		}
		if (isTotalPriceAvailable) {
			query.setParameter("totalPrice", totalPrice);
		}
		
		return query.getResultList();
	}
	
	public List<Order> getByParametersWithCriteariaAPI(Long id, LocalDate date, BigDecimal totalPrice) {
		boolean isIdAvailable = id != null;
		boolean isDateAvailable = date != null;
		boolean isTotalPriceAvailable = totalPrice != null;
		
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
		Root<Order> table = query.from(Order.class);		
		Predicate parameters = criteriaBuilder.and();
		
		if (isIdAvailable) {
			parameters = criteriaBuilder.and(parameters, criteriaBuilder.equal(table.get("id"), id));
		}
		if (isDateAvailable) {
			parameters = criteriaBuilder.and(parameters, criteriaBuilder.equal(table.get("date"), date));
		}
		if (isTotalPriceAvailable) {
			parameters = criteriaBuilder.and(parameters, criteriaBuilder.equal(table.get("totalPrice"), totalPrice));
		}
		
		query.where(parameters);
		
		return this.entityManager.createQuery(query).getResultList();
	}
}

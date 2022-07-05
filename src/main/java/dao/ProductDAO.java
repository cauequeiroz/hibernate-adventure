package dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import entity.Product;

public class ProductDAO {
	private EntityManager entityManager;

	public ProductDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void save(Product product) {	
		this.entityManager.persist(product);
	}
	
	public Product getById(Long id) {
		return this.entityManager.find(Product.class, id);
	}
	
	public List<Product> getAll() {
		return this.entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
	}
	
	public Product getByCategoryName(String name) {
		return this.entityManager
				.createQuery("SELECT p FROM Product p WHERE p.category.name = :name", Product.class)
				.setParameter("name", name)
				.getSingleResult();
	}
	
	public BigDecimal getPriceByName(String name) {
		return this.entityManager
				.createQuery("SELECT p.price FROM Product p WHERE p.name = :name", BigDecimal.class)
				.setParameter("name", name)
				.getSingleResult();
	}
}

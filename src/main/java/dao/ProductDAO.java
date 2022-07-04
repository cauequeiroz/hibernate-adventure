package dao;

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
}

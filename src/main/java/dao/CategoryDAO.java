package dao;

import javax.persistence.EntityManager;

import entity.Category;

public class CategoryDAO {
	private EntityManager entityManager;
	
	public CategoryDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void save(Category category) {
		this.entityManager.persist(category);
	}
}

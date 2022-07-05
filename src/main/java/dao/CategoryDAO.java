package dao;

import java.util.List;

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
	
	public void remove(Category category) {
		if (!this.entityManager.contains(category)) {
			category = this.entityManager.merge(category);
		}
		this.entityManager.remove(category);
	}
	
	public Category getById(Long id) {
		return this.entityManager.find(Category.class, id);
	}
	
	public List<Category> getAll() {
		return this.entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
	}
	
}

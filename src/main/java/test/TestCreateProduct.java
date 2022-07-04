package test;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import dao.CategoryDAO;
import dao.ProductDAO;
import entity.Category;
import entity.Product;
import util.JPAUtil;

public class TestCreateProduct {
	public static void main(String[] args) {
		EntityManager entityManager = JPAUtil.getEntityManager();
				
		ProductDAO productDAO = new ProductDAO(entityManager);
		CategoryDAO categoryDAO = new CategoryDAO(entityManager);
		
		Category games = new Category("Games");
		
		Product xbox = new Product();
		xbox.setName("Xbox 360");
		xbox.setDescription("A microsoft game console");
		xbox.setPrice(new BigDecimal("1499.99"));
		xbox.setCategory(games);		
		
		entityManager.getTransaction().begin();
		
		categoryDAO.save(games);
		productDAO.save(xbox);
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
}

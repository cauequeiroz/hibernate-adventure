package test;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import dao.CategoryDAO;
import dao.ProductDAO;
import entity.Category;
import entity.Product;
import util.JPAUtil;

public class TestFindProduct {
	public static void main(String[] args) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		CategoryDAO categoryDAO = new CategoryDAO(entityManager);
		ProductDAO productDAO = new ProductDAO(entityManager);
		
		createCategories(entityManager, categoryDAO);
		createProducts(entityManager, productDAO);
		
		categoryDAO.getAll().forEach(System.out::println);
		productDAO.getAll().forEach(System.out::println);
		
		// Find product by category name
		System.out.println(productDAO.getByCategoryName("Garden"));
		
		// Get price
		System.out.println(productDAO.getPriceByName("Xbox 360"));
		
		entityManager.close();
	}
	
	private static void createCategories(EntityManager entityManager, CategoryDAO categoryDAO) {
		Category category1 = new Category("Smartphone");
		Category category2 = new Category("Home");
		Category category3 = new Category("Garden");
		
		entityManager.getTransaction().begin();
		categoryDAO.save(category1);
		categoryDAO.save(category2);
		categoryDAO.save(category3);
		entityManager.getTransaction().commit();		
	}

	private static void createProducts(EntityManager entityManager, ProductDAO productDAO) {
		List<Category> categories = new CategoryDAO(entityManager).getAll();
		
		Product xbox = new Product();
		xbox.setName("Xbox 360");
		xbox.setDescription("A microsoft game console");
		xbox.setPrice(new BigDecimal("1499.99"));
		xbox.setCategory(categories.get(0));
		
		Product faucet = new Product();
		faucet.setName("Faucet");
		faucet.setDescription("To wash your dishes.");
		faucet.setPrice(new BigDecimal("299.99"));
		faucet.setCategory(categories.get(2));
		
		entityManager.getTransaction().begin();
		productDAO.save(xbox);
		productDAO.save(faucet);
		entityManager.getTransaction().commit();		
	}
}

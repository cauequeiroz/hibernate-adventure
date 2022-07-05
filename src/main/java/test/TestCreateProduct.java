package test;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import dao.CategoryDAO;
import dao.ProductDAO;
import entity.Category;
import entity.Product;
import util.JPAUtil;

public class TestCreateProduct {
	public static void main(String[] args) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		// Create Product
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
		
		// Create categories
		Category category1 = new Category("Smartphone");
		Category category2 = new Category("Home");
		Category category3 = new Category("Garden");
		
		entityManager.getTransaction().begin();
		
		categoryDAO.save(category1);
		categoryDAO.save(category2);
		categoryDAO.save(category3);		
		entityManager.flush();
		
		// Get category by ID
		Category categoryX = categoryDAO.getById(2l);
		System.out.println(categoryX);
		
		// Get all categories
		List<Category> categories = categoryDAO.getAll();
		categories.forEach(System.out::println);
		
		// Remove category
		Category categoryGarden = categoryDAO.getById(4l);
		categoryDAO.remove(categoryGarden);	
		categoryDAO.getAll().forEach(System.out::println);
		entityManager.flush();
		
		// Remove not managed category
		entityManager.clear();
		Category categoryHome = category2;
		categoryDAO.remove(categoryHome);
		entityManager.flush();
		categoryDAO.getAll().forEach(System.out::println);
		
		// Update a category
		Category categoryY = categoryDAO.getById(1l);
		categoryY.setName("Game Console");
		entityManager.flush();
		
		// Get product
		System.out.println(productDAO.getById(1l));
		
		entityManager.close();
	}
	
}

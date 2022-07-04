package test;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dao.ProductDAO;
import entity.Product;
import util.JPAUtil;

public class TestCreateProduct {
	public static void main(String[] args) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		ProductDAO productDAO = new ProductDAO(entityManager);
		
		Product xbox = new Product();
		xbox.setName("Xbox 360");
		xbox.setDescription("A microsoft game console");
		xbox.setPrice(new BigDecimal("1499.99"));
		
		entityManager.getTransaction().begin();
		productDAO.save(xbox);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
}

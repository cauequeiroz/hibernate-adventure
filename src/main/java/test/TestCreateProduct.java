package test;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.Product;

public class TestCreateProduct {
	public static void main(String[] args) {
		Product xbox = new Product();
		xbox.setName("Xbox 360");
		xbox.setDescription("A microsoft game console");
		xbox.setPrice(new BigDecimal("1499.99"));
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ecommerce");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		entityManager.persist(xbox);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
}

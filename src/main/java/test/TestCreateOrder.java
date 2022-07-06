package test;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import entity.Category;
import entity.Client;
import entity.Order;
import entity.OrderItem;
import entity.Product;
import util.JPAUtil;

public class TestCreateOrder {
	public static void main(String[] args) {
		Client client = new Client("Caue Queiroz", "334546434-80");
		
		Category category = new Category("Games");
		
		Product product = new Product();
		product.setName("Xbox 360");
		product.setDescription("A microsoft game console");
		product.setPrice(new BigDecimal("1499.99"));
		product.setCategory(category);		
		
		Order order = new Order(client);
		order.addItem(new OrderItem(2, product));
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		entityManager.getTransaction().begin();
		entityManager.persist(client);
		entityManager.persist(category);
		entityManager.persist(product);
		entityManager.persist(order);
		entityManager.getTransaction().commit();
		
		List<Order> resultList = entityManager.createQuery("SELECT o FROM Order o", Order.class).getResultList();
		resultList.forEach(resultOrder -> {
			System.out.println(resultOrder.getId());
			System.out.println(resultOrder.getClient().getName());
			System.out.println(resultOrder.getDate());
			System.out.println(resultOrder.getTotalPrice());
			System.out.println(resultOrder.getItems().get(0).getProduct().getName());
		});		
	}
}

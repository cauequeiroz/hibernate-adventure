package test;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import dao.OrderDAO;
import dto.SalesReportDTO;
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
		product.setPrice(new BigDecimal("2000.00"));
		product.setCategory(category);	
		
		Product product2 = new Product();
		product2.setName("Xbox One");
		product2.setDescription("A microsoft game console");
		product2.setPrice(new BigDecimal("3000.00"));
		product2.setCategory(category);	
		
		Order order1 = new Order(client);
		order1.addItem(new OrderItem(2, product));
		order1.addItem(new OrderItem(1, product));
		
		Order order2 = new Order(client);
		order2.addItem(new OrderItem(7, product));
		order2.addItem(new OrderItem(3, product2));
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		entityManager.getTransaction().begin();
		entityManager.persist(client);
		entityManager.persist(category);
		entityManager.persist(product);
		entityManager.persist(product2);
		entityManager.persist(order1);
		entityManager.persist(order2);
		entityManager.getTransaction().commit();
		
		OrderDAO orderDAO = new OrderDAO(entityManager);
		
		List<Order> resultList = orderDAO.getAll();
		resultList.forEach(System.out::println);
		
		System.out.println(orderDAO.getTotalSalesPrice());
		
		List<SalesReportDTO> salesReport = orderDAO.getSalesReport();
		salesReport.forEach(System.out::println);
	}
}

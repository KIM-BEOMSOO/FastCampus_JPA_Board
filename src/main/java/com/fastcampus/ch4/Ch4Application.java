package com.fastcampus.ch4;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Ch4Application implements CommandLineRunner {

	@Autowired
	EntityManagerFactory emf;

	public static void main(String[] args){
		SpringApplication app = new SpringApplication(Ch4Application.class);
//		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
//		EntityManager em = emf.createEntityManager();
//		System.out.println("em = " + em);
//		EntityTransaction tx = em.getTransaction();
//
//		User user = new User();
//		user.setId("ccc");
//		user.setPassword("4321");
//		user.setName("Lee");
//		user.setEmail("aaa@aaa.com");
//		user.setInDate(new Date());
//		user.setUpDate(new Date());
//
//		tx.begin();
//
//		// 1. 저장
//		em.persist(user);
//
//		// 2. 변경
//		user.setPassword("4444");
//		user.setEmail("QQQQ@QQQ.com");
//		tx.commit();
//
//		// 3. 조회
//		User user2 = em.find(User.class, "ccc");
//		System.out.println("user==user2 = " + (user == user2));
//
//		// 4. 삭제
//		tx.begin();
//		em.remove(user);
//		tx.commit();
	}
}

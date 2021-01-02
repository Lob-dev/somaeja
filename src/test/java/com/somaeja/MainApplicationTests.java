package com.somaeja;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootTest
class MainApplicationTests {
	@Test
	void contextLoads() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MainApplication.class);
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println("beanDefinitionName = " + beanDefinitionName);
		}
	}
}

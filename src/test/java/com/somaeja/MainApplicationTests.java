package com.somaeja;

import com.somaeja.common.config.PersistenceConfig;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
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

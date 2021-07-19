package com.sf.ennahdi.example.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.sf.ennahdi.example.dao.repository.FilmRepository;

@SpringBootApplication(scanBasePackages="com.sf.ennahdi.example")
@EnableJpaRepositories(basePackageClasses = {FilmRepository.class})
@EntityScan(basePackages = "com.sf.ennahdi.example.dao.entity")
public class FilmApp {
	
	 private static ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(FilmApp.class);
//		displayAllBeans();
	}
	
	
	public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }
}

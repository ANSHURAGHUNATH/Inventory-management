package com.amazon.inventorymanagementsystem;

import com.amazon.inventorymanagementsystem.model.AddProductEvent;
import com.amazon.inventorymanagementsystem.model.ProductResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class InventoryManagementSystemApplication {

	private static Map userDetailsMap;

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(InventoryManagementSystemApplication.class, args);
		InMemoryUserDetailsManager inMemoryUserDetailsManager = (InMemoryUserDetailsManager)context.getBean("userDetailsService");
		Field usersMapField = ReflectionUtils.findField(InMemoryUserDetailsManager.class, "users");
		ReflectionUtils.makeAccessible(usersMapField);
		userDetailsMap = (Map)ReflectionUtils.getField(usersMapField, inMemoryUserDetailsManager);
	}

	@EventListener
	public void notifyAllUsers(AddProductEvent addProductEvent) {
		userDetailsMap.keySet().forEach(user->System.out.println("Hi" + " " + user + "!" + " " + addProductEvent.getProductName() + " " + "has been added to inventory"));

	}

}

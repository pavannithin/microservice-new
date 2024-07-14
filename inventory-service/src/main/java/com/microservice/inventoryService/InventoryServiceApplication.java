package com.microservice.inventoryService;

import com.microservice.inventoryService.model.Inventory;
import com.microservice.inventoryService.repos.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("Iphone_15");
			inventory.setStock(100);
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("Iphone_15_red");
			inventory1.setStock(0);
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};

	}
}

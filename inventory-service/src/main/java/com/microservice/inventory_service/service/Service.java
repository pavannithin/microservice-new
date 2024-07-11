package com.microservice.inventory_service.service;

import com.microservice.inventory_service.model.Inventory;
import com.microservice.inventory_service.repos.InventoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {
private final InventoryRepository inventoryRepository;
    public boolean isInStock(String skuCode) {
        Optional<Inventory> allBySkuCode = inventoryRepository.findAllBySkuCode(skuCode);
        return allBySkuCode.isPresent();
    }
}

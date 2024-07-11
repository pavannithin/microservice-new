package com.microservice.inventory_service.service;

import com.microservice.inventory_service.dto.InventoryResponse;
import com.microservice.inventory_service.model.Inventory;
import com.microservice.inventory_service.repos.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        List<Inventory> allBySkuCode = inventoryRepository.findBySkuCodeIn(skuCode);
        List<InventoryResponse> inventoryResponses = allBySkuCode.stream().map(eachInventory -> InventoryResponse.builder()
                .skuCode(eachInventory.getSkuCode()).isInStock(eachInventory.getStock() > 0).build()).toList();
        return inventoryResponses;
    }
}

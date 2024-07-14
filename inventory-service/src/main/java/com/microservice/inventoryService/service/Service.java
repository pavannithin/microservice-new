package com.microservice.inventoryService.service;

import com.microservice.inventoryService.dto.InventoryResponse;
import com.microservice.inventoryService.model.Inventory;
import com.microservice.inventoryService.repos.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

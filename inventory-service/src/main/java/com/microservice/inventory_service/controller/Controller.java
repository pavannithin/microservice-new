package com.microservice.inventory_service.controller;

import com.microservice.inventory_service.dto.InventoryResponse;
import com.microservice.inventory_service.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class Controller {

    private final Service service;

//    initial url: localhost:8082/api/inventory/iphone_13
//    changed to accept to check status for multiple products url:
//    localhost:8082/inventory/iphone_13,iphone_14 if we take @PathVariable("sku-code") List<String> skuCode not more readable
//    localhost:8082/inventory?sku-code=iphone_13&sku-code=iphone_14 if we take @RequestParam("sku-code") List<String>
//    skuCode more readable
    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam("sku-code") List<String> skuCode){
        return service.isInStock(skuCode);
    }
}

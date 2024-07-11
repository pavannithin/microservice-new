package com.microservice.orderService.service;

import com.microservice.orderService.dto.InventoryResponse;
import com.microservice.orderService.dto.OrderLineItemsDto;
import com.microservice.orderService.dto.OrderRequest;
import com.microservice.orderService.model.Order;
import com.microservice.orderService.model.OrderLineItems;
import com.microservice.orderService.repos.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItems().stream().map(this::mapToDto).toList();
        order.setOrderLineItems(orderLineItems);
        List<String> orderSKUCodesList = order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();
        /*
        Before placing order  willi will check if all the
         products are present by calling inventory service API
          using web client(webclient supports both synchronous
          and asynchronous calls then rest template)
        */

        /* Body to mono method takes the return type of the API */

        InventoryResponse[] inventoryResponses = webClient.get().uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", orderSKUCodesList).build())
                .retrieve().bodyToMono(InventoryResponse[].class)
                .block();
        boolean isAllProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsInStock);
        if (isAllProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in the stock, please try again later");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}

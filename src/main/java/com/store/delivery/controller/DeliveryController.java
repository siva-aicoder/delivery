package com.store.delivery.controller;

import com.store.delivery.entity.OrderDetails;
import com.store.delivery.service.OrderDeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/delivery" )
public class DeliveryController {

    private final List<OrderDetails> orderDetails;
    private final OrderDeliveryService orderDeliveryService;

    public DeliveryController(List<OrderDetails> orderDetails, OrderDeliveryService orderDeliveryService) {
        this.orderDetails = orderDetails;
        this.orderDeliveryService = orderDeliveryService;
    }

    @GetMapping("/orderDetails")
    public ResponseEntity<List<OrderDetails>> orderDetails() {
        return new ResponseEntity<>(orderDetails, HttpStatus.ACCEPTED);

    }

    @GetMapping("/orderDeliveryMapping/store")
    public ResponseEntity<List<OrderDetails>> orderDeliveryMappingStore() {

        List<OrderDetails> deliveryMappedOrders = orderDeliveryService.deliveryStoreMapping(orderDetails);

        return new ResponseEntity<>(deliveryMappedOrders, HttpStatus.ACCEPTED);
    }

    @GetMapping("/orderDeliveryMapping/unscheduledStore")
    public ResponseEntity<List<OrderDetails>> orderDeliveryMappingUnscheduledStore() {

        List<OrderDetails> deliveryMappedOrders = orderDeliveryService.deliveryUnscheduledStoreMapping(orderDetails);

        return new ResponseEntity<>(deliveryMappedOrders, HttpStatus.ACCEPTED);
    }

}

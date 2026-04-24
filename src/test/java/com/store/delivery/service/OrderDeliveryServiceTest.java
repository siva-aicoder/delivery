package com.store.delivery.service;

import com.store.delivery.constant.DeliveryServiceConstants;
import com.store.delivery.entity.OrderDetails;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDeliveryServiceTest {

    private final OrderDeliveryService orderDeliveryService = new OrderDeliveryService();

    @Test
    void deliveryUnscheduledStoreMappingAssignsStoreFromDeliveryCity() {
        List<OrderDetails> orderDetails = List.of(
                OrderDetails.builder()
                        .orderId(1)
                        .deliveryCity("Manchester")
                        .deliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME)
                        .build(),
                OrderDetails.builder()
                        .orderId(2)
                        .deliveryCity("London")
                        .deliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME)
                        .build(),
                OrderDetails.builder()
                        .orderId(3)
                        .deliveryCity("Cambridge")
                        .deliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME)
                        .build()
        );

        List<OrderDetails> mappedOrders = orderDeliveryService.deliveryUnscheduledStoreMapping(orderDetails);

        assertEquals(DeliveryServiceConstants.MANCHESTER_STORE_NAME, mappedOrders.get(0).getDeliveryStoreName());
        assertEquals(DeliveryServiceConstants.LONDON_STORE_NAME, mappedOrders.get(1).getDeliveryStoreName());
        assertEquals(DeliveryServiceConstants.CAMBRIDGE_STORE_NAME, mappedOrders.get(2).getDeliveryStoreName());
    }
}

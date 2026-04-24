package com.store.delivery.service;

import com.store.delivery.constant.DeliveryServiceConstants;
import com.store.delivery.entity.OrderDetails;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDeliveryServiceTest {

    private final OrderDeliveryService orderDeliveryService = new OrderDeliveryService();

    @Test
    void deliveryUnscheduledStoreMappingAssignsNearestStoreForSameCity() {
        OrderDetails scheduledEarlier = OrderDetails.builder()
                .orderId(1)
                .deliveryCity("London")
                .orderTimestamp("10:00:00")
                .deliveryStoreName("London EX")
                .build();

        OrderDetails unscheduled = OrderDetails.builder()
                .orderId(2)
                .deliveryCity("London")
                .orderTimestamp("10:05:00")
                .deliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME)
                .build();

        OrderDetails scheduledLater = OrderDetails.builder()
                .orderId(3)
                .deliveryCity("London")
                .orderTimestamp("10:30:00")
                .deliveryStoreName("London SM")
                .build();

        List<OrderDetails> mappedOrders = orderDeliveryService.deliveryUnscheduledStoreMapping(
                List.of(scheduledEarlier, unscheduled, scheduledLater)
        );

        assertEquals("London EX", mappedOrders.get(1).getDeliveryStoreName());
    }

    @Test
    void deliveryUnscheduledStoreMappingFallsBackToCityDefaultWhenNoScheduledStoreExists() {
        OrderDetails unscheduled = OrderDetails.builder()
                .orderId(4)
                .deliveryCity("Cambridge")
                .orderTimestamp("07:30:00")
                .deliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME)
                .build();

        List<OrderDetails> mappedOrders = orderDeliveryService.deliveryUnscheduledStoreMapping(
                List.of(unscheduled)
        );

        assertEquals(
                DeliveryServiceConstants.CAMBRIDGE_STORE_NAME,
                mappedOrders.get(0).getDeliveryStoreName()
        );
    }
}

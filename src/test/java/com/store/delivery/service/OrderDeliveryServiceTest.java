package com.store.delivery.service;

import com.store.delivery.constant.DeliveryServiceConstants;
import com.store.delivery.entity.OrderDetails;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDeliveryServiceTest {

    private final OrderDeliveryService orderDeliveryService = new OrderDeliveryService();

    @Test
    void deliveryStoreMappingKeepsOrderLevelStoreNameOutsideBusinessHours() {
        OrderDetails orderDetails = OrderDetails.builder()
                .orderId(30)
                .deliveryCity("Cambridge")
                .orderTimestamp("18:53:00")
                .deliveryStoreName("Cambridge SM")
                .build();

        List<OrderDetails> mappedOrders = orderDeliveryService.deliveryStoreMapping(List.of(orderDetails));

        assertEquals("Cambridge SM", mappedOrders.get(0).getDeliveryStoreName());
    }

    @Test
    void deliveryUnscheduledStoreMappingUsesCityStoreInsteadOfDefaultManchester() {
        OrderDetails orderDetails = OrderDetails.builder()
                .orderId(11)
                .deliveryCity("London")
                .orderTimestamp("08:00:00")
                .deliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME)
                .build();

        List<OrderDetails> mappedOrders = orderDeliveryService.deliveryUnscheduledStoreMapping(List.of(orderDetails));

        assertEquals(DeliveryServiceConstants.LONDON_STORE_NAME, mappedOrders.get(0).getDeliveryStoreName());
    }
}

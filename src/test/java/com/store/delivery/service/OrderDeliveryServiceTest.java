package com.store.delivery.service;

import com.store.delivery.constant.DeliveryServiceConstants;
import com.store.delivery.entity.OrderDetails;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDeliveryServiceTest {

    private final OrderDeliveryService orderDeliveryService = new OrderDeliveryService();

    @Test
    void deliveryStoreMappingMarksOutsideStoreHoursAsUnscheduled() {
        OrderDetails order = OrderDetails.builder()
                .orderId(1)
                .deliveryCity("Delhi")
                .orderTimestamp("18:00:00")
                .deliveryStoreName("")
                .build();

        List<OrderDetails> mappedOrders = orderDeliveryService.deliveryStoreMapping(List.of(order));

        assertEquals(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME,
                mappedOrders.get(0).getDeliveryStoreName());
    }

    @Test
    void deliveryUnscheduledStoreMappingAssignsCorrectCityStoreForUnscheduledOrder() {
        OrderDetails order = OrderDetails.builder()
                .orderId(2)
                .deliveryCity("Hyderabad")
                .orderTimestamp("18:53:00")
                .deliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME)
                .build();

        List<OrderDetails> mappedOrders = orderDeliveryService.deliveryUnscheduledStoreMapping(List.of(order));

        assertEquals(DeliveryServiceConstants.HYDERABAD_STORE_NAME,
                mappedOrders.get(0).getDeliveryStoreName());
    }
}

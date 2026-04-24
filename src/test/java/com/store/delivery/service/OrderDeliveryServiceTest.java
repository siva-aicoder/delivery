package com.store.delivery.service;

import com.store.delivery.constant.DeliveryServiceConstants;
import com.store.delivery.entity.OrderDetails;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDeliveryServiceTest {

    private final OrderDeliveryService orderDeliveryService = new OrderDeliveryService();

    @Test
    void deliveryUnscheduledStoreMappingAssignsLondonStoreByCity() {
        OrderDetails orderDetails = OrderDetails.builder()
                .deliveryCity("London")
                .deliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME)
                .build();

        List<OrderDetails> deliveryMappedOrders =
                orderDeliveryService.deliveryUnscheduledStoreMapping(List.of(orderDetails));

        assertEquals(
                DeliveryServiceConstants.LONDON_STORE_NAME,
                deliveryMappedOrders.get(0).getDeliveryStoreName()
        );
    }

    @Test
    void deliveryUnscheduledStoreMappingAssignsCambridgeStoreByCity() {
        OrderDetails orderDetails = OrderDetails.builder()
                .deliveryCity("Cambridge")
                .deliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME)
                .build();

        List<OrderDetails> deliveryMappedOrders =
                orderDeliveryService.deliveryUnscheduledStoreMapping(List.of(orderDetails));

        assertEquals(
                DeliveryServiceConstants.CAMBRIDGE_STORE_NAME,
                deliveryMappedOrders.get(0).getDeliveryStoreName()
        );
    }
}

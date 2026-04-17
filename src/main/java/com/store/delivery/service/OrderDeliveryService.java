package com.store.delivery.service;

import com.store.delivery.constant.DeliveryServiceConstants;
import com.store.delivery.entity.OrderDetails;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;


@Service
public class OrderDeliveryService {

    public List<OrderDetails> deliveryStoreMapping(List<OrderDetails> orderDetails) {

        orderDetails.forEach(order -> order.setDeliveryStoreName(resolveStoreName(order, false)));

        return orderDetails;
    }

    public List<OrderDetails> deliveryUnscheduledStoreMapping(List<OrderDetails> orderDetails) {
        orderDetails.forEach(order -> {
            if (isUnscheduledOrder(order)) {
                order.setDeliveryStoreName(resolveStoreName(order, true));
            }
        });

        return orderDetails;
    }

    private String resolveStoreName(OrderDetails order, boolean includeUnscheduledOrders) {
        if (!includeUnscheduledOrders && isOutsideStoreHours(order.getOrderTimestamp())) {
            return DeliveryServiceConstants.UNSCHEDULED_STORE_NAME;
        }

        return resolveStoreNameByCity(order.getDeliveryCity());
    }

    private boolean isUnscheduledOrder(OrderDetails order) {
        return DeliveryServiceConstants.UNSCHEDULED_STORE_NAME.equals(order.getDeliveryStoreName()) ||
                isOutsideStoreHours(order.getOrderTimestamp());
    }

    private boolean isOutsideStoreHours(String orderTimestamp) {
        LocalTime orderTime = LocalTime.parse(orderTimestamp, DeliveryServiceConstants.TIME_FORMATTER);

        return orderTime.isBefore(DeliveryServiceConstants.OPENING_TIME) ||
                orderTime.isAfter(DeliveryServiceConstants.CLOSING_TIME) ||
                orderTime.equals(DeliveryServiceConstants.CLOSING_TIME);
    }

    private String resolveStoreNameByCity(String deliveryCity) {
        if ("Chennai".equals(deliveryCity)) {
            return DeliveryServiceConstants.CHENNAI_STORE_NAME;
        }
        if ("Delhi".equals(deliveryCity)) {
            return DeliveryServiceConstants.DELHI_STORE_NAME;
        }
        if ("Hyderabad".equals(deliveryCity)) {
            return DeliveryServiceConstants.HYDERABAD_STORE_NAME;
        }

        return DeliveryServiceConstants.UNSCHEDULED_STORE_NAME;
    }

}

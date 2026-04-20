package com.store.delivery.service;

import com.store.delivery.constant.DeliveryServiceConstants;
import com.store.delivery.entity.OrderDetails;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;


@Service
public class OrderDeliveryService {

    public List<OrderDetails> deliveryStoreMapping(List<OrderDetails> orderDetails){
        orderDetails.forEach(order -> {
            if (isWithinStoreHours(order)) {
                order.setDeliveryStoreName(resolveStoreNameByCity(order.getDeliveryCity()));
            } else {
                order.setDeliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME);
            }
        });

        return orderDetails;

    }

    public List<OrderDetails> deliveryUnscheduledStoreMapping(List<OrderDetails> orderDetails){
        deliveryStoreMapping(orderDetails);

        orderDetails.forEach(order -> {
            if (DeliveryServiceConstants.UNSCHEDULED_STORE_NAME.equals(order.getDeliveryStoreName())) {
                order.setDeliveryStoreName(resolveStoreNameByCity(order.getDeliveryCity()));
            }
        });

        return orderDetails;
    }

    private boolean isWithinStoreHours(OrderDetails order) {
        LocalTime orderTime = LocalTime.parse(
                order.getOrderTimestamp(),
                DeliveryServiceConstants.TIME_FORMATTER
        );

        return !orderTime.isBefore(DeliveryServiceConstants.OPENING_TIME) &&
                !orderTime.isAfter(DeliveryServiceConstants.CLOSING_TIME);
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

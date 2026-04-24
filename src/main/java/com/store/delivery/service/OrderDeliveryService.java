package com.store.delivery.service;

import com.store.delivery.constant.DeliveryServiceConstants;
import com.store.delivery.entity.OrderDetails;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;


@Service
public class OrderDeliveryService {

    public List<OrderDetails> deliveryStoreMapping(List<OrderDetails> orderDetails){

        orderDetails.forEach(order ->{
            if(isWithinOperatingHours(order.getOrderTimestamp())){
                assignDeliveryStoreName(order);
            } else {
                order.setDeliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME);
            }
        });

        return orderDetails;

    }

    public List<OrderDetails> deliveryUnscheduledStoreMapping(List<OrderDetails> orderDetails){
        orderDetails.forEach(order ->{
            assignDeliveryStoreName(order);
        });

        return orderDetails;
    }

    private boolean isWithinOperatingHours(String orderTimestamp) {
        LocalTime orderTime = LocalTime.parse(
                orderTimestamp,
                DeliveryServiceConstants.TIME_FORMATTER
        );

        return !orderTime.isBefore(DeliveryServiceConstants.OPENING_TIME) &&
                !orderTime.isAfter(DeliveryServiceConstants.CLOSING_TIME);
    }

    private void assignDeliveryStoreName(OrderDetails order) {
        if ("Manchester".equals(order.getDeliveryCity())) {
            order.setDeliveryStoreName(DeliveryServiceConstants.MANCHESTER_STORE_NAME);
        } else if ("London".equals(order.getDeliveryCity())) {
            order.setDeliveryStoreName(DeliveryServiceConstants.LONDON_STORE_NAME);
        } else if ("Cambridge".equals(order.getDeliveryCity())) {
            order.setDeliveryStoreName(DeliveryServiceConstants.CAMBRIDGE_STORE_NAME);
        }
    }

}

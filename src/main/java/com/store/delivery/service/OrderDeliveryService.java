package com.store.delivery.service;

import com.store.delivery.constant.DeliveryServiceConstants;
import com.store.delivery.entity.OrderDetails;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


@Service
public class OrderDeliveryService {

    public List<OrderDetails> deliveryStoreMapping(List<OrderDetails> orderDetails){

        orderDetails.forEach(order ->{
            LocalTime orderTime = getOrderTime(order);

            if(isWithinHours(orderTime)){
                order.setDeliveryStoreName(resolveStoreNameByCity(order.getDeliveryCity()));
            }else{
                order.setDeliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME);
            }
        });

        return orderDetails;
    }

    public List<OrderDetails> deliveryUnscheduledStoreMapping(List<OrderDetails> orderDetails){
        orderDetails.forEach(order ->{
            LocalTime orderTime = getOrderTime(order);

            if(!isWithinHours(orderTime) &&
                    Objects.equals(order.getDeliveryStoreName(), DeliveryServiceConstants.UNSCHEDULED_STORE_NAME)){
                order.setDeliveryStoreName(resolveStoreNameByCity(order.getDeliveryCity()));
            }
        });

        return orderDetails;
    }

    private LocalTime getOrderTime(OrderDetails order) {
        return LocalTime.parse(
                order.getOrderTimestamp(),
                DeliveryServiceConstants.TIME_FORMATTER
        );
    }

    private boolean isWithinHours(LocalTime orderTime) {
        return !orderTime.isBefore(DeliveryServiceConstants.OPENING_TIME) &&
                !orderTime.isAfter(DeliveryServiceConstants.CLOSING_TIME);
    }

    private String resolveStoreNameByCity(String deliveryCity) {
        if (Objects.equals(deliveryCity, "Manchester")) {
            return DeliveryServiceConstants.MANCHESTER_STORE_NAME;
        } else if (Objects.equals(deliveryCity, "London")) {
            return DeliveryServiceConstants.LONDON_STORE_NAME;
        } else if (Objects.equals(deliveryCity, "Cambridge")) {
            return DeliveryServiceConstants.CAMBRIDGE_STORE_NAME;
        }

        return DeliveryServiceConstants.UNSCHEDULED_STORE_NAME;
    }

}

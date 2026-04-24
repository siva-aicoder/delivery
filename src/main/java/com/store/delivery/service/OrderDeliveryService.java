package com.store.delivery.service;

import com.store.delivery.constant.DeliveryServiceConstants;
import com.store.delivery.entity.OrderDetails;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;


@Service
public class OrderDeliveryService {

    public List<OrderDetails> deliveryStoreMapping(List<OrderDetails> orderDetails){

        orderDetails.forEach(order ->{
            LocalTime orderTime = LocalTime.parse(
                    order.getOrderTimestamp(),
                    DeliveryServiceConstants.TIME_FORMATTER
            );

            boolean isWithinHours = orderTime.isAfter(DeliveryServiceConstants.OPENING_TIME) &&
                    orderTime.isBefore(DeliveryServiceConstants.CLOSING_TIME);

            if(isWithinHours){
                 if(order.getDeliveryCity().equals("Manchester")){
                    order.setDeliveryStoreName(DeliveryServiceConstants.MANCHESTER_STORE_NAME);
                }else if(order.getDeliveryCity().equals("London")){
                    order.setDeliveryStoreName(DeliveryServiceConstants.LONDON_STORE_NAME);
                }else if(order.getDeliveryCity().equals("Cambridge")){
                    order.setDeliveryStoreName(DeliveryServiceConstants.CAMBRIDGE_STORE_NAME);
                }
            }else{
                order.setDeliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME);
            }


        });

        return orderDetails;

    }

    public List<OrderDetails> deliveryUnscheduledStoreMapping(List<OrderDetails> orderDetails){
        orderDetails.forEach(order ->{
            if(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME.equals(order.getDeliveryStoreName())){
                order.setDeliveryStoreName(resolveUnscheduledStoreName(order, orderDetails));
            }
        });

        return orderDetails;
    }

    private String resolveUnscheduledStoreName(OrderDetails order, List<OrderDetails> orderDetails) {
        LocalTime orderTime = LocalTime.parse(
                order.getOrderTimestamp(),
                DeliveryServiceConstants.TIME_FORMATTER
        );

        return orderDetails.stream()
                .filter(existingOrder -> !existingOrder.equals(order))
                .filter(existingOrder -> order.getDeliveryCity().equals(existingOrder.getDeliveryCity()))
                .filter(existingOrder -> existingOrder.getDeliveryStoreName() != null)
                .filter(existingOrder -> !DeliveryServiceConstants.UNSCHEDULED_STORE_NAME.equals(existingOrder.getDeliveryStoreName()))
                .min(Comparator
                        .comparingLong(existingOrder -> Math.abs(
                                ChronoUnit.SECONDS.between(
                                        orderTime,
                                        LocalTime.parse(
                                                existingOrder.getOrderTimestamp(),
                                                DeliveryServiceConstants.TIME_FORMATTER
                                        )
                                )
                        ))
                        .thenComparing(OrderDetails::getOrderTimestamp)
                        .thenComparing(OrderDetails::getDeliveryStoreName))
                .map(OrderDetails::getDeliveryStoreName)
                .orElseGet(() -> resolveDefaultStoreName(order.getDeliveryCity()));
    }

    private String resolveDefaultStoreName(String deliveryCity) {
        if ("Manchester".equals(deliveryCity)) {
            return DeliveryServiceConstants.MANCHESTER_STORE_NAME;
        } else if ("London".equals(deliveryCity)) {
            return DeliveryServiceConstants.LONDON_STORE_NAME;
        } else if ("Cambridge".equals(deliveryCity)) {
            return DeliveryServiceConstants.CAMBRIDGE_STORE_NAME;
        }

        return DeliveryServiceConstants.UNSCHEDULED_STORE_NAME;
    }

}

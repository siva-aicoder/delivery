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
            if(order.getDeliveryStoreName().equals(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME)){
                order.setDeliveryStoreName(DeliveryServiceConstants.MANCHESTER_STORE_NAME);
            }
        });

        return orderDetails;
    }

}

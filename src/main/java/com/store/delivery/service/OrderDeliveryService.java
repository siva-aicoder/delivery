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
                if(order.getDeliveryCity().equals("Chennai")){
                    order.setDeliveryStoreName(DeliveryServiceConstants.CHENNAI_STORE_NAME);
                }else if(order.getDeliveryCity().equals("Delhi")){
                    order.setDeliveryStoreName(DeliveryServiceConstants.DELHI_STORE_NAME);
                }else if(order.getDeliveryCity().equals("Hyderabad")){
                    order.setDeliveryStoreName(DeliveryServiceConstants.HYDERABAD_STORE_NAME);
                }
            }else{
                order.setDeliveryStoreName(DeliveryServiceConstants.UNDETERMINED_STORE_NAME);
            }


        });

        return orderDetails;

    }

    public List<OrderDetails> deliveryUndeterminedStoreMapping(List<OrderDetails> orderDetails){
        orderDetails.forEach(order ->{
            if(order.getDeliveryStoreName().equals(DeliveryServiceConstants.UNDETERMINED_STORE_NAME)){
                order.setDeliveryStoreName(DeliveryServiceConstants.CHENNAI_STORE_NAME);
            }
        });

        return orderDetails;
    }

}

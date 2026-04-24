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
                String deliveryStoreName = getStoreNameByDeliveryCity(order.getDeliveryCity());

                if(deliveryStoreName != null){
                    order.setDeliveryStoreName(deliveryStoreName);
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
                String deliveryStoreName = getStoreNameByDeliveryCity(order.getDeliveryCity());

                if(deliveryStoreName != null){
                    order.setDeliveryStoreName(deliveryStoreName);
                }
            }
        });

        return orderDetails;
    }

    private String getStoreNameByDeliveryCity(String deliveryCity) {
        if("Manchester".equals(deliveryCity)){
            return DeliveryServiceConstants.MANCHESTER_STORE_NAME;
        }else if("London".equals(deliveryCity)){
            return DeliveryServiceConstants.LONDON_STORE_NAME;
        }else if("Cambridge".equals(deliveryCity)){
            return DeliveryServiceConstants.CAMBRIDGE_STORE_NAME;
        }

        return null;
    }

}

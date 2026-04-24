package com.store.delivery.service;

import com.store.delivery.constant.DeliveryServiceConstants;
import com.store.delivery.entity.OrderDetails;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class OrderDeliveryService {

    public List<OrderDetails> deliveryStoreMapping(List<OrderDetails> orderDetails){

        orderDetails.forEach(order ->{
            order.setDeliveryStoreName(resolveDeliveryStoreName(order));
        });

        return orderDetails;

    }

    public List<OrderDetails> deliveryUnscheduledStoreMapping(List<OrderDetails> orderDetails){
        orderDetails.forEach(order ->{
            order.setDeliveryStoreName(resolveDeliveryStoreName(order));
        });

        return orderDetails;
    }

    private String resolveDeliveryStoreName(OrderDetails order) {
        if (hasOrderLevelStoreName(order)) {
            return order.getDeliveryStoreName();
        }

        if ("Manchester".equals(order.getDeliveryCity())) {
            return DeliveryServiceConstants.MANCHESTER_STORE_NAME;
        } else if ("London".equals(order.getDeliveryCity())) {
            return DeliveryServiceConstants.LONDON_STORE_NAME;
        } else if ("Cambridge".equals(order.getDeliveryCity())) {
            return DeliveryServiceConstants.CAMBRIDGE_STORE_NAME;
        }

        return order.getDeliveryStoreName();
    }

    private boolean hasOrderLevelStoreName(OrderDetails order) {
        return order.getDeliveryStoreName() != null &&
                !order.getDeliveryStoreName().trim().isEmpty() &&
                !DeliveryServiceConstants.UNSCHEDULED_STORE_NAME.equals(order.getDeliveryStoreName());
    }

}

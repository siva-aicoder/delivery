package com.store.delivery.service;

import com.store.delivery.constant.DeliveryServiceConstants;
import com.store.delivery.entity.OrderDetails;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderDeliveryService {

    public List<OrderDetails> deliveryStoreMapping(List<OrderDetails> orderDetails){
        List<OrderDetails> deliveryMappedOrders = new ArrayList<>();

        orderDetails.forEach(order -> {
            OrderDetails mappedOrder = copyOrderDetails(order);

            if(isWithinMappingHours(order)){
                mappedOrder.setDeliveryStoreName(getMappedStoreName(order.getDeliveryCity()));
            }else{
                mappedOrder.setDeliveryStoreName(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME);
            }

            deliveryMappedOrders.add(mappedOrder);
        });

        return deliveryMappedOrders;

    }

    public List<OrderDetails> deliveryUnscheduledStoreMapping(List<OrderDetails> orderDetails){
        List<OrderDetails> deliveryMappedOrders = new ArrayList<>();

        orderDetails.forEach(order ->{
            OrderDetails mappedOrder = copyOrderDetails(order);

            if(isWithinMappingHours(order)){
                mappedOrder.setDeliveryStoreName(getMappedStoreName(order.getDeliveryCity()));
            }

            deliveryMappedOrders.add(mappedOrder);
        });

        return deliveryMappedOrders;
    }

    private boolean isWithinMappingHours(OrderDetails order) {
        LocalTime orderTime = LocalTime.parse(
                order.getOrderTimestamp(),
                DeliveryServiceConstants.TIME_FORMATTER
        );

        return orderTime.isAfter(DeliveryServiceConstants.OPENING_TIME) &&
                orderTime.isBefore(DeliveryServiceConstants.CLOSING_TIME);
    }

    private String getMappedStoreName(String deliveryCity) {
        if("Manchester".equals(deliveryCity)){
            return DeliveryServiceConstants.MANCHESTER_STORE_NAME;
        }else if("London".equals(deliveryCity)){
            return DeliveryServiceConstants.LONDON_STORE_NAME;
        }else if("Cambridge".equals(deliveryCity)){
            return DeliveryServiceConstants.CAMBRIDGE_STORE_NAME;
        }

        return DeliveryServiceConstants.UNSCHEDULED_STORE_NAME;
    }

    private OrderDetails copyOrderDetails(OrderDetails order) {
        return OrderDetails.builder()
                .orderId(order.getOrderId())
                .productName(order.getProductName())
                .productCategory(order.getProductCategory())
                .productWeight(order.getProductWeight())
                .deliveryCity(order.getDeliveryCity())
                .orderTimestamp(order.getOrderTimestamp())
                .deliveryPostalCode(order.getDeliveryPostalCode())
                .tripDistance(order.getTripDistance())
                .vehicleType(order.getVehicleType())
                .deliveryStoreName(order.getDeliveryStoreName())
                .build();
    }

}

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
        List<OrderDetails> deliveryMappedOrders = copyOrderDetails(orderDetails);

        deliveryMappedOrders.forEach(order ->{
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

        return deliveryMappedOrders;

    }

    public List<OrderDetails> deliveryUnscheduledStoreMapping(List<OrderDetails> orderDetails){
        List<OrderDetails> deliveryMappedOrders = copyOrderDetails(orderDetails);

        deliveryMappedOrders.forEach(order ->{
            if(DeliveryServiceConstants.UNSCHEDULED_STORE_NAME.equals(order.getDeliveryStoreName()) ||
                    order.getDeliveryStoreName() == null || order.getDeliveryStoreName().isBlank()){
                order.setDeliveryStoreName(resolveStoreName(order.getDeliveryCity()));
            }
        });

        return deliveryMappedOrders;
    }

    private List<OrderDetails> copyOrderDetails(List<OrderDetails> orderDetails) {
        List<OrderDetails> deliveryMappedOrders = new ArrayList<>();

        orderDetails.forEach(order -> deliveryMappedOrders.add(OrderDetails.builder()
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
                .build()));

        return deliveryMappedOrders;
    }

    private String resolveStoreName(String deliveryCity) {
        if ("Manchester".equals(deliveryCity)) {
            return DeliveryServiceConstants.MANCHESTER_STORE_NAME;
        }

        if ("London".equals(deliveryCity)) {
            return DeliveryServiceConstants.LONDON_STORE_NAME;
        }

        if ("Cambridge".equals(deliveryCity)) {
            return DeliveryServiceConstants.CAMBRIDGE_STORE_NAME;
        }

        return DeliveryServiceConstants.UNSCHEDULED_STORE_NAME;
    }

}

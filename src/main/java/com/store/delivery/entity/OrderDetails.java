package com.store.delivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetails{
    @JsonProperty("order_id")
    private int orderId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("delivery_city")
    private String deliveryCity;

    @JsonProperty("order_timestamp")
    private String orderTimestamp;

    @JsonProperty("delivery_store_name")
    private String deliveryStoreName;

}

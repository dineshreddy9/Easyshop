package com.easyshop.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Created by pavan on 2/17/17.
 */
@Getter
@Setter
@Document(collection = "order_dtl")
@Data
public class OrderDtlModel {

    @Id
    private String id;

    private Long orderDtlId;

    @NotNull
    private long orderId;

    @NotNull
    private int orderItemId;

    @NotNull
    private int orderItemQuantity;

    @NotNull
    private long orderItemPrice;

    @NotNull
    private String orderItemStatus;

    @Transient
    private String orderItemName;

    public OrderDtlModel(){}
}

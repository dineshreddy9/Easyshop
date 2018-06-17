package com.easyshop.model;

/**
 * Created by admin-hp on 2/4/17.
 */

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Document(collection = "subs_order_dtl")
@Data
public class SubscriptionOrderDtlModel {

    @Id
    private String id;

    private Long subsOrderDtlId;

    @NotNull
    private long subsOrderId;

    @NotNull
    private int subsOrderItemId;

    @NotNull
    private int subsOrderItemQuantity;

    private long subsOrderItemPrice;

    @NotNull
    private String subsOrderItemStatus;

    private String subsOrderItemName;

    public SubscriptionOrderDtlModel(){

    }
}


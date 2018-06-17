package com.easyshop.model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * Created by admin-hp on 2/4/17.
 */

@Getter
@Setter
@Document(collection = "subs_order_hdr")
@Data
public class SubscriptionOrderHdrModel {

    @Id
    private String id;

    private Long subsOrderHdrId;

    @NotNull
    private Long subsOrderId;

    @NotNull
    private int custId;

    @NotNull
    private int subsOrderItemCount;

    @NotNull
    private long subsOrderTotal;

    @NotNull
    private long taxAmount;

    @NotNull
    private String subsOrderStatus;

    @NotNull
    private int subsOrderAddressId;

    @NotNull
    @DateTimeFormat
    private Date subsOrderCreatedDate;

    @NotNull
    @DateTimeFormat
    private Date subsOrderUpdatedDate;

    @NotNull
    private int subsOrderBillingAddrId;

    public SubscriptionOrderHdrModel(){}

}




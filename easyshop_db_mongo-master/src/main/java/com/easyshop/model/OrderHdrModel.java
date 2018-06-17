package com.easyshop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by pavan on 2/17/17.
 */
@Getter
@Setter
@Document(collection = "order_hdr")
@Data
public class OrderHdrModel {

    @Id
    private String id;

    private Long orderHdrId;

    @NotNull
    private Long orderId;

    @NotNull
    private int custId;

    @NotNull
    private int orderItemCount;

    @NotNull
    private long orderTotal;

    @NotNull
    private long taxAmount;

    @NotNull
    private String orderStatus;

    @NotNull
    private int orderAddressId;

    @NotNull
    private int orderBillingAddrId;

    @NotNull
    @DateTimeFormat
    private Date orderCreatedDate;

    @NotNull
    @DateTimeFormat
    private Date orderUpdatedDate;

    @NotNull
    @DateTimeFormat
    private Date expectedDeliveryDate;


    public OrderHdrModel(){}
}

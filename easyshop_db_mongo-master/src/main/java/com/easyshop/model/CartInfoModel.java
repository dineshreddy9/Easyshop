package com.easyshop.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by pavan on 2/17/17.
 */
@Getter
@Setter
@Data
public class CartInfoModel {

    @NotNull
    private Long itemId;

    @NotNull
    private String itemName;

    @NotNull
    private long custId;

    @NotNull
    private float itemPrice;

    @NotNull
    private float totalPrice;

    @NotNull
    private long itemCount;


    public CartInfoModel(){}
}

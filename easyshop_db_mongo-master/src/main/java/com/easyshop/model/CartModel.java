package com.easyshop.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Created by pavan on 2/17/17.
 */
@Getter
@Setter
@Document(collection = "cart")
@Data
public class CartModel {

    @Id
    private String id;

    private Long cartItemId;

    @NotNull
    private long custId;

    @NotNull
    private int itemId;

    public CartModel(){}
}

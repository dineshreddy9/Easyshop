package com.easyshop.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;


/**
 * Created by admin-hp on 19/2/17.
 */
@Document(collection ="item")
@Getter
@Setter
@Data
public class CatalogModel {

    @Id
    private String id;

    private long itemId;

    @NotNull
    private String itemName;

    @NotNull
    private String itemDescription;

    @NotNull
    private float itemPrice;

    @NotNull
    private long itemQuantity;

    @NotNull
    private String itemImage;

    public CatalogModel(){}

}

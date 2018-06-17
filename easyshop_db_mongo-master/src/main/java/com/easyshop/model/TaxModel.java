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
@Document(collection = "tax_info")
@Data
public class TaxModel {

    @Id
    private String id;

    private Long taxId;

    @NotNull
    private int zipcode;

    @NotNull
    private float taxPercentage;

    public TaxModel(){}
}

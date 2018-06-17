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
@Document(collection= "address")
@Data
public class AddressModel {
    @Id
    private String id;

    @NotNull
    private Long addressId;

    @NotNull
    private long custId;

    @NotNull
    private String address1;

    @NotNull
    private String address2;

    @NotNull
    private String state;

    @NotNull
    private String city;

    @NotNull
    private String country;

    @NotNull
    private Integer zipcode;

    @NotNull
    private String phoneNumber;

    public AddressModel(){}
}

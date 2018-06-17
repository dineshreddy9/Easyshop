package com.easyshop.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Created by admin on 22/10/16.
 */
@Document(collection ="customer")
@Getter
@Setter
@Data
public class UserModel {

    @Id
    private String id;

    @NotNull
    private long custId;

    @NotNull
    private String custFirstName;

    @NotNull
    private String custLastName;
    @NotNull
    private String custEmailid;

    @NotNull
    private String custPassword;

    @NotNull
    private boolean activeStatus;

    private int securityQuesId;

    private String securityQuesAns;

    private String authToken;

    private QuestionModel question;

    private List<AddressModel> addresses;

    private List<CardModel> cards;

}

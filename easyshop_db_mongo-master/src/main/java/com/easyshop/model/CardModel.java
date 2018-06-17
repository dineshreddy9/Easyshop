package com.easyshop.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Created by pavan on 2/19/17.
 */
@Getter
@Setter
@Data
@Document(collection = "card")
public class CardModel {

    @Id
    private String id;

    private Long cardId;

    @NotNull
    private int custId;

    @NotNull
    private String cardNumber;

    @NotNull
    private int cardCvv;

    @NotNull
    private int cardExpMon;

    @NotNull
    private int cardExpYr;

    public CardModel(){}

}

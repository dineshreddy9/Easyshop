package com.easyshop.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Created by pavan on 2/7/17.
 */
@Document(collection = "questions")
@Getter
@Setter
@Data
public class QuestionModel {

    @Id
    private Long securityQuesId;

    @NotNull
    private String securityQuesDescription;

    QuestionModel(){}
}

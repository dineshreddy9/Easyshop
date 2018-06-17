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
@Document(collection = "message")
@Data
public class MessageModel {

    @Id
    private String id;

    private Long messageId;

    @NotNull
    private long custId;

    @NotNull
    private String messageContent;

    @NotNull
    private boolean isRead;

    @NotNull
    private String messageTime;

    public MessageModel(){}
}

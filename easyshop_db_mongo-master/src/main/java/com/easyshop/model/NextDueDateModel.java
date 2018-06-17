package com.easyshop.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin-hp on 2/4/17.
 */
@Getter
@Setter
@Document(collection= "subs_next_due_date")
@Data
public class NextDueDateModel {

    @Id
    private String id;

    @NotNull
    private Long subsOrderId;

    @NotNull
    private int subscriptionType; // 1: 1 month; 2: 2 months; 3: 3 months; 6: 6 months; 12: 12 months

    @NotNull
    @DateTimeFormat
    private Calendar nextDueDate;
}

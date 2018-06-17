package com.easyshop.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Getter
@Setter
@Document(collection = "sequence")
public class Sequence {

    @Id
    public String id;
    public String tableName;
    public String columnName;
    public long seqVal;

    public Sequence() {}



}


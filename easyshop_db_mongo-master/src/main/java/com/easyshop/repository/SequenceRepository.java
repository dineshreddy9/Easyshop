package com.easyshop.repository;

import com.easyshop.model.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SequenceRepository extends MongoRepository<Sequence, String> {

    Sequence findByColumnNameAndTableName(String columnName, String tableName);

}
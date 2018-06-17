package com.easyshop.repository;

import com.easyshop.model.TaxModel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by pavan on 3/24/17.
 */
public interface TaxRepository extends MongoRepository<TaxModel, String> {
    TaxModel findByZipcode(int zipcode);
}

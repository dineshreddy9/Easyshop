package com.easyshop.repository;

import com.easyshop.model.AddressModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by pavan on 2/21/17.
 */
public interface AddressRepository extends MongoRepository<AddressModel, String> {
    AddressModel findByAddressId(long addressId);

    List<AddressModel> findByCustId(long custId);
}

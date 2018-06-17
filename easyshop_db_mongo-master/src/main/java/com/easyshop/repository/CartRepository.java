package com.easyshop.repository;

import com.easyshop.model.CartModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by pavan on 2/21/17.
 */
public interface CartRepository extends MongoRepository<CartModel, String> {

    List<CartModel> findByCustId(long custId);

    Iterable<CartModel> findByItemId(int itemId);

    CartModel findTopByCustId(long custId);
}

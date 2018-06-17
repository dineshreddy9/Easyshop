package com.easyshop.repository;

import com.easyshop.model.OrderDtlModel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by pavan on 2/21/17.
 */
public interface OrderDtlRepository extends MongoRepository<OrderDtlModel, String> {

    Iterable<OrderDtlModel> findByOrderId(long orderId);

    OrderDtlModel findByOrderIdAndOrderItemId(long orderId, int itemId);

}

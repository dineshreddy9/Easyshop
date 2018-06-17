package com.easyshop.repository;

import com.easyshop.model.OrderHdrModel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by pavan on 2/21/17.
 */
public interface OrderHdrRepository extends MongoRepository<OrderHdrModel, String> {

    Iterable<OrderHdrModel> findByCustIdOrderByOrderHdrIdDesc(int custId);

    OrderHdrModel findByOrderId(long orderId);

    Iterable<OrderHdrModel> findAllByOrderByOrderHdrIdDesc();

}

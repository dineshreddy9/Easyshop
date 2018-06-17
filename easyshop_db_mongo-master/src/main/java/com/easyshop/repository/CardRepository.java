package com.easyshop.repository;

import com.easyshop.model.CardModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by pavan on 2/21/17.
 */
public interface CardRepository extends MongoRepository<CardModel, String> {
    List<CardModel> findByCustId(long custId);
    CardModel findByCardId(long cardId);
}

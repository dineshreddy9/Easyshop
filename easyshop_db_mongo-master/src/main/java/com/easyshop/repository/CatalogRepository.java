package com.easyshop.repository;

import com.easyshop.model.CatalogModel;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * Created by admin on 22/10/16.
 */
public interface CatalogRepository extends MongoRepository<CatalogModel, String> {

    //CatalogModel findByCustEmailidAndCustPasswordAndActiveStatus(String custEmailId, String custPassword, boolean activeStatus);
	//CatalogModel findByCustEmailidAndSecurityQuesAns(String custEmailId, String securityQuesAns);
	CatalogModel findByItemId(long itemid);
    CatalogModel findByItemName(String itemName);

    void deleteByItemId(long itemId);
}

package com.easyshop.repository;

import com.easyshop.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by admin on 22/10/16.
 */
public interface UserRepository extends MongoRepository<UserModel, String> {

    UserModel findByCustEmailidAndCustPasswordAndActiveStatus(String custEmailId, String custPassword, boolean activeStatus);
	UserModel findByCustEmailidAndSecurityQuesAns(String custEmailId, String securityQuesAns);
	UserModel findByCustEmailid(String custEmailId);
	
    UserModel findByCustId(long custId);

    UserModel findByAuthToken(String authToken);

}

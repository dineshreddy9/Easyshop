package com.easyshop.util;

import com.easyshop.model.UserModel;
import com.easyshop.repository.AddressRepository;
import com.easyshop.repository.CardRepository;
import com.easyshop.repository.UserRepository;

import java.util.ArrayList;

/**
 * Created by pavan on 4/13/17.
 */
public class ProfileUtil {

    public static UserModel getCustomerDetails(long id, UserRepository userRepository, AddressRepository addressRepository, CardRepository cardRepository){
        UserModel userModel = userRepository.findByCustId(id);
        userModel.setAddresses(addressRepository.findByCustId(id));
        userModel.setCards(cardRepository.findByCustId(id));
        return userModel;
    }
}

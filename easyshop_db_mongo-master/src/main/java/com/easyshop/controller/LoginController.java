package com.easyshop.controller;

import com.easyshop.model.AddressModel;
import com.easyshop.model.ForgotPasswordModel;
import com.easyshop.model.UserModel;
import com.easyshop.repository.*;
import com.easyshop.util.EasyShopUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by pavan on 1/26/17.
 */

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController{
    private final static Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    @RequestMapping(value = "/verifyLogin", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity verifyLogin(HttpServletRequest request, @RequestParam("email") String email, @RequestParam("pwd") String pwd) throws Exception{
        String uuid;
        JSONObject response = new JSONObject();
        JSONObject verifyLogin = new JSONObject();
        UserModel userModel = userRepository.findByCustEmailidAndCustPasswordAndActiveStatus(email,pwd,true);
        if(userModel!=null) {
            uuid = EasyShopUtil.getRandonUDID();
            response.put("status", true);
            response.put("uuid",uuid);
            response.put("custId",userModel.getCustId());
            response.put("firstName",userModel.getCustFirstName());
            response.put("cartCount", EasyShopUtil.getCartCount(cartRepository.findByCustId(userModel.getCustId())));
            verifyLogin.put("verifyLogin",response);
            userModel.setAuthToken(uuid);
            userRepository.save(userModel);
            return ResponseEntity.ok(verifyLogin.toString());
        }else {
            response.put("status", false);
            response.put("message","Email Id not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
        }
    }

    @RequestMapping(value = "/createUser", method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(HttpServletRequest request, @Valid @RequestBody UserModel userModel) throws Exception{
        JSONObject responseObject = new JSONObject();
        try {
            if(userRepository.findByCustEmailid(userModel.getCustEmailid()) == null) {
                String authtoken = EasyShopUtil.getRandonUDID();
                AddressModel addressModel = userModel.getAddresses().get(0);
                userModel.setCustId(EasyShopUtil.getMaxId("customer","custId",sequenceRepository));
                userModel.setAddresses(null);
                UserModel resp = userRepository.save(userModel);
                responseObject.put("status", true);
                JSONObject info = new JSONObject();
                info.put("custId",userModel.getCustId());
                info.put("firstName",userModel.getCustFirstName());
                responseObject.put("status", "success");
                responseObject.put("uuid", authtoken);
                responseObject.put("info", info);
                userModel.setAuthToken(authtoken);
                addressModel.setAddressId(EasyShopUtil.getMaxId("address","addressId",sequenceRepository));
                addressRepository.save(addressModel);
                userRepository.save(userModel);
                return ResponseEntity.ok(responseObject.toString());
            }else {
                responseObject.put("status",false);
                responseObject.put("message","Email Already Exists");
                return ResponseEntity.badRequest().body(responseObject.toString());
            }
        }catch (Exception e){
            responseObject.put("status",false);
            responseObject.put("message",e.getMessage());
            logger.log(Level.ERROR, "Unexpected exception",e);
            return ResponseEntity.ok(responseObject.toString());
        }
        //return ResponseEntity.ok(responseObject.toString());
    }

    @RequestMapping(value = "/forgetPassword", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity forgetPassword(@RequestParam("email") String email, @RequestParam("securityAns") String ans) throws Exception{

    JSONObject response = new JSONObject();
    JSONObject forgetPassword= new JSONObject();
    UserModel userModel = userRepository.findByCustEmailidAndSecurityQuesAns(email,ans);
    if(userModel!=null) {
        response.put("status", true);
        response.put("firstName",userModel.getCustFirstName());
        forgetPassword.put("forgetPassword",response);
        return ResponseEntity.ok(forgetPassword.toString());
     }else {
        response.put("status", false);
        response.put("message","Answer is Incorrect");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
         }
    }

    @RequestMapping(value = "/updatePassword", method = PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updatePassword(@Valid @RequestBody ForgotPasswordModel forgotPasswordModel) throws Exception{
        UserModel userModel = userRepository.findByCustEmailid(forgotPasswordModel.getEmailId());
        JSONObject response = new JSONObject();
        if(userModel!=null) {
            userModel.setCustPassword(forgotPasswordModel.getPassword());
            response.put("status", true);
            response.put("firstName",userModel.getCustFirstName());
            userRepository.save(userModel);
            //return ResponseEntity.ok(updatePassword.toString());
            return ResponseEntity.ok(response.toString());
        }else {
            response.put("status", false);
            response.put("message","Email Id not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
        }
    }

    private LoginController() { 
    }
}

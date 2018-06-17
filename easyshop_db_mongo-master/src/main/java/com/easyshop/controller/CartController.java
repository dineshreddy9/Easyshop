package com.easyshop.controller;

import com.easyshop.model.CartModel;
import com.easyshop.model.Sequence;
import com.easyshop.repository.*;
import com.easyshop.util.EasyShopUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by pavan on 3/17/17.
 */
@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {

    private final static Logger logger = Logger.getLogger(CartController.class);

    @Autowired
    private CartRepository cartRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/getCart", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getCart(HttpServletRequest request) throws Exception{
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        JSONObject response = new JSONObject();
        long custId = EasyShopUtil.getCustIdByToken(userRepository, request);
        response.put("data", EasyShopUtil.getCartDetails(custId, catalogRepository,cartRepository));
        response.put("cartCount", EasyShopUtil.getCartCount(cartRepository.findByCustId(custId)));
        return ResponseEntity.ok(response.toString());
    }

    @RequestMapping(value = "/getCartCount", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getCartCount(HttpServletRequest request) throws Exception{
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        JSONObject response = new JSONObject();
        long custId = EasyShopUtil.getCustIdByToken(userRepository, request);
        response.put("cartCount", EasyShopUtil.getCartCount(cartRepository.findByCustId(custId)));
        return ResponseEntity.ok(response.toString());
    }

    @RequestMapping(value = "/addToCart", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity addToCart(HttpServletRequest request, @RequestBody CartModel cartModel) throws Exception{
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        JSONObject response = new JSONObject();
        cartModel.setCartItemId(EasyShopUtil.getMaxId("cart","cartItemId",sequenceRepository));
        cartRepository.save(cartModel);
        response.put("status",true);
        response.put("cartCount",EasyShopUtil.getCartCount(cartRepository.findByCustId(cartModel.getCustId())));
        return ResponseEntity.ok(response.toString());
    }

    @RequestMapping(value = "/updateCart", method = PUT, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity updateCart(HttpServletRequest request, @RequestParam("type") String type, @RequestParam("itemId") int itemId) throws Exception{
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        long custId = EasyShopUtil.getCustIdByToken(userRepository,request);
        JSONObject response = new JSONObject();
        if("remove".equals(type)){
            cartRepository.delete(cartRepository.findByItemId(itemId));
        }else if("reduce".equals(type)) {
            cartRepository.delete(cartRepository.findTopByCustId(custId));
        }else if("increase".equals(type)) {
            CartModel cartModel = new CartModel();
            cartModel.setCartItemId(EasyShopUtil.getMaxId("cart","cartItemId",sequenceRepository));
            cartModel.setCustId(custId);
            cartModel.setItemId(itemId);
            cartRepository.save(cartModel);
        }
        response.put("data", EasyShopUtil.getCartDetails(custId, catalogRepository,cartRepository));
        response.put("cartCount", EasyShopUtil.getCartCount(cartRepository.findByCustId(custId)));
        return ResponseEntity.ok(response.toString());
    }

    @RequestMapping(value = "/validateCart", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity validateCart(HttpServletRequest request) throws Exception{
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        long custId = EasyShopUtil.getCustIdByToken(userRepository,request);
        JSONObject response = new JSONObject();
        long item = EasyShopUtil.validateCart(catalogRepository, EasyShopUtil.getCartDetails(custId, catalogRepository,cartRepository)); //item with no required quantity.
        if(item > 0) {
            response.put("status", false);
            response.put("message", "Stock not available for "+catalogRepository.findByItemId(item).getItemName());
        }else{
            response.put("status", true);
            response.put("message", "Stock available");
        }
        return ResponseEntity.ok(response.toString());
    }

}

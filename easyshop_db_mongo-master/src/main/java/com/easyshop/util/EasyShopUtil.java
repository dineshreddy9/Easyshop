package com.easyshop.util;

import com.easyshop.model.*;
import com.easyshop.repository.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by pavan on 2/5/17.
 */
public class EasyShopUtil {

    private final static Logger logger = Logger.getLogger(EasyShopUtil.class);

    private static final String X_AUTH_TOKEN= "X-AUTH-TOKEN";
    public static String getRandonUDID(){
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    public static boolean isValidCustomer(UserRepository userRepository, HttpServletRequest request){
        UserModel user = userRepository.findByAuthToken(request.getHeader(X_AUTH_TOKEN));
        if(user!=null){
            return true;
        }
        return false;
    }

    public static long getCustIdByToken(UserRepository userRepository, HttpServletRequest request){
        UserModel user = userRepository.findByAuthToken(request.getHeader(X_AUTH_TOKEN));
        if(user!=null){
            return user.getCustId();
        }
        return 0;
    }
    public static long validateCart(CatalogRepository catalogRepository, List<Map<String, Object>> items){
        int requestedQuantity = 0;
        long availableQuantity = 0;
        int itemId;
        for(int i=0; i<items.size(); i++){
            Map<String, Object> item = items.get(i);
            requestedQuantity = (Integer)item.get("itemCount");
            itemId = (Integer) item.get("itemId");
            availableQuantity = catalogRepository.findByItemId(itemId).getItemQuantity();
            if(availableQuantity < requestedQuantity){
                return itemId;
            }
        }
        return 0;
    }

    public static long getMaxId(String table, String column, SequenceRepository sequenceRepository) {
        Sequence sequence = sequenceRepository.findByColumnNameAndTableName(column, table);
        long seqVal = sequence.getSeqVal();
        seqVal++;
        sequence.setSeqVal(seqVal);
        sequenceRepository.save(sequence);
        return seqVal;
    }

    public static int getCartCount(Iterable<CartModel> cartModels){
        int count = 0;
        for (CartModel cartModel: cartModels) {
            count++;
        }
        return count;
    }

    public static List<Map<String, Object>> getCartDetails(long custId, CatalogRepository catalogRepository, CartRepository cartRepository){
        CatalogModel catalogModel;
        Map<Integer, Integer> itemCount = new HashMap<>();
        Map<Integer, Map<String, Object>> cartList = new HashMap();
        List<Map<String, Object>> finalList= new ArrayList<>();
        int itemId;
        List<CartModel> cartModelList = cartRepository.findByCustId(custId);
        for(CartModel cartModel: cartModelList){
            Map<String, Object> cart = new HashMap<>();
            itemId = cartModel.getItemId();
            catalogModel = catalogRepository.findByItemId(cartModel.getItemId());
            if(itemCount.get(itemId) != null){
                itemCount.put(itemId, itemCount.get(itemId)+1);
                if(cartList.get(itemId)!=null) {
                    cart.put("itemName", catalogModel.getItemName());
                    cart.put("itemPrice", catalogModel.getItemPrice());
                    cart.put("totalPrice", catalogModel.getItemPrice() * itemCount.get(cartModel.getItemId()));
                    cart.put("itemCount", itemCount.get(cartModel.getItemId()));
                    cart.put("itemId", cartModel.getItemId());
                    cart.put("itemImage", catalogModel.getItemImage());
                    cartList.put(itemId,cart);
                }
                //cartList.add(cart);
            }else{
                itemCount.put(itemId,1);
                cart.put("itemName", catalogModel.getItemName());
                cart.put("itemPrice", catalogModel.getItemPrice());
                cart.put("totalPrice", catalogModel.getItemPrice() * itemCount.get(cartModel.getItemId()));
                cart.put("itemCount", itemCount.get(cartModel.getItemId()));
                cart.put("itemId", cartModel.getItemId());
                cart.put("itemImage", catalogModel.getItemImage());
                cartList.put(itemId, cart);
            }
        }
        logger.log(Level.INFO,itemCount.toString());
        Iterator it = cartList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            finalList.add((Map<String, Object>)pair.getValue());
        }
        return finalList;
    }
}

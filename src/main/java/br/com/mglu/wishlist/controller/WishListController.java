package br.com.mglu.wishlist.controller;

import br.com.mglu.wishlist.models.WishList;
import br.com.mglu.wishlist.repository.WishListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishListController {


    public WishList makeInsert(HttpServletResponse response, WishListRepository wishListRepository, String clientId, String productId) throws Exception {
        WishList wishList = new WishList();

        Optional<WishList> optionalWishList = wishListRepository.findById(clientId);

        if(optionalWishList.isPresent()){
            wishList = optionalWishList.get();
            if(validateWishListIsFull(wishList) || validateProductAlreadyExistsInWishList(wishList, productId)){
                response.setStatus(HttpStatus.NOT_MODIFIED.value());
                throw new RuntimeException("Product not save List is full or Product Already exist");
            }else{
                wishList.getProductList().add(productId);
                response.setStatus(HttpStatus.OK.value());
            }

        }else{
            wishList.setClientId(clientId);
            List<String> list = new ArrayList<String>();
            list.add(productId);
            wishList.setProductList(list);
            response.setStatus(HttpStatus.OK.value());
        }

        wishListRepository.save(wishList);
        return wishList;
    }

    public WishList doRemoveProduct(HttpServletResponse response, WishListRepository wishListRepository, String clientId, String productId) throws Exception {
        WishList wishList = new WishList();
        Optional<WishList> optionalWishList = wishListRepository.findById(clientId);
        if(optionalWishList.isPresent()){
            wishList = optionalWishList.get();
            if(wishList.getProductList().contains(productId)){
                wishList.getProductList().remove(productId);
                response.setStatus(HttpStatus.OK.value());
                wishListRepository.save(wishList);
            }else{
                response.setStatus(HttpStatus.NO_CONTENT.value());
                throw new RuntimeException("Product not found");
            }
        }
        return wishList;
    }

    public WishList getWishList(HttpServletResponse response, WishListRepository wishListRepository, String clientId) {
        WishList wishList = new WishList();
        Optional<WishList> optionalWishList = wishListRepository.findById(clientId);
        if(optionalWishList.isPresent()){
            wishList = optionalWishList.get();
            response.setStatus(HttpStatus.OK.value());
        }else{
            response.setStatus(HttpStatus.NO_CONTENT.value());
            throw new RuntimeException("WishList not found");
        }
        return wishList;
    }

    public WishList getProductInWishList(HttpServletResponse response, WishListRepository wishListRepository, String clientId, String productId) {
        WishList wishList = new WishList();

        Optional<WishList> optionalWishList = wishListRepository.findById(clientId);
        if(optionalWishList.isPresent()){
            wishList = optionalWishList.get();
            if (wishList.getProductList().contains(productId)) {
                response.setStatus(HttpStatus.OK.value());
            }else{
                response.setStatus(HttpStatus.NO_CONTENT.value());
                throw new RuntimeException("Product not found in WishList");
            }
        }

        return wishList;
    }


    public Boolean validateWishListIsFull(WishList wishList){
        return wishList.getProductList().size() >= 20 ? Boolean.TRUE : Boolean.FALSE;
    }

    public Boolean validateProductAlreadyExistsInWishList(WishList wishList, String productId){
        return wishList.getProductList().contains(productId) ? Boolean.TRUE : Boolean.FALSE;
    }


}

/*
 * Copyright (c) - UOL Inc, Todos os direitos reservados
 *
 * Este arquivo e uma propriedade confidencial do Universo Online Inc. Nenhuma parte do mesmo pode
 * ser copiada, reproduzida, impressa ou transmitida por qualquer meio sem autorizacao expressa e
 * por escrito de um representante legal do Universo Online Inc.
 *
 * All rights reserved
 *
 * This file is a confidential property of Universo Online Inc. No part of this file may be
 * reproduced or copied in any form or by any means without written permission from an authorized
 * person from Universo Online Inc.
 */
package br.com.mglu.wishlist.resources;

import br.com.mglu.wishlist.controller.WishListController;
import br.com.mglu.wishlist.models.WishList;
import br.com.mglu.wishlist.repository.WishListRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/wishList")
@Api(value="Api Rest WishList")
@CrossOrigin(origins="*")
public class WishListResources {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    WishListController wishListController;

    @PutMapping("/addProduct/{clientId}/{productId}")
    @ApiOperation(value="Insert a new Product in a Wishlist")
    public WishList addProduct(HttpServletResponse httpServletResponse, @PathVariable(value="clientId") String clientId, @PathVariable(value="productId") String productId) throws Exception {
        return wishListController.makeInsert(httpServletResponse, wishListRepository, clientId, productId);
    }

    @DeleteMapping("/removeProduct/{clientId}/{productId}")
    @ApiOperation(value="Delete a Product of a WishList")
    public WishList removeProduct(HttpServletResponse httpServletResponse, @PathVariable(value="clientId") String clientId, @PathVariable(value="productId") String productId) throws Exception {
        return wishListController.doRemoveProduct(httpServletResponse, wishListRepository, clientId, productId);
    }

    @GetMapping("/getWishList/{clientId}")
    @ApiOperation(value="Returns the wishlist by Client")
    public WishList getWishList(HttpServletResponse httpServletResponse, @PathVariable(value="clientId") String clientId){
        return wishListController.getWishList(httpServletResponse, wishListRepository, clientId);
    }

    @GetMapping("/getProductInWishList/{clientId}/{productId}")
    @ApiOperation(value="Returns the wishlist by Client")
    public WishList getProductInWishList(HttpServletResponse httpServletResponse, @PathVariable(value="clientId") String clientId, @PathVariable(value="productId") String productId){
        return wishListController.getProductInWishList(httpServletResponse, wishListRepository, clientId, productId);
    }

}

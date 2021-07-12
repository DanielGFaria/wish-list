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
package br.com.mglu.wishlist.config;

import br.com.mglu.wishlist.controller.WishListController;
import br.com.mglu.wishlist.repository.WishListRepository;
import br.com.mglu.wishlist.resources.WishListResources;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;

public class WishListTestConfiguration {

    @Bean
    public WishListRepository wishListRepository() {
        return mock(WishListRepository.class);
    }

    @Bean
    public HttpServletResponse httpServletResponse(){
        return mock(HttpServletResponse.class);
    };

    @Bean
    public WishListResources wishListResources(){
        return new WishListResources();
    }

    @Bean
    public WishListController wishListController(){
        return new WishListController();
    }

}

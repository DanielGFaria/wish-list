package br.com.mglu.wishlist.repository;

import br.com.mglu.wishlist.models.WishList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends MongoRepository<WishList, String> {

}

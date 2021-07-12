package br.com.mglu.wishlist.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "teste")
public class WishList {

    @Id
    private String clientId;
    private List<String> productList;

     public WishList(String clientId, List<String> productList){
        super();
        this.clientId = clientId;
        this.productList = productList;
    }
}

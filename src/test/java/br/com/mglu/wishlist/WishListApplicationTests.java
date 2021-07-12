package br.com.mglu.wishlist;

import br.com.mglu.wishlist.config.WishListTestConfiguration;
import br.com.mglu.wishlist.controller.WishListController;
import br.com.mglu.wishlist.models.WishList;
import br.com.mglu.wishlist.repository.WishListRepository;
import br.com.mglu.wishlist.resources.WishListResources;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
@SpringBootTest(classes = WishListTestConfiguration.class)
class WishListApplicationTests{

	@Autowired
	WishListRepository wishListRepository;

	@Autowired
	WishListResources wishListResources;

	@Autowired
	HttpServletResponse httpServletResponse;

	@Test
	void testInsertSuccess() throws Exception {
		String product = "productForInsertSuccess";
		WishList wishList = new WishList();
		wishList = createWishListExample(wishList);

		WishList wishListResponse = new WishList();
		wishListResponse = createWishListExample(wishListResponse);
		wishListResponse.getProductList().add(product);

		Mockito.when(wishListRepository.findById(any())).thenReturn(java.util.Optional.of(wishList));
		Mockito.when(wishListRepository.save(any())).thenReturn(wishList);
		Mockito.when(httpServletResponse.getStatus()).thenReturn(200);
		WishList wishListFinal = wishListResources.addProduct(httpServletResponse, wishList.getClientId(), product);

		Assert.isTrue(verifyProductInWishList(wishListFinal, product), "Sucesso");
	}

	@Test
	void testInsertError(){
		String product = "productForInsertError";
		final WishList wishList = new WishList();
		final WishList finalWishList = createWishListExample(wishList);

		WishList wishListResponse = new WishList();
		wishListResponse = createWishListExample(wishListResponse);
		wishListResponse.getProductList().add(product);

		RuntimeException exception = mock(RuntimeException.class);
		Mockito.when(exception.getMessage()).thenReturn("Erro before save Product in WishList");

		Mockito.when(wishListRepository.findById(any())).thenReturn(java.util.Optional.of(wishList));
		Mockito.when(wishListRepository.save(wishList)).thenThrow(exception);
		try{
			wishListResources.addProduct(httpServletResponse, wishList.getClientId(), product);
		}catch (Exception e) {
			Assert.isTrue(e.getMessage().equals("Erro before save Product in WishList"));
		}
	}

	@Test
	void testInsertVerifyError(){
		String product = "produto1";
		final WishList wishList = new WishList();
		final WishList finalWishList = createWishListExample(wishList);

		WishList wishListResponse = new WishList();
		wishListResponse = createWishListExample(wishListResponse);
		wishListResponse.getProductList().add(product);

		Mockito.when(wishListRepository.findById(any())).thenReturn(java.util.Optional.of(wishList));
		try{
			WishList wishListFinal = wishListResources.addProduct(httpServletResponse, wishList.getClientId(), product);
		}catch (Exception e) {
			Assert.isTrue(e.getMessage().equals("Product not save List is full or Product Already exist"));
		}
	}

	@Test
	void testRemoveProductSuccess(){
		String product = "produto1";
		WishList wishList = new WishList();
		wishList = createWishListExample(wishList);

		WishList wishListRemoved = new WishList();
		wishListRemoved = createWishListExample(wishListRemoved);
		wishListRemoved.getProductList().remove(product);

		Mockito.when(wishListRepository.findById(any())).thenReturn(java.util.Optional.of(wishList));

		try{
			WishList wishListFinal = wishListResources.removeProduct(httpServletResponse, wishList.getClientId(), product);
			Assert.isTrue(!verifyProductInWishList(wishListFinal, product), "Sucesso");
		}catch (Exception e) {}
	}

	@Test
	void testRemoveProductError(){
		String product = "produto5";
		WishList wishList = new WishList();
		wishList = createWishListExample(wishList);

		WishList wishListRemoved = new WishList();
		wishListRemoved = createWishListExample(wishListRemoved);
		wishListRemoved.getProductList().remove(product);

		Mockito.when(wishListRepository.findById(any())).thenReturn(java.util.Optional.of(wishList));

		try{
			wishListResources.removeProduct(httpServletResponse, wishList.getClientId(), product);
		}catch (Exception e) {
			Assert.isTrue(e.getMessage().equals("Product not found"));
		}
	}

	@Test
	void testGetWishListSuccess(){
		WishList wishList = new WishList();
		wishList = createWishListExample(wishList);

		Mockito.when(wishListRepository.findById(any())).thenReturn(java.util.Optional.of(wishList));
		WishList wishListFinal = wishListResources.getWishList(httpServletResponse, wishList.getClientId());

		Assert.isTrue(wishListFinal.equals(wishList), "Sucesso");
	}

	@Test
	void testGetWishListError(){
		String clientId = "ClienteTeste123";
		Mockito.when(wishListRepository.findById(any())).thenReturn(Optional.empty());

		try{
			wishListResources.getWishList(httpServletResponse, clientId);
		}catch (Exception e) {
			Assert.isTrue(e.getMessage().equals("WishList not found"));
		}
	}

	@Test
	void testGetWishListProductSuccess(){
		String product = "produto1";

		WishList wishList = new WishList();
		wishList = createWishListExample(wishList);
		Mockito.when(wishListRepository.findById(any())).thenReturn(java.util.Optional.of(wishList));
		Mockito.when(httpServletResponse.getStatus()).thenReturn(200);

		WishList wishListFinal = new WishList();
		try{
			wishListFinal = wishListResources.getProductInWishList(httpServletResponse, wishList.getClientId(), product);
		}catch (Exception e) {}
		Assert.isTrue(wishListFinal.getProductList().contains(product), "Sucesso");
	}

	@Test
	void testGetWishListProductError(){
		String product = "produto1";

		WishList wishList = new WishList();
		wishList = createWishListExample(wishList);
		Mockito.when(wishListRepository.findById(any())).thenReturn(java.util.Optional.of(wishList));

		try{
			wishListResources.getProductInWishList(httpServletResponse, wishList.getClientId(), product);
		}catch (Exception e) {
			Assert.isTrue(e.getMessage().equals("Product not found in WishList"));
		}

	}

	private Boolean verifyProductInWishList(WishList wishList, String productId){
		return wishList.getProductList().contains(productId);
	}

	private WishList createWishListExample(WishList wishList){
		List<String> listProducts = new ArrayList<String>();
		listProducts.add("produto1");
		listProducts.add("produto2");
		listProducts.add("produto3");
		listProducts.add("produto4");

		wishList.setClientId("ClienteTeste123");
		wishList.setProductList(listProducts);
		return wishList;
	}
}

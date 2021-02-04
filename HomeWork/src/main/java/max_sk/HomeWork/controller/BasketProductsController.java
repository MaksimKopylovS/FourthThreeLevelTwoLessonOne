package max_sk.HomeWork.controller;

import lombok.RequiredArgsConstructor;
import max_sk.HomeWork.dto.ProductDTO;
import max_sk.HomeWork.services.BasketProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/basket")
@RequiredArgsConstructor
public class BasketProductsController {
    private BasketProductsService basketProductsService;

    @Autowired
    public BasketProductsController(BasketProductsService basketProductsService){
        this.basketProductsService = basketProductsService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDTO> basketProduct(@RequestBody ProductDTO productDTO) {
        basketProductsService.addProdut(productDTO);
        return basketProductsService.getProductList();
    }
    @GetMapping("/inc/{id}")
    public List<ProductDTO> incrementCount(@PathVariable Long id){
        return basketProductsService.incrementCount(id);
    }

    @GetMapping("/dec/{id}")
    public List<ProductDTO> decrimentCount(@PathVariable Long id){
        return basketProductsService.decrimentCount(id);
    }

    @GetMapping("/del/{id}")
    public List<ProductDTO> deleteProductFromBasket(@PathVariable Long id){
        return basketProductsService.deleteProductFromBasket(id);
    }

    @GetMapping("/order/{userName}")
    public void createOrder(@PathVariable String userName){

        basketProductsService.createOrder(userName);
    }
}

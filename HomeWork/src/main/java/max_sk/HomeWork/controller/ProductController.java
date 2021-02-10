package max_sk.HomeWork.controller;

import lombok.RequiredArgsConstructor;
import max_sk.HomeWork.dto.ProductDTO;
import max_sk.HomeWork.exceptions_handling.ResourceNotFoundException;
import max_sk.HomeWork.repository.specifications.ProductSpecifications;
import max_sk.HomeWork.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Page<ProductDTO> findAllProducts(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(name = "page", defaultValue = "1") Integer page
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAll(ProductSpecifications.build(params), page, 5);
    }

    @GetMapping("/{findId}")
    public ProductDTO findProductById(@PathVariable Long findId) {
        return productService.findProductById(findId).orElseThrow(()-> new ResourceNotFoundException("Product id: " + findId + " doens't exit"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO saveNewProduct(@RequestBody ProductDTO productDTO) {
        productDTO.setId(null);
        return productService.saveOrUpdate(productDTO);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO editProduct(@RequestBody ProductDTO productDTO) {
        System.out.println(productDTO.getCount() + "  " + productDTO.getTitle());
        return productService.editProduct(productDTO);
    }


    @DeleteMapping("/{productId}")
    public void deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);
    }
}

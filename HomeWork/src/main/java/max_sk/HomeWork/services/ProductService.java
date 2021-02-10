package max_sk.HomeWork.services;

import lombok.RequiredArgsConstructor;
import max_sk.HomeWork.dto.ProductDTO;
import max_sk.HomeWork.model.Product;
import max_sk.HomeWork.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<ProductDTO> findAll(Specification<Product> spec, int page, int pageSize) {
        return productRepository.findAll(spec, PageRequest.of(page - 1, pageSize)).map(ProductDTO::new);
    }

    public ProductDTO saveOrUpdate(ProductDTO productDTO) {
        productRepository.save(new Product(productDTO));
        return productDTO;
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<ProductDTO> findProductById(Long findId){
        return productRepository.findById(findId).map(ProductDTO::new);
    }

    public ProductDTO editProduct(ProductDTO productDTO){
        Product product = productRepository.getOne(productDTO.getId());
        productRepository.save(new Product(
                product.getId(),
                product.getTitle(),
                product.getCost(),
                product.getCreateAt(),
                new Date()
        ));
        return productDTO;
    }

}

package max_sk.HomeWork.repository.specifications;

import max_sk.HomeWork.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

public class ProductSpecifications {

    private static Specification<Product> idFilter(Long lon){
        return (Specification<Product>) (root, creteriaQuery, criteriaBuilder ) -> criteriaBuilder.equal(root.get("id"), lon);
    }
    private static Specification<Product> priceGreaterOrEqualsThan(int minPrice){
        return (Specification<Product>) (root, creteriaQuery, criteriaBuilder)-> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minPrice);
    }

    private static Specification<Product> priceLesserOrEqualsThan(int maxPrice){
        return (Specification<Product>) (root, criterialQuery, criterialBulder) -> criterialBulder.lessThanOrEqualTo(root.get("cost"), maxPrice);
    }

    private static Specification<Product> titleLike(String titlePart){
        return (Specification<Product>) (root, criterialQuery, criterialBulder) -> criterialBulder.like(root.get("title"), String.format("%%s%%", titlePart));
    }

    public static Specification<Product> build(MultiValueMap<String, String> params){
        Specification<Product> spec = Specification.where(null);
        if(params.containsKey("id")&& !params.getFirst("id").isBlank()){
            spec = spec.and(ProductSpecifications.idFilter(Long.parseLong(params.getFirst("id"))));
        }

        if (params.containsKey("min_cost") && !params.getFirst("min_cost").isBlank()) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(Integer.parseInt(params.getFirst("min_cost"))));
        }
        if (params.containsKey("max_cost") && !params.getFirst("max_cost").isBlank()) {
            spec = spec.and(ProductSpecifications.priceLesserOrEqualsThan(Integer.parseInt(params.getFirst("max_cost"))));
        }
        if (params.containsKey("title") && !params.getFirst("title").isBlank()) {
            spec = spec.and(ProductSpecifications.titleLike(params.getFirst("title")));
        }
        return spec;
    }

}

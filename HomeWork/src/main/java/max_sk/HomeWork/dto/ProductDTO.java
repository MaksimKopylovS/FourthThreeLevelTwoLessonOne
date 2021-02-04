package max_sk.HomeWork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import max_sk.HomeWork.model.Product;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private int cost;
    private int count;
    private int sumCost;

    public ProductDTO(Product product){
        this.id = product.getId();
        this.title = product.getTitle();
        this.cost= product.getCost();
        this.count = 1;
        this.sumCost = cost;
    }

    public void incrementCount(int count){
        this.count = count + 1;
    }

    public void decrimentCount(int count){
        this.count = count - 1;
    }

    public void toEnlageCost(int cost){
        this.sumCost = cost + this.sumCost;
    }
    public void reduceCost(int cost){
        this.sumCost = cost - this.sumCost;
    }

}

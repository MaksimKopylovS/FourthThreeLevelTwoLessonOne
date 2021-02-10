package max_sk.HomeWork.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import max_sk.HomeWork.dto.ProductDTO;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private int cost;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @CreationTimestamp
    private Date updateAt;

    @OneToMany
    @JoinTable(name = "orders",
            joinColumns = @JoinColumn(name = "id_product"))
    private Collection<Product> orderCollection;


    public Product(ProductDTO productDTO) {
        id = productDTO.getId();
        title = productDTO.getTitle();
        cost = productDTO.getCost();
    }

    public Product(Long id, String title, int cost, LocalDateTime createAt, Date updateAt) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}

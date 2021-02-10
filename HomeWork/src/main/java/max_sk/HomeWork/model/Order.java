package max_sk.HomeWork.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "id_product")
    private Long idProduct;

    @Column(name = "order_number")
    private Long orderNumber;

    @Column(name = "count")
    private int count;

    @Column(name = "sum_cost")
    private int sumCost;

    @Column(name = "adres")
    private String adres;

    @Column(name = "create_at")
    @CreationTimestamp
    private Date updateAt;

    public Order(Long id, Long idUser, Long idProduct, Long orderNumber,  int count,  int sumCost, String adres, Date date) {
        this.id = id;
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.orderNumber = orderNumber;
        this.count = count;
        this.sumCost = sumCost;
        this.adres = adres;
        this.updateAt = date;
    }
}

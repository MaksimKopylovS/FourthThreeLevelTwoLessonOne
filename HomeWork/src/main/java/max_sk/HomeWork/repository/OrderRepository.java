package max_sk.HomeWork.repository;

import max_sk.HomeWork.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>  {
}

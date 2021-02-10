package max_sk.HomeWork.services;

import lombok.RequiredArgsConstructor;
import max_sk.HomeWork.dto.OrderDTO;
import max_sk.HomeWork.repository.OrderRepository;
import max_sk.HomeWork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(scopeName = "prototype")
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    @Transactional
    public List<OrderDTO> orderDTOList(OrderDTO orderDTO){
        String query =
                " select USERS.USERNAME, PRODUCT.TITLE, ORDERS.ADRES" +
                " from ORDERS INNER join PRODUCT on ORDERS.ID_PRODUCT=PRODUCT.ID" +
                " inner join USERS  on ORDERS.ID_USER   = USERS.ID" +
                " where USERS.USERNAME ='"+ orderDTO.getUserName()+"'"
                ;
        List<Object[]> list = entityManager.createNativeQuery(query).getResultList();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Object[] objects : list){
            orderDTOList.add(new OrderDTO((String) objects[0], (String) objects[1], (String) objects[2]));
        }
        return orderDTOList;

    }
}

package max_sk.HomeWork.services;

import lombok.RequiredArgsConstructor;
import max_sk.HomeWork.dto.OrderDTO;
import max_sk.HomeWork.dto.ProductDTO;
import max_sk.HomeWork.model.Order;
import max_sk.HomeWork.model.User;
import max_sk.HomeWork.repository.OrderRepository;
import max_sk.HomeWork.repository.ProductRepository;
import max_sk.HomeWork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(scopeName = "prototype")
@RequiredArgsConstructor
public class BasketProductsService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private List<ProductDTO> basketList;
    private final EntityManager entityManager;
    private static Long orderNumber;

    @PostConstruct
    public void init(){
        basketList = new ArrayList<>();
        orderNumber = 1L;
    }

    public void addProdut(ProductDTO productDTO){
        for (int i = 0; i < basketList.size(); i++){
            if(basketList.get(i).getId() == productDTO.getId()){
                productDTO.incrementCount(basketList.get(i).getCount());
                productDTO.toEnlageCost(basketList.get(i).getSumCost());
                basketList.set(i, productDTO);
                return;
            }
        }
        basketList.add(productDTO);
    }

    public List<ProductDTO> getProductList(){
        return basketList;
    }

    public List<ProductDTO> incrementCount(Long id){
        ProductDTO productDTO = new ProductDTO(productRepository.getOne(id));
        for (int i = 0; i < basketList.size(); i++) {
            if (basketList.get(i).getId() == productDTO.getId()) {
                productDTO.incrementCount(basketList.get(i).getCount());
                productDTO.toEnlageCost(basketList.get(i).getSumCost());
                basketList.set(i, productDTO);
                return basketList;
            }
        }
        return basketList;
    }

    public List<ProductDTO> decrimentCount(Long id){
        ProductDTO productDTO = new ProductDTO(productRepository.getOne(id));
        for (int i = 0; i < basketList.size(); i++) {
            if (basketList.get(i).getId() == productDTO.getId()) {
                if(basketList.get(i).getCount() <= 0 & basketList.get(i).getSumCost() <= 0 ){
                    return basketList;
                }
                productDTO.decrimentCount(basketList.get(i).getCount());
                productDTO.reduceCost(basketList.get(i).getSumCost());
                basketList.set(i, productDTO);
                return basketList;
            }
        }
        return basketList;
    }
    public List<ProductDTO> deleteProductFromBasket(Long id){
        ProductDTO productDTO = new ProductDTO(productRepository.getOne(id));
        for (int i = 0; i < basketList.size(); i++) {
            if (basketList.get(i).getId() == productDTO.getId()) {
                 basketList.remove(i);
                return basketList;
            }
        }
        return basketList;
    }

    @Transactional
    public void createOrder(OrderDTO orderDTO){
        User user = userRepository.findByUsername(orderDTO.getUserName()).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", orderDTO.getUserName())));
        for (ProductDTO productDTO : basketList){
            entityManager.createNativeQuery("insert into orders(id_user, id_product, order_number, count, adres, sum_cost) values(:a,:b,:c,:d,:e,:f)")
                    .setParameter("a", user.getId() )
                    .setParameter("b", productDTO.getId())
                    .setParameter("c", orderNumber )
                    .setParameter("d", productDTO.getCount())
                    .setParameter("e", orderDTO.getAddres())
                    .setParameter("f", productDTO.getSumCost())
                    .executeUpdate();
        }
        basketList.clear();
        orderNumber++;
    }
}

package lab28.group4.asm1.repositories;

import lab28.group4.asm1.models.CartItem;
import lab28.group4.asm1.models.MenuItem;
import lab28.group4.asm1.models.Order;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends ListCrudRepository<CartItem, Long> {
    Optional<CartItem> findCartItemByMenuItem(MenuItem menuItem);

    List<CartItem> findCartItemsByOrder(Order order);
}
package lab28.group4.asm1.repositories;

import lab28.group4.asm1.models.MenuItem;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends ListCrudRepository<MenuItem, Long> {
}

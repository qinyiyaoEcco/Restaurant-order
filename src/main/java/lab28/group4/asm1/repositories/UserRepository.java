package lab28.group4.asm1.repositories;

import lab28.group4.asm1.Role;
import lab28.group4.asm1.models.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByRole(Role role);
}

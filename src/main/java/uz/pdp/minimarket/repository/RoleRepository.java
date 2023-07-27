package uz.pdp.minimarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.minimarket.entity.Role;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
    Optional<Role> findById(Integer id);
}

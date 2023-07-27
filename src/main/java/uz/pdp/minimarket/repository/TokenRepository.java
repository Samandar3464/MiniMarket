package uz.pdp.minimarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.minimarket.entity.FireBaseToken;

public interface TokenRepository extends JpaRepository<FireBaseToken, Integer> {
}

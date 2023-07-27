package uz.pdp.minimarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.minimarket.entity.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumberAndVerificationCode(String phoneNumber, Integer verificationCode);

    boolean existsByPhoneNumber(String phoneNumber);


}

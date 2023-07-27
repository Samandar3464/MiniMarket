package uz.pdp.minimarket.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.minimarket.entity.Measurement;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {

    List<Measurement> findAllByActiveTrue();

    boolean existsByNameAndActiveTrue(String name);
}

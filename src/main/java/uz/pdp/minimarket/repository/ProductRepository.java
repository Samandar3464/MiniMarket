package uz.pdp.minimarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.minimarket.entity.Category;
import uz.pdp.minimarket.entity.Market;
import uz.pdp.minimarket.entity.Product;


import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllByMarketIdAndActiveTrueAndDeletedFalseOrderByQuantityDesc(Integer marketId, Pageable pageable);

    Page<Product> findAllByQuantityGreaterThanAndMarketIdAndActiveTrueAndDeletedFalse(double quantity, Integer marketId, Pageable pageable);

    Page<Product> findAllByQuantityGreaterThanAndActiveTrueAndDeletedFalseOrderByCreatedDateDesc(double quantity, Pageable pageable);

    Page<Product> findAllByQuantityGreaterThanAndMarketInAndActiveTrueAndDeletedFalseOrderByCreatedDateDesc(double quantity, List<Market> activeMarkets, Pageable pageable);

    Page<Product> findAllByQuantityGreaterThanAndActiveFalseAndDeletedFalse(double quantity, Pageable pageable);

    boolean existsByMarketIdAndMeasurementIdAndName(Integer marketId, Integer measurementId, String name);

    Page<Product> findAllByCategoryInAndQuantityGreaterThanAndActiveTrueAndDeletedFalse(List<Category> categories, double quantity, Pageable pageable);


}

package com.petrov.persist;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> , JpaSpecificationExecutor<Product> {


    @Query("select p from Product p where (p.name like concat(:prefix, '%') or :prefix is null) and" +
            "(p.cost >= :minPrice or :minPrice is null) and" +
            "(p.cost <= :maxPrice or :maxPrice is null)")
    List<Product> filterProducts (
            @Param("prefix") String prefix,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice
    );
//
}


package com.petrov.persist;


import com.petrov.persist.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {


//    @Query("select p from Product p left join fetch p.category left join fetch p.brand" +
//            " where (p.name like concat(:prefix, '%') or :prefix is null) and" +
//            "(p.cost >= :minPrice or :minPrice is null) and" +
//            "(p.cost <= :maxPrice or :maxPrice is null)",
//            countQuery = "select count (p) from Product p")
//    Page<Product> findAll(
//            @Param("prefix") String prefix,
//            @Param("minPrice") BigDecimal minPrice,
//            @Param("maxPrice") BigDecimal maxPrice,
//            Pageable var2
//    );

    @Query(value = "select p from Product p " +
            "left join fetch p.category " +
            "left join fetch p.brand " +
            "left join fetch p.pictures",
            countQuery = "select count (p) from Product p")
    Page<Product> findAll(Specification<Product> spec, Pageable var2);
}


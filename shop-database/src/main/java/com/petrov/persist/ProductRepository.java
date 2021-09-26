package com.petrov.persist;

import com.petrov.persist.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

//    @Query(value = "select p from Product p " +
//            "left join fetch p.category " +
//            "left join fetch p.brand " +
//            "left join fetch p.pictures",
//            countQuery = "select count (p) from Product p")
    @EntityGraph("product-category-brand")
    Page<Product> findAll(Specification<Product> spec, Pageable var2);
}


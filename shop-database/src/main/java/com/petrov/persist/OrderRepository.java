package com.petrov.persist;

import com.petrov.persist.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("select o " +
            "from Order o " +
            "inner join o.user u " +
            "where u.username = :username")
    List<Order> findAllByUsername(String username);

}

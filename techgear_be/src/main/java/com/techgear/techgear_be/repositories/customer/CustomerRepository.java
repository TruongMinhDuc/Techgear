package com.techgear.techgear_be.repositories.customer;

import com.techgear.techgear_be.models.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    @Query("SELECT COUNT(c.id) FROM Customer c")
    int countByCustomerId();

}
